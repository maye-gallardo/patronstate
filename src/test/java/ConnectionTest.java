import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ConnectionTest {
	MailSystem system;
	ConsoleTelephone phone;
	Mailbox mailbox;
	Connection connection;
	Message message;
	StateRecording recording;

	@BeforeEach
	public void setup() {
		system = mock(MailSystem.class);
	    phone = mock(ConsoleTelephone.class);
	    mailbox = mock(Mailbox.class);
	    connection = new Connection(system);
	    message = mock(Message.class);
	    recording = new StateRecording();
	}
	
	@Test
	public void connectionShouldStartInitialPrompt() {
	    phone.speak("Enter mailbox number followed by #");
	}
	
	@Test
	public void dialExistingMailbox() {    
	    
	    when(system.findMailbox("1")).thenReturn(mailbox);
	    when(mailbox.getGreeting()).thenReturn("Hola Mundo:");
	    
	    connection.dial("1");
	    connection.dial("#");
	    phone.speak("Hola Mundo:");
	}

	
}
