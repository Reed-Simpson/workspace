package view.infopanels;
import java.awt.event.ActionEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.AbstractAction;
import javax.swing.JTextPane;

import general.Util;
import view.InfoPanel;

@SuppressWarnings("serial")
	public class ChatLinkMouseoverAction extends AbstractAction    {
		private String textLink;
		private final JTextPane textPane;
		private InfoPanel info;

		public ChatLinkMouseoverAction(String textLink,JTextPane textPane,InfoPanel info){
			this.textLink = textLink;
			this.textPane = textPane;
			this.info = info;
		}

		protected void execute(){
			Matcher matcher = Pattern.compile("\\{(\\D+):(-?\\d+),(-?\\d+),(\\d+)\\}").matcher(textLink);
			if(matcher.matches()) {
				String tooltipText = info.getToolTipText(
						matcher.group(1),
						Integer.valueOf(matcher.group(2)),
						Integer.valueOf(matcher.group(3)),
						Integer.valueOf(matcher.group(4))-1);
				Util.replace(tooltipText, "\r\n", "<br>");
				textPane.setToolTipText("<html style=\"width:300px\">"+tooltipText+"");
			}
		}

		@Override
		public void actionPerformed(ActionEvent e){
			execute();
		}
	}