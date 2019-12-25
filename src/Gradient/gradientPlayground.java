package Gradient;

public class gradientPlayground {
	public static void main(String[] args) {
		
		gradient x = new gradientImpl(1920, 1080);
		
		x.drawGradient();
		System.out.println(x.saveCurrentImage("testImage"));

	}
}
