package com.codeReading.busi.service.user;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.codeReading.busi.dal.model.User;
import com.codeReading.core.framework.web.InnerResultData;
import com.codeReading.core.framework.web.ResultData;

public interface IUserService {

	public ResultData get(String userid) throws Exception;

	/**
	 * 获取用户信息绑定邮箱，手机信息
	 * 
	 * @param userid
	 * @return
	 * @throws Exception
	 */
	public ResultData getBindInfo(String userid) throws Exception;

	/**
	 * 获取用户信息绑定邮箱，手机信息
	 * 
	 * @param userid
	 * @return
	 * @throws Exception
	 */
	public InnerResultData<Map<String, Object>> getUserBindInfo(String userid) throws Exception;

	/**
	 * 获取用户信息绑定邮箱，手机信息
	 * 
	 * @param userid
	 * @return
	 * @throws Exception
	 */
	public ResultData isUserBindPhone(String userid) throws Exception;

	/**
	 * 获取除密码外的用户信息
	 * 
	 * @param userid
	 * @return
	 * @throws Exception
	 */
	public InnerResultData<User> getUser(String userid) throws Exception;

	/**
	 * 判断索引用户是否可用
	 * 
	 * @param userid
	 *            用户编号
	 * @return 是否可用
	 * @throws Exception
	 */
	public InnerResultData<Boolean> isUserOK(String userid) throws Exception;

	/**
	 * 根据用户编号，从搜索引擎获取用户基本信息
	 * 
	 * @param userids
	 *            用户编号
	 * @return 用户基本信息列表
	 * @throws Exception
	 */
	public InnerResultData<List<Map<String, Object>>> findById(String... userids) throws Exception;

	/**
	 * 上传用户图像
	 * 
	 * @param userid
	 *            用户编号
	 * @param imgdata
	 *            图片数据
	 * @return
	 * @throws Exception
	 */
	public ResultData uploadAvatar(String userid, String imgdata) throws Exception;

	/**
	 * 上传用户图像
	 * 
	 * @param userid
	 *            用户编号
	 * @param imgdata
	 *            图片数据
	 * @return
	 * @throws Exception
	 */
	public String uploadAvatar(String userid, MultipartFile uploadFile) throws Exception;

	/**
	 * Get user basic information. There is only nickname for now. 
	 * @param userid 用户编号
	 * @return
	 * @throws Exception
	 */
	public ResultData getBasicInfo(String userid) throws Exception;

}
