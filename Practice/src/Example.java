import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

import java.util.ArrayList;
import java.util.List;

public class Example extends TelegramLongPollingBot {
	
	private int number;
	private String login="11", password = "22";
	private Long ChatID;	
	public static List<Long> admins = new ArrayList<>();
	public static List<Long> isitadmin = new ArrayList<>();
	public static int Timer=60000;
	public static ListArray Queue=new ListArray(Timer);
	public static AltherThread NewThread = new AltherThread(Queue, 1000);
	public static List<Long> IDs = new ArrayList<>();
	public static List<Integer> commands = new ArrayList<>();
	
 	public static void main(String[] args) {
		NewThread.start();
		ApiContextInitializer.init();
		TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
		try {
			telegramBotsApi.registerBot(new Example());
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}
	
 	public static void setCommand(Long ID, int command) 
 	{   if (command == 0 && IDs.contains(ID)) 
 		{
 			commands.remove(IDs.indexOf(ID));
 			IDs.remove(ID);
 		}
 		else if (IDs.contains(ID)) 
 		{
 			commands.set(IDs.indexOf(ID), command);
 		}
 		else if (IDs.contains(ID) == false)
 		{
 			IDs.add(ID);
 			commands.add(command);
 		}
 	}
 	
 	public static int getCommand(Long ID)
 	{
 		if (IDs.contains(ID)) {
 			return commands.get(IDs.indexOf(ID));	
 		}
 		else
 			return 0;
 	}
 	
 	private void setLogin (String newLogin)
	{
		if (admins.contains(ChatID))
		login = newLogin;
	}
	
	private void setPassword(String newPassword)
	{
		if (admins.contains(ChatID))
			password = newPassword;
	}

	private Boolean adminSignin(Message message) 
	{	
		if (isitadmin.contains(message.getChatId()) == false && message.getText().contentEquals(login) == true) {
			isitadmin.add(message.getChatId());
			sendMsg(message,"Логин введен",0);			
		}		
		else if (isitadmin.contains(message.getChatId()) && message.getText().contentEquals(password) == true) {			
			sendMsg(message,"Режим администратора включен",0);
			admins.add(message.getChatId());
			isitadmin.remove(message.getChatId());
		}
		else if (isitadmin.contains(message.getChatId()) && message.getText().contentEquals(password) == false) {
			isitadmin.remove(message.getChatId());
		}
		return admins.contains(message.getChatId());
	}
	
	private void adminOut(Long ChatID)
	{
		admins.remove(ChatID);
	}
	
	@Override
	public String getBotUsername() {
		return "IMA_queue_bot";
	}
 
	@Override
	public String getBotToken() {
		return "603908310:AAHrm8i46WcppLG43VSVTEVVyFCvaXqtw_A";
	}
	
	@Override
	public void onUpdateReceived(Update update) 
	{
        Message message = update.getMessage();
        
        if (message != null) {
        	ChatID = message.getChatId();
        }
        
       
        Boolean Signin = false;
        if (message != null && message.hasText() && admins.contains(ChatID)== false && getCommand(ChatID) == 0) 
        {
        	//активация режима администратора
        	Signin = adminSignin(message);
        }       
    	
        
       if (getCommand(ChatID) == 1 && message!= null) //смена логина
        {
    	   setCommand(ChatID,-1);
        	if (message.hasText()) 
        	{
        		setLogin(message.getText());
        		sendMsg(message,"Логин успешно изменен", 0);        		
        	}
        	else sendMsg(message,"Ошибка при смене логина", 0);        	
       }
       
       if (getCommand(ChatID) == 2 && message!= null) //смена пароля
       {
    	   setCommand(ChatID,-1);
       	if (message.hasText()) 
       	{
       		setPassword(message.getText());
       		sendMsg(message,"Пароль успешно изменен", 0);        		
       	}
       	else sendMsg(message,"Ошибка при смене пароля", 0);        	
       }
       
       if (getCommand(ChatID) == 10 && message != null)//удаление по номеру (номер вводится с клавиатуры)
       {
    	   setCommand(ChatID,-1);
    	   if (message.hasText())
    	   {
    		   Queue.delete(Queue.userInQueue(ChatID), ChatID);    		  
    		   sendMsg(message,"Удаление завершено", 0);
    	   }
    	   
       }
        
        
        if (message != null && message.hasText() && Signin == false && getCommand(ChatID) != -1) 
        {
            switch (message.getText()) 
            {
            	case "/start":
            		sendMsg(message, "Добро пожаловать, в ресторан *Здесь может быть название вашего ресторана*", 1);
            		setCommand(ChatID,0);
            		break;
            		
                case "Занять очередь":
                	if (Queue.numberList() > 0) {
                		sendMsg(message, "Выберите требуемое количество мест", 4);     
                        setCommand(ChatID,12);
                	}
                	else sendMsg(message, "Извините, на данный момент нет доступных столов!", 1);
                    break;
                    
                case "Посмотреть позицию":
                	 if (Queue.userInQueue(ChatID) != -1) 
                	 {
                		 int kol = Queue.outQueue(Queue.userInQueue(ChatID), ChatID);                		
          			     if (kol == -1)
          			    	 kol = 0;         			     
          			   
                		 sendMsg(message, "Ваша позиция в очереди к столу номер "+
                         		String.valueOf(Queue.userInQueue(ChatID)) + " - "
                         		+ String.valueOf(kol),1);
                         sendMsg(message,"Ваш ChatId - " + String.valueOf(ChatID), 1);
                	 }
                    
                    else if (Queue.userInProcessor(ChatID)!= -1) {
                    	sendMsg(message,"Ваша очередь уже подошла, пройдите к столу номер "+ String.valueOf(Queue.userInProcessor(ChatID)),3);
                    }
                    else 
                    sendMsg(message,"Вы еще не заняли очередь к столу, чтобы это сделать, нахмите кнопку <Занять очередь>",1);           
                	setCommand(ChatID,0);
                    break;
                    
                case "Выйти из очереди":
                	 Queue.delete(Queue.userInQueue(ChatID), ChatID);
                    sendMsg(message, "Вы вышли из очереди, теперь если вы снова захотите занять место, вы попадете в конец очереди",1);
                    setCommand(ChatID,0);
                    break;                
               
               case "Закончить": 
            	   Queue.abort(Queue.userInProcessor(ChatID), ChatID);
            	   sendMsg(message,"Всего хорошего!", 1);
            	   setCommand(ChatID,0);
            	   break;
            	   
               case "/changelogin": 
            	   if (admins.contains(ChatID)) 
            	   { 
            		   setCommand(ChatID,1);
            		  sendMsg(message,"Введите новый логин",0);
            	   }
            	   else
            	   {
                       sendMsg(message, "Команда не распознана",1);
               		}
            	   break;
            	   
               case "/changepassword": 
            	   if (admins.contains(ChatID)) 
            	   { 
            		   setCommand(ChatID,2);
            		  sendMsg(message,"Введите новый пароль",0);
            	   }
            	   else
            	   {
                       sendMsg(message, "Команда не распознана",1);
                        
               	   }
            	   break;
               case"/addtable": 
            	   if (admins.contains(ChatID)) 
            	   {             		  
            		  sendMsg(message,"Ввыберите количество мест",6); 
            		  setCommand(ChatID,3);
            	   }
            	   else
            	   {
                       sendMsg(message, "Команда не распознана",1);
                        
               	   }
            	   break;
               case"/deltable": 
            	   if (admins.contains(ChatID)) 
            	   {   
            		   int kol = Queue.numberList();
            		   if (kol == -1)
            			   kol = 0;
            		   sendMsg(message,"Количество столов "+ String.valueOf(kol),0);
            		   sendMsg(message,"Выберите нужный стол!", 5);
            		   setCommand(ChatID,4);
            	   }
            	   else
            	   {
                       sendMsg(message, "Команда не распознана",1);
                        
               	   }
            	   break;
               case"/setaccsess": 
            	   if (admins.contains(ChatID)) 
            	   {             		  
            		   sendMsg(message,"Выберите нужный стол!", 5);
            		   setCommand(ChatID,5);
            	   }
            	   else
            	   {
                       sendMsg(message, "Команда не распознана",1);
                        
               	   }
            	   break;
               case"/tablelist":
            	   if (admins.contains(ChatID)) 
            	   {
            		   if (Queue.numberList() == -1 || Queue.numberList() == 0) {
            			   sendMsg(message,"Столы отсутствуют", 0); 
            		   }
            		   
            		   for (int i=1;i< Queue.numberList()+1; i++) {
            			   int kol = Queue.getQueue(i);
            			   		if (kol == -1)
                   			kol = 0;            			   
            			   sendMsg(message,"Стол #"+ 
            			   String.valueOf(i) +": "
            			   		+ "Кол-во людей в очереди - " + String.valueOf(kol) +"; "
            			   		+ "Кол-во мест - "+ String.valueOf(Queue.getTableSize(i))+"; "
            			   		+ "Доступ к столу - "+ String.valueOf(Queue.accessStatus(i)) +".", 0); 
            		   }           		   
            		   setCommand(ChatID,0);  
            	   }
            	   
            	   else
            	   {
                       sendMsg(message, "Команда не распознана",1);
                        
               	   }
            	   break;
               case"/tableinfo":
            	   if (admins.contains(ChatID)) 
            	   {
            		   sendMsg(message,"Выберите нужный стол!", 5);
            		   setCommand(ChatID,6);
            	   }
            	   else
            	   {
                       sendMsg(message, "Команда не распознана",1);
                        
               	   }
            	   break;
               case"/tableproc":
            	   if (admins.contains(ChatID)) 
            	   {
            		   sendMsg(message,"Выберите нужный стол!", 5);
            		   setCommand(ChatID,7);
            	   }
            	   else
            	   {
                       sendMsg(message, "Команда не распознана",1);
                        
               	   }
            	   break;
               case"/tablequeue":
            	   if (admins.contains(ChatID)) 
            	   {
            		   sendMsg(message,"Выберите нужный стол!", 5);
            		   setCommand(ChatID,8);
            	   }
            	   else
            	   {
                       sendMsg(message, "Команда не распознана",1);
                        
               	   }
            	   break;
               case"/abort": 
            	   if (admins.contains(ChatID)) 
            	   {
            		   sendMsg(message,"Выберите нужный стол!", 5);
            		   setCommand(ChatID,9);
            	   }
            	   else
            	   {
                       sendMsg(message, "Команда не распознана",1);
                        
               	   }
            	   break;
               case "/delqueue":
            	   if (admins.contains(ChatID)) 
            	   {
            		   sendMsg(message,"Введите ChatID удаляемого человека", 0);
            		   setCommand(ChatID,10);
            	   }
            	   else
            	   {
                       sendMsg(message, "Команда не распознана",1);
                        
               	   }
            	   break;
               case "/out":
            	   if (admins.contains(ChatID)) 
            	   {
            	   adminOut(ChatID);            	   
            	   sendMsg(message, "Выход из режима администратора",1);
            	   setCommand(ChatID,0);
            	   }            	   
            	   else
            	   {
                       sendMsg(message, "Команда не распознана",1);
                        
               		}
            	   break;
                default:
                	if (message.getText().contentEquals(login) == false) {
                		
                		sendMsg(message, "Команда не распознана",1);                        	
                	}                    
                    break;
            }
        }
        else if (update.hasCallbackQuery()) {
        	String call_data = update.getCallbackQuery().getData();
            ChatID = update.getCallbackQuery().getMessage().getChatId();
            number = Integer.parseInt(call_data); 
            int kol;
            switch(getCommand(ChatID)) {
            case 3:
            	Queue.addList(number);
            	System.out.println(number);
            	sendMsg(ChatID,"Стол добавлен!", 0);
            	setCommand(ChatID,-1);
            	break;
            case 4:
            	Queue.deleteList(number);
           	 	sendMsg(ChatID,"Стол удален!", 0);
           	 	setCommand(ChatID,-1);
            	break;
            case 5:
            	Queue.accessSwitch(number);
           	 	if (Queue.accessStatus(number) == true)
           	 		sendMsg(ChatID,"Доступ к столу с номером " + call_data +" открыт!" , 0);
           	 	else
           	 			sendMsg(ChatID,"Доступ к столу с номером " + call_data +" закрыт!" , 0);
           	 	setCommand(ChatID,-1);
            	break;
            case 6:
            	
            	if ((Queue.getQueue(number) == -1))
            			kol = 0;
            	else
            		kol = Queue.getQueue(number);
            	sendMsg(ChatID,"Стол #"+ 
         			   String.valueOf(number) +": "
         			   		+ "Кол-во людей в очереди - " + String.valueOf(kol) +"; "
         			   		+ "Кол-во мест - "+ String.valueOf(Queue.getTableSize(number))+"; "
         			   		+ "Доступ к столу - "+ String.valueOf(Queue.accessStatus(number)) +".", 0); 
            	setCommand(ChatID,-1);
            	break;
            case 7:
            	if (Queue.getWorkerChatID(number) != 0) 
            	{
            		sendMsg(ChatID,"Выполняемый процесс в столе номер " + call_data+": "
                			+ "ChatID - "+ String.valueOf(Queue.getWorkerChatID(number))+"; "
                			+ "Номер талона - " +String.valueOf(Queue.getWorkerNumber(number)), 0); 
            	}
            	else
            		sendMsg(ChatID,"Выполняемый процесс в столе номер " + call_data+": отсутствует", 0);
            	           	
            	setCommand(ChatID,-1);
            	break;
            case 8:
            	if (Queue.getQueue(number) != -1) 
            	{
	            	sendMsg(ChatID,"Информация об очереди к столу " + call_data, 0);
	            	int j = Queue.getQueue(number)+1;
	            	for (int i=1; i< j; i++) {
	            		sendMsg(ChatID,"Позиция #"+ 
	                        	String.valueOf(i) +": ChatID - " + 
	                        	String.valueOf(Queue.returnChatID(i, number)) +"; Номер талона - "+ 
	                        	String.valueOf(Queue.returnNumber(i, number)) +".", 0);     
            	}	          
            	}
            	else
            		sendMsg(ChatID,"Очередь к столу " + call_data+" отсутствует", 0);
            	setCommand(ChatID,-1);
            	break;
            case 9:
            	Queue.abort(number);
            	sendMsg(ChatID,"Выполняемый процесс в столе номер " + call_data +" прерван!", 0);
            	setCommand(ChatID,-1);
            	break;
            case 12:
            	Queue.add(number, ChatID);
            	sendMsg(ChatID,"Вы заняли очередь за стол с количеством мест - " + call_data, 1);
            	if (Queue.userInQueue(ChatID) != -1) 
           	 	{
           		 kol = Queue.outQueue(Queue.userInQueue(ChatID), ChatID);
     			 if (kol == -1)
     			    	 kol = 0;         			     
     			   
           		 sendMsg(ChatID, "Ваша позиция в очереди к столу номер "+
                    		String.valueOf(Queue.userInQueue(ChatID)) + " - "
                    		+ String.valueOf(kol),1);
                    sendMsg(ChatID,"Ваш ChatId - " + String.valueOf(ChatID), 1);
           	 	}
               
               else if (Queue.userInProcessor(ChatID)!= -1) {
               	sendMsg(ChatID,"Ваша очередь уже подошла, пройдите к столу номер "+ String.valueOf(Queue.userInProcessor(ChatID)),1);
               }
            	setCommand(ChatID,-1);
            	break;
            default:
            	if (admins.contains(ChatID)) {
            		sendMsg(ChatID,"Нажатие кнопки не распознано, введите команду", 1);
            	}
            	else
            		sendMsg(ChatID,"Возникла непредвиденная ошибка, нажмите кнопку внизу экрана", 1);
            	break;
            }   
        }        
        if (getCommand(ChatID) == -1) setCommand(ChatID,0);
    }
	
	public InlineKeyboardMarkup Inlinemode(int mode) 
	{		
		InlineKeyboardMarkup inlinekeyboard = new InlineKeyboardMarkup();		
		List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();	
		
		if (mode == 0) {
			keyboard.add(Row(1,5));
			keyboard.add(Row(6,10));
		}
		
		if (mode == 1)
		{			
			if (Queue.getMaxTableSize() > 0)
			{
				if (Queue.getMaxTableSize() > 5)
					keyboard.add(Row(1,5));
				else				
				keyboard.add(Row(1,Queue.getMaxTableSize()));
			}
			if (Queue.getMaxTableSize() > 5)
			{
				keyboard.add(Row(6,Queue.getMaxTableSize()));
			}
		}	
		inlinekeyboard.setKeyboard(keyboard);
		return inlinekeyboard;
	}

	public InlineKeyboardButton Button(int i)
	{
		InlineKeyboardButton button = new InlineKeyboardButton();
		button.setText(String.valueOf(i));
		button.setCallbackData(String.valueOf(i));
		return button;
	}
	
	public List<InlineKeyboardButton> Row(int begin, int end)
	{
		List<InlineKeyboardButton> row = new ArrayList<>();		
		for (int i= begin; i <=end; i++) 
		{			
			row.add(Button(i));
		}		
		return row;
	}
	
	public InlineKeyboardMarkup InlineTablemode() 
	{
		int kol_table= Queue.numberList();
			InlineKeyboardMarkup inlinekeyboard = new InlineKeyboardMarkup();
			List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
			int kol_rows = (kol_table / 8) + 1;
			int begin = 1, end = 0;
			if (kol_table<=8)
				end = kol_table;
			else end = 8;
			for (int i = 0; i < kol_rows; i++)
			{				
				if (end <= kol_table && begin <= kol_table)
				{					
					keyboard.add(Row(begin, end));
				}
				begin = end+1;
				end+=8;
				if (kol_table<end)
					end = kol_table;
			}			
			inlinekeyboard.setKeyboard(keyboard);
			return inlinekeyboard;
	}
	
	public ReplyKeyboardMarkup Mmode(int mode)
	{
		
		 ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
	        
	        replyKeyboardMarkup.setSelective(true);
	        replyKeyboardMarkup.setResizeKeyboard(true);
	        replyKeyboardMarkup.setOneTimeKeyboard(false);
	        
	        List<KeyboardRow> keyboard = new ArrayList<>();
        if (mode == 1) 
        { 
	        // Первая строчка клавиатуры
	        KeyboardRow keyboardFirstRow = new KeyboardRow();	       
	        keyboardFirstRow.add("Занять очередь");	       
	        keyboard.add(keyboardFirstRow);
        }
       
        else if(mode == 2) 
        {  
	        KeyboardRow keyboardFirstRow = new KeyboardRow();	              
	        keyboardFirstRow.add("Посмотреть позицию");	        
	        KeyboardRow keyboardSecondRow = new KeyboardRow();	       
	        keyboardSecondRow.add("Выйти из очереди");  
	        keyboard.add(keyboardFirstRow);
	        keyboard.add(keyboardSecondRow);
        }        
        else if (mode == 3) 
        {
        	KeyboardRow keyboardFirstRow = new KeyboardRow();
        	keyboardFirstRow.add("Закончить");
        	keyboard.add(keyboardFirstRow);
        }
        replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;
	}

	public void sendMsg (Long chatID, String text, int mode) {
		SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        // 0 - Стандартная клавиатура     		
        // 4 - Инлайн клавиатура выбора количества мест (для админа)
        // 5 - клавиатура выбора столов
        // 6 - Вывод количества мест (для создания столов)
        // В остальных на основе проверок наличия клиента в очереди или в процессе выводится клавиатура
        // Если пользователь в очереди: кнопки ПОСМОТРЕТЬ ПОЗЦИЮ и ВЫЙТИ ИЗ ОЧЕРЕДИ
        // Если пользователь в процессе: кнопка ЗАКОНЧИТЬ
        // иначе выводит кнопку ЗАНЯТЬ ОЧЕРЕДЬ    
        	
        if (mode == 0 || (mode == 1 && admins.contains(ChatID)))
        {
        	ReplyKeyboardRemove keyboard = new ReplyKeyboardRemove();
        	sendMessage.setReplyMarkup(keyboard);
        }        
        
        else if (mode == 4) 
        {        	
        	sendMessage.setReplyMarkup(Inlinemode(1));
        }
        else if (mode == 5)
        {
        	sendMessage.setReplyMarkup(InlineTablemode());
        }
        else if (mode == 6)
        {
        	sendMessage.setReplyMarkup(Inlinemode(0));
        }
        else if (Queue.userInQueue(chatID)!= -1)
        	sendMessage.setReplyMarkup(Mmode(2));
        else if (Queue.userInProcessor(chatID) != -1) 
        	sendMessage.setReplyMarkup(Mmode(3));
        else
        	sendMessage.setReplyMarkup(Mmode(1));
        
	    sendMessage.setChatId(chatID.toString());
	    sendMessage.setText(text);
	    try 
	    {
	        execute(sendMessage);
	    } 
	    catch (TelegramApiException e) 
	    {
	        e.printStackTrace();
	    }         
    }
	
    public void sendMsg (Message message, String text, int mode) {
    	SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
     // 0 - Стандартная клавиатура     		
        // 4 - Инлайн клавиатура выбора количества мест (для админа)
        // 5 - клавиатура выбора столов
        // 6 - Вывод количества мест (для создания столов)
        // В остальных на основе проверок наличия клиента в очереди или в процессе выводится клавиатура
        // Если пользователь в очереди: кнопки ПОСМОТРЕТЬ ПОЗЦИЮ и ВЫЙТИ ИЗ ОЧЕРЕДИ
        // Если пользователь в процессе: кнопка ЗАКОНЧИТЬ
        // иначе выводит кнопку ЗАНЯТЬ ОЧЕРЕДЬ    
        if (mode == 0 || (mode == 1 && admins.contains(message.getChatId())))
        {
        	ReplyKeyboardRemove keyboard = new ReplyKeyboardRemove();
        	sendMessage.setReplyMarkup(keyboard);
        }    
        
        else if (mode == 4) 
        {        	
        	sendMessage.setReplyMarkup(Inlinemode(1));
        }
        else if (mode == 5)
        {
        	sendMessage.setReplyMarkup(InlineTablemode());
        }
        else if (mode == 6)
        {
        	sendMessage.setReplyMarkup(Inlinemode(0));
        }
        else if (Queue.userInQueue(message.getChatId())!= -1)
        	sendMessage.setReplyMarkup(Mmode(2));
        else if (Queue.userInProcessor(message.getChatId()) != -1) 
        	sendMessage.setReplyMarkup(Mmode(3));
        else
        	sendMessage.setReplyMarkup(Mmode(1));
        
	    sendMessage.setChatId(message.getChatId().toString());
	    sendMessage.setText(text);
	    try 
	    {
	        execute(sendMessage);
	    } 
	    catch (TelegramApiException e) 
	    {
	        e.printStackTrace();
	    }       
    }
}
