package cmpe282swaroop121.client;


import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Project {

	
	int id;
	String name;
	
	float budget;
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getBudget() {
		return budget;
	}

	public void setBudget(float budget) {
		this.budget = budget;
	}
}
