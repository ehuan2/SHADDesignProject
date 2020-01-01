import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

class Point {

	int x, y;
	Point next;

	public Point() {

	}

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public boolean equals(Object T) {

		if (T instanceof Point) {
			Point a = (Point) T;
			return a.x == this.x && a.y == this.y;
		}
		return false;
	}

	public int hashCode() {
		return x + y;
	}

	public String toString() {
		return x + " " + y;
	}
	
}

public class findPath {

	public static Bot findBot(int x, int y) {
		
		Bot small = new Bot();
		int smallValue = Integer.MAX_VALUE;

		for(int i = 0; i <= UImain.allBots.length-1; i++) {

			Bot a = UImain.allBots[i];
			if(a.avail) {
			int value = (int) Math.sqrt(Math.pow(a.x - x, 2) + Math.pow(a.y - y, 2));

			if (value < smallValue) {
				small = a;
				smallValue = value;
			}
			}
		}

		return small;
	}

	public static Stack<Point> findBFS(Point a, Bot b, Stack<Point> list, boolean first) {

		Set<Block> set = UImain.setBlock;

		Queue<Point> q = new LinkedList<>();

		Set<Point> setPoint = new HashSet<>();

		q.add(new Point(b.x, b.y));
		setPoint.add(new Point(b.x, b.y));
		
		Point find = new Point(a.x, a.y);

		while (!q.isEmpty()) {
			Point next = q.poll();

			if(next.x+25 >= find.x && next.x - 25 <= find.x && next.y + 25 >= find.y && next.y - 25 <= find.y) {
			Point nextAgain = next;
				
				while(!nextAgain.equals(new Point(b.x, b.y)) || !(nextAgain == null)) {
					list.add(nextAgain);
					nextAgain = nextAgain.next;
					if(nextAgain == null) {
						break;
					}
				}
				
				Stack<Point> stack = new Stack<>();
				
				if(first) {
					stack = findBFS(new Point(50,375), new Bot(next.x,next.y), new Stack<>(), false);

				}
				
				stack.addAll(list);
				
				return stack;
			}

			int x = next.x;
			int y = next.y;

			boolean up = true, down = true, right = true, left = true;

			Iterator<Block> i = set.iterator();

			while (i.hasNext()) {
				Block nextBlock = i.next();
				
				if ((x + 25) < nextBlock.x2 && (x + 75) > nextBlock.x2 && ((y+25) > nextBlock.y1 && (y-25) < nextBlock.y2)) {
					right = false;
				}
				if ((x - 25) > nextBlock.x2 && (x - 75) < nextBlock.x2 && ((y+25) > nextBlock.y1 && (y-25) < nextBlock.y2)) {
					left = false;
				}

				if ((y + 25) < nextBlock.y1 && (y + 75) > nextBlock.y1 && ((x+25) > nextBlock.x1 && (x-25) < nextBlock.x2)) {
					down = false;
				}
				if ((y - 25) > nextBlock.y2 && (y - 75) < nextBlock.y2 && ((x+25) > nextBlock.x1 && (x-25) < nextBlock.x2)) {

					up = false;
				}
			}

			if(up) {
				Point add = new Point(next.x, next.y-50);
				
				if(!setPoint.contains(add)) {
					add.next = next;
				q.add(add);
				setPoint.add(add);
				}
			}
			
			if(down) {
				Point add = new Point(next.x, next.y+50);
				
				if(!setPoint.contains(add)) {
					add.next = next;
				q.add(add);
				setPoint.add(add);
				}
			}
			
			if(right) {
				Point add = new Point(next.x+50, next.y);
				
				if(!setPoint.contains(add)) {
					add.next = next;
				q.add(add);
				setPoint.add(add);
				}
			}
			
			if(left) {
				Point add = new Point(next.x-50, next.y);
				
				if(!setPoint.contains(add)) {
					add.next = next;
					q.add(add);
				setPoint.add(add);
				}
			}
			
		}

		return null;

	}
	
	public static boolean getInter(Point a, Point b, Point c, double m, double mRec) {
		
		double xFin = 0;
		double yFin = 0;
		
		int y1 = a.y; int y2 = b.y;
		int x1 = a.x; int x2 = b.x;
		
		xFin = (y1-y2 + m*x1 + mRec*x2) / (m + mRec);
	
		yFin = m*(xFin - x1) + y1;
		
	
		if(x2 < c.x) {
			if(xFin < x2 || xFin > c.x) {
				return true;
			}
		} else {
			if(xFin < c.x || xFin > x2) {
				return true;
			}	
		}
		
		if(y2 < c.y) {
			if(yFin < y2 || yFin > c.y) {
				return true;
			}
		} else {
			if(yFin < c.y || yFin > y2) {
				return true;
			}	
		}
		
		double dist = Math.sqrt((Math.pow(x1-xFin, 2) + Math.pow(y1-yFin, 2)));
		
		return dist > 25;
		
	}

}
