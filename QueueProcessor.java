package Assignment2;

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
		if (agent != null) {
			agent.initialize();
			agents.addLast(agent);
		}
	}
	
	public void addAgent( Agent agent, Client client ) {
		if (agent != null && client == null) {
			agent.initialize();
			agents.addLast(agent);
		} else if (agent != null) {
			agent.currentClient = client;
			occupiedAgents.addLast(agent);
		}
	}
	
	public void addClient( Client client ) {
		if (client != null) {
			clients.addLast(client);
		}
	}
	
	public void serveClients() {
		while (!clients.isEmpty() && !agents.isEmpty()) {
			Client client = clients.pollFirst();
			Agent agent = agents.pollFirst();
			agent.currentClient = client;
			occupiedAgents.addLast(agent);
		}
	}
	
	public Agent serveClient() {
		if (!clients.isEmpty() && !agents.isEmpty()) {
			Client client = clients.pollFirst();
			Agent agent = agents.pollFirst();
			agent.currentClient = client;
			occupiedAgents.addLast(agent);
			return agent;
		}
		return null;
	}
	
	public List<Client> servingClients() {
		List<Client> clientsBeingServed = new ArrayList<Client>();
		for (Agent agent : occupiedAgents) {
			Client client = agent.getCurrentClient();
			if (client != null) {
				clientsBeingServed.addLast(client);
			}
		}
		if (!clientsBeingServed.isEmpty()) return clientsBeingServed;
		return null;
	}
	
	public String displayServingClients() {
		List<Client> clientsBeingServed = servingClients();
		if (!clientsBeingServed.isEmpty()) {
			StringBuilder clientsBeingServedList = new StringBuilder();
			for (Client client : clientsBeingServed) {
				clientsBeingServedList.append(client.toString()).append("\n");
			}
			return clientsBeingServedList.toString();
		}
		return null;
	}
	
	public String displayServiceLine() {
		if (!occupiedAgents.isEmpty()) {
			StringBuilder serviceLine = new StringBuilder();
			for (Agent agent : occupiedAgents) {
				serviceLine.append(agent.toString()).append("\n");
			}
			return serviceLine.toString();	
		}
		return null;
	}
	
	public String makeAnnouncement( Agent agent ) {
		if (agent != null && agent.getCurrentClient() != null) {
			return "Serving " + agent.getCurrentClient().getClientID() +
					" at station " + agent.getStation();
		}
		return null;
	}
} 
