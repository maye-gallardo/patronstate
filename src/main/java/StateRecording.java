import java.util.ArrayList;

public class StateRecording implements StateInterfaceConnection{
	
	@Override
	public void dial(String key, Connection c) {
		c.login(key);
	}

}
