package io;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
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
		double printscale = 2.0;
		Point center = panel.getSelectedGridPoint();
		BufferedImage image = new BufferedImage((int)(WIDTH*printscale), (int)(HEIGHT*printscale), BufferedImage.TYPE_INT_RGB);
		panel.screenshot(image,printscale);
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
	public void print(MapPanel panel) {
		PrinterJob pj = PrinterJob.getPrinterJob();
		PageFormat pf = pj.defaultPage();

		//BufferedImage image = new BufferedImage((int)pf.getImageableWidth(), (int)pf.getImageableHeight(), BufferedImage.TYPE_INT_RGB);
		System.out.println("Imagable area"+(int)pf.getImageableWidth()+"x"+(int)pf.getImageableHeight());
		//panel.screenshot(image);
		pj.setPrintable(panel);
		
		if (pj.printDialog()) {
			// User clicked OK in the print dialog, proceed with printing
			try {
				pj.print(); // Initiates the printing process
			} catch (PrinterException exc) {
				System.err.println("Printing error: " + exc.getMessage());
			}
		} else {
			// User cancelled the print dialog
			System.out.println("Printing cancelled by user.");
		}
	}

}
