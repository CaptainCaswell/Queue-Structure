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

		
		if ( agent != null) {
			agent.initialize();
			agents.add( agent );
		} else {
			System.out.println("Error: cannot add null agent.");
		}

		// TO HERE
	}
	
	public void addAgent( Agent agent, Client client ) {
		
		// INSERT HERE

		// Check for null agent
		if ( agent == null ) {
			System.out.println("Error: cannot add null agent.");
			return;
		}

		// Check for null client (no client)
		if ( client == null ) {
			System.out.println("Error: Client is null, adding idle agent.");
			agents.add ( agent );
		}

		// Client is valid (occupised)
		else {
			agent.currentClient = client;
			occupiedAgents.add( agent );
		}
		
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

		if ( agents.isEmpty() ) {
			System.out.println("Error: No free agents available.");
			return;
		}

		if ( clients.isEmpty() ) {
			System.out.println("Error: No customers waiting.");
			return;
		}

		// As long as there or free agents and unserved clients, assign first agent to first client
		while ( !agents.isEmpty() && !clients.isEmpty() ) {
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

		if ( clients.isEmpty() ) {
			System.out.println("Error: No customers waiting.");
			return null;
		}

		if ( agents.peek() == null ) {
			agents.poll();
			System.out.println("Error: Null agent found.");
			return null;
		}

		if ( clients.peek() == null ) {
			clients.poll();
			System.out.println("Error: Null client found.");
			return null;
		}

		Agent tempAgent = agents.poll();
		tempAgent.currentClient = clients.poll();

		occupiedAgents.add( tempAgent );

		return tempAgent;
		// TO HERE
		
	}
	
	public List<Client> servingClients() {
		
		// INSERT HERE

		List<Client> helped = new LinkedList<Client>();

		for ( Agent agent : getOccupiedAgents() ) {
			if ( agent == null ) {
				System.out.println("Error: Null agent found.");
				continue;
			}

			if ( agent.getCurrentClient() == null ) {
				System.out.println("Error: Null client found.");
				continue;
			}
			
			helped.add( agent.getCurrentClient() );
		}

		return helped;
		// TO HERE

	}
	
	public String displayServingClients() {
		
		// INSERT HERE
		String output = "";

		for (Client client :  servingClients() ) {
			if ( client == null ) {
				System.out.println("Error: Null client found.");
				continue;
			}
			output += client + "\n";
		}

		if ( output == "" ) System.out.println("Error: No clients being served.");
		
		return output;
		// TO HERE

	}
	
	public String displayServiceLine() {
		
		// INSERT HERE

		String output = "";

		for (Agent agent : occupiedAgents) {

			if ( agent == null ) {
				System.out.println("Error: Null agent found.");
				continue;
			}
			output += agent + "\n";
		}
		
		if ( output == "" ) System.out.println("Error: No clients being served.");


		return output;
		// TO HERE

	}
	
	public String makeAnnouncement( Agent agent ) {
		
		// INSERT HERE

		// If agent null
		if ( agent == null ) {
			System.out.println("Error: Null agent found.");
			return "";
		} 
		
		// Not sure if supposed to output if agent is idle. Assuming we should.
		// If agent idle
		if ( agent.getCurrentClient() == null ) {
			return "Idle at station " + agent.getStation() + ".";
		}

		// If agent occupied
		return "Serving " + agent.getCurrentClient().getClientID() + " at station " + agent.getStation() + ".";


		// TO HERE

	}
} 