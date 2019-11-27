public class StateChangeGreeting implements StateInterfaceConnection{

	@Override
	public void dial(String key, Connection c) {
		c.changeGreeting(key);
		
	}
	
}
