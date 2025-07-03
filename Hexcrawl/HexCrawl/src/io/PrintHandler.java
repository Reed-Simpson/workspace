package io;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import util.Util;
import view.MapPanel;

public class PrintHandler {
	
	private File directory;

	public PrintHandler() {
		this.directory = new File(AppData.getDirectory(),"\\screenshots");
	}

	public void print(MapPanel panel) {
		BufferedImage image = new BufferedImage(panel.getWidth(), panel.getHeight(), BufferedImage.TYPE_INT_RGB);
		panel.print(image);
		Point center = panel.getSelectedGridPoint();
		image = image.getSubimage((image.getWidth()-1100)/2, (image.getHeight()-850)/2, 1100, 850);
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
