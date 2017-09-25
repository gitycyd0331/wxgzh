package top.caoyd.www.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import top.caoyd.www.common.result.RobotResult;
import top.caoyd.www.common.util.CheckoutUtil;
import top.caoyd.www.service.WechatService;

/**
 * 
 * @author 曹亚东
 *
 */
@Controller
public class WechatController {
	private static final Logger log = Logger.getLogger(WechatController.class);
	@Autowired
	private WechatService wechatService;

	/**
	 * 微信消息接收和token验证
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/wechat.wx", method = RequestMethod.GET)
	public void wechatService(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		log.debug("进入到方法");
		PrintWriter print = null;
		// 微信加密签名
		String signature = request.getParameter("signature");
		// 时间戳
		String timestamp = request.getParameter("timestamp");
		// 随机数
		String nonce = request.getParameter("nonce");
		// 随机字符串
		String echostr = request.getParameter("echostr");
		// 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
		if (signature != null
				&& CheckoutUtil.checkSignature(signature, timestamp, nonce)) {
			try {
				print = response.getWriter();
				print.write(echostr);
				print.flush();
				print.close();
			} catch (IOException e) {
				log.error("验证失败 " + e);
			}
		}
	}

	/**
	 * 接收来自微信发来的消息
	 * 
	 * @param out
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/wechat.wx", method = RequestMethod.POST)
	public void wechatServicePost(HttpServletRequest request,
			HttpServletResponse response) {
		log.debug("进入请求" );
		PrintWriter print = null;
		String responseMessage = wechatService.processRequest(request);
		log.debug("返回的参数 " + responseMessage);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=utf-8");
		try {
			print = response.getWriter();
			print.print(responseMessage);
			print.flush();
			print.close();
		} catch (IOException e) {
			log.error("发送失败 " + responseMessage + e);
		}
	}

	@RequestMapping(value = "/ss.wx")
	public void ss(HttpServletRequest request, HttpServletResponse response) {
		wechatService.selectAll();
	}

	/**
	 * 图灵机器人
	 * @param request
	 * @param response
	 * @param info
	 * @return
	 */
	@ResponseBody
	@RequestMapping({"/{info}/robot.wx"})
	public RobotResult sendMessage(HttpServletRequest request,
			HttpServletResponse response, @PathVariable("info") String info) {
		try {
			if(info.equals(new String(info.getBytes("ISO-8859-1"),"ISO-8859-1"))){
				return wechatService.sendMessage(new String(info.getBytes("ISO-8859-1"),"utf-8"));
			}
			if(info.equals(new String(info.getBytes("utf-8"),"utf-8"))){
				return wechatService.sendMessage(info);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
