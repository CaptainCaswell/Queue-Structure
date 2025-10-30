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
		
		// INSERT HERE
		if ( agent == null ) {
			System.out.println("Error: cannot add null agent.");
			return;
		}

		if ( agent.isIdle() ) { agents.add( agent ); }
		else { occupiedAgents.add( agent ); }

		// TO HERE
	}
	
	public void addAgent( Agent agent, Client client ) {
		
		// INSERT HERE

		if ( client != null ) {
			System.out.println("Error: cannot add null customer.");
		}

		else {
			agent.currentClient = client;
		}
		
		addAgent( agent );
		
		// TO HERE
	}
	
	public void addClient( Client client ) {

		// INSERT HERE
		if ( client == null ) {
			System.out.println("Error: cannot add null customer.");
			return;
		}

		clients.add( client );

		// TO HERE

	}
	
	public void serveClients() {
		
		// INSERT HERE

		// As long as there or free agents and unserved clients, assign first agent to first client
		while ( !agents.isEmpty() &&  !clients.isEmpty() ) {
			serveClient();
		}


		// TO HERE

	}
	
	public Agent serveClient() {
		
		// INSERT HERE
		if ( agents.isEmpty() ) {
			System.out.println("Error: No free agents available.");
			return null;
		}

		if ( agents.isEmpty() ) {
			System.out.println("Error: No customers waiting.");
			return null;
		}

		Agent tempAgent = agents.poll();
		tempAgent.currentClient = clients.poll();

		return tempAgent;
		// TO HERE
		
	}
	
	public List<Client> servingClients() {
		
		// INSERT HERE

		List<Client> helped = new LinkedList<Client>();

		for (Agent i : occupiedAgents) {
			helped.add(i.currentClient);
		}

		
		return helped;
		// TO HERE

	}
	
	public String displayServingClients() {
		
		// INSERT HERE
		String output = "";

		for (Client i :  servingClients() ) {
			output += i + "\n";
		}
		
		return output;
		// TO HERE

	}
	
	public String displayServiceLine() {
		
		// INSERT HERE

		String output = "";

		for (Agent i : agents) {
			output += i + "\n";
		}

		
		return output;
		// TO HERE

	}
	
	public String makeAnnouncement( Agent agent ) {
		
		// INSERT HERE



		
		return null;
		// TO HERE

	}
} 