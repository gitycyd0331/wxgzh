package top.caoyd.www.common.result;

public class RobotResult extends BeanResult{
	
	private String text;
	private String url;

	public RobotResult(){}
	
	public RobotResult(String code, String text, String url) {
		super(code);
		this.text = text;
		this.url = url;
	}

	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getUrl() {
		return this.url;
	}
}
