
public class Block {
	int x1, y1, x2, y2;

	public Block() {
		// TODO Auto-generated constructor stub
	}

	public Block(int x1, int y1) {
		this.x1 = x1;
		this.y1 = y1;
	}

	public boolean equals(Object T) {
		
		if (T instanceof Block) {
			
			
			Block a = (Block) T;
			return a.x1 == this.x1 && a.y1 == this.y1 && a.x2 == this.x2 && a.y2 == this.y2;
			
			
		}
		return false;
	}

	public int hashCode() {
		return x1 + y1 + x2 + y2;
	}

	public String toString() {
		return x1 + " " + y1 + " " + x2 + " " + y2;
	}
	
	public void setBlock(int x2, int y2) {
		
		
		this.x2 = x2;
		this.y2 = y2;
		
	}
	
}
