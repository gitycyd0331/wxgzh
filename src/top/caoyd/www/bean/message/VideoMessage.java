package top.caoyd.www.bean.message;


/**
 * 视频消息
 * 
 * @author 曹亚东
 *
 */
public class VideoMessage extends BaseMessage {

	private String mediaId;
	private String thumbMediaId;

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	public String getThumbMediaId() {
		return thumbMediaId;
	}

	public void setThumbMediaId(String thumbMediaId) {
		this.thumbMediaId = thumbMediaId;
	}

	@Override
	public String toString() {
		return "VideoMessage [mediaId=" + mediaId + ", thumbMediaId="
				+ thumbMediaId + "]";
	}

}
