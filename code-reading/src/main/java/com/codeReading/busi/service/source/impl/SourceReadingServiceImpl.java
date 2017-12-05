package com.codeReading.busi.service.source.impl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.lucene.search.Query;
import org.opensolaris.opengrok.analysis.AnalyzerGuru;
import org.opensolaris.opengrok.analysis.Definitions;
import org.opensolaris.opengrok.analysis.FileAnalyzer.Genre;
import org.opensolaris.opengrok.analysis.FileAnalyzerFactory;
import org.opensolaris.opengrok.configuration.Project;
import org.opensolaris.opengrok.history.Annotation;
import org.opensolaris.opengrok.history.HistoryGuru;
import org.opensolaris.opengrok.index.IndexDatabase;
import org.opensolaris.opengrok.search.QueryBuilder;
import org.opensolaris.opengrok.search.context.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codeReading.busi.dal.model.SourceProject;
import com.codeReading.busi.dpn.enums.FileReturnType;
import com.codeReading.busi.dpn.exception.source.SourceFileNotExistException;
import com.codeReading.busi.dpn.source.SourceSearchResult;
import com.codeReading.busi.po.SourceSearchPO;
import com.codeReading.busi.service.source.ISourceFileService;
import com.codeReading.busi.service.source.ISourceProjectService;
import com.codeReading.busi.service.source.ISourceReadingService;
import com.codeReading.core.framework.web.BaseService;
import com.codeReading.core.framework.web.ResultData;
import com.codeReading.core.opengrok.DirectoryListing;
import com.codeReading.core.opengrok.Navigation;
import com.codeReading.core.opengrok.Prefix;
import com.codeReading.core.opengrok.SearchHelper;
import com.codeReading.core.opengrok.SourceReadingBean;
import com.codeReading.core.opengrok.SourceReadingHelper;
import com.codeReading.core.opengrok.SourceSearchBean;
import com.codeReading.core.opengrok.SourceSearchHelper;
import com.codeReading.core.opengrok.Suggestion;
import com.codeReading.core.opengrok.SuggestionBean;
import com.codeReading.core.opengrok.Util;
import com.codeReading.core.opengrok.search.Results;

@Service
public class SourceReadingServiceImpl extends BaseService implements ISourceReadingService {
	private Logger log = LoggerFactory.getLogger(SourceReadingServiceImpl.class);

	@Autowired ISourceProjectService sourceProjectService;
	@Autowired ISourceFileService sourceFiletService;

	
	@Override
	public ResultData reading(HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.info("### 开始 阅读代码， request.getServletPath={}", request.getServletPath());
		ResultData result = ResultData.init();
		
		SourceReadingBean readingBean = new SourceReadingBean("/xref", request);
		SourceReadingHelper cfg = new SourceReadingHelper(readingBean);
		//File xrefFile = cfg.findDataFile();
		String rev = cfg.getRequestedRevision();
		if (cfg.isDir()) {	
			//如果是目录，但是url结尾不是“/”结束，需要重定向
			String url = request.getServletPath();
			if(!url.endsWith("/")){
				result.setRetCode("redirect");
				result.setReason(url+"/");
			}
		}

		File resourceFile = cfg.getResourceFile();
		String path = cfg.getPath();
		String basename = resourceFile.getName();
		
		SourceProject sourceProject = sourceProjectService.getSourceProjectByName(cfg.getProject().getDescription());
		if(null==sourceProject ){
			//请求的文件不存在
			throw new SourceFileNotExistException();
		}
		result.setData("project", sourceProject);
//		String filepath = resourceFile.getAbsolutePath().substring(cfg.getSourceRootPath().length()+
//				sourceProject.getProjectpath().length());
//		SourceFile sourceFile = sourceFiletService.getSourceFileByPath(sourceProject.getProjectid(), filepath);
//		
//		if(cfg.resourceNotAvailable() || null==sourceFile){
//			//请求的文件不存在
//			throw new SourceFileNotExistException();
//		}
//		result.setData("sourceFile", getFieldValueMap(sourceFile));
//		result.setData("sourceProject", getFieldValueMap(sourceProject));
		
		String rawPath = request.getContextPath() + Prefix.DOWNLOAD_P + path;
		Reader reader = null;
		StringWriter out = new StringWriter();
		
		//navigation
		List<Navigation> navigations = cfg.getNavigations();
		result.setData("navigations", navigations);
		
		//把当前工程放到cookie中
	    Project activeProject = Project.getProject(resourceFile);
        String cookieValue = cfg.getRequestedProjectsAsString();	//定制过，只有一个
        if (activeProject != null && !activeProject.getDescription().equals(cookieValue)) {
        	// update cookie
            cookieValue =  activeProject.getDescription();	////定制过，只放一个
            Cookie cookie = new Cookie("OpenGrokProject", cookieValue);
            // TODO hmmm, projects.jspf doesn't set a path
            cookie.setPath(request.getContextPath() + '/');
            response.addCookie(cookie);
        }
		
		if (cfg.isDir()) {			
			// valid resource is requested
			// mast.jsp assures, that resourceFile is valid and not /
			// see cfg.resourceNotAvailable()
			result.setData("returnType", FileReturnType.DIR.typeName());//如果是文件夹
			
			DirectoryListing dl = new DirectoryListing(cfg.getEftarReader());
			List<String> files = cfg.getResourceFileList();
			if (!files.isEmpty()) {
				//文件列表
				StringWriter filelist = new StringWriter();
				List<String> readMes = dl.listTo(Util.URIEncodePath(request.getContextPath()), resourceFile, filelist, path,
						files);
				result.setData("filelist", filelist.toString());
				filelist.close();
				
				//显示readme
				File[] catfiles = cfg.findDataFiles(readMes);
				HashMap<String, String> readmes = new HashMap<String, String>(catfiles.length);
				for (int i = 0; i < catfiles.length; i++) {
					if (catfiles[i] == null) {
						continue;
					}
					StringWriter readme = new StringWriter();
					Util.dump(readme, catfiles[i], catfiles[i].getName().endsWith(".gz"));
					readmes.put(readMes.get(i), readme.toString());
					readme.close();
				}
				result.setData("readmes", readmes);
			}
		}else if (rev.length() != 0) {
			//版本信息不为空
			 // requesting a previous revision
	        FileAnalyzerFactory a = AnalyzerGuru.find(basename);
	        Genre g = AnalyzerGuru.getGenre(a);
	        String error = null;
	        if (g == Genre.PLAIN|| g == Genre.HTML || g == null) {
	        	InputStream in = null;
	            try {
	                in = HistoryGuru.getInstance()
	                    .getRevision(resourceFile.getParent(), basename, rev.substring(2));
	            } catch (Exception e) {
	                // fall through to error message
	                error = e.getMessage();
	            }
	            if (in != null) {
	                try {
	                    if (g == null) {
	                        a = AnalyzerGuru.find(in);
	                        g = AnalyzerGuru.getGenre(a);
	                    }
	                    if (g == Genre.DATA || g == Genre.XREFABLE
	                        || g == null)
	                    {
	            			result.setData("returnType", FileReturnType.DATA.typeName());
							result.setData("rawPath", rawPath+"?"+rev);
	                    } else {
	                        if (g == Genre.PLAIN) {
	                            // We don't have any way to get definitions
	                            // for old revisions currently.
	                            Definitions defs = null;
	                            Annotation annotation = cfg.getAnnotation();
	                            //not needed yet
	                            //annotation.writeTooltipMap(out);
	                            reader = new InputStreamReader(in);
	                            AnalyzerGuru.writeXref(a, reader, out, defs,
	                                annotation, Project.getProject(resourceFile));
		            			result.setData("returnType", FileReturnType.PLAIN.typeName());
								result.setData("returnData", processSourceLine(out.toString()));
	                        } else if (g == Genre.IMAGE) {
		            			result.setData("returnType", FileReturnType.IMAGE.typeName());
								result.setData("rawPath", rawPath+"?"+rev);
	                        } else if (g == Genre.HTML) {
	                            reader = new InputStreamReader(in);
	                            Util.dump(out, reader);
	            
		            			result.setData("returnType", FileReturnType.HTML.typeName());
								result.setData("returnData", out.toString());
	                        } else {
	                        	result.setData("returnType", FileReturnType.OTHER.typeName());
								result.setData("rawPath", rawPath+"?"+rev);
								result.setData("basename", basename);
								result.setData("revision", rev.substring(2));
	                        }
	                    }
	                } catch (IOException e) {
	                    error = e.getMessage();
	                } finally {
	                    if (reader != null) {
	                        try { reader.close(); in = null;}
	                        catch (Exception e) { /* ignore */ }
	                    }
	                    if (in != null) {
	                        try { in.close(); }
	                        catch (Exception e) { /* ignore */ }
	                    }
	                }
	            } else {
	                result.setData("returnType", FileReturnType.ERROR.typeName());
					result.setData("returnData", error != null? error : "Error reading file");
	            }
	        }
		}else{

	        // requesting cross referenced file
	        File xrefFile = null;
	        if (!cfg.annotate()) {
	            xrefFile = cfg.findDataFile();
	        }
	        if (xrefFile != null) {
	            Util.dump(out, xrefFile, xrefFile.getName().endsWith(".gz"));
	            
	            result.setData("returnType", FileReturnType.PLAIN.typeName());
			    result.setData("returnData", processSourceLine(out.toString()));
	        } else {
	            // annotate
	            BufferedInputStream bin =
	                new BufferedInputStream(new FileInputStream(resourceFile));
	            try {
	                FileAnalyzerFactory a = AnalyzerGuru.find(basename);
	                Genre g = AnalyzerGuru.getGenre(a);
	                if (g == null) {
	                    a = AnalyzerGuru.find(bin);
	                    g = AnalyzerGuru.getGenre(a);
	                }
	                if (g == Genre.IMAGE) {
					    result.setData("returnType", FileReturnType.IMAGE.typeName());
					    result.setData("rawPath", rawPath);
	                } else if ( g == Genre.HTML) {
	                    reader = new InputStreamReader(bin);
	                    Util.dump(out, reader);
	                    result.setData("returnType", FileReturnType.HTML.typeName());
					    result.setData("returnData", out.toString());
	                } else if (g == Genre.PLAIN) {
	                    // We're generating xref for the latest revision, so we can
	                    // find the definitions in the index.
	                    Definitions defs = IndexDatabase.getDefinitions(resourceFile);
	                    Annotation annotation = cfg.getAnnotation();
	                    reader = new InputStreamReader(bin);
	                    AnalyzerGuru.writeXref(a, reader, out, defs, annotation,
	                        Project.getProject(resourceFile));
	                    
	                    result.setData("returnType", FileReturnType.PLAIN.typeName());
					    result.setData("returnData", processSourceLine(out.toString()));
	                } else {
						result.setData("returnType", FileReturnType.OTHER.typeName());
						result.setData("rawPath", rawPath);
						result.setData("basename", basename);
	                }
	            } finally {
	                if (reader != null) {
	                    try { reader.close(); bin = null; }
	                    catch (Exception e) { /* ignore */ }
	                }
	                if (bin != null) {
	                    try { bin.close(); }
	                    catch (Exception e) { /* ignore */ }
	                }
	            }
	        }
		}
		
		result.setData("out", out.getBuffer());
		log.info("### 结束 阅读代码 result={}", result);
		
		out.close();
		return result;
	}
	
	/**
	 * 给代码中的每一行加上<div class="codeLine"></div>， 并且去掉换行符。
	 * @param src 代码原内容
	 * @return 处理完的内容
	 * @throws Exception
	 */
	private String processSourceLine(String src) throws Exception{
		int firstLineIndex = src.indexOf("<a class=\"l\"");
		if(firstLineIndex>0){
			String prefix = src.substring(0, firstLineIndex);
			String mainContent = src.substring(firstLineIndex);
			return prefix+mainContent.replace("<a class=\"l\"", "<div class=\"codeLine\"><a class=\"l\"")
					.replace("<a class=\"hl\"", "<div class=\"codeLine\"><a class=\"hl\"")
					.replace("\n\r", "</div>")
					.replace("\n", "</div>")
					.replace("\r", "</div>");
		}else{
			return src.replace("<a class=\"l\"", "<div class=\"codeLine\"><a class=\"l\"")
					.replace("<a class=\"hl\"", "<div class=\"codeLine\"><a class=\"hl\"")
					.replace("\n\r", "</div>")
					.replace("\n", "</div>")
					.replace("\r", "</div>");
		}		
	}	

	@Override
	public ResultData search(SourceSearchPO po, HttpServletRequest request) throws Exception {
		log.info("### 开始 搜索代码， request.getServletPath={}", request.getServletPath());
		ResultData result = ResultData.init();
		
		SourceSearchBean searchBean = new SourceSearchBean(request);
		searchBean.setDefs(po.getDefs());
		searchBean.setHist(po.getHist());
		searchBean.setN(po.getN());
		searchBean.setPath(po.getPath());
		if (null != po.getProject()) {
			TreeSet<String> projects = new TreeSet<String>();
			projects.add(po.getProject());
			searchBean.setProjects(projects);
		}
		searchBean.setQ(po.getQ());
		searchBean.setRefs(po.getRefs());
		searchBean.addSortkeywords(po.getSort());
		searchBean.setStart(po.getStart());
		searchBean.setT(po.getT());
		searchBean.setType(po.getType());
		SourceSearchHelper cfg = new SourceSearchHelper(searchBean);
	    cfg.getEnv().setUrlPrefix(request.getContextPath() + Prefix.SEARCH_R + "?");

	    //String projects = cfg.getRequestedProjectsAsString();

	    SearchHelper searchHelper = cfg.prepareSearch()
	            .prepareExec(cfg.getRequestedProjects()).executeQuery().prepareSummary();
	    
	    if (searchHelper.errorMsg != null) {
	        cfg.setTitle("Search Error");
	    } else {
	        cfg.setTitle("Search");
	    }
	    if (searchHelper.errorMsg != null) {
	        result.setData("errorMsg", searchHelper.errorMsg);
	        result.setData("hasError", true);
	    }else if (searchHelper.hits == null) {
	    	SourceSearchResult searchResult = new SourceSearchResult();
	        searchResult.setTotalHits(0);
	        result.setData("searchResult", searchResult);
	        result.setData("hasError", false);
	    }else if (searchHelper.hits.length == 0) {
	    	List<Suggestion> suggestions = searchHelper.getSuggestions();
	    	SourceSearchResult searchResult = new SourceSearchResult();
	        searchResult.setTotalHits(0);
	        StringWriter out = new StringWriter();
	        Util.htmlize(searchHelper.query.toString(), out);out.close();
	        searchResult.setYourSearch(out.toString());
	        
	        result.setData("suggestions", SuggestionBean.getSuggestionBeanList(suggestions));
	        result.setData("searchResult", searchResult);
	        result.setData("hasError", false);
	    } else {
	    	// We have a lots of results to show: create a slider for
	        String slider = "";
	        int thispage;  // number of items to display on the current page
	        int start = searchHelper.start;
	        int max = searchHelper.maxItems;
	        int totalHits = searchHelper.totalHits;
	        
	        //FIXME 分页应该放到jsp中
	        if (searchHelper.maxItems < searchHelper.totalHits) {
	            StringBuilder buf = new StringBuilder(4096);
	            thispage = (start + max) < totalHits ? max : totalHits - start;
	            StringBuilder urlp = createUrl(searchHelper, false, cfg);
	            int labelStart = 1;
	            int sstart = start - max * (start / max % 10 + 1) ;
	            if (sstart < 0) {
	                sstart = 0;
	                labelStart = 1;
	            } else {
	                labelStart = sstart / max + 1;
	            }
	            int label = labelStart;
	            int labelEnd = label + 11;
	            for (int i = sstart; i < totalHits && label <= labelEnd; i+= max) {
	                if (i <= start && start < i + max) {
	                    buf.append("<span class=\"sel\">").append(label).append("</span>");
	                } else {
	                    buf.append("<a class=\"more\" href=\"s?n=").append(max)
	                        .append("&amp;start=").append(i).append(urlp).append("\">");
	                    if (label == labelStart && label != 1) {
	                        buf.append("&lt;&lt");
	                    } else if (label == labelEnd && i < totalHits) {
	                        buf.append("&gt;&gt;");
	                    } else {
	                        buf.append(label);
	                    }
	                    buf.append("</a>");
	                }
	                label++;
	            }
	            slider = buf.toString();
	        } else {
	            // set the max index to max or last
	            thispage = totalHits - start;
	        }
	        StringWriter out = new StringWriter();
	        Results.prettyPrint(out, searchHelper, start, start + thispage);
	        SourceSearchResult searchResult = new SourceSearchResult();
	        searchResult.setSlider(slider);
	        searchResult.setSorted(searchHelper.order.getDesc());
	        searchResult.setStart(start + 1);
	        searchResult.setThispage(thispage + start);
	        searchResult.setTotalHits(totalHits);
	        
	        result.setData("searchResult", searchResult);
	        result.setData("resultContent", out.toString());
	        result.setData("hasError", false);
	        out.close();
	    }
	    
	    if(null==po.getProject()){
	    	po.setProject(cfg.getRequestedProjects().first());	
	    }
	    result.setData("searchBean", po);
		log.info("### 结束 搜索代码result{}", result);
		return result;
	}
	
	
    private StringBuilder createUrl(SearchHelper sh, boolean menu, SourceSearchHelper cfg) {
        StringBuilder url = new StringBuilder(64);
        QueryBuilder qb = sh.builder;
        if (menu) {
            url.append("search?");
        } else {
            Util.appendQuery(url, "sort", sh.order.toString());
        }
        if (qb != null) {
            Util.appendQuery(url, "q", qb.getFreetext());
            Util.appendQuery(url, "defs", qb.getDefs());
            Util.appendQuery(url, "refs", qb.getRefs());
            Util.appendQuery(url, "path", qb.getPath());
            Util.appendQuery(url, "hist", qb.getHist());
            Util.appendQuery(url, "type", qb.getType());
        }
        if (sh.projects != null && sh.projects.size() != 0) {
            Util.appendQuery(url, "project", cfg.getRequestedProjectsAsString());
        }
        return url;
    }
    
    @Override
	public ResultData more(SourceSearchPO po, HttpServletRequest request) throws Exception {
		log.info("### 开始 more， request.getServletPath={}", request.getServletPath());
		ResultData result = ResultData.init();
		SourceSearchBean searchBean = new SourceSearchBean(request);
		searchBean.setDefs(po.getDefs());
		searchBean.setHist(po.getHist());
		searchBean.setN(po.getN());
		searchBean.setPath(po.getPath());
		if (null != po.getProject()) {
			TreeSet<String> projects = new TreeSet<String>();
			projects.add(po.getProject());
			searchBean.setProjects(projects);
		}
		searchBean.setQ(po.getQ());
		searchBean.setRefs(po.getRefs());
		searchBean.addSortkeywords(po.getSort());
		searchBean.setStart(po.getStart());
		searchBean.setT(po.getT());
		searchBean.setType(po.getType());
		SourceSearchHelper cfg = new SourceSearchHelper(searchBean);
		QueryBuilder qbuilder = cfg.getQueryBuilder();
		Query tquery = qbuilder.build();

		SourceReadingBean readingBean = new SourceReadingBean("/xref", request);
		SourceReadingHelper readingHelper = new SourceReadingHelper(readingBean);

		StringWriter out = new StringWriter();
		if (tquery != null) {
			Context sourceContext = new Context(tquery, qbuilder.getQueries());
			
			sourceContext.getContext(new FileReader(readingHelper.getResourceFile()), out, request.getContextPath()
					+ Prefix.XREF_P, null, readingHelper.getPath(), null, false, false, null);
		}
		result.setData("tquery", tquery);
		result.setData("resultContent", out.toString());
		log.info("### 完成  more， result={}", result);
		return result;
    }
}
