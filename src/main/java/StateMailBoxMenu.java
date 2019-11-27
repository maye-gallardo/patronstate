public class StateMailBoxMenu implements StateInterfaceConnection{

	@Override
	public void dial(String key, Connection c) {
		c.mailboxMenu(key);
		
	}
	
}
