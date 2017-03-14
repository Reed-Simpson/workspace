package data;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class RecordList{
	public static final int TIMECATEGORY = 0;
	public static final int CLICKSCATEGORY = 1;
	public static final int BBBVCATEGORY = 2;
	Record[][][] recordlist;
	int[] wins;
	int[] losses;
	public static final int NUMENTRIESLOGGED = 5;


	public RecordList(){
		recordlist = new Record[4][3][NUMENTRIESLOGGED];
		wins = new int[4];
		losses = new int[4];
	}
	
	public int getWinCount(int difficulty){
		return this.wins[difficulty];
	}
	
	public int getLossCount(int difficulty){
		return this.losses[difficulty];
	}
	
	public boolean addRecord(long time, int clicks,int BBBV,int difficulty){
		Record r = new Record(time,clicks,BBBV);
		boolean result = false;
		for(int category=0;category<3;category++){
			Record r1 = r;
			for(int i=0;i<NUMENTRIESLOGGED;i++){
				if(recordlist[difficulty][category][i]==null){
					recordlist[difficulty][category][i]=r1;
					result=true;
					break;
				}else if(r1.compareTo(recordlist[difficulty][category][i], category)>=0){
					Record r2 = recordlist[difficulty][category][i].clone();
					recordlist[difficulty][category][i]=r1;
					r1=r2;
					result=true;
				}
			}
		}
		wins[difficulty]++;
		return result;
	}

	public boolean addLoss(int difficulty){
		losses[difficulty]++;
		return true;
	}

	public String toString(){
		StringBuilder result = new StringBuilder();
		for(int difficulty=0;difficulty<4;difficulty++){
			result.append("Difficulty: "+difficulty+"\r\n");
			for(int category=0;category<3;category++){
				result.append("---Category: "+category+"\r\n");
				for(int i=0;i<NUMENTRIESLOGGED;i++){
					Record r = recordlist[difficulty][category][i];
					if(r!=null)	result.append(r.toString()+"\r\n");
				}
			}
		}
		return result.toString().trim();
	}

	public Object[][] getData(int difficulty,int category){
		Object[][] result = new Object[NUMENTRIESLOGGED][3];
		for(int i=0;i<result.length;i++){
			Record r=recordlist[difficulty][category][i];
			if(r==null){
				result[i][0]="";
				result[i][1]="";
				result[i][2]="";
			}else{
				result[i][0]=new Integer((int) (r.time/1000000000)+1);
				result[i][1]=new Integer(r.clicks);
				result[i][2]=new Integer(r.BBBV);
			}
		}

		return result;
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		boolean result = false;

		Writer writer = null;
		try {
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path), "utf-8"));
			for(int difficulty=0;difficulty<4;difficulty++){
				writer.write(wins[difficulty]+" "+losses[difficulty]+"\r\n");
				for(int category=0;category<3;category++){
					for(int i=0;i<NUMENTRIESLOGGED;i++){
						Record r = recordlist[difficulty][category][i];
						if(r!=null)writer.write(r.time+","+r.clicks+","+r.BBBV+"|");
					}
					if(category<2) writer.write("\r\n");
				}
				if(difficulty<3)writer.write("\r\n***\r\n");
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

		//System.out.println(content);
		this.parseRecordList(content);
		return result;
	}
	
	private boolean parseRecordList(String content){
		String[] difficulties = content.split("\r\n\\*\\*\\*\r\n");
		for(int difficulty=0;difficulty<difficulties.length;difficulty++){
			String[] categories = difficulties[difficulty].split("\r\n");
			String[] stats = categories[0].split(" ");
			this.wins[difficulty]=Integer.valueOf(stats[0]);
			this.losses[difficulty]=Integer.valueOf(stats[1]);
			for(int category=0;category<categories.length-1;category++){
				String[] records = categories[category+1].split("\\|");
				for(int i=0;i<records.length;i++){
					this.parseRecord(records[i], difficulty, category, i);
				}
			}
		}
		return true;
	}
	
	private boolean parseRecord(String record, int difficulty,int category, int i){
		if(!record.matches("\\d+,\\d+,\\d+")) return false;
		String[] splitRecord = record.split(",");
		Record r = new Record(Long.valueOf(splitRecord[0]),Integer.valueOf(splitRecord[1]),Integer.valueOf(splitRecord[2]));
		this.recordlist[difficulty][category][i]=r;
		return true;
	}

	private class Record implements Comparable<Record>{
		final long time;
		final int clicks;
		final int BBBV;

		public Record(long time, int clicks,int BBBV){
			this.time=time;
			this.clicks=clicks;
			this.BBBV=BBBV;
		}

		public int compareTo(Record r) {
			return this.compareTo(r, TIMECATEGORY);
		}
		public int compareTo(Record r, int category){
			if(r==null) return Integer.MAX_VALUE;
			int result;
			if(category==TIMECATEGORY){
				long temp = 2*(r.time-this.time);
				if(temp>=(long)Integer.MAX_VALUE) result=Integer.MAX_VALUE;
				else if(temp<=(long)Integer.MIN_VALUE) result=Integer.MIN_VALUE;
				else result=(int)temp;
				if(result==0){
					if(this.BBBV>r.BBBV) result++;
					else if(this.BBBV<r.BBBV) result--;
					else if(r.clicks>this.clicks) result++;
					else if(r.clicks<this.clicks) result--;
				}
			}else if(category==CLICKSCATEGORY){
				result = 2*(r.clicks-this.clicks);
				if(result==0){
					if(this.BBBV>r.BBBV) result++;
					else if(this.BBBV<r.BBBV) result--;
					else if(r.time>this.time) result++;
					else if(r.time<this.time) result--;
				}
			}else if(category==BBBVCATEGORY){
				result = 2*(this.BBBV-r.BBBV);
				if(r.time>this.time) result++;
				else if(r.time<this.time) result--;
				else if(r.clicks>this.clicks) result++;
				else if(r.clicks<this.clicks) result--;
			}else{
				throw new IllegalArgumentException("Undefined Category");
			}
			return result;
		}
		public String toString(){
			return "time: "+time+", 3BV: "+BBBV+", clicks: "+clicks;
		}
		public Record clone(){
			return new Record(this.time,this.clicks,this.BBBV);
		}
	}
}
