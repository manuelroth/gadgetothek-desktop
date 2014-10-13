package dl;


public class Message {
	private String target;
	private String type;
	private String data;
	
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	
	public Message(String target, String type, String data )
	{
		this.target = target;
		this.type = type;
		this.data = data;
	}
}


