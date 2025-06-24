package view;

import java.awt.BorderLayout;

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
        
        //setUndecorated(true);
        add(label, BorderLayout.NORTH);
        add(progressBar, BorderLayout.CENTER);
        setSize(1000, 800);
    }

    public void createProgressUI() {
        pack();
        setAlwaysOnTop(true);
		this.setLocationRelativeTo(motherFrame);
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
		dispose();
    }
}