package top.caoyd.www.service;

import javax.servlet.http.HttpServletRequest;

import top.caoyd.www.common.result.RobotResult;

/**
 * 微信接口
 * 
 * @author 曹亚东
 *
 */
public interface WechatService {

	/**
	 * 消息处理
	 * 
	 * @param paramHttpServletRequest
	 * @return
	 */
	public abstract String processRequest(
			HttpServletRequest paramHttpServletRequest);

	public abstract void selectAll();

	/**
	 * 聊天机器人
	 * @param paramString
	 * @return
	 * @throws Exception
	 */
	public abstract RobotResult sendMessage(String paramString)
			throws Exception;
}
