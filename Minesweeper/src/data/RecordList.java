package data;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Comparator;

public class RecordList{
	public enum Category{
		TIME(0),CLICKS(1),BBBV(2);
		private int value;
		private Category(int value){this.value = value;}
	};
	public enum Difficulty{
		EASY(0),MEDIUM(1),HARD(2),CUSTOM(3);
		private int value;
		private Difficulty(int value){this.value = value;}
		public int value(){return value;};
	};
	ArrayList<Record> recordlist_easy = new ArrayList<Record>();
	ArrayList<Record> recordlist_medium = new ArrayList<Record>();
	ArrayList<Record> recordlist_hard = new ArrayList<Record>();
	ArrayList<Record> recordlist_custom = new ArrayList<Record>();
	int[] wins;
	int[] losses;


	public RecordList(){
		wins = new int[4];
		losses = new int[4];
	}

	public int getWinCount(Difficulty difficulty){
		return this.wins[difficulty.value];
	}

	public int getLossCount(Difficulty difficulty){
		return this.losses[difficulty.value];
	}

	public boolean addRecord(long time, int clicks,int BBBV,Difficulty difficulty){
		Record r = new Record(time,clicks,BBBV);
		ArrayList<Record> theList = getRecordList(difficulty);
		theList.add(r);

		wins[difficulty.value]++;
		return true;
	}

	private ArrayList<Record> getRecordList(Difficulty difficulty) {
		ArrayList<Record> theList = null;
		switch(difficulty){
		case EASY: theList=recordlist_easy; break;
		case MEDIUM: theList=recordlist_medium; break;
		case HARD: theList=recordlist_hard; break;
		case CUSTOM: theList=recordlist_custom; break;
		}
		return theList;
	}

	public boolean addLoss(int difficulty){
		losses[difficulty]++;
		return true;
	}

	public String toString(){
		StringBuilder result = new StringBuilder();
		for(Difficulty difficulty:Difficulty.values()){
			result.append("Difficulty: "+difficulty.value+"\r\n");
			ArrayList<Record> theList = getRecordList(difficulty);
			for(Record r:theList){
				result.append(r.toString()+"\r\n");
			}
		}
		return result.toString().trim();
	}

	public Object[][] getData(Difficulty difficulty,Category category){
		ArrayList<Record> theList = getRecordList(difficulty);
		theList.sort(getComparator(category));
		Object[][] result = new Object[theList.size()][3];
		for(int i=0;i<result.length;i++){
			Record r=theList.get(i);
			result[i][0]=new Integer((int) (r.time/1000000000)+1);
			result[i][1]=new Integer(r.clicks);
			result[i][2]=new Integer(r.BBBV);
		}

		return result;
	}
	
	private static Comparator<Record> getComparator(Category category){
		switch(category){
		case TIME: return new TimeComparator();
		case CLICKS: return new ClicksComparator();
		case BBBV: return new BBBVComparator();
		default: return new TimeComparator();
		}
	}

	public boolean save(String path){
		if(path==null){
			return false;
		}
		File filePath = new File(path);
		if(!filePath.exists()){
			try {
				filePath.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		boolean result = false;

		Writer writer = null;
		try {
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path), "utf-8"));
			for(Difficulty difficulty:Difficulty.values()){
				writer.write(wins[difficulty.value]+" "+losses[difficulty.value]+"\r\n");
				ArrayList<Record> theList = getRecordList(difficulty);
				for(Record r:theList){
					writer.write(r.time+","+r.clicks+","+r.BBBV+"\r\n");
				}
				if(difficulty!=Difficulty.CUSTOM)writer.write("\r\n***\r\n");
			}
			result=true;
			//System.out.println("Saved to: "+path);
		} catch (IOException ex) {
			// report
		} finally {
			try {writer.close();} catch (Exception ex) {}
		}
		return result;
	}

	public boolean load(String path){
		if(path==null){
			return false;
		}
		boolean result = false;

		String content = null;
		File file = new File(path);
		if(!file.exists()) return false;
		try {
			FileReader reader = new FileReader(file);
			char[] chars = new char[(int) file.length()];
			reader.read(chars);
			content = new String(chars);
			reader.close();
			result=true;
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.parseRecordList(content);
		return result;
	}

	private boolean parseRecordList(String content){
		String[] difficulties = content.split("\r\n\\*\\*\\*\r\n");
		for(Difficulty difficulty:Difficulty.values()){
			String[] records = difficulties[difficulty.value].split("\r\n");
			String[] stats = records[0].split(" ");
			this.wins[difficulty.value]=Integer.valueOf(stats[0]);
			this.losses[difficulty.value]=Integer.valueOf(stats[1]);
			for(int i=1;i<records.length;i++){
				this.parseRecord(records[i], difficulty, i);
			}
		}
		return true;
	}

	private boolean parseRecord(String record, Difficulty difficulty,int i){
		if(!record.matches("\\d+,\\d+,\\d+")) return false;
		String[] splitRecord = record.split(",");
		Record r = new Record(Long.valueOf(splitRecord[0]),Integer.valueOf(splitRecord[1]),Integer.valueOf(splitRecord[2]));
		ArrayList<Record> theList = getRecordList(difficulty);
		theList.add(r);
		return true;
	}

	private static class Record {
		final long time;
		final int clicks;
		final int BBBV;

		public Record(long time, int clicks,int BBBV){
			this.time=time;
			this.clicks=clicks;
			this.BBBV=BBBV;
		}

		public String toString(){
			return "time: "+time+", 3BV: "+BBBV+", clicks: "+clicks;
		}
		public Record clone(){
			return new Record(this.time,this.clicks,this.BBBV);
		}
	}

	private static class TimeComparator implements Comparator<Record>{
		@Override
		public int compare(Record r1, Record r2) {
			int result;
			long temp = 2*(r1.time-r2.time);
			if(temp>=(long)Integer.MAX_VALUE) result=Integer.MAX_VALUE;
			else if(temp<=(long)Integer.MIN_VALUE) result=Integer.MIN_VALUE;
			else result=(int)temp;
			if(result==0){
				if(r1.BBBV>r2.BBBV) result--;
				else if(r1.BBBV<r2.BBBV) result++;
				else if(r2.clicks>r1.clicks) result--;
				else if(r2.clicks<r1.clicks) result++;
			}
			return result;
		}
	}
	private static class ClicksComparator implements Comparator<Record>{
		@Override
		public int compare(Record r1, Record r2) {
			int result = 2*(r1.clicks-r2.clicks);
			if(r2.BBBV>r1.BBBV) result++;
			else if(r2.BBBV<r1.BBBV) result--;
			else if(r1.time>r2.time) result++;
			else if(r1.time<r2.time) result--;
			return result;
		}
	}
	private static class BBBVComparator implements Comparator<Record>{
		@Override
		public int compare(Record r1, Record r2) {
			int result = 2*(r2.BBBV-r1.BBBV);
			if(r1.time>r2.time) result++;
			else if(r1.time<r2.time) result--;
			else if(r1.clicks>r2.clicks) result++;
			else if(r1.clicks<r2.clicks) result--;
			return result;
		}
	}
}
