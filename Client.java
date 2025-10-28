public abstract class Client {
	
	private String clientID;
	private static int id = 99999; //for autogeneration of ids
	
	public Client() {
		clientID = "CL" + String.valueOf(++id);
	}
	
	public String getClientID() { return clientID; }
}