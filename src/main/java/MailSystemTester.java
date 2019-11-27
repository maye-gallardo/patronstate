import java.util.Scanner;

public class MailSystemTester
{
	private static final int MAILBOX_COUNT = 20;
	
   public static void main(String[] args){
      MailSystem system = new MailSystem(MAILBOX_COUNT);
      Scanner console = new Scanner(System.in);
      ConsoleTelephone p = new ConsoleTelephone(console);
      Connection c = new Connection(system);
      p.run(c);
   }
}
