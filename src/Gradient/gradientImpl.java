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
	
	@Override
	public void newImage(int x, int y) {
		/* in: x,y size of new image
		 * return: n/a
		 * effect: new blank image
		 */
		img = new BufferedImage(x, y, BufferedImage.TYPE_INT_RGB);
	}

	@Override
	public void drawGradient() {
		/* in: n/a
		 * return: n/a
		 * effect: draw random gradient on image
		 */
		
		int numOfGradientPoints = ((int) (Math.random() * 5) + 2);
		ArrayList<OriginPoint> gradientPoints = new ArrayList<OriginPoint>();
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
					gradientPoints.add(new OriginPointImpl(img.getWidth(), 
							img.getHeight(), (int) (Math.random() * 5)));
				} else if(sector1.get(currentSide) == 1) {
					// right
					gradientPoints.add(new OriginPointImpl(img.getWidth(),
							(int) ((Math.random()*(img.getHeight()*.51)) + img.getHeight()*.25), (int) (Math.random() * 5)));
				} else if(sector1.get(currentSide) == 2) {
					// top right
					gradientPoints.add(new OriginPointImpl(img.getWidth(),
							0, (int) (Math.random() * 5)));
				} else {
					// top
					gradientPoints.add(new OriginPointImpl((int) ((Math.random()*(img.getWidth()*.51)) + img.getWidth()*.25),
							0, (int) (Math.random() * 5)));
				}
				sector1.remove(currentSide);
				onSector1 = false;
			} else {
				currentSide = (int) (Math.random() * sector2.size());
				if (sector2.get(currentSide) == 4) {
					// top left
					gradientPoints.add(new OriginPointImpl(0, 
							0, (int) (Math.random() * 5)));
				} else if(sector2.get(currentSide) == 5) {
					// left
					gradientPoints.add(new OriginPointImpl(0,
							(int) ((Math.random()*(img.getHeight()*.51)) + img.getHeight()*.25), (int) (Math.random() * 5)));
				} else if(sector2.get(currentSide) == 6) {
					// bottom left
					gradientPoints.add(new OriginPointImpl(0,
							img.getHeight(), (int) (Math.random() * 5)));
				} else {
					// bottom
					gradientPoints.add(new OriginPointImpl((int) ((Math.random()*(img.getWidth()*.51)) + img.getWidth()*.25),
							img.getHeight(), (int) (Math.random() * 5)));
				}
				sector2.remove(currentSide);
				onSector1 = true;
			}
		}
		
		// print gradient points
		for(int i = 0; i < numOfGradientPoints; i++) {
			OriginPoint currentPoint = gradientPoints.get(i);
			System.out.println("Point" + i + ": (x, y): (" + currentPoint.getX() + ", " + 
			currentPoint.getY() + ") strength: " + currentPoint.getStrength());
		}
		System.out.print("\n");
				
		// Make color gradients from origin gradient points
		Graphics2D graphics  = img.createGraphics();
		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, img.getWidth(), img.getHeight());
		
		for(int a = 0; a < numOfGradientPoints; a++) {
			// origin and origin color
			OriginPoint currentPoint = gradientPoints.get(a);
			int colorR = (int) (Math.random()*256);
			int colorG = (int) (Math.random()*256);
			int colorB = (int) (Math.random()*256);
			int colorAlpha = (currentPoint.getStrength() + 1) * 51;
			
			//origin pixel
			Color currentColor = new Color(img.getRGB(currentPoint.getX(), currentPoint.getY()));
			int oldColorR = currentColor.getRed();
			int oldColorG = currentColor.getGreen();
			int oldColorB = currentColor.getBlue();
			int oldColorAlpha = currentColor.getAlpha();
			graphics.setColor(new Color((colorR + oldColorR)/2, (colorG + oldColorG)/2, (colorB + oldColorB)/2, (colorAlpha + oldColorAlpha)/2));
			graphics.drawLine(currentPoint.getX(), currentPoint.getY(), currentPoint.getX(), currentPoint.getY());
			
			// boxes
			int rowLength = 3;
			int layerCount = 0;
			int currentX;
			int currentY;
			while(colorAlpha > 0) {
				
				for(int b = 0; b < Math.ceil(rowLength/2); b++) { // top
					currentX = currentPoint.getX()  
				}
					
				currentColor = new Color(img.getRGB(currentX, currentY));
				oldColorR = currentColor.getRed();
				oldColorG = currentColor.getGreen();
				oldColorB = currentColor.getBlue();
				oldColorAlpha = currentColor.getAlpha();
				graphics.setColor(new Color((colorR + oldColorR)/2, (colorG + oldColorG)/2, (colorB + oldColorB)/2, (colorAlpha + oldColorAlpha)/2));
				graphics.drawLine(currentPoint.getX(), currentPoint.getY(), currentPoint.getX(), currentPoint.getY());
				
				rowLength += 2;
				layerCount++;
				colorAlpha--;
			}
		}

		graphics.dispose();
	}

	@Override
	public void drawCircleWaterColor() {
		/* in: n/a
		 * return: n/a
		 * effect: draw circleWaterColor on image
		 */
		
		int numOfOriginPoints = ((int) (Math.random() * 5) + 2);
		ArrayList<OriginPoint> originPoints = new ArrayList<OriginPoint>();
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
		for (int i = 0; i < numOfOriginPoints; i++) {
			if(onSector1) {
				currentSide = (int) (Math.random() * sector1.size());
				if (sector1.get(currentSide) == 0) {
					// bottom right
					originPoints.add(new OriginPointImpl(img.getWidth(), 
							img.getHeight(), (int) (Math.random() * 5)));
				} else if(sector1.get(currentSide) == 1) {
					// right
					originPoints.add(new OriginPointImpl(img.getWidth(),
							(int) ((Math.random()*(img.getHeight()*.51)) + img.getHeight()*.25), (int) (Math.random() * 5)));
				} else if(sector1.get(currentSide) == 2) {
					// top right
					originPoints.add(new OriginPointImpl(img.getWidth(),
							0, (int) (Math.random() * 5)));
				} else {
					// top
					originPoints.add(new OriginPointImpl((int) ((Math.random()*(img.getWidth()*.51)) + img.getWidth()*.25),
							0, (int) (Math.random() * 5)));
				}
				sector1.remove(currentSide);
				onSector1 = false;
			} else {
				currentSide = (int) (Math.random() * sector2.size());
				if (sector2.get(currentSide) == 4) {
					// top left
					originPoints.add(new OriginPointImpl(0, 
							0, (int) (Math.random() * 5)));
				} else if(sector2.get(currentSide) == 5) {
					// left
					originPoints.add(new OriginPointImpl(0,
							(int) ((Math.random()*(img.getHeight()*.51)) + img.getHeight()*.25), (int) (Math.random() * 5)));
				} else if(sector2.get(currentSide) == 6) {
					// bottom left
					originPoints.add(new OriginPointImpl(0,
							img.getHeight(), (int) (Math.random() * 5)));
				} else {
					// bottom
					originPoints.add(new OriginPointImpl((int) ((Math.random()*(img.getWidth()*.51)) + img.getWidth()*.25),
							img.getHeight(), (int) (Math.random() * 5)));
				}
				sector2.remove(currentSide);
				onSector1 = true;
			}
		}
		
		// print origin points
		for(int i = 0; i < numOfOriginPoints; i++) {
			OriginPoint currentPoint = originPoints.get(i);
			System.out.println("Point" + i + ": (x, y): (" + currentPoint.getX() + ", " + 
			currentPoint.getY() + ") strength: " + currentPoint.getStrength());
		}
		System.out.print("\n");
		
		// Make circle water color
		Graphics2D graphics  = img.createGraphics();
		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, img.getWidth(), img.getHeight());
		
		for(int i = 0; i < numOfOriginPoints; i++) {
			OriginPoint currentPoint = originPoints.get(i);
			graphics.setColor(new Color((int) (Math.random()*256), (int) (Math.random()*256), (int) (Math.random()*256), (currentPoint.getStrength() + 1) * 51));
			
			int circleDiameter;
			if (img.getHeight() > img.getWidth()) {
				circleDiameter = (int) img.getHeight()*2;
			} else {
				circleDiameter = (int) img.getWidth()*2;
			}
//			int circleDiameter = 10;
			
			graphics.fillOval(currentPoint.getX() - (circleDiameter/2), currentPoint.getY() - (circleDiameter/2), circleDiameter, circleDiameter);
		}
		graphics.dispose();
	}

	@Override
	public boolean saveCurrentImage(String fileName) {
		try {
		    File outputfile = new File(fileName + ".png");
		    ImageIO.write(img, "png", outputfile);
		    return true;
		} catch (IOException e) {
		    return false;
		}
		
	}
}
