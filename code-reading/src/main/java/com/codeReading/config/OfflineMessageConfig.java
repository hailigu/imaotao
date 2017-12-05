package com.codeReading.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import com.codeReading.core.email.MailSender;
import com.codeReading.core.sms.SmsSender;
import com.codeReading.core.sms.SmsType;

/**
 * 与用户的离线消息交互
 * 
 * @author Rofly
 */
@Configuration
@PropertySource(value = "classpath:offline-message.properties")
public class OfflineMessageConfig {

	@Autowired
	private Environment env;

	@Bean
	public MailSender mailSender() {
		String hostName = env.getRequiredProperty("email.hostname");
		int smtpPort = Integer.parseInt(env.getProperty("email.smtpPort", "25"));
		boolean isSSL = Boolean.parseBoolean(env.getProperty("email.ssl", "false"));
		String authName = env.getRequiredProperty("email.authname");
		String authNameTitle = env.getProperty("email.authnametitle", "问津");
		String authPassword = env.getRequiredProperty("email.authpassword");

		return new MailSender(hostName, smtpPort, isSSL, authName, authNameTitle, authPassword);
	}

	@Bean
	public SmsSender smsSender() {
		String postUrl = env.getRequiredProperty("sms.url");
		String sname = env.getRequiredProperty("sms.sname");
		String spwd = env.getRequiredProperty("sms.spwd");
		String sprdid = env.getRequiredProperty("sms.sprdid");
		String scorpid = env.getProperty("sms.scorpid");

		SmsSender smsSender = new SmsSender(postUrl, sname, spwd, sprdid, getSmsTemplates());
		smsSender.setErrorReasons(getErrorReasons());
		if (null != scorpid)
			smsSender.setScorpid(scorpid);
		return smsSender;
	}

	/**
	 * 短信模板配置
	 */
	private Map<SmsType, String> getSmsTemplates() {
		Map<SmsType, String> templates = new HashMap<SmsType, String>();

		templates.put(SmsType.USER_REGESTER, "您的手机验证码为{code}，30分钟内有效，请及时操作。");
		templates.put(SmsType.USER_REGESTER_SUCCESS, "[{nickname}]您好，感谢注册，问津将使您的工作、学习更加高效。");
		// 忘记密码模板
		templates.put(SmsType.USER_FORGET, "您正在修改{pwdtype}密码，验证码:{code}，30分钟内有效。工作人员不会向您索取，请勿泄露。");
		templates.put(SmsType.USER_FORGET_SUCCESS, "您的{pwdtype}密码修改成功。如非本人操作，请联系4008-363-616");
		// Add sms template here.
		templates.put(SmsType.SERVICE_BOUGHT, "您的服务“{service_title}”已被 {payer_nickname} 预约，请在{responsetime}内响应。详情见 " + env.getRequiredProperty("sms.domain") + "/orders");
		templates.put(SmsType.PAYER_ORDER_CONFIRMED, "您的预约已被专家 {expert_nickname}（{expert_contact}）接受，您可以联系对方啦。详情见 " + env.getRequiredProperty("sms.domain") + "/orders");

		// 绑定手机模板
		templates.put(SmsType.PHONE_BIND, "您的手机验证码为{code}，30分钟内有效，请及时操作。");
		templates.put(SmsType.PHONE_BIND_SUCCESS, "尊敬的[{nickname}]您好，手机绑定成功！您可以通过手机登录问津平台。");

		return templates;
	}

	private Map<Integer, String> getErrorReasons() {
		Map<Integer, String> errors = new HashMap<Integer, String>();
		errors.put(0, "提交成功");
		errors.put(-99, "异常");
		errors.put(101, "提交参数不可为空，或参数格式错误");
		errors.put(102, "发送时间格式不正确,正确格式为yyyy-MM-ddHH:mm:ss");
		errors.put(103, "调用速度超过限制");
		errors.put(201, "Ems格式转换错误");
		errors.put(202, "Tms内容异常");
		errors.put(203, "Mix格式彩信增加Smil文件错误");
		errors.put(1007, "错误的信息类型");
		errors.put(1008, "超过最大并发提交用户数");
		errors.put(1009, "号码为空或超过最大提交号码个数");
		errors.put(1010, "信息内容为空或超过最大信息字节长度");
		errors.put(1011, "超过最大企业号码长度,或企业号码不包含");
		errors.put(1012, "企业号码未包含");
		errors.put(1013, "账户未启用或鉴权失败");
		errors.put(1014, "账户提交方式不正确或Ip受限");
		errors.put(1015, "提交速度受限");
		errors.put(1016, "产品不存在或未开启");
		errors.put(1017, "提交信息类型与产品信息类型不符合");
		errors.put(1018, "超过产品发送时段");
		errors.put(1019, "提交彩信必须有标题");
		errors.put(1020, "提交短信不可超过X个字");
		errors.put(1021, "提交彩信不可超过xK");
		errors.put(1022, "消息拆分失败");
		errors.put(1023, "无效计费条数");
		errors.put(1024, "信息内容包含关键字");
		errors.put(1025, "Account:XXX计费失败");
		errors.put(1026, "提交至调度中心失败");
		errors.put(1027, "提交成功，信息保存失败");
		errors.put(1028, "账户%s无对应的产品");
		errors.put(1029, "扩展产品%d不可提交多个号码");
		errors.put(1031, "提交时间[%s]+31天>定时发送时间[%s]>提交时间[%s] 规则不成立");
		errors.put(1032, "无签名或者签名规则不正确，具体规则咨询客户经理");
		errors.put(1034, "内容中不能包含“|”。“|”系统中作为内容拆分分隔符使用。");
		errors.put(2001, "余额不足");

		return errors;
	}
}
