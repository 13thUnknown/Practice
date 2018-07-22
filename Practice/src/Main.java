
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
 * .getTableSize(int ListNum) �� ���� ��� ����� �����. ���������� ������ �����.
 * .getWorkerChatID(int ListNum) �� ���� ��� ����� �����. ���������� ChatID �� ����������.
 * .getWorkerNumber(int ListNum) �� ���� ��� ����� �����. ���������� ������������� ����� �� ����������.
 * .getBegin() ��������� ���������� �� ������ ����.
 * .hardAdd(int ListNum, long ChatID, int Number) ��������� ������������ � ������ ChatID, � ������ ����, � ������ �������.
 * .hardEnd() ��������� ���������� �� �����
 * .hardAddList(int tableSize,int timer,int numberList) ��������� ���� � ������� �����������.
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
		int Timer=100000;
		ListArray Queue=new ListArray(Timer);
		AltherThread NewThread=new AltherThread(Queue, 1000);
		NewThread.start();
		Queue.addList(1);
		Queue.addList(2);
		for (int i=0;i<10;i++)
			Queue.add(2, 123+i);
		Thread.sleep(1000);
		System.out.println(Queue.outQueue(1,3));
		while (!Queue.workerIsEmpty(2))
		{
			Queue.abort(Queue.userInProcessor(123),123);
//			AltherThread.sleep(500);
			Queue.info(1);
			Queue.info(2);
			Thread.sleep(1000);
		}
		System.exit(0);
	}

}
