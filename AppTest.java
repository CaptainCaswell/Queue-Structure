import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import java.util.*;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AppTest {

    QueueProcessor queue;
    QueueProcessor.Agent agent;
    QueueProcessor.Agent agent2;
    QueueProcessor.Agent agent3;
    QueueProcessor.Agent agent4;
    QueueProcessor.Agent agent5;
    QueueProcessor.Agent agentNull;
    
    Customer client;
    Customer client2;
    Customer client3;
    Customer client4;
    Customer client5;
    Customer clientNull;

    String agentStr;

    ByteArrayOutputStream output;
    PrintStream terminal;
    
    @Before
    public void setupStreams() {
        // Make new instance of QueueProccessor
        queue = new QueueProcessor();

        // Test agent and client
        agent = queue.new Agent();
        agent2 = queue.new Agent();
        agent3 = queue.new Agent();
        agent4 = queue.new Agent();
        agent5 = queue.new Agent();
        client = new Customer( "Ryan" );
        client2 = new Customer( "Gage" );
        client3 = new Customer( "Bart" );
        client4 = new Customer( "Lisa" );
        client5 = new Customer( "Maggie" );

        // Null agent and client
        agentNull = null;
        clientNull = null;

        // Regex for agent serving client, matches any numbers
        agentStr = "Station: STA-\\d{2}, ID: AG\\d{4}, Serving Client: \\[ID: CL\\d{6}, Name: Ryan\\]";

        // Move System.Out to variable
        output = new ByteArrayOutputStream();
        terminal = new PrintStream( output );
        System.setOut( terminal );
    }

    

    @Test
    public void testAddAgent() {

        queue.addAgent( agent );

        assertEquals( 1, queue.getIdleAgents().size() );
        assertEquals( 0, queue.getOccupiedAgents().size() );

        assertEquals( "", output.toString().trim() );
    }

    @Test
    public void testAddNullAgent() {

        queue.addAgent( agentNull );

        assertEquals( 0, queue.getIdleAgents().size() );
        assertEquals( 0, queue.getOccupiedAgents().size() );

        assertEquals( "Error: cannot add null agent.", output.toString().trim() );
    }

    @Test
    public void testAddAgentWithClient() {

        queue.addAgent( agent, client );

        assertEquals( 0, queue.getIdleAgents().size() );
        assertEquals( 1, queue.getOccupiedAgents().size() );

        assertEquals( "", output.toString().trim() );
    }

    @Test
    public void testAddAgentWithNullClient() {

        queue.addAgent( agent, clientNull );

        assertEquals( 1, queue.getIdleAgents().size() );
        assertEquals( 0, queue.getOccupiedAgents().size() );

        assertEquals( "Error: Client is null, adding idle agent.", output.toString().trim() );
        
    }

    @Test
    public void testAddNullAgentWithClient() {

        queue.addAgent( agentNull, client );

        assertEquals( 0, queue.getIdleAgents().size() );
        assertEquals( 0, queue.getOccupiedAgents().size() );
        
        assertEquals( "Error: cannot add null agent.", output.toString().trim() );
    }

    @Test
    public void testAddNullAgentWithNullClient() {

        queue.addAgent( agentNull, clientNull );

        assertEquals( 0, queue.getIdleAgents().size() );
        assertEquals( 0, queue.getOccupiedAgents().size() ); 

        assertEquals( "Error: cannot add null agent.", output.toString().trim() );
    }

    @Test
    public void testServeClient() {

        queue.addAgent( agent );
        queue.addClient( client );

        assertEquals( 1, queue.getIdleAgents().size() );
        assertEquals( 0, queue.getOccupiedAgents().size() );
        assertEquals( 1, queue.getClients().size() );

        QueueProcessor.Agent returnAgent = queue.serveClient();

        assertEquals( 0, queue.getIdleAgents().size() );
        assertEquals( 1, queue.getOccupiedAgents().size() );
        assertEquals( 0, queue.getClients().size() );
        
        assertTrue( returnAgent.toString().matches( agentStr ) );
        
        assertEquals( "", output.toString().trim() );

    }

    @Test
    public void testServeNoClient() {

        queue.addAgent( agent );

        assertEquals( 1, queue.getIdleAgents().size() );
        assertEquals( 0, queue.getOccupiedAgents().size() );
        assertEquals( 0, queue.getClients().size() );

        QueueProcessor.Agent returnAgent = queue.serveClient();

        assertEquals( 1, queue.getIdleAgents().size() );
        assertEquals( 0, queue.getOccupiedAgents().size() );
        assertEquals( 0, queue.getClients().size() );
        
        assertEquals( null, returnAgent );

        assertEquals( "Error: No customers waiting.", output.toString().trim() );
    }

    @Test
    public void testServeClientNoAgent() {

        queue.addClient( client );

        assertEquals( 0, queue.getIdleAgents().size() );
        assertEquals( 0, queue.getOccupiedAgents().size() );
        assertEquals( 1, queue.getClients().size() );

        QueueProcessor.Agent returnAgent = queue.serveClient();

        assertEquals( 0, queue.getIdleAgents().size() );
        assertEquals( 0, queue.getOccupiedAgents().size() );
        assertEquals( 1, queue.getClients().size() );
        
        assertEquals( null, returnAgent );

        assertEquals( "Error: No free agents available.", output.toString().trim() );

    }

    @Test
    public void testServeNullClientNullAgent() {

        assertEquals( 0, queue.getIdleAgents().size() );
        assertEquals( 0, queue.getOccupiedAgents().size() );
        assertEquals( 0, queue.getClients().size() );

        QueueProcessor.Agent returnAgent = queue.serveClient();

        assertEquals( 0, queue.getIdleAgents().size() );
        assertEquals( 0, queue.getOccupiedAgents().size() );
        assertEquals( 0, queue.getClients().size() );
        
        assertEquals( null, returnAgent );

        assertEquals( "Error: No free agents available.", output.toString().trim() );

    }

    @Test
    public void testServeClients() {

        queue.addAgent( agent );
        queue.addAgent( agent2 );
        queue.addAgent( agent3 );
        queue.addAgent( agent4 );
        queue.addAgent( agent5 );
        queue.addClient( client );
        queue.addClient( client2 );
        queue.addClient( client3 );
        queue.addClient( client4 );
        queue.addClient( client5 );

        assertEquals( 5, queue.getIdleAgents().size() );
        assertEquals( 0, queue.getOccupiedAgents().size() );
        assertEquals( 5, queue.getClients().size() );

        queue.serveClients();

        assertEquals( 0, queue.getIdleAgents().size() );
        assertEquals( 5, queue.getOccupiedAgents().size() );
        assertEquals( 0, queue.getClients().size() );

        assertEquals( "", output.toString().trim() );
    }

    @Test
    public void testServeClientsExtraAgents() {

        queue.addAgent( agent );
        queue.addAgent( agent );
        queue.addAgent( agent );
        queue.addAgent( agent );
        queue.addAgent( agent );
        queue.addClient( client );

        assertEquals( 5, queue.getIdleAgents().size() );
        assertEquals( 0, queue.getOccupiedAgents().size() );
        assertEquals( 1, queue.getClients().size() );

        queue.serveClients();

        assertEquals( 4, queue.getIdleAgents().size() );
        assertEquals( 1, queue.getOccupiedAgents().size() );
        assertEquals( 0, queue.getClients().size() );

        assertEquals( "", output.toString().trim() );
    }

    @Test
    public void testServeClientsExtraClients() {

        queue.addAgent( agent );
        queue.addClient( client );
        queue.addClient( client2 );
        queue.addClient( client3 );
        queue.addClient( client4 );
        queue.addClient( client5 );

        assertEquals( 1, queue.getIdleAgents().size() );
        assertEquals( 0, queue.getOccupiedAgents().size() );
        assertEquals( 5, queue.getClients().size() );

        queue.serveClients();

        assertEquals( 0, queue.getIdleAgents().size() );
        assertEquals( 1, queue.getOccupiedAgents().size() );
        assertEquals( 4, queue.getClients().size() );

        assertEquals( "", output.toString().trim() );
    }

    @Test
    public void servingClients() {

        queue.addAgent( agent );
        queue.addAgent( agent2 );
        queue.addAgent( agent3 );
        queue.addAgent( agent4 );
        queue.addAgent( agent5 );
        queue.addClient( client );
        queue.addClient( client2 );
        queue.addClient( client3 );
        queue.addClient( client4 );
        queue.addClient( client5 );

        assertEquals( 5, queue.getIdleAgents().size() );
        assertEquals( 0, queue.getOccupiedAgents().size() );
        assertEquals( 5, queue.getClients().size() );

        queue.serveClients();

        assertEquals( 0, queue.getIdleAgents().size() );
        assertEquals( 5, queue.getOccupiedAgents().size() );
        assertEquals( 0, queue.getClients().size() );

        List<Client> helpedClients = queue.servingClients();

        assertEquals( 5, helpedClients.size() );

        assertEquals( "", output.toString().trim() );
    }

    @Test
    public void servingClientsSomeIdle() {

        queue.addAgent( agent );
        queue.addAgent( agent2 );
        queue.addAgent( agent3 );
        queue.addAgent( agent4 );
        queue.addAgent( agent5 );
        queue.addClient( client );
        queue.addClient( client2 );
        queue.addClient( client3 );
        queue.addClient( client4 );
        queue.addClient( client5 );

        assertEquals( 5, queue.getIdleAgents().size() );
        assertEquals( 0, queue.getOccupiedAgents().size() );
        assertEquals( 5, queue.getClients().size() );

        queue.serveClient();
        queue.serveClient();

        assertEquals( 3, queue.getIdleAgents().size() );
        assertEquals( 2, queue.getOccupiedAgents().size() );
        assertEquals( 3, queue.getClients().size() );

        List<Client> helpedClients = queue.servingClients();

        assertEquals( 2, helpedClients.size() );

        assertEquals( "", output.toString().trim() );
    }

    @Test
    public void servingClientsEmpty() {

        assertEquals( 0, queue.getIdleAgents().size() );
        assertEquals( 0, queue.getOccupiedAgents().size() );
        assertEquals( 0, queue.getClients().size() );

        queue.serveClient();

        assertEquals( 0, queue.getIdleAgents().size() );
        assertEquals( 0, queue.getOccupiedAgents().size() );
        assertEquals( 0, queue.getClients().size() );

        List<Client> helpedClients = queue.servingClients();

        assertEquals( 0, helpedClients.size() );

        assertEquals( "Error: No free agents available.", output.toString().trim() );
    }

    @Test
    public void testDisplayClients() {

        queue.addAgent( agent );
        queue.addAgent( agent2 );
        queue.addClient( client );
        queue.addClient( client2 );

        assertEquals( 2, queue.getIdleAgents().size() );
        assertEquals( 0, queue.getOccupiedAgents().size() );
        assertEquals( 2, queue.getClients().size() );

        queue.serveClients();

        assertEquals( 0, queue.getIdleAgents().size() );
        assertEquals( 2, queue.getOccupiedAgents().size() );
        assertEquals( 0, queue.getClients().size() );

        String str = queue.displayServingClients();
        
        assertEquals( "[ID: CL100000, Name: Ryan]\n[ID: CL100001, Name: Gage]", str.trim() );

        assertEquals( "", output.toString().trim() );
    }

    @Test
    public void testDisplayClientsEmpty() {

        String str = queue.displayServingClients();
        
        assertEquals( "", str.trim() );

        assertEquals( "Error: No clients being served.", output.toString().trim() );
    }

    @Test
    public void testDisplayClients() {

        queue.addAgent( agent );
        queue.addAgent( agent2 );
        queue.addClient( client );
        queue.addClient( client2 );

        assertEquals( 2, queue.getIdleAgents().size() );
        assertEquals( 0, queue.getOccupiedAgents().size() );
        assertEquals( 2, queue.getClients().size() );

        queue.serveClients();

        assertEquals( 0, queue.getIdleAgents().size() );
        assertEquals( 2, queue.getOccupiedAgents().size() );
        assertEquals( 0, queue.getClients().size() );

        String str = queue.displayServingClients();
        
        assertEquals( "[ID: CL100000, Name: Ryan]\n[ID: CL100001, Name: Gage]", str.trim() );

        assertEquals( "", output.toString().trim() );
    }






    
}
