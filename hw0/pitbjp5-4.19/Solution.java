public class Solution {
	public static void main (String[] args) {
		quadrant(Integer.parseInt(args[0]),
			Integer.parseInt(args[1]));
	}
	public static int quadrant (double x, double y) {
		if (x == 0 || y == 0) {
			return 0;
		}
		if (x < 0) {
			if (y < 0) {
				return 3;
			} else {
				return 2;
			}
		} else {
			if (y < 0) {
				return 4;
			} else {
				return 1;
			}
		}
	}
}
