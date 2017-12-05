package com.codeReading.core.opengrok;

import java.io.File;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.opensolaris.opengrok.configuration.Project;
import org.opensolaris.opengrok.configuration.RuntimeEnvironment;
import org.opensolaris.opengrok.history.Annotation;
import org.opensolaris.opengrok.history.HistoryGuru;
import org.opensolaris.opengrok.index.IgnoredNames;




import com.codeReading.core.util.FileUtil;

public class SourceReadingHelper {
	boolean check4on = true;
	private RuntimeEnvironment env;
	private SourceReadingBean readingBean; // customize by tq
	//private Configuration config; // customize by tq

	private IgnoredNames ignoredNames;
	private String path;
	private File resourceFile;
	private String resourcePath;
	private EftarFileReader eftarReader;
	private String sourceRootPath;
	private Boolean isDir;
	private String uriEncodedPath;
	private Prefix prefix;
	private String pageTitle;
	private String dtag;
	private String rev;
	private Boolean hasAnnotation;
	private Boolean annotate;
	private Annotation annotation;
	private Boolean hasHistory;
	private List<String> dirFileList;
	private File dataRoot;
	private StringBuilder headLines;
	private SortedSet<String> requestedProjects;
	private List<Navigation> navigations;
	private String requestedProjectsString;
	private static final Logger log = Logger.getLogger(SourceReadingHelper.class.getName());

	
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
	 * Add the given data to the &lt;head&gt; section of the html page to
	 * generate.
	 * 
	 * @param data
	 *            data to add. It is copied as is, so remember to escape special
	 *            characters ...
	 */
	public void addHeaderData(String data) {
		if (data == null || data.length() == 0) {
			return;
		}
		if (headLines == null) {
			headLines = new StringBuilder();
		}
		headLines.append(data);
	}

	/**
	 * Get addition data, which should be added as is to the &lt;head&gt;
	 * section of the html page.
	 * 
	 * @return an empty string if nothing to add, the data otherwise.
	 */
	public String getHeaderData() {
		return headLines == null ? "" : headLines.toString();
	}

	/**
	 * Check, whether the request contains minimal information required to
	 * produce a valid page. If this method returns an empty string, the
	 * referred file or directory actually exists below the source root
	 * directory and is readable.
	 * 
	 * @return {@code null} if the referred src file, directory or history is
	 *         not available, an empty String if further processing is ok and a
	 *         non-empty string which contains the URI encoded redirect path if
	 *         the request should be redirected.
	 * @see #resourceNotAvailable()
	 * @see #getOnRedirect()
	 * @see #getDirectoryRedirect()
	 */
	public String canProcess() {
		if (resourceNotAvailable()) {
			return getOnRedirect();
		}
		String redir = getDirectoryRedirect();
		if (redir == null && getPrefix() == Prefix.HIST_L && !hasHistory()) {
			return null;
		}
		// jel: outfactored from list.jsp - seems to be bogus
		if (isDir()) {
			if (getPrefix() == Prefix.XREF_P) {
				if (getResourceFileList().isEmpty() && !getRequestedRevision().isEmpty() && !hasHistory()) {
					return null;
				}
			} else if ((getPrefix() == Prefix.RAW_P) || (getPrefix() == Prefix.DOWNLOAD_P)) {
				return null;
			}
		}
		return redir == null ? "" : redir;
	}

	/**
	 * 获得一个目录下的文件列表 Get a list of filenames in the requested path.
	 * 
	 * @return an empty list, if the resource does not exist, is not a directory
	 *         or an error occurred when reading it, otherwise a list of
	 *         filenames in that directory, sorted alphabetically
	 * @see #getResourceFile()
	 * @see #isDir()
	 */
	public List<String> getResourceFileList() {
		if (dirFileList == null) {
			if (isDir() && getResourcePath().length() > 1) {
				File[] files = FileUtil.listSortedFiles(getResourceFile());
				if(null == files){
					dirFileList = Collections.emptyList();
				}else{
					dirFileList = new ArrayList<String>(files.length);
					for(File file:files){
						dirFileList.add(file.getName());
					}
				}
			}
		}
		return dirFileList;
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
	 * Get the definition tag for the request related file or directory.
	 * 
	 * @return an empty string if not found, the tag otherwise.
	 */
	public String getDefineTagsIndex() {
		if (dtag != null) {
			return dtag;
		}
		getEftarReader();
		if (eftarReader != null) {
			try {
				dtag = eftarReader.get(getPath());
				// cfg.getPrefix() != Prefix.XREF_S) {
			} catch (IOException e) {
				log.log(Level.INFO, "Failed to get entry from eftar reader: ", e);
			}
		}
		if (dtag == null) {
			dtag = "";
		}
		return dtag;
	}

	/**
	 * Get the revision parameter {@code r} from the request.
	 * 
	 * @return {@code "r=<i>revision</i>"} if found, an empty string otherwise.
	 */
	public String getRequestedRevision() {
		if (rev == null) {
			String tmp = readingBean.getR();
			rev = (tmp != null && tmp.length() > 0) ? "r=" + tmp : "";
		}
		return rev;
	}

	/**
	 * Check, whether the request related resource has history information.
	 * 
	 * @return {@code true} if history is available.
	 * @see HistoryGuru#hasHistory(File)
	 */
	public boolean hasHistory() {
		if (hasHistory == null) {
			hasHistory = Boolean.valueOf(HistoryGuru.getInstance().hasHistory(getResourceFile()));
		}
		return hasHistory.booleanValue();
	}

	/**
	 * Check, whether annotations are available for the related resource.
	 * 
	 * @return {@code true} if annotations are available.
	 */
	public boolean hasAnnotations() {
		if (hasAnnotation == null) {
			hasAnnotation = Boolean.valueOf(!isDir() && HistoryGuru.getInstance().hasHistory(getResourceFile()));
		}
		return hasAnnotation.booleanValue();
	}

	/**
	 * Check, whether the resource to show should be annotated.
	 * 
	 * @return {@code true} if annotation is desired and available.
	 */
	public boolean annotate() {
		if (annotate == null) {
			annotate = Boolean.valueOf(hasAnnotations() && Boolean.parseBoolean(readingBean.getA()));
		}
		return annotate.booleanValue();
	}

	/**
	 * Get the annotation for the requested resource.
	 * 
	 * @return {@code null} if not available or annotation was not requested,
	 *         the cached annotation otherwise.
	 */
	public Annotation getAnnotation() {
		if (isDir() || getResourcePath().equals("/") || !annotate()) {
			return null;
		}
		if (annotation != null) {
			return annotation;
		}
		getRequestedRevision();
		try {
			annotation = HistoryGuru.getInstance().annotate(getResourceFile(), rev.isEmpty() ? null : rev.substring(2));
		} catch (IOException e) {
			log.log(Level.WARNING, "Failed to get annotations: ", e);
			/* ignore */
		}
		return annotation;
	}

	/**
	 * Get the name which should be show as "Crossfile"
	 * 
	 * @return the name of the related file or directory.
	 */
	public String getCrossFilename() {
		return getResourceFile().getName();
	}

	/**
	 * Get the {@code path} parameter and display value for "Search only in"
	 * option.
	 * 
	 * @return always an array of 3 fields, whereby field[0] contains the path
	 *         value to use (starts and ends always with a '/'). Field[1] the
	 *         contains string to show in the UI. field[2] is set to
	 *         {@code disabled=""} if the current path is the "/" directory,
	 *         otherwise set to an empty string.
	 */
	public String[] getSearchOnlyIn() {
		if (isDir()) {
			return getPath().length() == 0 ? new String[] { "/", "this directory", "disabled=\"\"" } : new String[] { getPath(),
					"this directory", "" };
		}
		String[] res = new String[3];
		res[0] = getPath().substring(0, getPath().lastIndexOf('/') + 1);
		res[1] = res[0];
		res[2] = "";
		return res;
	}

	/**
	 * Get the project {@link #getPath()} refers to.
	 * 
	 * @return {@code null} if not available, the project otherwise.
	 */
	public Project getProject() {
		return Project.getProject(getResourceFile());
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
	 * Get the document hash provided by the request parameter {@code h}.
	 * 
	 * @return {@code null} if the request does not contain such a parameter,
	 *         its value otherwise.
	 */
	public String getDocumentHash() {
		return readingBean.getH();
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
		Cookie[] cookies = readingBean.getRequest().getCookies();
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
	 * Get the parameter values for the given name. Splits comma separated
	 * values automatically into a list of Strings.
	 * 
	 * @param name
	 *            name of the parameter.
	 * @return a possible empty list.
	 */
	private List<String> getParamVals(String paramName) {
		String vals[] = readingBean.getRequest().getParameterValues(paramName);
		List<String> res = new ArrayList<String>();
		if (vals != null) {
			for (int i = vals.length - 1; i >= 0; i--) {
				splitByComma(vals[i], res);
			}
		}
		return res;
	}
	  /**
     * Get a reference to a set of requested projects via request parameter
     * {@code project} or cookies or defaults. <p> NOTE: This method assumes,
     * that project names do <b>not</b> contain a comma (','), since this
     * character is used as name separator!
     *
     * @return a possible empty set of project names aka descriptions but never
     * {@code null}. It is determined as follows: <ol> <li>If there is no
     * project in the runtime environment (RTE) an empty set is returned.
     * Otherwise:</li> <li>If there is only one project in the RTE, this one
     * gets returned (no matter, what the request actually says). Otherwise</li>
     * <li>If the request parameter {@code project} contains any available
     * project, the set with invalid projects removed gets returned.
     * Otherwise:</li> <li>If the request has a cookie with the name
     * {@code OpenGrokProject} and it contains any available project, the set
     * with invalid projects removed gets returned. Otherwise:</li> <li>If a
     * default project is set in the RTE, this project gets returned.
     * Otherwise:</li> <li>an empty set</li> </ol>
     */
    public SortedSet<String> getRequestedProjects() {
        if (requestedProjects == null) {
            requestedProjects =
                    getRequestedProjects("project", "OpenGrokProject");
        }
        return requestedProjects;
    }
	/**
	 * Same as {@link #getRequestedProjects()}, but with a variable cookieName
	 * and parameter name. This way it is trivial to implement a project filter
	 * ...
	 * 
	 * @param paramName
	 *            the name of the request parameter, which possibly contains the
	 *            project list in question.
	 * @param cookieName
	 *            name of the cookie which possible contains project lists used
	 *            as fallback
	 * @return a possible empty set but never {@code null}.
	 */
	protected SortedSet<String> getRequestedProjects(String paramName, String cookieName) {
		TreeSet<String> set = new TreeSet<String>();
		List<Project> projects = getEnv().getProjects();
		if (projects == null) {
			return set;
		}
		if (projects.size() == 1) {
			set.add(projects.get(0).getDescription());
			return set;
		}
		List<String> vals = getParamVals(paramName);
		for (String s : vals) {
			if (Project.getByDescription(s) != null) {
				set.add(s);
			}
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
	 * Set the page title to use.
	 * 
	 * @param title
	 *            title to set (might be {@code null}).
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

	/**
	 * 代码皮肤（样式）选择 Get the base path to use to refer to CSS stylesheets and
	 * related resources. Usually used to create links.
	 * 
	 * @return the appropriate application directory prefixed with the
	 *         application's context path (e.g. "/source/default").
	 * @see HttpServletRequest#getContextPath()
	 * @see RuntimeEnvironment#getWebappLAF()
	 */
	public String getCssDir() {
		return readingBean.getRequest().getContextPath() + '/' + getEnv().getWebappLAF();
	}

	/**
	 * Get the current runtime environment.
	 * 
	 * @return the runtime env.
	 * @see RuntimeEnvironment#getInstance()
	 * @see RuntimeEnvironment#register()
	 */
	// public RuntimeEnvironment getEnv() {
	// if (env == null) {
	// env = RuntimeEnvironment.getInstance().register();
	// }
	// return env;
	// }

	/**
	 * Get the name patterns used to determine, whether a file should be
	 * ignored.
	 * 
	 * @return the corresponding value from the current runtime config..
	 */
	public IgnoredNames getIgnoredNames() {
		if (ignoredNames == null) {
			ignoredNames = getEnv().getIgnoredNames();
		}
		return ignoredNames;
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
			prefix = Prefix.get(readingBean.getRequest().getServletPath());
		}
		return prefix;
	}

	/**
	 * Get the canonical path of the related resource
	 * relative to the source root directory (used file separators are all '/').
	 * No check is made, whether the obtained path is really an accessible
	 * resource on disk.
	 * 
	 * @see HttpServletRequest#getServletPath()
	 * @return a possible empty String (denotes the source root directory) but
	 *         not {@code null}.
	 */
	public String getPath() {
		if (path == null) {
			//spring 框架下如下格式'/xref/src/gogogo/main.cc' tq
			String servletPath = readingBean.getRequest().getServletPath();	
			if (servletPath == null || servletPath.charAt(0) != '/') {
				return "/";
			}
			//去掉第一个前缀 如'/xref' tq
			int idx = servletPath.indexOf('/', 1);
			String resourcepath = (idx == -1) ? "/" : servletPath.substring(idx, servletPath.length());

			path = Util.getCanonicalPath(resourcepath, '/');
			if ("/".equals(path)) {
				path = "";
			}
		}
		return path;
	}

	/**
	 * If a requested resource is not available,
	 * append "/on/" to the source root directory and try again to resolve it.
	 * @see SourceReadingBean#getServletPath
	 * @return on success a none-{@code null} gets returned, which should be
	 *         used to redirect the client to the propper path.
	 */
	public String getOnRedirect() {
		if (check4on) {
			File newFile = new File(getSourceRootPath() + "/on/" + getPath());
			if (newFile.canRead()) {
				return readingBean.getRequest().getContextPath() + readingBean.getServletPath() + "/on"
						+ getUriEncodedPath() + (newFile.isDirectory() ? trailingSlash(getPath()) : "");
			}
		}
		return null;
	}

	/**
	 * Get the on disk file to the request related file or directory.
	 * 
	 * NOTE: If a repository contains hard or symbolic links, the returned file
	 * may finally point to a file outside of the source root directory.
	 * 
	 * @return {@code new File("/")} if the related file or directory is not
	 *         available (can not be find below the source root directory), the
	 *         readable file or directory otherwise.
	 * @see #getSourceRootPath()
	 * @see #getPath()
	 */
	public File getResourceFile() {
		if (resourceFile == null) {
			resourceFile = new File(getSourceRootPath(), getPath());
			if (!resourceFile.canRead()) {//FIXME sometims cannot read
				resourceFile = new File("/");
			}
		}
		return resourceFile;
	}


	/**
	 * Get the canonical on disk path to the request related file or directory
	 * with all file separators replaced by a '/'.
	 * 
	 * @return "/" if the evaluated path is invalid or outside the source root
	 *         directory), otherwise the path to the readable file or directory.
	 * @see #getResourceFile()
	 */
	public String getResourcePath() {
		if (resourcePath == null) {
			resourcePath = getResourceFile().getPath().replace(File.separatorChar, '/');
		}
		return resourcePath;
	}

	/**
	 * Check, whether the related request resource matches a valid file or
	 * directory below the source root directory and wether it matches an
	 * ignored pattern.
	 * 
	 * @return {@code true} if the related resource does not exists or should be
	 *         ignored.
	 * @see #getIgnoredNames()
	 * @see #getResourcePath()
	 */
	public boolean resourceNotAvailable() {
		getIgnoredNames();
		return getResourcePath().equals("/") || ignoredNames.ignore(getPath())
				|| ignoredNames.ignore(getResourceFile().getParentFile().getName()) || ignoredNames.ignore(getResourceFile());
	}

	/**
	 * Check, whether the request related path represents a directory.
	 * 
	 * @return {@code true} if directory related request
	 */
	public boolean isDir() {
		if (isDir == null) {
			isDir = Boolean.valueOf(getResourceFile().isDirectory());
		}
		return isDir.booleanValue();
	}

	private static String trailingSlash(String path) {
		return path.length() == 0 || path.charAt(path.length() - 1) != '/' ? "/" : "";
	}

	private File checkFile(File dir, String name, boolean compressed) {
		File f;
		if (compressed) {
			f = new File(dir, name + ".gz");
			if (f.exists() && f.isFile() && f.lastModified() >= getResourceFile().lastModified()) {
				return f;
			}
		}
		f = new File(dir, name);
		if (f.exists() && f.isFile() && f.lastModified() >= getResourceFile().lastModified()) {
			return f;
		}
		return null;
	}

	private File checkFileResolve(File dir, String name, boolean compressed) {
		File lresourceFile = new File(getSourceRootPath() + getPath(), name);
		if (!lresourceFile.canRead()) {
			lresourceFile = new File("/");
		}
		File f;
		if (compressed) {
			f = new File(dir, name + ".gz");
			if (f.exists() && f.isFile() && f.lastModified() >= lresourceFile.lastModified()) {
				return f;
			}
		}
		f = new File(dir, name);
		if (f.exists() && f.isFile() && f.lastModified() >= lresourceFile.lastModified()) {
			return f;
		}
		return null;
	}

	/**
	 * Find the files with the given names in the {@link #getPath()} directory
	 * relative to the crossfile directory of the opengrok data directory. It is
	 * tried to find the compressed file first by appending the file extension
	 * ".gz" to the filename. If that fails or an uncompressed version of the
	 * file is younger than its compressed version, the uncompressed file gets
	 * used.
	 * 
	 * @param filenames
	 *            filenames to lookup.
	 * @return an empty array if the related directory does not exist or the
	 *         given list is {@code null} or empty, otherwise an array, which
	 *         may contain {@code null} entries (when the related file could not
	 *         be found) having the same order as the given list.
	 */
	public File[] findDataFiles(List<String> filenames) {
		if (filenames == null || filenames.isEmpty()) {
			return new File[0];
		}
		File[] res = new File[filenames.size()];
		File dir = new File(getEnv().getDataRootPath() + Prefix.XREF_P + getPath());
		if (dir.exists() && dir.isDirectory()) {
			getResourceFile();
			boolean compressed = getEnv().isCompressXref();
			for (int i = res.length - 1; i >= 0; i--) {
				res[i] = checkFileResolve(dir, filenames.get(i), compressed);
			}
		}
		return res;
	}

	/**
	 * Lookup the file {@link #getPath()} relative to the crossfile directory of
	 * the opengrok data directory. It is tried to find the compressed file
	 * first by appending the file extension ".gz" to the filename. If that
	 * fails or an uncompressed version of the file is younger than its
	 * compressed version, the uncompressed file gets used.
	 * 
	 * @return {@code null} if not found, the file otherwise.
	 */
	public File findDataFile() {
		return checkFile(new File(getEnv().getDataRootPath() + Prefix.XREF_P), getPath(), getEnv().isCompressXref());
	}

	/**
	 * Get the path the request should be redirected
	 * (if any).
	 * 
	 * @return {@code null} if there is no reason to redirect, the URI encoded
	 *         redirect path to use otherwise.
	 */
	public String getDirectoryRedirect() {
		if (isDir()) {
			if (getPath().length() == 0) {
				// => /
				return null;
			}
			getPrefix();
			if (prefix != Prefix.XREF_P && prefix != Prefix.HIST_L) {
				// if it is an existing dir perhaps people wanted dir xref
				return readingBean.getRequest().getContextPath() + Prefix.XREF_P + getUriEncodedPath()
						+ trailingSlash(getPath());
			}
			String ts = trailingSlash(getPath());
			if (ts.length() != 0) {
				return readingBean.getRequest().getContextPath() + prefix + getUriEncodedPath() + ts;
			}
		}
		return null;
	}

	/**
	 * Get the URI encoded canonical path to the related file or directory (the
	 * URI part between the servlet path and the start of the query string).
	 * 
	 * @return an URI encoded path which might be an empty string but not
	 *         {@code null}.
	 * @see #getPath()
	 */
	public String getUriEncodedPath() {
		if (uriEncodedPath == null) {
			uriEncodedPath = Util.URIEncodePath(getPath());
		}
		return uriEncodedPath;
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
	
	public SourceReadingHelper(SourceReadingBean readingBean) {
		this.readingBean = readingBean;
		
		//initial some data
		getEnv();
		getPath();
		getResourceFile();
	}
	
	
	//////////////////////////////////////mine////////////////////////////////////////////
	public List<Navigation> getNavigations() {
		if(null == navigations){
			ArrayList<Navigation> navis = new ArrayList<Navigation>();
			String ptrPath = this.getPath();
			if(ptrPath.charAt(ptrPath.length()-1) == '/'){
				//去掉最后一个'/'
				ptrPath= ptrPath.substring(0, ptrPath.length()-1);
			}
			while(ptrPath.length()>1){
				File resource = new File(getSourceRootPath(), ptrPath);
				Navigation navi = new Navigation();
				navi.setFilename(resource.getName());
				navi.setIsDir(resource.isDirectory());
				if(resource.isDirectory()){
					navi.setLink(this.getPrefix()+ptrPath+"/");
				}else{
					navi.setLink(this.getPrefix()+ptrPath);
				}
				
				navi.setSubfiles(this.getSubNavigations(ptrPath));
				navis.add(0, navi);
				
				//去掉最后一个文件名和'/'（'/filname'）
				ptrPath= ptrPath.substring(0, ptrPath.length()-resource.getName().length()-1);
			}
			navigations = navis;
		}	
		return navigations;
	}
	
	/**
	 * 获取一个路径下的所有子文件
	 * @param path
	 * @return
	 */
	private List<Navigation> getSubNavigations(String path) {
		File[] files = null;
		if(path.charAt(path.length()-1) == '/'){
			//去掉最后一个'/'
			path= path.substring(0, path.length()-1);
		}
		File resource = new File(getSourceRootPath(), path);
		if (resource.canRead() && resource.isDirectory() ) {
			files = FileUtil.listSortedFiles(resource);//resource.listFiles();
		}else{
			//可能是文件
			return new ArrayList<Navigation>(0);
		}
		
		if (files == null) {
			return  new ArrayList<Navigation>(0);
		}
		
		ArrayList<Navigation> subn = new ArrayList<Navigation>(files.length);
		for(File subfile : files){
			Navigation navi = new Navigation();
			navi.setFilename(subfile.getName());
			navi.setIsDir(subfile.isDirectory());
			if(subfile.isDirectory()){
				navi.setLink(this.getPrefix()+path+"/"+subfile.getName()+"/");
			}else{
				navi.setLink(this.getPrefix()+path+"/"+subfile.getName());
			}
			
			subn.add(navi);
		}
		return subn;
	}
}
