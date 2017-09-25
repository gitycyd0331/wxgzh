package top.caoyd.www.bean.message;

import java.util.HashMap;
import java.util.Map;

/**
 * 图片消息
 * 
 * @author 曹亚东
 *
 */
public class ImageMessage extends BaseMessage {
	// 图片链接
	private String PicUrl;
	//通过素材管理中的接口上传多媒体文件，得到的id
	private Map<String, String> Image;

	public ImageMessage() {
	}

	public ImageMessage(String mediaid) {
		Image = new HashMap<String, String>();
		Image.put("MediaId", mediaid);
	}

	public String getPicUrl() {
		return PicUrl;
	}

	public void setPicUrl(String picUrl) {
		PicUrl = picUrl;
	}

	@Override
	public String toString() {
		return "ImageMessage [PicUrl=" + PicUrl + "]";
	}

}
