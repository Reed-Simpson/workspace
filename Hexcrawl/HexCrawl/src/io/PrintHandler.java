package io;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import util.Util;
import view.MapPanel;

public class PrintHandler {
	public static final int WIDTH = 1100;
	public static final int HEIGHT = 850;

	private File directory;

	public PrintHandler() {
		this.directory = new File(AppData.getDirectory(),"\\screenshots");
	}

	public void screenshot(MapPanel panel) {
		Point center = panel.getSelectedGridPoint();
		BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		if(panel.print(image)) {
			File outputFile = new File(directory,"screenshot-["+Util.posString(center, panel.getRecord().getZero())+"]x"+panel.getScale()+".png"); // Specify your desired file name and path

			try {
				directory.mkdirs();
				ImageIO.write(image, "png", outputFile);// "png" is the format, outputFile is the destination
				JOptionPane.showMessageDialog(panel,
						"Image Saved to: "+outputFile.getAbsolutePath(),
						"Print Confirmation",
						JOptionPane.INFORMATION_MESSAGE);
				System.out.println(outputFile.getAbsolutePath());
			} catch (IOException e) {
				e.printStackTrace();
			} 
		}
	}

}
