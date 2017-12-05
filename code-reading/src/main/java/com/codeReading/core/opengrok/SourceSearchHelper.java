package com.codeReading.core.opengrok;

import java.io.File;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;

import org.opensolaris.opengrok.configuration.Project;
import org.opensolaris.opengrok.configuration.RuntimeEnvironment;
import org.opensolaris.opengrok.search.QueryBuilder;


public class SourceSearchHelper {
	boolean check4on = true;
	private RuntimeEnvironment env;
	private SourceSearchBean searchBean; // customize by tq
	//private SourceReadingBean readingBean; // customize by tq
	//private Configuration config; // customize by tq
	private EftarFileReader eftarReader;
	private String sourceRootPath;
	private Prefix prefix;
	private SortedSet<String> requestedProjects;
	private QueryBuilder queryBuilder;
	private File dataRoot;
	private String requestedProjectsString;
	private String pageTitle;
	 
	private static final Logger log = Logger.getLogger(SourceSearchHelper.class.getName());

	
    /**
     * Get the current runtime environment.
     *
     * @return the runtime env.
     * @see RuntimeEnvironment#getInstance()
     * @see RuntimeEnvironment#register()
     */
    public RuntimeEnvironment getEnv() {
        if (env == null) {
            env = RuntimeEnvironment.getInstance().register();
        }
        return env;
    }
    
	/**
	 * 获得请求地址的前缀，如 /xref,/search等 Get the prefix for
	 * the related request.
	 * 
	 * @return {@link Prefix#UNKNOWN} if the servlet path matches any known
	 *         prefix, the prefix otherwise.
	 */
	public Prefix getPrefix() {
		if (prefix == null) {
			// prefix = Prefix.get(req.getServletPath());
			prefix = Prefix.get(searchBean.getRequest().getServletPath());
		}
		return prefix;
	}
	
	/**
	 * Get the eftar reader for the data directory. If it has been already
	 * opened and not closed, this instance gets returned. One should not close
	 * it once used: {@link #cleanup(ServletRequest)} takes care to close it.
	 * 
	 * @return {@code null} if a reader can't be established, the reader
	 *         otherwise.
	 */
	public EftarFileReader getEftarReader() {
		if (eftarReader == null || eftarReader.isClosed()) {
			File f = getEnv().getConfiguration().getDtagsEftar();
			if (f == null) {
				eftarReader = null;
			} else {
				try {
					eftarReader = new EftarFileReader(f);
				} catch (Exception e) {
					log.log(Level.FINE, "Failed to create EftarFileReader: ", e);
				}
			}
		}
		return eftarReader;
	}
	
	/**
	 * Get the canonical path to root of the source tree. File separators are
	 * replaced with a '/'.
	 * 
	 * @return The on disk source root directory.
	 * @see RuntimeEnvironment#getSourceRootPath()
	 */
	public String getSourceRootPath() {
		if (sourceRootPath == null) {
			// String srcpath = getEnv().getSourceRootPath();
			String srcpath = getEnv().getSourceRootPath();
			if (srcpath != null) {
				sourceRootPath = srcpath.replace(File.separatorChar, '/');
			}
		}
		return sourceRootPath;
	}
	
	private static Pattern COMMA_PATTERN = Pattern.compile(",");
	private static void splitByComma(String value, List<String> result) {
		if (value == null || value.length() == 0) {
			return;
		}
		String p[] = COMMA_PATTERN.split(value);
		for (int k = 0; k < p.length; k++) {
			if (p[k].length() != 0) {
				result.add(p[k]);
			}
		}
	}
	/**
	 * Get the cookie values for the given name. Splits comma separated values
	 * automatically into a list of Strings.
	 * 
	 * @param cookieName
	 *            name of the cookie.
	 * @return a possible empty list.
	 */
	public List<String> getCookieVals(String cookieName) {
		Cookie[] cookies = searchBean.getRequest().getCookies();
		ArrayList<String> res = new ArrayList<String>();
		if (cookies != null) {
			for (int i = cookies.length - 1; i >= 0; i--) {
				if (cookies[i].getName().equals(cookieName)) {
					splitByComma(cookies[i].getValue(), res);
				}
			}
		}
		return res;
	}
	
	/**
	 * Get sort orders from the request parameter {@code sort} and if this list
	 * would be empty from the cookie {@code OpenGrokorting}.
	 * 
	 * @return a possible empty list which contains the sort order values in the
	 *         same order supplied by the request parameter or cookie(s).
	 */
	public List<SortOrder> getSortOrder() {
		List<SortOrder> sort = new ArrayList<SortOrder>();
		List<String> vals = searchBean.getSortkeywords();
		for (String s : vals) {
			SortOrder so = SortOrder.get(s);
			if (so != null) {
				sort.add(so);
			}
		}
		if (sort.isEmpty()) {
			vals = getCookieVals("OpenGrokSorting");
			for (String s : vals) {
				SortOrder so = SortOrder.get(s);
				if (so != null) {
					sort.add(so);
				}
			}
		}
		return sort;
	}
	
	/**
	 * Get opengrok's configured dataroot directory. It is verified, that the
	 * used environment has a valid opengrok data root set and that it is an
	 * accessible directory.
	 * 
	 * @return the opengrok data directory.
	 * @throws InvalidParameterException
	 *             if inaccessible or not set.
	 */
	public File getDataRoot() {
		if (dataRoot == null) {
			String tmp = getEnv().getDataRootPath();
			if (tmp == null || tmp.length() == 0) {
				throw new InvalidParameterException("dataRoot parameter is not " + "set in configuration.xml!");
			}
			dataRoot = new File(tmp);
			if (!(dataRoot.isDirectory() && dataRoot.canRead())) {
				throw new InvalidParameterException("The configured dataRoot '" + tmp
						+ "' refers to a none-existing or unreadable directory!");
			}
		}
		return dataRoot;
	}
	/**
	 * Get a reference to a set of requested projects via request parameter
	 * {@code project} or cookies or defaults.
	 * <p>
	 * NOTE: This method assumes, that project names do <b>not</b> contain a
	 * comma (','), since this character is used as name separator!
	 * 
	 * @return a possible empty set of project names aka descriptions but never
	 *         {@code null}. It is determined as follows:
	 *         <ol>
	 *         <li>If there is no project in the runtime environment (RTE) an
	 *         empty set is returned. Otherwise:</li> <li>If there is only one
	 *         project in the RTE, this one gets returned (no matter, what the
	 *         request actually says). Otherwise</li> <li>If the request
	 *         parameter {@code project} contains any available project, the set
	 *         with invalid projects removed gets returned. Otherwise:</li> <li>
	 *         If the request has a cookie with the name {@code OpenGrokProject}
	 *         and it contains any available project, the set with invalid
	 *         projects removed gets returned. Otherwise:</li> <li>If a default
	 *         project is set in the RTE, this project gets returned. Otherwise:
	 *         </li> <li>an empty set</li>
	 *         </ol>
	 */
	public SortedSet<String> getRequestedProjects() {
		if (requestedProjects == null) {
			//requestedProjects = getRequestedProjects("project", "OpenGrokProject");
			requestedProjects = searchBean.getProjects();
			if(null==requestedProjects || 0==requestedProjects.size()){
				requestedProjects = getRequestedProjects("project", "OpenGrokProject");
			}
		}
		return requestedProjects;
	}
	
	/**
	 * Get a reference to the {@code QueryBuilder} wrt. to the current request
	 * parameters:
	 * <dl>
	 * <dt>q</dt>
	 * <dd>freetext lookup rules</dd>
	 * <dt>defs</dt>
	 * <dd>definitions lookup rules</dd>
	 * <dt>path</dt>
	 * <dd>path related rules</dd>
	 * <dt>hist</dt>
	 * <dd>history related rules</dd>
	 * </dl>
	 * 
	 * @return a query builder with all relevant fields populated.
	 */
	public QueryBuilder getQueryBuilder() {
		if (queryBuilder == null) {
			queryBuilder = new QueryBuilder().setFreetext(searchBean.getQ()).setDefs(searchBean.getDefs())
					.setRefs(searchBean.getRefs()).setPath(searchBean.getPath()).setHist(searchBean.getHist())
					.setType(searchBean.getType());

			// This is for backward compatibility with links created by OpenGrok
			// 0.8.x and earlier. We used to concatenate the entire query into a
			// single string and send it in the t parameter. If we get such a
			// link, just add it to the freetext field, and we'll get the old
			// behaviour. We can probably remove this code in the first feature
			// release after 0.9.
			String t = searchBean.getT();
			if (t != null) {
				queryBuilder.setFreetext(t);
			}
		}
		return queryBuilder;
	}
	
	/**
	 * Get the <b>start</b> index for a search result to return by looking up
	 * the {@code start} request parameter.
	 * 
	 * @return 0 if the corresponding start parameter is not set or not a
	 *         number, the number found otherwise.
	 */
	public int getSearchStart() {
		return searchBean.getStart();
	}
	
	/**
	 * Get the number of search results to max. return by looking up the
	 * {@code n} request parameter.
	 * 
	 * @return the default number of hits if the corresponding start parameter
	 *         is not set or not a number, the number found otherwise.
	 */
	public int getSearchMaxItems() {
		Integer n = searchBean.getN();
		if (null == n)
			n = getEnv().getHitsPerPage();
		return n;
	}
	
	public SearchHelper prepareSearch() {
		SearchHelper sh = new SearchHelper();
		sh.dataRoot = getDataRoot(); // throws Exception if none-existent
		List<SortOrder> sortOrders = getSortOrder();
		sh.order = sortOrders.isEmpty() ? SortOrder.RELEVANCY : sortOrders.get(0);
		List<Project> proj = getEnv().getProjects();
		boolean hasProjects = (proj != null && !proj.isEmpty());
		if (getRequestedProjects().isEmpty() && hasProjects) {
			sh.errorMsg = "You must select a project!";
			return sh;
		}
		sh.builder = getQueryBuilder();
		if (sh.builder.getSize() == 0) {
			// Entry page show the map
			sh.redirect = searchBean.getRequest().getContextPath() + '/';
			return sh;
		}
		
		sh.start = getSearchStart();
		sh.maxItems = getSearchMaxItems();
		sh.contextPath = searchBean.getRequest().getContextPath();
		// jel: this should be IMHO a config param since not only core dependend
		sh.parallel = Runtime.getRuntime().availableProcessors() > 1;
		sh.isCrossRefSearch = getPrefix() == Prefix.SEARCH_R;
		sh.compressed = getEnv().isCompressXref();
		sh.desc = getEftarReader();
		sh.sourceRoot = new File(getSourceRootPath());
		return sh;
	}

	  /**
     * Same as {@link #getRequestedProjects()}, but with a variable cookieName
     * and parameter name. This way it is trivial to implement a project filter
     * ...
     *
     * @param paramName the name of the request parameter, which possibly
     * contains the project list in question.
     * @param cookieName name of the cookie which possible contains project
     * lists used as fallback
     * @return a possible empty set but never {@code null}.
     */
    protected SortedSet<String> getRequestedProjects(String paramName,
            String cookieName) {
    	TreeSet<String> set = new TreeSet<String>();
		List<Project> projects = getEnv().getProjects();
		if (projects == null) {
			return set;
		}
		if (projects.size() == 1) {
			set.add(projects.get(0).getDescription());
			return set;
		}
		if (set.isEmpty()) {
			List<String> cookies = getCookieVals(cookieName);
			for (String s : cookies) {
				if (Project.getByDescription(s) != null) {
					set.add(s);
				}
			}
		}
		if (set.isEmpty()) {
			Project defaultProject = getEnv().getDefaultProject();
			if (defaultProject != null) {
				set.add(defaultProject.getDescription());
			}
		}
		return set;
    }
    
    /**
     * Same as {@link #getRequestedProjects()} but returns the project names as
     * a coma separated String.
     *
     * @return a possible empty String but never {@code null}.
     */
    public String getRequestedProjectsAsString() {
        if (requestedProjectsString == null) {
            Set<String> projects = getRequestedProjects();
            if (projects.isEmpty()) {
                requestedProjectsString = "";
            } else {
                StringBuilder buf = new StringBuilder();
                for (String name : projects) {
                    buf.append(name).append(',');
                }
                buf.setLength(buf.length() - 1);
                requestedProjectsString = buf.toString();
            }
        }
        return requestedProjectsString;
    }
    /**
     * Set the page title to use.
     *
     * @param title title to set (might be {@code null}).
     */
    public void setTitle(String title) {
        pageTitle = title;
    }

    /**
     * Get the page title to use.
     *
     * @return {@code null} if not set, the page title otherwise.
     */
    public String getTitle() {
        return pageTitle;
    }
	public SourceSearchHelper(SourceSearchBean searchBean) {
		this.searchBean = searchBean;
	}
}
