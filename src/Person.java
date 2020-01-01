import javax.swing.JButton;
import javax.swing.Timer;

public class Person extends JButton {

	String name; int weight; Timer timer; Point nextPoint; int index; byte okay;
	
	public Person() {
		// TODO Auto-generated constructor stub
		name = "N/A"; weight = 1;
	}
	
	public Person(String nme) {
		super(nme);
		name = nme; weight = 1;
		
	}
	
	public Person(String nme, int wght) {
		name = nme; 
		weight = wght;
	}
	
	public boolean equals(Object T) {
		if(T instanceof Person) {
		
			Person a = (Person)T;
			return a.name.equals(this.name);
			
		}
		return false;
	}
	
	
	public int hashCode() {
		return name.length();
	}

}
