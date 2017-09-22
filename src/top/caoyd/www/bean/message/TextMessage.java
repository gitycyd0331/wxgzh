package top.caoyd.www.bean.message;


/**
 * 文本消息
 * 
 * @author 曹亚东
 *
 */
public class TextMessage extends BaseMessage {
	// 消息内容
	private String Content;

	public TextMessage() {
		super();
	}

	public TextMessage(String content) {
		this.Content = content;
	}

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		this.Content = content;
	}

	@Override
	public String toString() {
		return "TextMessage [Content=" + Content + "]";
	}
}
