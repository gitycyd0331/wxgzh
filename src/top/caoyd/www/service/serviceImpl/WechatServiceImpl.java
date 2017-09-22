package top.caoyd.www.service.serviceImpl;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import top.caoyd.www.bean.message.ImageMessage;
import top.caoyd.www.bean.message.TextMessage;
import top.caoyd.www.common.cont.WxConst;
import top.caoyd.www.common.result.RobotResult;
import top.caoyd.www.common.util.RobotUtil;
import top.caoyd.www.common.util.WechatMessageUtil;
import top.caoyd.www.dao.IUserInstanceDao;
import top.caoyd.www.service.WechatService;

/**
 * 微信接口实现类
 * 
 * @author 曹亚东
 *
 */
@Service
public class WechatServiceImpl implements WechatService {

	private static Logger log = Logger.getLogger(WechatService.class);

	@Resource
	private IUserInstanceDao userInstanceDao;

	/**
	 * 实现消息处理
	 */
	public String processRequest(HttpServletRequest request) {
		Map<String, String> map = WechatMessageUtil.xmlToMap(request);

		String toUserName = map.get("FromUserName");

		String fromUserName = map.get("ToUserName");

		String msgType = map.get("MsgType");

		long createTime = new Date().getTime();

		String responseMessage = null;

		switch (msgType) {
		case WxConst.MESSSAGE_TYPE_TEXT:
			String content = map.get("Content");
			try {
				TextMessage textMessage = new TextMessage(RobotUtil
						.sendMessage(content).getText());
				textMessage.setToUserName(toUserName);
				textMessage.setFromUserName(fromUserName);
				textMessage.setCreateTime(new Date().getTime());
				textMessage.setMsgType(msgType);
				responseMessage = WechatMessageUtil.beanToXML(textMessage);
			} catch (Exception e) {
				log.error("文本消息转化为xml失败" + e);
			}
			return responseMessage;
		case WxConst.MESSSAGE_TYPE_IMAGE:
			String picUrl = map.get("PicUrl");
			// String mediaId = map.get("MediaId");
			ImageMessage imageMessage = new ImageMessage();
			imageMessage.setToUserName(toUserName);
			imageMessage.setFromUserName(fromUserName);
			imageMessage.setCreateTime(createTime);
			imageMessage.setMsgType(msgType);
			imageMessage
					.setPicUrl("http://www.iteye.com/upload/logo/user/603624/2dc5ec35-073c-35e7-9b88-274d6b39d560.jpg");
			try {
				responseMessage = WechatMessageUtil.beanToXML(imageMessage);
			} catch (Exception e) {
				log.error("文本消息转化为xml失败" + e);
			}
			return responseMessage;
		}
		log.debug("要回复的消息:" + responseMessage);
		return responseMessage;
	}

	@Override
	public void selectAll() {
		System.out.println(userInstanceDao.selectAll());
	}

	/**
	 * 聊天机器人
	 */
	public RobotResult sendMessage(String info) throws Exception {
		return RobotUtil.sendMessage(info);
	}
}
