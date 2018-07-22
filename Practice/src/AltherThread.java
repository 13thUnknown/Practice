public class AltherThread extends Thread
{
	private ListArray Queue;
	private int Timer;
	AltherThread(ListArray Queue,int Timer)
	{
		this.Queue=Queue;
		this.Timer=Timer;		
	}
	@Override
	public void run()
	{
		while (true)
		{
			Queue.checker();
			try {
				Thread.sleep(Timer);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}