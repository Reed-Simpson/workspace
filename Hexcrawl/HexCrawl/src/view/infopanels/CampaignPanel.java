package view.infopanels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

import data.HexData;
import data.Reference;
import util.Util;
import view.InfoPanel;
import view.MyTextPane;

@SuppressWarnings("serial")
public class CampaignPanel extends JPanel {


	private JTabbedPane campaignTabs;
	private JPanel charactersPanel;
	private ArrayList<MyTextPane> charactersList;
	private JTextArea charactersEmpty;
	private ArrayList<MyTextPane> threadsList;
	private InfoPanel info;


	public CampaignPanel(InfoPanel info) {
		this.info = info;
		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));

		campaignTabs = new JTabbedPane();
		
		JPanel charactersTabPanel = new JPanel();
		charactersTabPanel.setLayout(new BoxLayout(charactersTabPanel, BoxLayout.Y_AXIS));
		JPanel charactersHeader = new JPanel();
		charactersHeader.setLayout(new BoxLayout(charactersHeader, BoxLayout.X_AXIS));
		charactersHeader.add(Box.createHorizontalStrut(40));
		charactersHeader.add(new JLabel("Characters:"),BorderLayout.WEST);
		charactersHeader.add(Box.createHorizontalGlue());
		charactersPanel = new JPanel();
		charactersHeader.add(Box.createHorizontalStrut(140));
		charactersTabPanel.add(charactersHeader);
		this.charactersList = new ArrayList<MyTextPane>();
		charactersPanel.setLayout(new BoxLayout(charactersPanel, BoxLayout.Y_AXIS));
		charactersPanel.setMaximumSize(new Dimension(InfoPanel.INFOPANELWIDTH-30,99999));
		//charactersPanel.setPreferredSize(new Dimension(INFOPANELWIDTH-30,999));
		ArrayList<Reference> chars = info.getPanel().getRecord().getCampaignCharacters();
		for(int i=0;i<chars.size();i++) {
			MyTextPane pane = new MyTextPane(info, i, HexData.CHARACTER);
			pane.setMaximumSize(new Dimension(InfoPanel.INFOPANELWIDTH-30,9999));
			pane.setRef(chars.get(i));
			pane.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2,Color.gray));
			charactersPanel.add(pane);
			charactersList.add(pane);
		}
		charactersEmpty = new JTextArea();
		charactersEmpty.setMaximumSize(new Dimension(InfoPanel.INFOPANELWIDTH-30,9999));
		charactersEmpty.setEnabled(false);
		charactersEmpty.setText("None");
		charactersPanel.add(charactersEmpty);
		if(chars.size()==0) {
			charactersEmpty.setVisible(true);
		}else {
			charactersEmpty.setVisible(false);
		}
		JScrollPane charactersScrollPane = new JScrollPane(charactersPanel);
		charactersScrollPane.setPreferredSize(new Dimension(InfoPanel.INFOPANELWIDTH-30,9999));
		charactersScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		charactersScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		charactersTabPanel.add(charactersScrollPane);
		campaignTabs.add("Characters",charactersTabPanel);
		


		JPanel threadsPanel = new JPanel(new BorderLayout());
		JPanel threadsHeader = new JPanel();
		threadsHeader.setLayout(new BoxLayout(threadsHeader, BoxLayout.X_AXIS));
		threadsHeader.add(Box.createHorizontalStrut(40));
		threadsHeader.add(new JLabel("Threads:"),BorderLayout.WEST);
		threadsHeader.add(Box.createHorizontalGlue());
		JPanel threads = new JPanel();
		JScrollPane threadsScrollPane = new JScrollPane(threads);
		JButton addThreadButton = new JButton("➕ Add Thread");
		threadsHeader.add(Box.createHorizontalStrut(140));
		threadsPanel.add(threadsHeader, BorderLayout.NORTH);
		this.threadsList = new ArrayList<MyTextPane>();
		threads.setLayout(new BoxLayout(threads, BoxLayout.Y_AXIS));
		ArrayList<String> threadText = info.getPanel().getRecord().getCampaignThreads();
		for(int i=0;i<threadText.size();i++) {
			MyTextPane pane = new MyTextPane(info, i, HexData.THREAD);
			pane.setMaximumSize(new Dimension(InfoPanel.INFOPANELWIDTH-30,9999));
			pane.doPaint();
			threads.add(pane);
			threadsList.add(pane);
			threads.add(Box.createVerticalStrut(2));
		}
		addThreadButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MyTextPane pane = new MyTextPane(info, threadsList.size(), HexData.THREAD);
				pane.setMaximumSize(new Dimension(InfoPanel.INFOPANELWIDTH-30,9999));
				threads.add(pane);
				threadsList.add(pane);
				threads.add(Box.createVerticalStrut(2));
				info.repaint();
			}
		});
		addThreadButton.setMaximumSize(new Dimension(999, 30));
		threadsPanel.add(addThreadButton,BorderLayout.SOUTH);
		threadsPanel.add(threadsScrollPane,BorderLayout.CENTER);
		campaignTabs.add("Threads",threadsPanel);
		
		this.add(campaignTabs);
	}
	

	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		for(int i=0;i<threadsList.size();i++) {
			this.threadsList.get(i).doPaint();
		}
		for(int i=0;i<charactersList.size();i++) {
			this.charactersList.get(i).doPaint();
		}
	}
	


	public void removeCharacter(int index) {
		info.getPanel().getController().removeData(HexData.CHARACTER, null, index);
		charactersList.remove(index).setVisible(false);
		if(charactersList.size()==0) charactersEmpty.setVisible(true);
	}

	public void addCharacter(HexData type, Point point, int index) {
		Point displayPoint = Util.normalizePos(point, info.getPanel().getRecord().getZero());
		Reference ref = new Reference(type,displayPoint,index);
		info.getPanel().getController().putData(HexData.CHARACTER, null, charactersList.size(), ref.toString());
		MyTextPane pane = new MyTextPane(info, charactersList.size(), HexData.CHARACTER);
		pane.setRef(ref);
		pane.doPaint();
		pane.setAlignmentX(LEFT_ALIGNMENT);
		pane.setMaximumSize(new Dimension(InfoPanel.INFOPANELWIDTH-30,9999));
		pane.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2,Color.gray));
		charactersPanel.add(pane);
		charactersList.add(pane);
		charactersEmpty.setVisible(false);
	}
}
