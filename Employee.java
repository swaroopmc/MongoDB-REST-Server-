package cmpe282swaroop121.client;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Employee {

	
	int id;
	String firstName;
	
	String lastName;
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setfirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setlastName(String lastName) {
		this.lastName = lastName;
	}
}
