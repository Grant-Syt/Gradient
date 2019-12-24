package Gradient;

public class OriginPointImpl implements OriginPoint {
	private int x;
	private int y;
	private int strength;
	private int colorR;
	private int colorG;
	private int colorB;
	private int colorAlpha;
	
	public OriginPointImpl(int x, int y, int strength) {
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
	
	public void setColorR(int colorR) {
		this.colorR = colorR;
	}
	
	public void setColorG(int colorG) {
		this.colorG = colorG;
	}
	
	public void setColorB(int colorB) {
		this.colorB = colorB;
	}
	
	public void setColorAlpha(int colorAlpha) {
		this.colorAlpha = colorAlpha;
	}
	
	public int getColorR() {
		return colorR;
	}
	
	public int getColorG() {
		return colorG;
	}
	
	public int getColorB() {
		return colorB;
	}
	
	public int getColorAlpha() {
		return colorAlpha;
	}
}
