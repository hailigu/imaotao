package com.codeReading.busi.service.user.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import com.codeReading.busi.dal.iface.user.IUserBadgeDao;
import com.codeReading.busi.dal.iface.user.IUserDao;
import com.codeReading.busi.dal.model.User;
import com.codeReading.busi.dpn.enums.UserState;
import com.codeReading.busi.dpn.exception.ItemNotFoundException;
import com.codeReading.busi.dpn.search.UserSearchHelper;
import com.codeReading.busi.dpn.search.repository.UserRepository;
import com.codeReading.busi.service.user.IUserService;
import com.codeReading.core.framework.web.BaseService;
import com.codeReading.core.framework.web.InnerResultData;
import com.codeReading.core.framework.web.ResultData;
import com.codeReading.core.resource.ResourceConfiguration;
import com.codeReading.core.resource.UpYun;
import com.codeReading.core.util.DateUtil;

@Service
public class UserService extends BaseService implements IUserService {
	private Logger log = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private IUserDao userDao;
	@Autowired
	private UserSearchHelper userSearchHelper;
	@Autowired
	private ResourceConfiguration resConfig;
	@Autowired
	private IUserBadgeDao userBadgeDao;

	@Override
	public ResultData get(String userid) throws Exception {
		log.debug("尝试获取用户基本信息{}", userid);
		ResultData result = ResultData.init();
		User user = userDao.get(userid);
		if (null != user) {
			result.setData(getFieldValueMap(user));
		} else {
			result.setData(new HashMap<String, Object>());
		}
		return result;
	}
	@Override
	public ResultData getBasicInfo(String userid) throws Exception {
		log.debug("尝试获取用户基本信息{}", userid);
		ResultData result = ResultData.init();
		User user = userDao.get(userid);
		if (null != user) {
			result.setData("userid", user.getUserid());
			result.setData("nickname", user.getNickname());
		} else {
			result.setData(new HashMap<String, Object>());
		}
		return result;
	}

	@Override
	public ResultData getBindInfo(String userid) throws Exception {
		log.debug("尝试获取用户绑定邮箱，手机信息{}", userid);
		ResultData result = ResultData.init();
		Map<String, Object> bindInfo = userDao.getUserBindInfo(userid);
		result.setData(bindInfo);
		return result;
	}

	@Override
	public InnerResultData<Map<String, Object>> getUserBindInfo(String userid) throws Exception {
		log.debug("尝试获取用户绑定邮箱，手机信息{}", userid);
		InnerResultData<Map<String, Object>> result = new InnerResultData<Map<String, Object>>();
		Map<String, Object> bindInfo = userDao.getUserBindInfo(userid);
		result.setData(bindInfo);
		return result;
	}

	@Override
	public ResultData isUserBindPhone(String userid) throws Exception {
		log.debug("尝试判断用户是否绑定了手机{}", userid);
		Boolean isBindPhone = false;
		ResultData result = ResultData.init();
		if(StringUtils.isNotEmpty(userid)){
			Map<String, Object> bindInfo = userDao.getUserBindInfo(userid);
			if (null != bindInfo && StringUtils.isNotEmpty((String)bindInfo.get("phone"))) {
				isBindPhone = true;
			}
		}
		result.setData("isBindPhone", isBindPhone);
		return result;
	}

	@Override
	public InnerResultData<User> getUser(String userid) throws Exception {
		log.debug("[服务]尝试获取用户基本信息 user={}", userid);
		InnerResultData<User> result = new InnerResultData<User>();
		User user = userDao.get(userid);
		if (null != user) {
			result.setData(user);
		} else {
			throw new ItemNotFoundException();
		}
		log.debug("[服务]完成获取用户基本信息 result={}", result);
		return result;
	}

	@Override
	public InnerResultData<Boolean> isUserOK(String userid) throws Exception {
		log.debug("#开始 判断索引用户是否可用 userid={}", userid);
		InnerResultData<Boolean> result = new InnerResultData<Boolean>();
		if (!StringUtils.isEmpty(userid)) {
			User user = userDao.get(userid);
			if (null != user) {
				if (UserState.NORMAL.equals(user.getState())) {
					result.setData(true);
				} else {
					result.setData(false);
				}
			} else {
				result.setData(false);
			}
		} else {
			result.setData(false);
		}
		log.info("#完成 判断索引用户是否可用 result={}", result);
		return result;
	}

	@Override
	public InnerResultData<List<Map<String, Object>>> findById(String... userids) throws Exception {
		Assert.notNull(userids, "用户编号不能为NULL");
		log.debug("[服务]开始搜索相关用户, userids={}", ArrayUtils.toString(userids));
		InnerResultData<List<Map<String, Object>>> result = new InnerResultData<List<Map<String, Object>>>();
		List<Map<String, Object>> users = userSearchHelper.getForList(userids);
		result.setData(users);
		log.debug("[服务]完成搜索相关用户， 结果共{}条", users.size());
		return result;
	}

	@Override
	public ResultData uploadAvatar(String userid, String imgdata) throws Exception {
		log.debug("[服务]开始上传用户图像{}", userid);
		ResultData result = ResultData.init();
		imgdata = imgdata.substring(imgdata.indexOf("base64,") + 7);
		byte[] datas = Base64.decodeBase64(imgdata);

		// 上传到又拍云
		String avatar = resConfig.getAvatarDir() + File.separator + userid + File.separator + new Date().getTime() + ".jpg";
		UpYun upyun = new UpYun(resConfig.getBucketName(), resConfig.getOperatorName(), resConfig.getOperatorPassword());

		if (!upyun.writeFile(pathFormat(File.separator + avatar), datas, true)) {
			log.error("上传图像到又拍云出粗");
			throw new Exception();
		}

		// 备份图像到本地
		File file = new File(resConfig.getBackupPath() + File.separator + resConfig.getAvatarDir() + File.separator + userid);
		if (!file.exists()) {
			file.mkdirs();
		}
		FileOutputStream fos = new FileOutputStream(new File(resConfig.getBackupPath() + File.separator + avatar));
		fos.write(datas);
		fos.close();

		User user = new User();
		user.setUserid(userid);
		user.setAvatar(pathFormat(avatar));
		user.setModtime(DateUtil.currentTimestamp());
		userDao.update(user);
		
		// 更新索引
		userSearchHelper.update(new UserRepository(userDao.get(userid), null, null));

		result.setData("avatar", pathFormat(avatar));
		log.debug("[服务]完成上传用户图像  result={}", result);
		return result;
	}

	private String pathFormat(String path) {
		return path.replace("\\", "/");
	}

	@Override
	public String uploadAvatar(String userid, MultipartFile uploadFile) throws Exception {
		log.debug("[服务]开始上传用户图像{}", userid);
		// 备份图像到本地
		File file = new File(resConfig.getBackupPath() + File.separator + resConfig.getAvatarDir() + File.separator + userid);
		if (!file.exists()) {
			file.mkdirs();
		}

		String avatar = resConfig.getAvatarDir() + File.separator + userid + File.separator + new Date().getTime() + ".jpg";
		uploadFile.transferTo(new File(resConfig.getBackupPath() + File.separator + avatar));

		UpYun upyun = new UpYun(resConfig.getBucketName(), resConfig.getOperatorName(), resConfig.getOperatorPassword());
		if (!upyun.writeFile(pathFormat(File.separator + avatar), new File(resConfig.getBackupPath() + File.separator + avatar), true)) {
			log.error("上传图像到又拍云出粗");
			throw new Exception();
		}

		User user = new User();
		user.setUserid(userid);
		user.setAvatar(pathFormat(avatar) + "!200");
		user.setModtime(DateUtil.currentTimestamp());
		userDao.update(user);
		
		//List<Badge> badges = userBadgeDao.findBadgesByUser(userid);
		// 更新索引
		userSearchHelper.update(new UserRepository(userDao.get(userid), null, null));
		log.debug("[服务]完成上传用户图像  avatar={}", pathFormat(avatar) );
		return pathFormat(avatar) + "!200";
	}
}
