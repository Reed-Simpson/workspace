package view;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

public class ProgressBarDialog extends JDialog {
    private static final long serialVersionUID = 1L;

    private JProgressBar progressBar;
    private JFrame motherFrame;
    private JLabel label = new JLabel("loading.. ");

    public ProgressBarDialog(JFrame frame) {
    	this.progressBar = new JProgressBar(0, 100);
		progressBar.setStringPainted(true);
        this.motherFrame = frame;
        
        setUndecorated(true);
        add(label, BorderLayout.NORTH);
        add(progressBar, BorderLayout.CENTER);
        progressBar.setPreferredSize(new Dimension(500, 100));
        pack();
		this.setLocationRelativeTo(motherFrame);
    }

    public void createProgressUI() {
        setAlwaysOnTop(true);
        setVisible(true);
    }
    
    public JProgressBar getProgressBar() {
    	return this.progressBar;
    }
    public void setLabel(String s) {
    	label.setText(s);
    }
    public void createProgressUI(String s) {
    	setLabel(s);
    	createProgressUI();
    }
    public void removeProgressUI() {
        //setAlwaysOnTop(false);
        setVisible(false);
		//dispose();
    }
}