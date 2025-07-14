package view.infopanels;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import data.Reference;
import view.InfoPanel;

@SuppressWarnings("serial")
	public class TextLinkAction extends AbstractAction    {
		private Reference textLink;
		private InfoPanel info;

		public TextLinkAction(Reference textLink,InfoPanel info){
			this.textLink = textLink;
			this.info = info;
		}

		public void execute(){
			info.selectTabAndIndex(textLink);
		}

		@Override
		public void actionPerformed(ActionEvent e){
			execute();
		}
	}