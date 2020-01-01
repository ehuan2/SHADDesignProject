import java.awt.Color;

public class Bot {
	boolean avail = true;
	int max;
	int carry;
	int x;
	int y;
	Color color = new Color(0,255,0);
	
	public Bot() {
		// TODO Auto-generated constructor stub
	}

	public Bot(int minValue, int minValue2) {
		// TODO Auto-generated constructor stub
		x = minValue; 
		y = minValue2; carry = 0; 
	}

	public Bot(int x, int y, int max) {
		this.x = x; this.y = y; this.max = max; carry = 0;
	}
	
	public Bot(int max) {
		this.max = max;
	}
	
	public String toString() {
		return x + " "  + y;
	}
	
}
