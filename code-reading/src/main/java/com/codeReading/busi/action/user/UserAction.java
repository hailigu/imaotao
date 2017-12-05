package com.codeReading.busi.action.user;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.Principal;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.codeReading.busi.service.user.IUserService;
import com.codeReading.core.framework.web.BaseAction;
import com.codeReading.core.framework.web.ResultData;

/**
 * 用户信息页面
 * 
 * @author Rofly
 */
@Controller
@RequestMapping("u")
public class UserAction extends BaseAction {
	private Logger log = LoggerFactory.getLogger(UserAction.class);

	@Autowired
	private IUserService userService;	


	/**
	 * 获取用户的源码工程信息
	 * 
	 * @param userid
	 *            用户编号
	 * @param result
	 *            返回的用户相关信息
	 * @return 用户信息页面
	 */
	@RequestMapping(value={"/{userid}/user-sources", "/{userid}"})
	public String getUserSourceProjects(@PathVariable("userid") String userid, Map<String, Map<String, Object>> result) {
		try {
			log.info("#获取用户源码, userid={}", userid);
			ResultData data = userService.get(userid);
			collect(data, result);
			return "user/user-sourceProjects";
		} catch (Exception e) {
			collect(e, result);
			return ERROR;
		}
	}

	/**
	 * 获取用户的文章信息
	 * 
	 * @param userid
	 *            用户编号
	 * @param result
	 *            返回的用户相关信息
	 * @return 用户信息页面
	 */
	@RequestMapping("/{userid}/user-articles")
	public String getUserArticles(@PathVariable("userid") String userid, Map<String, Map<String, Object>> result) {
		try {
			log.info("#获取用户文章, userid={}", userid);
			ResultData data = userService.get(userid);
			collect(data, result);
			return "user/user-articles";
		} catch (Exception e) {
			collect(e, result);
			return ERROR;
		}
	}
	
	
	

	/**
	 * 获取用户的源码注释信息
	 * 
	 * @param userid
	 *            用户编号
	 * @param result
	 *            返回的用户相关信息
	 * @return 用户信息页面
	 */
	@RequestMapping("/{userid}/user-annotations")
	public String getUserAnnotations(@PathVariable("userid") String userid, Map<String, Map<String, Object>> result) {
		try {
			log.info("#获取用户注释, userid={}", userid);
			ResultData data = userService.get(userid);
			collect(data, result);
			return "user/user-annotations";
		} catch (Exception e) {
			collect(e, result);
			return ERROR;
		}
	}
	
		

	/**
	 * 获取用户的信息
	 * 
	 * @param userid
	 *            用户编号
	 * @param result
	 *            返回的用户相关信息
	 * @return 用户信息页面
	 */
	@RequestMapping("/bind")
	public void getUserBindInfo(Principal principal, Map<String, Map<String, Object>> result) {
		try {
			log.info("#获取用户数据, userid={}", principal.getName());
			ResultData data = userService.getBindInfo(principal.getName());
			collect(data, result);
		} catch (Exception e) {
			collect(e, result);
		}
	}

	/**
	 * 判断用户是否绑定了手机
	 * 
	 * @param principal
	 * @param result
	 *            返回的用户相关信息
	 */
	@RequestMapping("/is-bind-phone")
	public void isUserBindPhone(Principal principal, Map<String, Map<String, Object>> result) {
		try {
			log.info("#判断用户是否绑定了手机, userid={}", principal.getName());
			ResultData data = userService.isUserBindPhone(principal.getName());
			collect(data, result);
		} catch (Exception e) {
			collect(e, result);
		}
	}

	/**
	 * 获取用户的信息
	 * 
	 * @param userid
	 *            用户编号
	 * @param result
	 *            返回的用户相关信息
	 * @return 用户信息页面
	 */
	@RequestMapping("/avatar")
	public String avatar(HttpServletRequest request, Map<String, Map<String, Object>> result) {
		try {
			String userid = request.getSession().getAttribute("userid").toString();
			log.info("#获取用户数据, userid={}", userid);
			ResultData data = userService.get(userid);
			collect(data, result);
			return "user";
		} catch (Exception e) {
			collect(e, result);
			return ERROR;
		}
	}
	
	
	/**
	 * 获取指定用户的信息
	 * 
	 * @param userid
	 *            用户编号
	 * @param result
	 *            返回的用户相关信息
	 * @return 用户信息页面
	 */
	@RequestMapping("/theuseravatar")
	public String theUserAvatar(String userid, Map<String, Map<String, Object>> result) {
		try {
			log.info("#获取用户数据, userid={}", userid);
			ResultData data = userService.get(userid);
			collect(data, result);
			return "user";
		} catch (Exception e) {
			collect(e, result);
			return ERROR;
		}
	}

	/**
	 * 上传用户图像
	 * 
	 * @param imgdata
	 *            图片数据
	 * @param principal
	 * @param request
	 * @param result
	 */
	@RequestMapping("/upload-avatar")
	public void uploadAvatar(String imgdata, Principal principal, Map<String, Map<String, Object>> result) {
		try {
			String userid = principal.getName();
			log.info("#上传用户图像, userid={}", userid);
			ResultData data = userService.uploadAvatar(userid, imgdata);
			collect(data, result);
		} catch (Exception e) {
			collect(e, result);
		}
	}

	/**
	 * 上传用户图像
	 * 
	 * @param principal
	 * @param request
	 * @param response
	 */
	@RequestMapping("/upload-original")
	public void uploadAvatar(Principal principal, MultipartHttpServletRequest request, HttpServletResponse response) {
		log.info("#上传用户图像, userid={}", principal.getName());
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			String avatar = userService.uploadAvatar(principal.getName(), request.getFile("imgFile"));
			out.write("{\"retCode\":\"000000\",\"avatar\":\"" + avatar + "\"}");
			out.flush();
			out.close();
		} catch (Exception e) {
			try {
				out = response.getWriter();
				out.write("{\"retCode\":\"0X0X0X\"}");
				out.flush();
				out.close();
			} catch (IOException ex) {
				log.error("返回状态异常");
			}
		}
	}
}