package Gradient;

public class gradientPointImpl implements gradientPoint {
	private int x;
	private int y;
	private int strength;
	
	public gradientPointImpl(int x, int y, int strength) {
		this.x = x;
		this.y = y;
		this.strength = strength;
	}

	@Override
	public int getX() {
		return x;
	}

	@Override
	public int getY() {
		return y;
	}

	@Override
	public int getStrength() {
		return strength;
	}

}
