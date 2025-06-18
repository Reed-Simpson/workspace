package view;

public class MyLogger {
	private long startTime;
	private long timeThreshold;
	private String log = "";
	
	public MyLogger(long timeThreshold) {
		this.timeThreshold = timeThreshold;
		this.startTime = System.currentTimeMillis();
	}
	public MyLogger() {
		new MyLogger(0);
	}

	public long getTimeThreshold() {
		return timeThreshold;
	}

	public void setTimeThreshold(long timeThreshold) {
		this.timeThreshold = timeThreshold;
	}
	
	public boolean log(String log,long time) {
		if(log!=null) this.log+=log;
		boolean result = false;
		if((time-startTime)>timeThreshold&&this.log.length()>0) {
			System.out.print(this.log);
			this.log = "";
			result = true;
		}
		return result;
	}
	public boolean log(String log) {
		return this.log(log, System.currentTimeMillis());
	}
	public void newline() {
		this.log("\r\n");
	}
	public boolean logln(String log,long time) {
		if(log==null) log="";
		return this.log(log+"\r\n", time);
	}
	public boolean logln(String log) {
		return this.logln(log,System.currentTimeMillis());
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public void reset(long time) {
		this.startTime = time;
		this.log = "";
	}
	public void reset() {
		this.reset(System.currentTimeMillis());
	}
	public boolean isPastThresh() {
		return (System.currentTimeMillis()-startTime)>timeThreshold;
	}
	

}
