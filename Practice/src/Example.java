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
	public static  ListArray Queue=new ListArray(Timer);
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
			sendMsg(message,"Ëîãèí ââåäåí",0);			
		}		
		else if (isitadmin.contains(message.getChatId()) && message.getText().contentEquals(password) == true) {			
			sendMsg(message,"Ðåæèì àäìèíèñòðàòîðà âêëþ÷åí",0);
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
        	//àêòèâàöèÿ ðåæèìà àäìèíèñòðàòîðà
        	Signin = adminSignin(message);
        }       
    	
        
       if (getCommand(ChatID) == 1 && message!= null) //ñìåíà ëîãèíà
        {
    	   setCommand(ChatID,-1);
        	if (message.hasText()) 
        	{
        		setLogin(message.getText());
        		sendMsg(message,"Ëîãèí óñïåøíî èçìåíåí", 0);        		
        	}
        	else sendMsg(message,"Îøèáêà ïðè ñìåíå ëîãèíà", 0);        	
       }
       
       if (getCommand(ChatID) == 2 && message!= null) //ñìåíà ïàðîëÿ
       {
    	   setCommand(ChatID,-1);
       	if (message.hasText()) 
       	{
       		setPassword(message.getText());
       		sendMsg(message,"Ïàðîëü óñïåøíî èçìåíåí", 0);        		
       	}
       	else sendMsg(message,"Îøèáêà ïðè ñìåíå ïàðîëÿ", 0);        	
       }
       
       if (getCommand(ChatID) == 10 && message != null)//óäàëåíèå ïî íîìåðó (íîìåð ââîäèòñÿ ñ êëàâèàòóðû)
       {
    	   setCommand(ChatID,-1);
    	   if (message.hasText())
    	   {
    		   Queue.delete(Queue.userInQueue(ChatID), ChatID);    		  
    		   sendMsg(message,"Óäàëåíèå çàâåðøåíî", 0);
    	   }
    	   
       }
        
        
        if (message != null && message.hasText() && Signin == false && getCommand(ChatID) != -1) 
        {
            switch (message.getText()) 
            {
            	case "/start":
            		sendMsg(message, "Äîáðî ïîæàëîâàòü, â ðåñòîðàí *Çäåñü ìîæåò áûòü íàçâàíèå âàøåãî ðåñòîðàíà*", 1);
            		setCommand(ChatID,0);
            		break;
            		
                case "Çàíÿòü î÷åðåäü":
                	if (Queue.numberList() > 0) {
                		sendMsg(message, "Âûáåðèòå òðåáóåìîå êîëè÷åñòâî ìåñò", 4);     
                        setCommand(ChatID,12);
                	}
                	else sendMsg(message, "Èçâèíèòå, íà äàííûé ìîìåíò íåò äîñòóïíûõ ñòîëîâ!", 1);
                    break;
                    
                case "Ïîñìîòðåòü ïîçèöèþ":
                	 if (Queue.userInQueue(ChatID) != -1) 
                	 {
                		 int kol = Queue.outQueue(Queue.userInQueue(ChatID), ChatID);
          			     if (kol == -1)
          			    	 kol = 0;         			     
          			   
                		 sendMsg(message, "Âàøà ïîçèöèÿ â î÷åðåäè ê ñòîëó íîìåð "+
                         		String.valueOf(Queue.userInQueue(ChatID)) + " - "
                         		+ String.valueOf(kol),1);
                         sendMsg(message,"Âàø ChatId - " + String.valueOf(ChatID), 1);
                	 }
                    
                    else if (Queue.userInProcessor(ChatID)!= -1) {
                    	sendMsg(message,"Âàøà î÷åðåäü óæå ïîäîøëà, ïðîéäèòå ê ñòîëó íîìåð "+ String.valueOf(Queue.userInProcessor(ChatID)),3);
                    }
                    else 
                    sendMsg(message,"Âû åùå íå çàíÿëè î÷åðåäü ê ñòîëó, ÷òîáû ýòî ñäåëàòü, íàõìèòå êíîïêó <Çàíÿòü î÷åðåäü>",1);           
                	setCommand(ChatID,0);
                    break;
                    
                case "Âûéòè èç î÷åðåäè":
                	 Queue.delete(Queue.userInQueue(ChatID), ChatID);
                    sendMsg(message, "Âû âûøëè èç î÷åðåäè, òåïåðü åñëè âû ñíîâà çàõîòèòå çàíÿòü ìåñòî, âû ïîïàäåòå â êîíåö î÷åðåäè",1);
                    setCommand(ChatID,0);
                    break;                
               
               case "Çàêîí÷èòü": 
            	   Queue.abort(Queue.userInProcessor(ChatID), ChatID);
            	   sendMsg(message,"Âñåãî õîðîøåãî!", 1);
            	   setCommand(ChatID,0);
            	   break;
            	   
               case "/changelogin": 
            	   if (admins.contains(ChatID)) 
            	   { 
            		   setCommand(ChatID,1);
            		  sendMsg(message,"Ââåäèòå íîâûé ëîãèí",0);
            	   }
            	   else
            	   {
                       sendMsg(message, "Êîìàíäà íå ðàñïîçíàíà",1);
               		}
            	   break;
            	   
               case "/changepassword": 
            	   if (admins.contains(ChatID)) 
            	   { 
            		   setCommand(ChatID,2);
            		  sendMsg(message,"Ââåäèòå íîâûé ïàðîëü",0);
            	   }
            	   else
            	   {
                       sendMsg(message, "Êîìàíäà íå ðàñïîçíàíà",1);
                        
               	   }
            	   break;
               case"/addtable": 
            	   if (admins.contains(ChatID)) 
            	   {             		  
            		  sendMsg(message,"Ââûáåðèòå êîëè÷åñòâî ìåñò",6); 
            		  setCommand(ChatID,3);
            	   }
            	   else
            	   {
                       sendMsg(message, "Êîìàíäà íå ðàñïîçíàíà",1);
                        
               	   }
            	   break;
               case"/deltable": 
            	   if (admins.contains(ChatID)) 
            	   {   
            		   int kol = Queue.numberList();
            		   if (kol == -1)
            			   kol = 0;
            		   sendMsg(message,"Êîëè÷åñòâî ñòîëîâ "+ String.valueOf(kol),0);
            		   sendMsg(message,"Âûáåðèòå íóæíûé ñòîë!", 5);
            		   setCommand(ChatID,4);
            	   }
            	   else
            	   {
                       sendMsg(message, "Êîìàíäà íå ðàñïîçíàíà",1);
                        
               	   }
            	   break;
               case"/setaccsess": 
            	   if (admins.contains(ChatID)) 
            	   {             		  
            		   sendMsg(message,"Âûáåðèòå íóæíûé ñòîë!", 5);
            		   setCommand(ChatID,5);
            	   }
            	   else
            	   {
                       sendMsg(message, "Êîìàíäà íå ðàñïîçíàíà",1);
                        
               	   }
            	   break;
               case"/tablelist":
            	   if (admins.contains(ChatID)) 
            	   {
            		   for (int i=1;i< Queue.numberList()+1; i++) {
            			   int kol = Queue.getQueue(i);
            			   		if (kol == -1)
                   			kol = 0;            			   
            			   sendMsg(message,"Ñòîë #"+ 
            			   String.valueOf(i) +": "
            			   		+ "Êîë-âî ëþäåé â î÷åðåäè - " + String.valueOf(kol) +"; "
            			   		+ "Êîë-âî ìåñò - "+ String.valueOf(Queue.getTableSize(i))+"; "
            			   		+ "Äîñòóï ê ñòîëó - "+ String.valueOf(Queue.accessStatus(i)) +".", 0); 
            		   }           		   
            		   setCommand(ChatID,0);  
            	   }
            	   
            	   else
            	   {
                       sendMsg(message, "Êîìàíäà íå ðàñïîçíàíà",1);
                        
               	   }
            	   break;
               case"/tableinfo":
            	   if (admins.contains(ChatID)) 
            	   {
            		   sendMsg(message,"Âûáåðèòå íóæíûé ñòîë!", 5);
            		   setCommand(ChatID,6);
            	   }
            	   else
            	   {
                       sendMsg(message, "Êîìàíäà íå ðàñïîçíàíà",1);
                        
               	   }
            	   break;
               case"/tableproc":
            	   if (admins.contains(ChatID)) 
            	   {
            		   sendMsg(message,"Âûáåðèòå íóæíûé ñòîë!", 5);
            		   setCommand(ChatID,7);
            	   }
            	   else
            	   {
                       sendMsg(message, "Êîìàíäà íå ðàñïîçíàíà",1);
                        
               	   }
            	   break;
               case"/tablequeue":
            	   if (admins.contains(ChatID)) 
            	   {
            		   sendMsg(message,"Âûáåðèòå íóæíûé ñòîë!", 5);
            		   setCommand(ChatID,8);
            	   }
            	   else
            	   {
                       sendMsg(message, "Êîìàíäà íå ðàñïîçíàíà",1);
                        
               	   }
            	   break;
               case"/abort": 
            	   if (admins.contains(ChatID)) 
            	   {
            		   sendMsg(message,"Âûáåðèòå íóæíûé ñòîë!", 5);
            		   setCommand(ChatID,9);
            	   }
            	   else
            	   {
                       sendMsg(message, "Êîìàíäà íå ðàñïîçíàíà",1);
                        
               	   }
            	   break;
               case "/delqueue":
            	   if (admins.contains(ChatID)) 
            	   {
            		   sendMsg(message,"Ââåäèòå ChatID óäàëÿåìîãî ÷åëîâåêà", 0);
            		   setCommand(ChatID,10);
            	   }
            	   else
            	   {
                       sendMsg(message, "Êîìàíäà íå ðàñïîçíàíà",1);
                        
               	   }
            	   break;
               case "/out":
            	   if (admins.contains(ChatID)) 
            	   {
            	   adminOut(ChatID);            	   
            	   sendMsg(message, "Âûõîä èç ðåæèìà àäìèíèñòðàòîðà",1);
            	   setCommand(ChatID,0);
            	   }            	   
            	   else
            	   {
                       sendMsg(message, "Êîìàíäà íå ðàñïîçíàíà",1);
                        
               		}
            	   break;
                default:
                	if (message.getText().contentEquals(login) == false) {
                		
                		sendMsg(message, "Êîìàíäà íå ðàñïîçíàíà",1);                        	
                	}                    
                    break;
            }
        }
        else if (update.hasCallbackQuery()) {
        	String call_data = update.getCallbackQuery().getData();
            ChatID = update.getCallbackQuery().getMessage().getChatId();
            number = Integer.parseInt(call_data); 
            
            if (getCommand(ChatID) == 3)
            {           	
            	Queue.addList(number);
            	System.out.println(number);
            	sendMsg(ChatID,"Ñòîë äîáàâëåí!", 0);
            	setCommand(ChatID,-1);
            }
            if (getCommand(ChatID) == 4)
            {            	 
            	 Queue.deleteList(number);
            	 sendMsg(ChatID,"Ñòîë óäàëåí!", 0);
            	 setCommand(ChatID,-1);
            }
            if (getCommand(ChatID) == 5)
            {            	
            	 Queue.accessSwitch(number);
            	 if (Queue.accessStatus(number) == true)
            	 sendMsg(ChatID,"Äîñòóï ê ñòîëó ñ íîìåðîì " + call_data +" îòêðûò!" , 0);
            	 else
            		 sendMsg(ChatID,"Äîñòóï ê ñòîëó ñ íîìåðîì " + call_data +" çàêðûò!" , 0);
            	 setCommand(ChatID,-1);
            }
            if (getCommand(ChatID) == 6)
            {    
            	int kol;
            	if ((Queue.getQueue(number) == -1))
            			kol = 0;
            	else
            		kol = Queue.getQueue(number);
            	sendMsg(ChatID,"Ñòîë #"+ 
         			   String.valueOf(number) +": "
         			   		+ "Êîë-âî ëþäåé â î÷åðåäè - " + String.valueOf(kol) +"; "
         			   		+ "Êîë-âî ìåñò - "+ String.valueOf(Queue.getTableSize(number))+"; "
         			   		+ "Äîñòóï ê ñòîëó - "+ String.valueOf(Queue.accessStatus(number)) +".", 0); 
            	setCommand(ChatID,-1);
            }
            if (getCommand(ChatID) == 7)
            {
            	if (Queue.getWorkerChatID(number) != 0) 
            	{
            		sendMsg(ChatID,"Âûïîëíÿåìûé ïðîöåññ â ñòîëå íîìåð " + call_data+": "
                			+ "ChatID - "+ String.valueOf(Queue.getWorkerChatID(number))+"; "
                			+ "Íîìåð òàëîíà - " +String.valueOf(Queue.getWorkerNumber(number)), 0); 
            	}
            	else
            		sendMsg(ChatID,"Âûïîëíÿåìûé ïðîöåññ â ñòîëå íîìåð " + call_data+": îòñóòñòâóåò", 0);
            	           	
            	setCommand(ChatID,-1);
            }
            if (getCommand(ChatID) == 8)
            {  
            	if (Queue.getQueue(number) != -1) 
            	{
	            	sendMsg(ChatID,"Èíôîðìàöèÿ îá î÷åðåäè ê ñòîëó " + call_data, 0);
	            	for (int i=1; i< Queue.getQueue(number)+1; i++) {
	            		sendMsg(ChatID,"Ïîçèöèÿ #"+ 
	                        	String.valueOf(i) +": ChatID - " + 
	                        	String.valueOf(Queue.returnChatID(number, i)) +"; Номер талона - "+ 
	                        	String.valueOf(Queue.returnNumber(number, i)) +".", 0);     
            	}	          
            	}
            	else
            		sendMsg(ChatID,"Î÷åðåäü ê ñòîëó " + call_data+" îòñóòñòâóåò", 0);
            	setCommand(ChatID,-1);
            }
            if (getCommand(ChatID) == 9)
            {            	
            	Queue.abort(number);
            	sendMsg(ChatID,"Âûïîëíÿåìûé ïðîöåññ â ñòîëå íîìåð " + call_data +" ïðåðâàí!", 0);
            	setCommand(ChatID,-1);
            }           
            if (getCommand(ChatID) == 12)
            {
            	Queue.add(number, ChatID);
            	sendMsg(ChatID,"Âû çàíÿëè î÷åðåäü çà ñòîë ñ êîëè÷åñòâîì ìåñò - " + call_data, 1);
            	if (Queue.userInQueue(ChatID) != -1) 
           	 	{
           		 int kol = Queue.outQueue(Queue.userInQueue(ChatID), ChatID);
     			 if (kol == -1)
     			    	 kol = 0;         			     
     			   
           		 sendMsg(ChatID, "Âàøà ïîçèöèÿ â î÷åðåäè ê ñòîëó íîìåð "+
                    		String.valueOf(Queue.userInQueue(ChatID)) + " - "
                    		+ String.valueOf(kol),1);
                    sendMsg(ChatID,"Âàø ChatId - " + String.valueOf(ChatID), 1);
           	 	}
               
               else if (Queue.userInProcessor(ChatID)!= -1) {
               	sendMsg(ChatID,"Âàøà î÷åðåäü óæå ïîäîøëà, ïðîéäèòå ê ñòîëó íîìåð "+ String.valueOf(Queue.userInProcessor(ChatID)),1);
               }
            	setCommand(ChatID,-1);
            }
            if (getCommand(ChatID) == 0) 
            {
            	if (admins.contains(ChatID)) {
            		sendMsg(ChatID,"Íàæàòèå êíîïêè íå ðàñïîçíàíî, ââåäèòå êîìàíäó", 1);
            	}
            	else
            		sendMsg(ChatID,"Âîçíèêëà íåïðåäâèäåííàÿ îøèáêà, íàæìèòå êíîïêó âíèçó ýêðàíà", 1);
            	
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

	public InlineKeyboardButton Button(int i){
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
	        // Ïåðâàÿ ñòðî÷êà êëàâèàòóðû
	        KeyboardRow keyboardFirstRow = new KeyboardRow();	       
	        keyboardFirstRow.add("Çàíÿòü î÷åðåäü");	       
	        keyboard.add(keyboardFirstRow);
        }
       
        else if(mode == 2) 
        {  
	        KeyboardRow keyboardFirstRow = new KeyboardRow();	              
	        keyboardFirstRow.add("Ïîñìîòðåòü ïîçèöèþ");	        
	        KeyboardRow keyboardSecondRow = new KeyboardRow();	       
	        keyboardSecondRow.add("Âûéòè èç î÷åðåäè");  
	        keyboard.add(keyboardFirstRow);
	        keyboard.add(keyboardSecondRow);
        }        
        else if (mode == 3) 
        {
        	KeyboardRow keyboardFirstRow = new KeyboardRow();
        	keyboardFirstRow.add("Çàêîí÷èòü");
        	keyboard.add(keyboardFirstRow);
        }
        replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;
	}

	public void sendMsg (Long chatID, String text, int mode) {
		SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        // 0 - Ñòàíäàðòíàÿ êëàâèàòóðà     		
        // 4 - Èíëàéí êëàâèàòóðà âûáîðà êîëè÷åñòâà ìåñò (äëÿ àäìèíà)
        // 5 - êëàâèàòóðà âûáîðà ñòîëîâ
        // 6 - Âûâîä êîëè÷åñòâà ìåñò (äëÿ ñîçäàíèÿ ñòîëîâ)
        // Â îñòàëüíûõ íà îñíîâå ïðîâåðîê íàëè÷èÿ êëèåíòà â î÷åðåäè èëè â ïðîöåññå âûâîäèòñÿ êëàâèàòóðà
        // Åñëè ïîëüçîâàòåëü â î÷åðåäè: êíîïêè ÏÎÑÌÎÒÐÅÒÜ ÏÎÇÖÈÞ è ÂÛÉÒÈ ÈÇ Î×ÅÐÅÄÈ
        // Åñëè ïîëüçîâàòåëü â ïðîöåññå: êíîïêà ÇÀÊÎÍ×ÈÒÜ
        // èíà÷å âûâîäèò êíîïêó ÇÀÍßÒÜ Î×ÅÐÅÄÜ    
        	
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
     // 0 - Ñòàíäàðòíàÿ êëàâèàòóðà     		
        // 4 - Èíëàéí êëàâèàòóðà âûáîðà êîëè÷åñòâà ìåñò (äëÿ àäìèíà)
        // 5 - êëàâèàòóðà âûáîðà ñòîëîâ
        // 6 - Âûâîä êîëè÷åñòâà ìåñò (äëÿ ñîçäàíèÿ ñòîëîâ)
        // Â îñòàëüíûõ íà îñíîâå ïðîâåðîê íàëè÷èÿ êëèåíòà â î÷åðåäè èëè â ïðîöåññå âûâîäèòñÿ êëàâèàòóðà
        // Åñëè ïîëüçîâàòåëü â î÷åðåäè: êíîïêè ÏÎÑÌÎÒÐÅÒÜ ÏÎÇÖÈÞ è ÂÛÉÒÈ ÈÇ Î×ÅÐÅÄÈ
        // Åñëè ïîëüçîâàòåëü â ïðîöåññå: êíîïêà ÇÀÊÎÍ×ÈÒÜ
        // èíà÷å âûâîäèò êíîïêó ÇÀÍßÒÜ Î×ÅÐÅÄÜ    
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
