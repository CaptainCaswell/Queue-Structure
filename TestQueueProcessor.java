import java.util.*;

public class TestQueueProcessor {

	private static QueueProcessor qp = new QueueProcessor();
	public static void main( String [] args ) {
		
		
		//Creating 20 Agents
		int n = 20;
		for( int i = 0; i < n; i++ ) {
			QueueProcessor.Agent agent = qp.new Agent();
			qp.addAgent( agent );
		}
		
		qp.addClient( new Customer("Opeyemi") ); 
		qp.addClient( new Customer("Joshua") ); 
		qp.addClient( new Customer("Jake") ); 
		qp.addClient( new Customer("Sukhjot") );
		qp.addClient( new Customer("Toprak") );
		qp.addClient( new Customer("Singh") ); 
		qp.addClient( new Customer("Manjot") ); 
		qp.addClient( new Customer("Jade") ); 
		qp.addClient( new Customer("Helana") );
		qp.addClient( new Customer("Emma") );
		qp.addClient( new Customer("William") );
		qp.addClient( new Customer("Zulfiqar") ); 
		qp.addClient( new Customer("Gurmandeep") );		
		qp.addClient( new Customer("Manpreet") ); 
		qp.addClient( new Customer("Sekhon") );
		qp.addClient( new Customer("Judith") );
		
		
		List customers = new ArrayList<Customer>();
		customers.add( new Customer("Opeyemi") ); 
		customers.add( new Customer("Joshua") ); 
		customers.add( new Customer("Jake") ); 
		customers.add( new Customer("Sukhjot") );
		customers.add( new Customer("Toprak") );
		customers.add( new Customer("Singh") ); 
		customers.add( new Customer("Sergey") ); 
		customers.add( new Customer("Chris") ); 
		customers.add( new Customer("Gabriel") );
		customers.add( new Customer("Leon") );
		customers.add( new Customer("Abel") );
		customers.add( new Customer("Carl") ); 
		customers.add( new Customer("Amir") );		
		customers.add( new Customer("Mandeep") ); 
		customers.add( new Customer("Ismail") );
		customers.add( new Customer("Maryam") );
		
		
	
		Random rand = new Random();
		int k = 0;
		QueueProcessor.Agent agent;
		while( true ) {
			try {
				if( !qp.getIdleAgents().isEmpty() ) {
					QueueProcessor.Agent ag = qp.serveClient();
					String x = qp.makeAnnouncement( ag );
					System.out.println( x );
					Thread.sleep( 5000 );
					System.out.println( qp.displayServiceLine() );
					if( k == 5 ) {
						int i = 0;
						while( i < 5 ) {
							qp.addAgent( qp.getOccupiedAgents().poll() );
							int y = rand.nextInt( customers.size() - 1 );
							qp.addClient( (Customer) customers.get( y ) );
							i++;
						}
						k = 0;
					}
					k++;
				}
				
			} catch ( Exception e ) {
				System.err.format("IOException: %s%n", e);
			}
		}
	}
}