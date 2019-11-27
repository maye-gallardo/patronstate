public class StateMessageMenu implements StateInterfaceConnection{

	@Override
	public void dial(String key, Connection c) {
		c.messageMenu(key);
		
	}
	
}
