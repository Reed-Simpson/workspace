package view;
import java.awt.event.ActionEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.AbstractAction;

@SuppressWarnings("serial")
	public class ChatLinkAction extends AbstractAction    {
		private String textLink;
		private InfoPanel info;

		ChatLinkAction(String textLink,InfoPanel info){
			this.textLink = textLink;
			this.info = info;
		}

		protected void execute(){
			Matcher matcher = Pattern.compile("\\{(\\D+):(-?\\d+),(-?\\d+),(\\d+)\\}").matcher(textLink);
			if(matcher.matches()) {
				//System.out.println(textLink);
				info.selectTabAndIndex(
						matcher.group(1),
						Integer.valueOf(matcher.group(2)),
						Integer.valueOf(matcher.group(3)),
						Integer.valueOf(matcher.group(4)));
			}
		}

		@Override
		public void actionPerformed(ActionEvent e){
			execute();
		}
	}