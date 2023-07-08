public class Solution {
	public static void main(String[] args) {
		printIndexed(args[0]);
	}
	public static void printIndexed(String s) {
		int length = s.length();
		for(int i = 0; i < length; ++i) {
			System.out.print(s.charAt(i));
			System.out.print(length - i - 1);
		}
	}
}
