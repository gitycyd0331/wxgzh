package top.caoyd.www.service.serviceImpl;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import top.caoyd.www.bean.message.ImageMessage;
import top.caoyd.www.bean.message.TextMessage;
import top.caoyd.www.bean.message.VoiceMessage;
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
	 *  实现消息处理
	 */
	public String processRequest(HttpServletRequest request) {
		Map<String, String> map = WechatMessageUtil.xmlToMap(request);
		
		String toUserName = map.get("FromUserName");

		String fromUserName = map.get("ToUserName");

		String msgType = map.get("MsgType");
		
		String content = map.get("Content");
		
		long createTime = new Date().getTime();
		
		String responseMessage = "success";
		log.debug("类型:" + msgType);
		log.debug("收到的参数是:" + map);
		switch (msgType) {
		//关注和取消关注
		case WxConst.MESSSAGE_TYPE_EVENT:
			try {
				//事件类型，subscribe(订阅)、unsubscribe(取消订阅)
				String event = map.get("Event");
				TextMessage textMessage = null;
				//订阅
				if("subscribe".endsWith(event)){
					textMessage = new TextMessage("欢迎关注本测试号");
				}
				//取消订阅
				if("unsubscribe".endsWith(event)){
					textMessage = new TextMessage("欢迎您再次关注");
				}
				textMessage.setToUserName(toUserName);
				textMessage.setFromUserName(fromUserName);
				textMessage.setCreateTime(new Date().getTime());
				textMessage.setMsgType(msgType);
				responseMessage = WechatMessageUtil.beanToXML(textMessage);
			} catch (Exception e) {
				log.error("文本消息转化为xml失败" + e);
			}
			return responseMessage;
		// 文本
		case WxConst.MESSSAGE_TYPE_TEXT:
			try {
				TextMessage textMessage = new TextMessage(RobotUtil
						.sendMessage(content.trim().replaceAll(" ", "")).getText());
				textMessage.setToUserName(toUserName);
				textMessage.setFromUserName(fromUserName);
				textMessage.setCreateTime(new Date().getTime());
				textMessage.setMsgType(msgType);
				responseMessage = WechatMessageUtil.beanToXML(textMessage);
			} catch (Exception e) {
				log.error("文本消息转化为xml失败" + e);
			}
			return responseMessage;
			// 图片
		case WxConst.MESSSAGE_TYPE_IMAGE:
			String mediaIdImage = map.get("MediaId");
			ImageMessage imageMessage = new ImageMessage(mediaIdImage);
			imageMessage.setToUserName(toUserName);
			imageMessage.setFromUserName(fromUserName);
			imageMessage.setCreateTime(createTime);
			imageMessage.setMsgType(msgType);
			try {
				responseMessage = WechatMessageUtil.beanToXML(imageMessage);
			} catch (Exception e) {
				log.error("文本消息转化为xml失败" + e);
			}
			return responseMessage;
			// 语音
		case WxConst.MESSSAGE_TYPE_VOICE:
			String mediaIdvoice = map.get("MediaId");
			VoiceMessage voiceMessage = new VoiceMessage(mediaIdvoice);
			voiceMessage.setToUserName(toUserName);
			voiceMessage.setFromUserName(fromUserName);
			voiceMessage.setCreateTime(createTime);
			voiceMessage.setMsgType(msgType);
			try {
				responseMessage = WechatMessageUtil.beanToXML(voiceMessage);
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
