package view;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class RegionPanel extends JPanel{
	private static final long serialVersionUID = 8127723847211857505L;
	

	public RegionPanel() {
		this.setPreferredSize(new Dimension(300,300));
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setAlignmentX(LEFT_ALIGNMENT);
		JPanel posData = new JPanel();
		posData.setLayout(new BoxLayout(posData, BoxLayout.Y_AXIS));
		posData.setAlignmentX(LEFT_ALIGNMENT);
		
	}

}
