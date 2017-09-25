package top.caoyd.www.bean.message;

import java.util.HashMap;
import java.util.Map;

/**
 * 语音消息
 * 
 * @author 曹亚东
 *
 */
public class VoiceMessage extends BaseMessage {

	// 语音格式
	private String Format;
	// 通过素材管理中的接口上传多媒体文件，得到的id
	private Map<String, String> Voice;

	public VoiceMessage() {
	}

	public VoiceMessage(String mediaId) {
		Voice = new HashMap<String, String>();
		Voice.put("MediaId", mediaId);
	}

	public String getFormat() {
		return Format;
	}

	public void setFormat(String format) {
		Format = format;
	}

	@Override
	public String toString() {
		return "VoiceMessage [Format=" + Format + ", Voice=" + Voice + "]";
	}

}
