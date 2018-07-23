
public class Info 
{
	private int Number;
	private int Queue;
	private Info next;
	private Info prev;
	private long ChatID;
	Info()
	{
		Number=-1;
		Queue=-1;
		next=null;
		prev=null;
		ChatID=0;
	}
	
	public Info getPrev() {
		return prev;
	}
	public void setPrev(Info prev) {
		this.prev = prev;
	}
	public int getNumber() {
		return Number;
	}
	public void setNumber(int number) {
		Number = number;
	}
	public int getQueue() {
		return Queue;
	}
	public void setQueue(int queue) {
		Queue = queue;
	}
	public Info getNext() {
		return next;
	}
	public void setNext(Info next) {
		this.next = next;
	}

	public long getChatID() {
		return ChatID;
	}

	public void setChatID(long ChatID) {
		this.ChatID = ChatID;
	}
	
}
