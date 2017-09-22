package top.caoyd.www.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * 微信消息处理的工具类
 * 
 * @author 曹亚东
 *
 */
public class WechatMessageUtil {

	private static final Logger log = Logger.getLogger(WechatMessageUtil.class);

	/**
	 * 解析微信发来的请求 XML
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> pareXml(HttpServletRequest request)
			throws Exception {

		// 将解析的结果存储在HashMap中
		Map<String, String> reqMap = new HashMap<String, String>();

		// 从request中取得输入流
		InputStream inputStream = request.getInputStream();
		// 读取输入流
		SAXReader reader = new SAXReader();
		Document document = reader.read(inputStream);
		// 得到xml根元素
		Element root = document.getRootElement();
		// 得到根元素的所有子节点
		List<Element> elementList = root.elements();
		// 遍历所有的子节点取得信息类容
		for (Element elem : elementList) {
			reqMap.put(elem.getName(), elem.getText());
		} // 释放资源
		inputStream.close();
		inputStream = null;

		return reqMap;
	}

	/**
	 * 将xml转化为Map集合
	 * 
	 * @param request
	 * @return
	 */
	public static Map<String, String> xmlToMap(HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();
		SAXReader reader = new SAXReader();
		InputStream ins = null;
		try {
			ins = request.getInputStream();
		} catch (IOException e) {
			log.error("WechatMessageUtil类，input异常" + e);
		}
		Document doc = null;
		try {
			doc = reader.read(ins);
		} catch (DocumentException e) {
			log.error("WechatMessageUtil类，读取异常" + e);
		}
		Element root = doc.getRootElement();
		@SuppressWarnings("unchecked")
		List<Element> list = root.elements();
		for (Element e : list) {
			map.put(e.getName(), e.getText());
		}
		try {
			ins.close();
		} catch (IOException e) {
			log.error("WechatMessageUtil类，io关闭异常" + e);
		}
		return map;
	}

	/**
	 * 对象转微信所需的XML格式
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public static String beanToXML(Object obj) throws Exception {
		StringBuffer sb = new StringBuffer("<xml>\n");

		// 类的class和不含继承的属性
		Class<?> clazz = obj.getClass();
		Field[] fields = clazz.getDeclaredFields();

		// 父类Class和不含继承的属性
		Class<?> superClass = clazz.getSuperclass();
		Field[] superFields = superClass.getDeclaredFields();

		// 拼装父类的字段和字段值
		String superFieldName = null;
		Object superFieldNamevlaue = null;
		for (Field field : superFields) {
			field.setAccessible(true);
			superFieldName = field.getName(); // 得到父类的字段名
			superFieldNamevlaue = field.get(obj); // 得到父类的字段值
			if (superFieldNamevlaue == null) {
				continue;
			}
			sb.append("<").append(superFieldName).append(">");
			if ("CreateTime".equals(superFieldName)
					|| "MsgId".equals(superFieldName)) {
				sb.append(superFieldNamevlaue);
			} else {
				sb.append("<![CDATA[").append(superFieldNamevlaue)
						.append("]]>");
			}
			sb.append("</").append(superFieldName).append(">\n");
		}

		// 拼装自身的字段和字段值
		String fieldName = null;
		Object mapObj = null;
		for (Field field : fields) {
			field.setAccessible(true);// 获得权限
			fieldName = field.getName();// 获得字段名
			mapObj = field.get(obj);// 获得字段值
			// 如果字段是Map类型，形如ImageRspMsg类中Map字段
			if (mapObj instanceof Map) {
				// 迭代map集合
				StringBuffer mapFieldValue = new StringBuffer("");
				String key = "";
				@SuppressWarnings("rawtypes")
				Map castMap = (Map) mapObj;
				@SuppressWarnings("rawtypes")
				Iterator iterator = castMap.keySet().iterator();
				while (iterator.hasNext()) {
					// 迭代
					key = (String) iterator.next();
					mapFieldValue.append("<").append(key).append(">");
					// 调用value的toString方法
					mapFieldValue.append("<![CDATA[")
							.append(castMap.get(key).toString()).append("]]>");
					mapFieldValue.append("</").append(key).append(">\n");
				}
				sb.append("<").append(fieldName).append(">\n");
				sb.append(mapFieldValue); // map集合内的迭代结果，勿加CDATA
				sb.append("</").append(fieldName).append(">\n");
			}
			// 字段非Map类型，则按照String类型处理（获得value时直接调用toString方法）
			else {
				sb.append("<").append(fieldName).append(">");
				sb.append("<![CDATA[").append(mapObj).append("]]>");
				sb.append("</").append(fieldName).append(">\n");
			}
		}

		sb.append("</xml>");

		return sb.toString();
	}

	// /**
	// * 文本消息转化为xml 响应消息转换成xml返回 文本对象转换成xml
	// *
	// * @param textMessage
	// * @return
	// */
	// public static String textMessageToXml(TextMessage textMessage) {
	// xstream.alias("xml", textMessage.getClass());
	// return xstream.toXML(textMessage);
	// }
	//
	// /**
	// *
	// * 扩展xstream，使其支持CDATA块
	// */
	// private static XStream xstream = new XStream(new XppDriver() {
	// public HierarchicalStreamWriter createWriter(Writer out) {
	// return new PrettyPrintWriter(out) {
	// // 对全部xml节点的转换都添加CDATA标记
	// boolean cdata = true;
	// public void startNode(String name, Class clazz) {
	// super.startNode(name, clazz);
	// if (name.equals("CreateTime")) {
	// cdata = false;
	// } else {
	// cdata = true;
	// }
	// }
	//
	// protected void writeText(QuickWriter writer, String text) {
	// if (cdata) {
	// writer.write("<![CDATA[");
	// writer.write(text);
	// writer.write("]]>");
	// } else {
	// writer.write(text);
	// }
	// }
	// };
	// }
	// });

}