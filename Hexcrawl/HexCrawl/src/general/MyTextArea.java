package general;

import java.awt.event.MouseEvent;

import javax.swing.JTextArea;

@SuppressWarnings("serial") // Same-version serialization only
public class MyTextArea extends JTextArea{
	@Override
	public String getToolTipText(MouseEvent event) {
		// TODO Auto-generated method stub
		return super.getToolTipText(event);
	}
}
