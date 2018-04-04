package com.edu.david.cn;

public class Task {
	private Thread t;
	private CallBack c;
	private long startTime;
	public Task(Thread t,CallBack c){
		this.t=t;
		this.c=c;
	}
	public Thread getThread() {
		return t;
	}
	public void setThread(Thread t) {
		this.t = t;
	}
	public CallBack getCallBack() {
		return c;
	}
	public void setCallBack(CallBack c) {
		this.c = c;
	}
	public long getStartTime(){
		return this.startTime;
	}
	public void setStartTime(long startTime){
		this.startTime=startTime;
	}
}
