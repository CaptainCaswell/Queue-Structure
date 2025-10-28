import java.util.*;

public class QueueProcessor {
	
	public class Agent {
		
		private Client currentClient;
		private String agentID;
		private String station;
		//private boolean free = true;
		
		private static int id = 999; //for autogeneration of ids
		private static int stationID = 9; //for autogeneration of ids
		
		public Agent() {
			this.agentID = "AG"+ String.valueOf( ++id );
			int x = ++stationID;
			station = "STA-"+ String.valueOf( x );
		}
		
		public Agent( Client assignedClient ) {
			this(); //invokes the default constructor
			currentClient = assignedClient;
		}
		
		public String getStation() { return station; }
		public boolean isIdle() { return currentClient == null; }
		public Client getCurrentClient() { return currentClient; }
		public void initialize() { currentClient = null; }
		public String toString() {
			return "Station: " + station +", ID: " + agentID +", Serving Client: " + currentClient;
		}
	}
	
	//attributes of the processor class
	private LinkedList<Client> clients;
	private LinkedList<Agent> agents;
	private LinkedList<Agent> occupiedAgents;
	
	//default constructor
	public QueueProcessor() {
		clients = new LinkedList<Client>();
		agents = new LinkedList<Agent>();
		occupiedAgents = new LinkedList<Agent>();
	}
	
	//Getter methods
	public LinkedList<Client> getClients() { return clients; }
	public LinkedList<Agent> getIdleAgents() { return agents; }
	public LinkedList<Agent> getOccupiedAgents() { return occupiedAgents; }
	
	public void addAgent( Agent agent ) {
		
		//PLEASE INSERT YOUR CODE HERE
	}
	
	public void addAgent( Agent agent, Client client ) {
		
		//PLEASE INSERT YOUR CODE HERE
	}
	
	public void addClient( Client client ) {
		//PLEASE INSERT YOUR CODE HERE
	}
	
	public void serveClients() {
		
		//PLEASE INSERT YOUR CODE HERE
	}
	
	public Agent serveClient() {
		
		//PLEASE INSERT YOUR CODE HERE
		return null;
	}
	
	public List<Client> servingClients() {
		
		//PLEASE INSERT YOUR CODE HERE
		return null;
	}
	
	public String displayServingClients() {
		
		//PLEASE INSERT YOUR CODE HERE
		return null;
	}
	
	public String displayServiceLine() {
		
		//PLEASE INSERT YOUR CODE HERE
		return null;
	}
	
	public String makeAnnouncement( Agent agent ) {
		
		//PLEASE INSERT YOUR CODE HERE
		return null;
	}
} 