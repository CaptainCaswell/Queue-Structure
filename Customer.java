public class Customer extends Client {
	
	private String name;
	
	//Constructors
	public Customer( String name ) {
		super();
		this.name = name;
	}
	
	//Accessor methods
	public String getName() { return name; }
	public void setName( String name ) { this.name = name; }
	
	public String toString() {
		return "[ID: " + getClientID() +", Name: " + name + "]";
	}
}