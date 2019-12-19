package Gradient;

public class gradientPlayground {
	public static void main(String[] args) {
		gradient x = new gradientImpl(6016, 3384);
		System.out.println(x.gradientify());
		x.print("testimage");
//		double countA = 0;
//		double count = 0;
//		for(int i = 0; i < 2000; i++) {
//			if(((int) (Math.random()*2)) == 0) {
//				countA++;
//			}
//			count++;
//			System.out.println((int) (Math.random()*2));
//			System.out.println((int) (Math.random()*2) == 0);
//		}
//		System.out.println(countA/count);
	}
}
