
public class ListArray 
{
	private int numberList;
	private Table Begin;
	private Table End;
	private int timer;
	ListArray(int timer)
	{
		this.numberList=0;
		this.Begin=null;
		this.End=null;
		this.timer=timer;
	}

	public void addList(int tableSize)
	{
		numberList++;
		Table NewList=new Table(timer,numberList,tableSize);
		if (Begin==null&&End==null)
		{
			Begin = NewList;
			End = NewList;	
		}
		else
		{
			End.setNext(NewList);
			End=End.getNext();
		}
	}
	public void hardAddList(int tableSize,int timer,int numberList)
	{
		Table NewList=new Table(timer,numberList,tableSize);
		if (Begin==null&&End==null)
		{
			Begin = NewList;
			End = NewList;	
		}
		else
		{
			End.setNext(NewList);
			End=End.getNext();
		}
	}
	public void fixList(Table pointer)
	{
		while (pointer!=null)
		{
			pointer.setListNum(pointer.getListNum()-1);
			pointer=pointer.getNext();
		}
		
	}
	public boolean deleteList(int ListNum)
	{
		Table pointer=Begin;
		Table prev=Begin;
		numberList--;
		if (Begin.getListNum()==ListNum)
		{
			Begin=Begin.getNext();
			pointer.setNext(null);
			this.fixList(Begin);
			return true;
		}
		while (pointer!=null)
		{
			if (pointer.getListNum()==ListNum)
			{
				if (pointer.getNext()==null)
				{
					prev.setNext(null);
					End=prev;
				}
				else
				{
					prev.setNext(pointer.getNext());
					pointer.setNext(null);
					fixList(prev.getNext());
				}
				return true;
			}
			prev=pointer;
			pointer=pointer.getNext();
		}
		numberList++;
		return false;
	}
	public int numberList()
	{
		int i=0;
		Table pointer=Begin;
		while (pointer!=null)
		{
			i++;
			pointer=pointer.getNext();
		}
		return i;
	}
	public boolean add(int AmountPeople,long ChatID)
	{
		Table PriorPointer=null;
		Table pointer=Begin;
		int priority=-1;
		if (this.userInQueue(ChatID)==-1 && this.userInProcessor(ChatID)==-1)
		{
			while (pointer != null) 
			{
				if (pointer.getTableSize() >= AmountPeople && pointer.accessStatus()==true) 
				{
					if (priority==-1)
					{
						priority=pointer.getPriority(AmountPeople);
						PriorPointer=pointer;
					}
					if (priority>pointer.getPriority(AmountPeople))
					{
						priority=pointer.getPriority(AmountPeople);
						PriorPointer=pointer;
					}
				}
				pointer = pointer.getNext();
			}
			PriorPointer.add(ChatID);
			return true;
		}
		return false;
	}
	public void hardAdd(int ListNum, long ChatID, int Number)
	{
		Table pointer=Begin;
		while (pointer!=null)
		{
			if (pointer.getListNum()==ListNum)
			{
				pointer.hardAdd(ChatID, Number);
				return;
			}
			pointer=pointer.getNext();
		}
	}
	public void hardEnd()
	{
		Table pointer=Begin;
		while (pointer!=null)
		{
			pointer.hardEnd();
			pointer=pointer.getNext();
		}
	}
	public void out(int numberList)
	{
		Table pointer=Begin;
		while (pointer!=null)
		{
			if (pointer.getListNum()==numberList)
			{
				pointer.out();
				return;
			}
			pointer=pointer.getNext();
		}
	}
	
	public void delete(int numberList,int Number)
	{
		Table pointer=Begin;
		while (pointer!=null)
		{
			if (pointer.getListNum()==numberList)
			{
				pointer.delete(Number);
				return;
			}
			pointer=pointer.getNext();
		}
	}
	public void delete(int numberList, long ChatID)
	{
		Table pointer=Begin;
		while (pointer!=null)
		{
			if (pointer.getListNum()==numberList)
			{
				pointer.delete(ChatID);
				return;
			}
			pointer=pointer.getNext();
		}
	}
	
	public void checker()
	{
		Table pointer=Begin;
		while (pointer!=null)
		{
			pointer.checker();
			pointer=pointer.getNext();
		}
	}
	public void info(int numberList)
	{
		Table pointer=Begin;
		while (pointer!=null)
		{
			if (pointer.getListNum()==numberList)
			{
				pointer.info();
				return;
			}
			pointer=pointer.getNext();
		}
	}
	public boolean workerIsEmpty(int numberList)
	{
		Table pointer=Begin;
		while (pointer!=null)
		{
			if (pointer.getListNum()==numberList)
			{
				return pointer.workerIsEmpty();
			}
			pointer=pointer.getNext();
		}
		System.out.println("Очередь с данным номером не найдена.");
		return false;
	}
	
	public void abort(int numberList)
	{
		Table pointer=Begin;
		while (pointer!=null)
		{
			if (pointer.getListNum()==numberList)
			{
				pointer.abort();
				return;
			}
			pointer=pointer.getNext();
		}
	}
	
	public void abort(int numberList, long ChatID)
	{
		Table pointer=Begin;
		while (pointer!=null)
		{
			if (pointer.getListNum()==numberList)
			{
				pointer.abort(ChatID);
				return;
			}
			pointer=pointer.getNext();
		}
	}
	
	public int outQueue(int numberList, long ChatID)
	{
		Table pointer=Begin;
		while (pointer!=null)
		{
			if (pointer.getListNum()==numberList)
			{
				return pointer.outQueue(ChatID);
			}
			pointer=pointer.getNext();
		}
		return -1;
	}
	
	public void accessSwitch(int numberList) 
	{
		Table pointer=Begin;
		while (pointer!=null)
		{
			if (pointer.getListNum()==numberList)
			{
				pointer.accessSwitch();
				return;
			}
			pointer=pointer.getNext();
		}
	}
	
	public boolean accessStatus(int numberList)
	{
		Table pointer=Begin;
		while (pointer!=null)
		{
			if (pointer.getListNum()==numberList)
			{
				return pointer.accessStatus();
			}
			pointer=pointer.getNext();
		}
		return false;
	}
	public int userInQueue(long ChatID)
	{
		Table pointer=Begin;
		while (pointer!=null)
		{
			if (pointer.userInQueue(ChatID)==true)
			{
				return pointer.getListNum();
			}
			pointer=pointer.getNext();
		}
		return -1;
	}
	public int userInProcessor(long ChatID)
	{
		Table pointer=Begin;
		while (pointer!=null)
		{
			if (pointer.IsInProcessor(ChatID)==true)
			{
				return pointer.getListNum();
			}
			pointer=pointer.getNext();
		}
		return -1;
	}
	public int getQueue(int ListNum)
	{
		Table pointer=Begin;
		while (pointer!=null)
		{
			if (pointer.getListNum()==ListNum)
			{
				return pointer.getQueue();
			}
			pointer=pointer.getNext();
		}
		return -1;
	}
	public int getMaxTableSize()
	{
		int max=-1;
		Table pointer=Begin;
		while (pointer!=null)
		{
			if(pointer.getTableSize()>max)
				max=pointer.getTableSize();
			pointer=pointer.getNext();
		}
		return max;
	}
	
	public long returnChatID(int Queue,int ListNum)
	{
		try {
			AltherThread.sleep(50);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Table pointer=Begin;
		while (pointer!=null)
		{
			if (pointer.getListNum()==ListNum)
			{
				return pointer.returnChatID(Queue);
			}
			pointer=pointer.getNext();
		}
		return -1;
	}
	
	public int returnNumber(int Queue,int ListNum)
	{
		try {
			AltherThread.sleep(50);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Table pointer=Begin;
		while (pointer!=null)
		{
			if (pointer.getListNum()==ListNum)
			{
				return pointer.returnNumber(Queue);
			}
			pointer=pointer.getNext();
		}
		return -1;
	}
	public int getTableSize(int ListNum)
	{
		Table pointer=Begin;
		while (pointer!=null)
		{
			if (pointer.getListNum()==ListNum)
			{
				return pointer.getTableSize();
			}
			pointer=pointer.getNext();
		}
		return -1;
	}
	
	public int getWorkerNumber(int ListNum)
	{
		Table pointer=Begin;
		while (pointer!=null)
		{
			if (pointer.getListNum()==ListNum)
			{
				return pointer.getWorkerNumber();
			}
			pointer=pointer.getNext();
		}
		return -1;
	}
	
	public long getWorkerChatID(int ListNum)
	{
		Table pointer=Begin;
		while (pointer!=null)
		{
			if (pointer.getListNum()==ListNum)
			{
				return pointer.getWorkerChatID();
			}
			pointer=pointer.getNext();
		}
		return -1;
	}

	public Table getBegin() {
		return Begin;
	}

	public void setBegin(Table begin) {
		Begin = begin;
	}
	
}
