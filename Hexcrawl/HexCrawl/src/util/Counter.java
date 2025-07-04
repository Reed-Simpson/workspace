package util;

import javax.swing.JProgressBar;

import view.MyLogger;

public class Counter {
	private long counter = 0;
	private long counterMax;
	private double counterThresh;
	private int counterThreshcount;
	private MyLogger logger;
	private JProgressBar bar;
	
	public Counter(long max,int messageCount) {
		this.resetCounter(max, messageCount);
	}	
	public Counter(long max,int messageCount,MyLogger log) {
		this.resetCounter(max, messageCount);
		this.logger = log;
	}
	public Counter(long max,JProgressBar bar) {
		this.resetCounter(max, 20);
		this.bar = bar;
	}

	public long getCounter() {
		return counter;
	}
	public synchronized void increment() {
		this.counter++;
		if(bar!=null) {
			bar.setValue((int) (counter*bar.getMaximum()/counterMax));
		}
		if(counter>=counterThresh*counterThreshcount) {
			String s = "("+(counter*100/counterMax)+"%)";
			if(logger!=null) logger.log(s);
			else System.out.print(s);
			counterThreshcount++;
		}else if(logger!=null) {
			logger.log(null);
		}
	}
	public void resetCounter(long max,int messageCount) {
		this.counterMax = max;
		this.counter = 0;
		this.counterThresh = ((double)counterMax)/messageCount;
		this.counterThreshcount = 1;
		if(this.logger!=null) logger.reset();
		if(bar!=null) {
			bar.setValue(0);
		}
	}
	public void resetCounter(long max) {
		resetCounter(max,20);
	}
	public void resetCounter() {
		resetCounter(this.counterMax);
	}

	public long getCounterMax() {
		return counterMax;
	}

	public MyLogger getLog() {
		return logger;
	}

	public void setLog(MyLogger log) {
		this.logger = log;
	}

}
