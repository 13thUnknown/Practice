
public class Table 
{
	private int ListNum;
	private Info Begin;
	private Info End;
	private Processor Worker;
	private int timer;
	private int Number;
	private int MaxNumber;
	private boolean enable;
	private int TableSize;
	private Table next;
	
	Table(int timer,int ListNum,int TableSize)
	{
		this.ListNum=ListNum;
		this.Number=1;
		
		this.timer=timer;
		this.Worker=new Processor(this.timer);
		this.enable=true;
		this.TableSize=TableSize;
		this.MaxNumber=0;
	}
	
	public void add(long ChatID)
	{
		
		Info NewInfo=new Info();
		NewInfo.setChatID(ChatID);
		NewInfo.setNumber(Number);
		if (Begin==null&&End==null)
		{
			if (Worker.isEmpty())
			{
				Worker.fill(NewInfo);
			}
			else 
			{
				Begin = NewInfo;
				End = NewInfo;
				End.setQueue(1);
				
			}
		}
		else
		{
			Info Prev=End;
			End.setNext(NewInfo);
			End=End.getNext();
			End.setPrev(Prev);
			End.setQueue(Prev.getQueue()+1);
		}
		Number++;
	}
	public void hardAdd(long ChatID, int Number) 
	{
		this.Number=Number;
		if (MaxNumber<Number)
			MaxNumber=Number;
		this.add(ChatID);
	}
	public void hardEnd()
	{
		this.Number=this.MaxNumber+1;
	}
	public void out()
	{
		Info pointer=Begin;
		if (Begin==null)
		{
			System.out.println("Queue is empty");
			
		}
		else
		{
			while (pointer != null) 
			{
				System.out.println(pointer.getNumber() + " " + pointer.getQueue());
				pointer = pointer.getNext();
			}
		}
	}
	public void delete(int Number)
	{
		Info prev;
		Info pointer=Begin;
		if (Begin.getNumber()==Number)
		{
			Begin=Begin.getNext();
			pointer.setNext(null);
			this.fixQueue(Begin);
			return;
		}
		while (pointer!=null)
		{
			if (pointer.getNumber()==Number)
			{
				prev=pointer.getPrev();
				if (pointer.getNext()==null)
				{
					prev.setNext(null);
					pointer.setPrev(null);
				}
				else
				{
					prev.setNext(pointer.getNext());
					pointer.setNext(null);
					pointer.setPrev(null);
					this.fixQueue(prev.getNext());
				}
				return;
			}
			pointer=pointer.getNext();
		}
	}
	public void fixQueue(Info pointer)
	{
		while (pointer!=null)
		{
			pointer.setQueue(pointer.getQueue()-1);
			pointer=pointer.getNext();
		}
	}
	public boolean delete(long ChatID)
	{
		if (findNumber(ChatID)!=-1)
		{
			this.delete(findNumber(ChatID));
			return true;
		}
		return false;
	}
	
	public int findNumber(long ChatID)
	{
		Info pointer=Begin;
		while (pointer!=null)
		{
			if (pointer.getChatID()==ChatID)
				return pointer.getNumber();
			pointer=pointer.getNext();
		}
		return -1;	
	}
	
	public void checker() 
	{
		if (Worker.isEmpty() && Begin!=null)
		{
			Worker.fill(Begin);
			this.delete(Begin.getNumber());
		}
		else if (Worker.getReserved() < System.currentTimeMillis() && Begin!=null)
		{
			Worker.fill(Begin);
			this.delete(Begin.getNumber());
		}
		else if ((Worker.getReserved() < System.currentTimeMillis()) && Begin==null)
		{
			Worker.reset();
		}
	}
	public void info()
	{
		if (Worker.isEmpty())
			System.out.println("Worker is empty");
		else
			System.out.println("Worker under process ¹ "+Worker.getNumber());
		this.out();
	}
	public boolean workerIsEmpty()
	{
		return Worker.isEmpty();
	}
	public void abort()
	{
		Worker.reset();
	}
	public void abort(long ChatID)
	{
		Worker.reset(ChatID);
	}
	public int outQueue(long ChatID)
	{
		Info pointer=Begin;
		Number=findNumber(ChatID);
		while (pointer!=null) 
		{
			if (pointer.getNumber() == Number)
				return pointer.getQueue();
			else
				pointer = pointer.getNext();
		}
		return -1;
	}
	public int getQueue()
	{
		if (End!=null)
			return End.getQueue();
		else return -1;
	}
	
	public boolean userInQueue(long ChatID)
	{
		Info pointer=Begin;
		while (pointer!=null)
		{
			if (pointer.getChatID()==ChatID)
				return true;
			pointer=pointer.getNext();
		}
		return false;
	}
	
	public void accessSwitch()
	{
		if (enable==true)
			enable=false;
		else
			enable=true;
	}
	public boolean accessStatus()
	{
		return enable;
	}

	public Table getNext() {
		return next;
	}

	public void setNext(Table next) {
		this.next = next;
	}

	public int getListNum() {
		return ListNum;
	}

	public void setListNum(int listNum) {
		ListNum = listNum;
	}

	public int getTableSize() {
		return TableSize;
	}

	public void setTableSize(int tableSize) {
		TableSize = tableSize;
	}
	public boolean IsInProcessor(long ChatID)
	{
		return Worker.IsInProcessor(ChatID);
	}
	
	public int getPriority(int AmountPeople)
	{
		return (TableSize-AmountPeople)*5+this.getQueue()+1;
	}
	
	public long returnChatID(int Queue)
	{
		Info pointer=Begin;
		while (pointer!=null)
		{
			if (pointer.getQueue()==Queue)
			{
				return pointer.getChatID();
			}
			pointer=pointer.getNext();
		}
		return -1;
	}
	
	public int returnNumber(int Queue)
	{
		Info pointer=Begin;
		while (pointer!=null)
		{
			if (pointer.getQueue()==Queue)
			{
				return pointer.getNumber();
			}
			pointer=pointer.getNext();
		}
		return -1;
	}
	public int getWorkerNumber()
	{
		return Worker.getNumber();
	}
	public long getWorkerChatID()
	{
		return Worker.getChatID();
	}
	public Info getBegin() {
		return this.Begin;
	}

}
