package view;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;

import io.AppData;
import io.SaveRecord;

public class MapFrame extends JFrame{
	private static final long serialVersionUID = 843200012193641481L;
	private AppData data;
	private SaveRecord record;

	public MapFrame() throws SecurityException, IOException {
		super("Reed's Hexcrawl Generator v0.1");

		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
	        @Override
	        public void windowClosing(WindowEvent e) {
	        	int result = JOptionPane.NO_OPTION;
	        	if(record.hasUnsavedData()) {
		        	result = JOptionPane.showConfirmDialog(MapFrame.this,
		                    "You have unsaved changes. Would you like to save before you exit?",
		                    "Save Changes?",
		                    JOptionPane.YES_NO_CANCEL_OPTION);
	        	}
	        	if(result==JOptionPane.NO_OPTION) {
		        	MapFrame.this.dispose();
		        	System.exit(0);
	        	}else if(result==JOptionPane.YES_OPTION) {
	        		record.save(data);
		        	MapFrame.this.dispose();
		        	System.exit(0);
	        	}else {
	        		//Do nothing
	        	}
	        }
	    });
		this.data = AppData.load();
		if(data==null) {
			data = new AppData();
		}
		File save = data.getMostRecent();
		SaveRecord record = SaveRecord.load(save,data);
		if(record==null) {
			record = new SaveRecord();
			record.setSaveLocation(record.getDefaultSaveFile());
		}
		this.load(record);

	}

	public void load(SaveRecord record) {
		this.record=record;
		ProgressBarDialog dialog = new ProgressBarDialog(this);
		MapPanel panel = new MapPanel(this,record,dialog);
		InfoPanel info = new InfoPanel(panel);
		RegionPanel region = new RegionPanel();
		JTabbedPane tabs = new JTabbedPane();
		tabs.add("Info Panel",info);
		tabs.add("Region Panel",region);
		this.add(panel,BorderLayout.CENTER);
		this.add(info,BorderLayout.EAST);
		MenuBar bar = new MenuBar(panel);
		this.add(bar,BorderLayout.NORTH);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		this.setExtendedState(Frame.MAXIMIZED_BOTH);
	}

	public AppData getAppData() {
		return this.data;
	}

	public static void main(String[] args) throws SecurityException, IOException{
		Logger logger = Logger.getLogger("HexCrawlGenerator");
		File file = new File(System.getenv("APPDATA")+"/ReedsHexcrawl/logs/");
		file.mkdirs();
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String formattedTimestamp = now.format(formatter);
		FileHandler handler = new FileHandler(System.getenv("APPDATA")+"/ReedsHexcrawl/logs/"+formattedTimestamp+".log",true);
		logger.addHandler(handler);
		try {
			new MapFrame();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e, "Fatal Error!", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			logger.log(Level.SEVERE,e.toString(),e);
		}
	}


}
