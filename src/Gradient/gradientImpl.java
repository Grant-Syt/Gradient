package Gradient;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class gradientImpl implements gradient {
	BufferedImage img = null;
	
	public gradientImpl(int x, int y) {
		this.newImage(x, y);
	}
	
//	public gradientImpl(String x) {
//		this.newImage(x);
//	}
	
	@Override
	public boolean newImage(int x, int y) {
		/* in: x,y size of new image
		 * return: true for success
		 * effect: new blank image
		 */
		img = new BufferedImage(x, y, BufferedImage.TYPE_INT_RGB);
		return true;
	}

//	@Override
//	public boolean newImage(String x) {
//		/* in: string of file name
//		 * return: true for success
//		 * effect: new image from files
//		 */
//		try {
//		    img = ImageIO.read(new File(x));
//		} catch (IOException e) {
//			return false;
//		}
//		return true;
//	}

	@Override
	public boolean gradientify() {
		/* in: n/a
		 * return: true for success
		 * effect: draw random gradient on image
		 */
		
		int numOfGradientPoints = ((int) (Math.random() * 5) + 2);
		ArrayList<gradientPoint> gradientPoints = new ArrayList<gradientPoint>();
		boolean onSector1 = ((int) (Math.random()*2)) == 0;
		int currentSide;
		/* sector 1
		 * 0: bottom right corner
		 * 1: right
		 * 2: top right
		 * 3: top
		 * sector 2
		 * 4: top left
		 * 5: left
		 * 6: bottom left
		 * 7: bottom
		 */
		ArrayList<Integer> sector1 = new ArrayList<Integer>();
		sector1.add(0);
		sector1.add(1);
		sector1.add(2);
		sector1.add(3);
		ArrayList<Integer> sector2 = new ArrayList<Integer>();
		sector2.add(4);
		sector2.add(5);
		sector2.add(6);
		sector2.add(7);
		
		// Select semi-random points on border
		for (int i = 0; i < numOfGradientPoints; i++) {
			if(onSector1) {
				currentSide = (int) (Math.random() * sector1.size());
				if (sector1.get(currentSide) == 0) {
					// bottom right
					gradientPoints.add(new gradientPointImpl(img.getWidth(), 
							img.getHeight(), (int) (Math.random() * 5)));
				} else if(sector1.get(currentSide) == 1) {
					// right
					gradientPoints.add(new gradientPointImpl(img.getWidth(),
							(int) ((Math.random()*(img.getHeight()*.51)) + img.getHeight()*.25), (int) (Math.random() * 5)));
				} else if(sector1.get(currentSide) == 2) {
					// top right
					gradientPoints.add(new gradientPointImpl(img.getWidth(),
							0, (int) (Math.random() * 5)));
				} else {
					// top
					gradientPoints.add(new gradientPointImpl((int) ((Math.random()*(img.getWidth()*.51)) + img.getWidth()*.25),
							0, (int) (Math.random() * 5)));
				}
				sector1.remove(currentSide);
				onSector1 = false;
			} else {
				currentSide = (int) (Math.random() * sector2.size());
				if (sector2.get(currentSide) == 4) {
					// top left
					gradientPoints.add(new gradientPointImpl(0, 
							0, (int) (Math.random() * 5)));
				} else if(sector2.get(currentSide) == 5) {
					// left
					gradientPoints.add(new gradientPointImpl(0,
							(int) ((Math.random()*(img.getHeight()*.51)) + img.getHeight()*.25), (int) (Math.random() * 5)));
				} else if(sector2.get(currentSide) == 6) {
					// bottom left
					gradientPoints.add(new gradientPointImpl(0,
							img.getHeight(), (int) (Math.random() * 5)));
				} else {
					// bottom
					gradientPoints.add(new gradientPointImpl((int) ((Math.random()*(img.getWidth()*.51)) + img.getWidth()*.25),
							img.getHeight(), (int) (Math.random() * 5)));
				}
				sector2.remove(currentSide);
				onSector1 = true;
			}
		}
		
		// test points
		for(int i = 0; i < numOfGradientPoints; i++) {
			gradientPoint currentPoint = gradientPoints.get(i);
			System.out.println("Point" + i + ": (x, y): (" + currentPoint.getX() + ", " + 
			currentPoint.getY() + ") strength: " + currentPoint.getStrength());
		}
		System.out.print("\n");
		
		// Make color circles
		Graphics2D graphics  = img.createGraphics();
		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, img.getWidth(), img.getHeight());
		for(int i = 0; i < numOfGradientPoints; i++) {
			gradientPoint currentPoint = gradientPoints.get(i);
			Color currentColor = new Color((int) (Math.random()*256), (int) (Math.random()*256), (int) (Math.random()*256), (currentPoint.getStrength() + 1) * 51);
			graphics.setColor(currentColor);
			int circleDiameter = (int) img.getWidth()*2;
//			int circleDiameter = 10;
			graphics.fillOval(currentPoint.getX() - (circleDiameter/2), currentPoint.getY() - (circleDiameter/2), circleDiameter, circleDiameter);
		}
		
		graphics.dispose();
		return true;
	}

	@Override
	public boolean print(String fileName) {
		try {
		    File outputfile = new File(fileName + ".png");
		    ImageIO.write(img, "png", outputfile);
		    return true;
		} catch (IOException e) {
		    return false;
		}
		
	}

}
