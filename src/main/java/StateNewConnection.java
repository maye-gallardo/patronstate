public class StateNewConnection implements StateInterfaceConnection{

	@Override
	public void dial(String key, Connection c) {
		c.connect(key);
		
	}
	
}
