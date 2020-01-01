
public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		int m = 1;
		int mRec = -1;
		Point next = new Point(0,1);
		Point line = new Point(0,0);
		
		System.out.println(findPath.getInter(next, line, new Point(1,1), m, mRec));
		
	}

}
