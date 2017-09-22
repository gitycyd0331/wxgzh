package top.caoyd.www.bean.message;


/**
 * 图片消息
 * 
 * @author 曹亚东
 *
 */
public class ImageMessage extends BaseMessage {
	// 图片链接
	private String PicUrl;
	//图片消息媒体id
	private String MediaId; 
	public String getPicUrl() {
		return PicUrl;
	}

	public void setPicUrl(String picUrl) {
		PicUrl = picUrl;
	}

	public String getMediaId() {
		return MediaId;
	}

	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}

	@Override
	public String toString() {
		return "ImageMessage [PicUrl=" + PicUrl + "]";
	}

}
