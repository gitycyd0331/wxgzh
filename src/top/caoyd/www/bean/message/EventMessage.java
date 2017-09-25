package top.caoyd.www.bean.message;

/**
 * 关注/取消关注事件
 * 
 * @author 曹亚东
 *
 */
public class EventMessage {

	//事件类型，subscribe(订阅)、unsubscribe(取消订阅)
	private String Event;

	public String getEvent() {
		return Event;
	}

	public void setEvent(String event) {
		Event = event;
	}

	@Override
	public String toString() {
		return "EventMessage [Event=" + Event + "]";
	}

}
