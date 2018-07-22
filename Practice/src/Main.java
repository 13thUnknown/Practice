
/**
 * @author Mike
 * Methods:
 * !!!!!!!!!!!!!!Нумерация идёт с 1!!!!!!!!!!!!!!
 * .addList(int TableSize) Создаёт новую очередь. На вход идёт размер стола.
 * .deleteList(int ListNum) Удаляет очередь с выбранным номером. Возвращает значение true или false в зависимости от того, удалилась ли очерель или нет.
 * .numberList() Возращает int значение кол-ва очередей.
 * .add(int AmountPeople,long ChatID) Добавление в список. На вход идёт номер очереди(стола) и номер телефона.
 * .out(int numberList) Выводит список на экран. Возможна переработка. Если список пусто то выведет сообщение на экран.
 * .delete(int numberList,int Number) Удаляет элемент с таким же номером. На вход идёт номер.
 * .delete(int numberList, long ChatID) Удаление юзера из очереди. Возращает результат удаления.
 * .info(int numberList) Выводит на экран линейный список и состояние процессора.
 * .workerIsEmpty(int numberList) Возращает состояние процессора
 * .abort(int numberList) Прерывает выполнение процесса
 * .abort(int numberList, long ChatID) Прерывает процесс при совпадении номера телефона
 * .outQueue(int numberList, long ChatID) Принимает ChatID и вызвращает номер в очереди. 
 * .accessStatus(int numberList) Возращает значение параметра enable данного стола.
 * .accessSwitch(int numberList) Меняет значение параметра enable на противоположное.
 * .userInQueue(long ChatID) Проверяет на наличие юзера в очереди и выводит номер стола, в очереди которого он стоит. Вернёт -1 если не стоит в очереди.
 * .userInProcessor(long ChatID) Проверяет на наличие юзера в прокцессоре и выводит номер стола, в за которым он сейчас сидит. Вернёт -1 если не сидит ни за каким столом.
 * .getQueue(int numberList) Принимает номер стола и возвращает кол-во людей в очереди
 * .getMaxTableSize() Возвращает максимально доступное чисто мест.
 * .returnChatID(int Queue,int ListNum) Принимает порядкый номер и возвращает ChatID. Нумерация начинается с 1.
 * .returnNumber(int Queue,int ListNum) Принимает порядкый номер и возвращает фиксированный номер. Нумерация начинается с 1.
 * .getTableSize(int ListNum) На вход идёт номер стола. Возвращает размер стола.
 * .getWorkerChatID(int ListNum) На вход идёт номер стола. Возвращает ChatID из процессора.
 * .getWorkerNumber(int ListNum) На вход идёт номер стола. Возвращает фиксированный номер из процессора.
 * .getBegin() Получение указателья на первый стол.
 * .hardAdd(int ListNum, long ChatID, int Number) Добавляет пользователя с данным ChatID, в данный стол, с данным номером.
 * .hardEnd() Завершает добавление из файла
 * .hardAddList(int tableSize,int timer,int numberList) Добавляет стол с данными параметрами.
 * TODO
 * -Перевод кол-ва мест в столе в String
 * -Информация о столе
 * -Возвращает кол-во мест стола по номеру
 * -Возвращение ячейки по номеру в очереди
 * 
 * TODO 
 * -Перевод номера стола в string
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
