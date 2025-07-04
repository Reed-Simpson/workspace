package view.infopanels;
import java.awt.event.ActionEvent;
import java.util.regex.Matcher;

import javax.swing.AbstractAction;

import data.Reference;
import view.InfoPanel;

@SuppressWarnings("serial")
	public class TextLinkAction extends AbstractAction    {
		private String textLink;
		private InfoPanel info;

		public TextLinkAction(String textLink,InfoPanel info){
			this.textLink = textLink;
			this.info = info;
		}

		public void execute(){
			Matcher matcher = Reference.PATTERN.matcher(textLink);
			if(matcher.matches()) {
				info.selectTabAndIndex(
						matcher.group(1),
						Integer.valueOf(matcher.group(2)),
						Integer.valueOf(matcher.group(3)),
						Integer.valueOf(matcher.group(4))-1);
			}
		}

		@Override
		public void actionPerformed(ActionEvent e){
			execute();
		}
	}