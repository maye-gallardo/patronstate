public class StateChangePasscode implements StateInterfaceConnection{

	@Override
	public void dial(String key, Connection c) {
		c.changePasscode(key);
		
	}
	
}
