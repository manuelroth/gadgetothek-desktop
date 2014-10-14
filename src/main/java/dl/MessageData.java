package dl;

public class MessageData {
	private String target;
	private String type;
	private Dto<?> data;
	
	public String getTarget() {
		return target;
	}

	public String getType() {
		return type;
	} 
	
	public Dto<?> getData() {
		return data;
	} 
	
	
	public MessageData(String target, String type, Dto<?> data )
	{
		this.target = target;
		this.type = type;
		this.data = data;
	}
}