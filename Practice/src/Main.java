
/**
 * @author Mike
 * Methods:
 * !!!!!!!!!!!!!!��������� ��� � 1!!!!!!!!!!!!!!
 * .addList(int TableSize) ������ ����� �������. �� ���� ��� ������ �����.
 * .deleteList(int ListNum) ������� ������� � ��������� �������. ���������� �������� true ��� false � ����������� �� ����, ��������� �� ������� ��� ���.
 * .numberList() ��������� int �������� ���-�� ��������.
 * .add(int AmountPeople,long ChatID) ���������� � ������. �� ���� ��� ����� �������(�����) � ����� ��������.
 * .out(int numberList) ������� ������ �� �����. �������� �����������. ���� ������ ����� �� ������� ��������� �� �����.
 * .delete(int numberList,int Number) ������� ������� � ����� �� �������. �� ���� ��� �����.
 * .delete(int numberList, long ChatID) �������� ����� �� �������. ��������� ��������� ��������.
 * .info(int numberList) ������� �� ����� �������� ������ � ��������� ����������.
 * .workerIsEmpty(int numberList) ��������� ��������� ����������
 * .abort(int numberList) ��������� ���������� ��������
 * .abort(int numberList, long ChatID) ��������� ������� ��� ���������� ������ ��������
 * .outQueue(int numberList, long ChatID) ��������� ChatID � ���������� ����� � �������. 
 * .accessStatus(int numberList) ��������� �������� ��������� enable ������� �����.
 * .accessSwitch(int numberList) ������ �������� ��������� enable �� ���������������.
 * .userInQueue(long ChatID) ��������� �� ������� ����� � ������� � ������� ����� �����, � ������� �������� �� �����. ����� -1 ���� �� ����� � �������.
 * .userInProcessor(long ChatID) ��������� �� ������� ����� � ����������� � ������� ����� �����, � �� ������� �� ������ �����. ����� -1 ���� �� ����� �� �� ����� ������.
 * .getQueue(int numberList) ��������� ����� ����� � ���������� ���-�� ����� � �������
 * .getMaxTableSize() ���������� ����������� ��������� ����� ����.
 * .returnChatID(int Queue,int ListNum) ��������� �������� ����� � ���������� ChatID. ��������� ���������� � 1.
 * .returnNumber(int Queue,int ListNum) ��������� �������� ����� � ���������� ������������� �����. ��������� ���������� � 1.
 * 
 * TODO
 * -������� ���-�� ���� � ����� � String
 * -���������� � �����
 * -���������� ���-�� ���� ����� �� ������
 * -����������� ������ �� ������ � �������
 * 
 * TODO 
 * -������� ������ ����� � string
 */
public class Main {

	public static void main(String[] args) throws InterruptedException 
	{
		int Timer=30000;
		ListArray Queue=new ListArray(Timer);
		AltherThread NewThread=new AltherThread(Queue, 1000);
		NewThread.start();
		Queue.addList(6);
		Queue.addList(5);
		for (int i=0;i<10;i++)
			Queue.add(1, 123);
		
		System.out.println(Queue.outQueue(1,3));
		while (!Queue.workerIsEmpty(1))
		{
//			AltherThread.sleep(500);
			Queue.info(1);
			Thread.sleep(1000);
		}
		System.exit(0);
	}

}
