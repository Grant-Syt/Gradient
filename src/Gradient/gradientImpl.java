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
		
		// select origin points
		ArrayList<OriginPointImpl> originPoints = this.selectOriginPoints(((int) (Math.random() * 5) + 2));
				
		// draw gradient boxes
		this.drawGradientBoxes(originPoints);
	}

	@Override
	public void drawCircleWaterColor() {
		/* in: n/a
		 * return: n/a
		 * effect: draw circleWaterColor on image
		 */
		
		// select origin points
		ArrayList<OriginPointImpl> originPoints = this.selectOriginPoints(((int) (Math.random() * 5) + 2));
		
		// draw circles
		Graphics2D graphics  = img.createGraphics();
		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, img.getWidth(), img.getHeight());
		
		for(int i = 0; i < originPoints.size(); i++) {
			OriginPoint currentPoint = originPoints.get(i);
			graphics.setColor(new Color((int) (Math.random()*256), (int) (Math.random()*256), (int) (Math.random()*256), (currentPoint.getStrength() + 1) * 51));
			
			int circleDiameter;
//			if (img.getHeight() > img.getWidth()) {
//				circleDiameter = (int) img.getHeight()*2;
//			} else {
//				circleDiameter = (int) img.getWidth()*2;
//			}
			circleDiameter = 10;
			
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
	
	// helper methods
	
	private ArrayList<OriginPointImpl> selectOriginPoints(int numOfOriginPoints) {
		/* in: number of origin points to make
		 * out: list of origin points
		 * effect: make list of origin points
		 */
		
		ArrayList<OriginPointImpl> originPoints = new ArrayList<OriginPointImpl>();
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
		
		return originPoints;
	}
	
	private void drawGradientBoxes(ArrayList<OriginPointImpl> originPoints) {
		/* in: list of origin points
		 * out: n/a
		 * effect: draw gradient boxes
		 */
		Graphics2D graphics  = img.createGraphics();
		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, img.getWidth(), img.getHeight());
		
		int biggerSide;
		if (img.getHeight() > img.getWidth()) {
			biggerSide = img.getHeight();
		} else {
			biggerSide = img.getWidth();
		}
		
		// origin points
		for(int a = 0; a < originPoints.size(); a++) {
			OriginPointImpl currentPoint = originPoints.get(a);
			currentPoint.setColorR((int) (Math.random()*256));
			currentPoint.setColorG((int) (Math.random()*256));
			currentPoint.setColorB((int) (Math.random()*256));
			currentPoint.setColorAlpha((int) ((currentPoint.getStrength() + 1) * 51));
			
			//origin pixel
			if (!(currentPoint.getX() < 0 || currentPoint.getX() >= img.getWidth() || currentPoint.getY() < 0 || currentPoint.getY() >= img.getHeight())) {
				graphics.setColor(new Color(currentPoint.getColorR(), currentPoint.getColorG(), currentPoint.getColorB(), currentPoint.getColorAlpha()));
				graphics.drawLine(currentPoint.getX(), currentPoint.getY(), currentPoint.getX(), currentPoint.getY());
			}
			currentPoint.setColorAlpha((int) (currentPoint.getColorAlpha() - (255/(biggerSide + 2))));
		}
		
		Color oldColor;
		int oldColorR;
		int oldColorG;
		int oldColorB;
		int oldColorAlpha;
		int rowLength = 3;
		int layerCount = 1;
		int oldColorDivisor = 2;
		int currentX = (int) (img.getWidth()/2);
		int currentY = (int) (img.getHeight()/2);
		boolean imageHasWhite = true;
		int hasWhiteCount;
		
		// all layers
		while(imageHasWhite == true) {
			for(int a = 0; a < originPoints.size(); a++) { // add one layer to each gradient box
				OriginPointImpl currentPoint = originPoints.get(a);
				hasWhiteCount = (rowLength-1) * 4;
				// top
				currentX = currentPoint.getX() - layerCount;
				currentY = currentPoint.getY() - layerCount;
				for(int b = 0; b < rowLength-1; b++) {
					if (!(currentX < 0 || currentX >= img.getWidth() || currentY < 0 || currentY >= img.getHeight())) {
						oldColor = new Color(img.getRGB(currentX, currentY));
						oldColorR = oldColor.getRed();
						oldColorG = oldColor.getGreen();
						oldColorB = oldColor.getBlue();
						oldColorAlpha = oldColor.getAlpha();
						if (!(oldColorR == 255 && oldColorG == 255 && oldColorB == 255)) {
							hasWhiteCount--;
						}
						graphics.setColor(new Color((int) ((currentPoint.getColorR() + (oldColorR/oldColorDivisor))/2), 
								(int) ((currentPoint.getColorG() + (oldColorG/oldColorDivisor))/2),
								(int) ((currentPoint.getColorB() + (oldColorB/oldColorDivisor))/2), 
								(int) ((currentPoint.getColorAlpha() + (oldColorAlpha/oldColorDivisor))/2)));
						graphics.drawLine(currentX, currentY, currentX, currentY);
					}
					currentX++;
				}

				// right
				currentX = currentPoint.getX() + layerCount;
				currentY = currentPoint.getY() - layerCount;
				for(int b = 0; b < rowLength-1; b++) {
					if (!(currentX < 0 || currentX >= img.getWidth() || currentY < 0 || currentY >= img.getHeight())) {
						oldColor = new Color(img.getRGB(currentX, currentY));
						oldColorR = oldColor.getRed();
						oldColorG = oldColor.getGreen();
						oldColorB = oldColor.getBlue();
						oldColorAlpha = oldColor.getAlpha();
						if (!(oldColorR == 255 && oldColorG == 255 && oldColorB == 255)) {
							hasWhiteCount--;
						}
						graphics.setColor(new Color((int) ((currentPoint.getColorR() + (oldColorR/oldColorDivisor))/2), 
								(int) ((currentPoint.getColorG() + (oldColorG/oldColorDivisor))/2),
								(int) ((currentPoint.getColorB() + (oldColorB/oldColorDivisor))/2), 
								(int) ((currentPoint.getColorAlpha() + (oldColorAlpha/oldColorDivisor))/2)));
						graphics.drawLine(currentX, currentY, currentX, currentY);
					}
					currentY++;
				}

				//bottom
				currentX = currentPoint.getX() + layerCount;
				currentY = currentPoint.getY() + layerCount;
				for(int b = 0; b < rowLength-1; b++) {
					if (!(currentX < 0 || currentX >= img.getWidth() || currentY < 0 || currentY >= img.getHeight())) {
						oldColor = new Color(img.getRGB(currentX, currentY));
						oldColorR = oldColor.getRed();
						oldColorG = oldColor.getGreen();
						oldColorB = oldColor.getBlue();
						oldColorAlpha = oldColor.getAlpha();
						if (!(oldColorR == 255 && oldColorG == 255 && oldColorB == 255)) {
							hasWhiteCount--;
						}
						graphics.setColor(new Color((int) ((currentPoint.getColorR() + (oldColorR/oldColorDivisor))/2), 
								(int) ((currentPoint.getColorG() + (oldColorG/oldColorDivisor))/2),
								(int) ((currentPoint.getColorB() + (oldColorB/oldColorDivisor))/2), 
								(int) ((currentPoint.getColorAlpha() + (oldColorAlpha/oldColorDivisor))/2)));
						graphics.drawLine(currentX, currentY, currentX, currentY);
					}
					currentX--;
				}

				// left
				currentX = currentPoint.getX() - layerCount;
				currentY = currentPoint.getY() + layerCount;
				for(int b = 0; b < rowLength-1; b++) {
					if (!(currentX < 0 || currentX >= img.getWidth() || currentY < 0 || currentY >= img.getHeight())) {
						oldColor = new Color(img.getRGB(currentX, currentY));
						oldColorR = oldColor.getRed();
						oldColorG = oldColor.getGreen();
						oldColorB = oldColor.getBlue();
						oldColorAlpha = oldColor.getAlpha();
						if (!(oldColorR == 255 && oldColorG == 255 && oldColorB == 255)) {
							hasWhiteCount--;
						}
						graphics.setColor(new Color((int) ((currentPoint.getColorR() + (oldColorR/oldColorDivisor))/2), 
								(int) ((currentPoint.getColorG() + (oldColorG/oldColorDivisor))/2),
								(int) ((currentPoint.getColorB() + (oldColorB/oldColorDivisor))/2), 
								(int) ((currentPoint.getColorAlpha() + (oldColorAlpha/oldColorDivisor))/2)));
						graphics.drawLine(currentX, currentY, currentX, currentY);
					}
					currentY--;
				}
				if (hasWhiteCount == 0) {
					imageHasWhite = false;
				}
				currentPoint.setColorAlpha((int) (currentPoint.getColorAlpha() - (255/(biggerSide + 2))));
			}
			rowLength += 2;
			layerCount++;
		}
		
//		for(int a = 0; a < originPoints.size(); a++) {
//			Color oldColor;
//			int oldColorR;
//			int oldColorG;
//			int oldColorB;
//			int oldColorAlpha;
//			int biggerSide;
//
//			if (img.getHeight() > img.getWidth()) {
//				biggerSide = img.getHeight();
//			} else {
//				biggerSide = img.getWidth();
//			}
//			
//			// origin and origin color
//			OriginPoint centerPoint = originPoints.get(a);
//			int colorR = (int) (Math.random()*256);
//			int colorG = (int) (Math.random()*256);
//			int colorB = (int) (Math.random()*256);
//			int colorAlpha = (int) ((centerPoint.getStrength() + 1) * 51);
//			
//			//origin pixel
//			if (!(centerPoint.getX() < 0 || centerPoint.getX() >= img.getWidth() || centerPoint.getY() < 0 || centerPoint.getY() >= img.getHeight())) {
//				oldColor = new Color(img.getRGB(centerPoint.getX(), centerPoint.getY()));
//				oldColorR = oldColor.getRed();
//				oldColorG = oldColor.getGreen();
//				oldColorB = oldColor.getBlue();
//				oldColorAlpha = oldColor.getAlpha();
//				graphics.setColor(new Color((int) ((colorR + oldColorR)/2), (int) ((colorG + oldColorG)/2), (int) ((colorB + oldColorB)/2), (int) ((colorAlpha + oldColorAlpha)/2)));
//				graphics.drawLine(centerPoint.getX(), centerPoint.getY(), centerPoint.getX(), centerPoint.getY());
//			}
//			colorAlpha -= (int) (255/(biggerSide + 2));
//			
//			// all layers
//			int rowLength = 3;
//			int layerCount = 1;
//			int currentX = (int) (img.getWidth()/2);
//			int currentY = (int) (img.getHeight()/2);
//			
//			int oldColorDivisor = 2;
//			
//			
//			while(layerCount < biggerSide + 2) {
//
//				//top
//				currentX = centerPoint.getX() - layerCount;
//				currentY = centerPoint.getY() - layerCount;
//				for(int b = 0; b < rowLength-1; b++) {
//					if (!(currentX < 0 || currentX >= img.getWidth() || currentY < 0 || currentY >= img.getHeight())) {
//						oldColor = new Color(img.getRGB(currentX, currentY));
//						oldColorR = oldColor.getRed();
//						oldColorG = oldColor.getGreen();
//						oldColorB = oldColor.getBlue();
//						oldColorAlpha = oldColor.getAlpha();
//						graphics.setColor(new Color((int) ((colorR + (oldColorR/oldColorDivisor))/2), (int) ((colorG + (oldColorG/oldColorDivisor))/2), (int) ((colorB + (oldColorB/oldColorDivisor))/2), (int) ((colorAlpha + (oldColorAlpha/oldColorDivisor))/2)));
//						graphics.drawLine(currentX, currentY, currentX, currentY);
//					}
//					currentX++;
//				}
//
//				// right
//				currentX = centerPoint.getX() + layerCount;
//				currentY = centerPoint.getY() - layerCount;
//				for(int b = 0; b < rowLength-1; b++) {
//					if (!(currentX < 0 || currentX >= img.getWidth() || currentY < 0 || currentY >= img.getHeight())) {
//						oldColor = new Color(img.getRGB(currentX, currentY));
//						oldColorR = oldColor.getRed();
//						oldColorG = oldColor.getGreen();
//						oldColorB = oldColor.getBlue();
//						oldColorAlpha = oldColor.getAlpha();
//						graphics.setColor(new Color((int) ((colorR + (oldColorR/oldColorDivisor))/2), (int) ((colorG + (oldColorG/oldColorDivisor))/2), (int) ((colorB + (oldColorB/oldColorDivisor))/2), (int) ((colorAlpha + (oldColorAlpha/oldColorDivisor))/2)));
//						graphics.drawLine(currentX, currentY, currentX, currentY);
//					}
//					currentY++;
//				}
//
//				//bottom
//				currentX = centerPoint.getX() + layerCount;
//				currentY = centerPoint.getY() + layerCount;
//				for(int b = 0; b < rowLength-1; b++) {
//					if (!(currentX < 0 || currentX >= img.getWidth() || currentY < 0 || currentY >= img.getHeight())) {
//						oldColor = new Color(img.getRGB(currentX, currentY));
//						oldColorR = oldColor.getRed();
//						oldColorG = oldColor.getGreen();
//						oldColorB = oldColor.getBlue();
//						oldColorAlpha = oldColor.getAlpha();
//						graphics.setColor(new Color((int) ((colorR + (oldColorR/oldColorDivisor))/2), (int) ((colorG + (oldColorG/oldColorDivisor))/2), (int) ((colorB + (oldColorB/oldColorDivisor))/2), (int) ((colorAlpha + (oldColorAlpha/oldColorDivisor))/2)));
//						graphics.drawLine(currentX, currentY, currentX, currentY);
//					}
//					currentX--;
//				}
//
//				// left
//				currentX = centerPoint.getX() - layerCount;
//				currentY = centerPoint.getY() + layerCount;
//				for(int b = 0; b < rowLength-1; b++) {
//					if (!(currentX < 0 || currentX >= img.getWidth() || currentY < 0 || currentY >= img.getHeight())) {
//						oldColor = new Color(img.getRGB(currentX, currentY));
//						oldColorR = oldColor.getRed();
//						oldColorG = oldColor.getGreen();
//						oldColorB = oldColor.getBlue();
//						oldColorAlpha = oldColor.getAlpha();
//						graphics.setColor(new Color((int) ((colorR + (oldColorR/oldColorDivisor))/2), (int) ((colorG + (oldColorG/oldColorDivisor))/2), (int) ((colorB + (oldColorB/oldColorDivisor))/2), (int) ((colorAlpha + (oldColorAlpha/oldColorDivisor))/2)));
//						graphics.drawLine(currentX, currentY, currentX, currentY);
//					}
//					currentY--;
//				}
//
//				rowLength += 2;
//				layerCount++;
//				colorAlpha -= (int) (255/(biggerSide + 2));
//			}
//		}
		graphics.dispose();
	}
}
