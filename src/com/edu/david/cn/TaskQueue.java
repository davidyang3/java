package com.edu.david.cn;

import java.util.Date;
import java.util.Vector;

public class TaskQueue {
	private Vector <Task>doing;
	private Vector <Task>waiting;
	private Thread listenDoing;
	private Thread listenQueue;
	final static int defaultdo=4;
	final static int defaultQueue=10;
	final static double defaultTime=3;
	private int dlength;
	private int wlength;
	double deadTime;
	public TaskQueue(){
		this(defaultdo,defaultQueue,defaultTime);
	}
	public TaskQueue(int lengthDoing,int lengthWaiting,double time){
		doing=new Vector<Task>(lengthDoing);
		waiting=new Vector<Task>(lengthWaiting);
		this.dlength=lengthDoing;
		this.wlength=lengthWaiting;
		this.deadTime=time;
		final int dlength=this.dlength;
		listenDoing=new Thread(){
			@Override
			public void run(){
			//to listen the taskqueue
				while(true){
					synchronized(doing){
						for(int i=0;i<doing.size();i++){
							Task k=doing.get(i);
							if(k.getThread().isAlive()){
								if(true){
									//if the thread is out time
									Date dt= new Date();
									Long time= dt.getTime();
									if(time-k.getStartTime()>deadTime*1000){
										k.getThread().stop();
										k.getCallBack().onFailed(new Long(k.getThread().getId()).toString()+"超时：");
										doing.remove(i);
									}
								}
							}else{
								k.getCallBack().onSuccess(new Long(k.getThread().getId()).toString());
								doing.remove(i);
							}
						}
						if(doing.size()<dlength){
							doing.notifyAll();
						}
					}
				}
			}
		};
		listenQueue=new Thread(){
			@Override
			public void run(){
				while(true){
					synchronized(doing){
						if(!(doing.size()<dlength)){
							try {
								doing.wait();
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								return;
							}
						}else{
							while(!waiting.isEmpty()&&dlength-doing.size()!=0){
								Task k=waiting.get(0);
								if(doing.add(k)){
									waiting.remove(0);
									Date dt= new Date();
									Long time= dt.getTime();
									k.setStartTime(time);
									k.getThread().start();
									/*System.out.println(k.getThread().getId()+":进入执行队列");
									System.out.print("正在执行的有：");
									for(int j=0;j<doing.size();j++){
										System.out.print(doing.get(j).getThread().getId()+",");
									}
									System.out.println("");*/
								}else{
									return;
								}
							}
						}
					}
				}
			}
		};
		listenDoing.start();
		listenQueue.start();
	}
	public void addTask(Thread t,CallBack cb){
		addTask(new Task(t,cb));
	}
	/*public void print(){
		System.out.println(doing.size()+"_"+waiting.size());
	}*/
	public void addTask(Task task){
		if(waiting.size()>=wlength||!waiting.add(task)){
			task.getCallBack().onFailed(task.getThread().getId()+"加入队列失败");			
		}
	}
}
