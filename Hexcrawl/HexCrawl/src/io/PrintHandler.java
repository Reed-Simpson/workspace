package io;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import util.Util;
import view.MapPanel;

public class PrintHandler {
	public static final int WIDTH = 1100;
	public static final int HEIGHT = 850;

	private File directory;

	public PrintHandler() {
		this.directory = new File(AppData.getDirectory(),"\\screenshots");
	}

	public void print(MapPanel panel) {
		Point center = panel.getSelectedGridPoint();
		Point viewCenter = panel.getAbsolutePos(center);
		Point viewCorner = new Point(viewCenter.x-WIDTH/2,viewCenter.y-HEIGHT/2);
		BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		if(panel.print(image,viewCorner)) {
			//image = image.getSubimage((image.getWidth()-1100)/2, (image.getHeight()-850)/2, 1100, 850);
			File outputFile = new File(directory,"screenshot-["+Util.posString(center, panel.getRecord().getZero())+"]x"+panel.getScale()+".png"); // Specify your desired file name and path
			try {
				directory.mkdirs();
				ImageIO.write(image, "png", outputFile);// "png" is the format, outputFile is the destination
				System.out.println(outputFile.getAbsolutePath());
			} catch (IOException e) {
				e.printStackTrace();
			} 
		}
	}

}
