package Gradient;

public class gradientPlayground {
	public static void main(String[] args) {
		
		gradient x = new gradientImpl(100, 100);
		
		x.drawGradient();
		System.out.println(x.saveCurrentImage("testimage"));

	}
}
