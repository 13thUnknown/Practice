
public class Processor
{
	private int number;
	private long ChatID;
	private long reserved;
	private boolean empty;
	private int timer;
	
	
	public long getPhoneNum() {
		return ChatID;
	}
	public void setPhoneNum(long ChatID) {
		this.ChatID = ChatID;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public long getReserved() {
		return reserved;
	}
	public void setReserved(long reserved) {
		this.reserved = reserved;
	}
	
	Processor(int timer)
	{
		ChatID=0;
		number=-1;
		reserved=-1;
		empty=true;
		this.timer=timer;
		
	}
	
	public long getChatID() {
		return ChatID;
	}
	public void setChatID(long chatID) {
		ChatID = chatID;
	}
	public int getTimer() {
		return timer;
	}
	public void setTimer(int timer) {
		this.timer = timer;
	}
	public boolean isEmpty() {
		return empty;
	}
	public void setEmpty(boolean empty) {
		this.empty = empty;
	}
	public void fill(Info pointer)
	{
		number=pointer.getNumber();
		reserved=System.currentTimeMillis() + timer;
		empty=false;
		ChatID=pointer.getChatID();
	}
	public void reset()
	{
		ChatID=0;
		number=-1;
		reserved=-1;
		empty=true;
	}

	public void reset(long ChatID)
	{
		if (this.ChatID==ChatID)
			this.reset();
		else
		{
			System.out.println("Неверный номер телефона!");
		}
	}
	
	public boolean IsInProcessor(long ChatID)
	{
		if (this.ChatID==ChatID)
			return true;
		else
			return false;
	}
	

	
	
	
	

}
