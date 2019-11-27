import java.util.ArrayList;

public class Connection
{
	   private Mailbox currentMailbox;
	   private MailSystem system;
	   private String currentRecording;
	   private String accumulatedKeys;
	   private ConsoleTelephone phone;
	   private StateInterfaceConnection state;
	   private UIMessages uimessages;
	   private StateInterfaceConnection stateInterfaceConnec;
	   
	   private ArrayList<Telephone> conector = new ArrayList<Telephone>();

	   private static final StateNewConnection newConnection = new StateNewConnection();
	   private static final StateRecording recording = new StateRecording();
	   private static final StateMessageMenu messageMenu = new StateMessageMenu();
	   private static final StateMailBoxMenu mailBoxMenu = new StateMailBoxMenu();
	   private static final StateChangePasscode changePasscode = new StateChangePasscode();
	   private static final StateChangeGreeting changeGreeting = new StateChangeGreeting();
	   
   public Connection(MailSystem s){
      system = s;
      resetConnection(); 
      this.conector = new ArrayList<Telephone>();
   }

   public void dial(String key){
	   stateInterfaceConnec.dial(key, this);
	}

	   public void record(String voice){
	      if (state == recording || state == changeGreeting)
	         currentRecording += voice;
	   }

	   public void hangup(){
	      if (state == recording)
	         currentMailbox.addMessage(new Message(currentRecording));
	      resetConnection();
	   }

	   private void resetConnection(){
	      currentRecording = "";
	      accumulatedKeys = "";
	      state = newConnection;
	      conector.forEach(x -> x.speak(uimessages.INITIAL_PROMPT));
	   }

	   public void connect(String key){
	      if (key.equals("#"))
	      {
	         currentMailbox = system.findMailbox(accumulatedKeys);
	         if (currentMailbox != null)
	         {
	            state = recording;
	            conector.forEach(x -> x.speak(currentMailbox.getGreeting()));
	         }
	         else
	        	conector.forEach(x -> x.speak(uimessages.INCORRECT_MAILBOX));
	         accumulatedKeys = "";
	      }
	      else
	         accumulatedKeys += key;
	   }

	   public void login(String key){
	      if (key.equals("#"))
	      {
	         if (currentMailbox.checkPasscode(accumulatedKeys))
	         {
	            state = mailBoxMenu;
	            conector.forEach(x -> x.speak(uimessages.MAILBOX_MENU_TEXT));
	         }
	         else
	        	 conector.forEach(x -> x.speak(uimessages.INCORRECT_PASSCODE));
	         accumulatedKeys = "";
	      }
	      else
	         accumulatedKeys += key; 
	   }

	   public void changePasscode(String key){
	      if (key.equals("#"))
	      {
	         currentMailbox.setPasscode(accumulatedKeys);
	         state = mailBoxMenu;
	         conector.forEach(x -> x.speak(uimessages.MAILBOX_MENU_TEXT));
	         accumulatedKeys = "";
	      }
	      else
	         accumulatedKeys += key;
	   }

	   public void changeGreeting(String key){
	      if (key.equals("#"))
	         currentMailbox.setGreeting(currentRecording);
	         currentRecording = "";
	         state = mailBoxMenu;
	         conector.forEach(x -> x.speak(uimessages.MAILBOX_MENU_TEXT));
	   }

	   public void mailboxMenu(String key){
		  switch(Integer.parseInt(key)) {
			  case 1:{
				  state = messageMenu;
				  conector.forEach(x -> x.speak(uimessages.MESSAGE_MENU_TEXT));
			      break;
			  } 
			  case 2:{
				  state = changePasscode;
				  conector.forEach(x -> x.speak(uimessages.NEW_PASSWORD));
			      break;
			  }
			  case 3:{
				  state = changeGreeting;
			      conector.forEach(x -> x.speak(uimessages.RECORD_GREETING));
			      break;
			  }
		  }
	   }

	   public void messageMenu(String key){
		  switch(Integer.parseInt(key)) {
		  	case  1:{
		  		String output = getMessage();
		  		conector.forEach(x -> x.speak(output));
		        break;
		  	}
		  	case 2:{
		  		currentMailbox.saveCurrentMessage();
		  		conector.forEach(x -> x.speak(uimessages.MESSAGE_MENU_TEXT));
		        break;
		  	}
		  	case 3:{
		  		currentMailbox.removeCurrentMessage();
		  		conector.forEach(x -> x.speak(uimessages.MESSAGE_MENU_TEXT));
		        break;
		  	}
		  	case 4:{
		  		state = mailBoxMenu;
		  		conector.forEach(x -> x.speak(uimessages.MAILBOX_MENU_TEXT));
		        break;
		  	}
		  }
	   }

		private String getMessage() {
			String output = "";
			 Message m = currentMailbox.getCurrentMessage();
			 if (m == null) output += "No messages." + "\n";
			 else output += m.getText() + "\n";
			 output += uimessages.MESSAGE_MENU_TEXT;
			return output;
		}
	}