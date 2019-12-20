package Gradient;

public class gradientPlayground {
	public static void main(String[] args) {
		
		gradient x = new gradientImpl(6016, 3384);
		
		System.out.println(x.gradientify());
		x.print("testimage");

	}
}
