package com.edu.david.cn;
class th extends Thread{
	public int time;
	public th(int time){
		this.time=time*1000;
	}
	@Override
	public void run(){
		//System.out.println(this.getId()+":Start");
		try {
			this.sleep(time);
			//System.out.println(this.getId()+":finish");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
public class testQueue {
	public static void main(String args[]){
		TaskQueue tq=new TaskQueue(4,10,2.5);
		CallBack cb=new CallBack(){

			@Override
			public void onSuccess(String msg) {
				// TODO Auto-generated method stub
				System.out.println(msg+":success");
			}

			@Override
			public void onFailed(String msg) {
				// TODO Auto-generated method stub
				System.out.println(msg+":failed");
			}
			
		};
		tq.addTask(new Thread(){
			@Override
			public void run(){
				while(true){
					
				}
			}
		},cb);
		tq.addTask(new th(1),cb);
		tq.addTask(new th(2),cb);
		tq.addTask(new th(1),cb);
		tq.addTask(new th(2),cb);
		tq.addTask(new th(3),cb);
		tq.addTask(new th(4),cb);
		tq.addTask(new Thread(){
			@Override
			public void run(){
				while(true){
					
				}
			}
		},cb);
		tq.addTask(new th(3),cb);
		tq.addTask(new th(2),cb);
		tq.addTask(new th(1),cb);
		tq.addTask(new th(2),cb);
		/*tq.print();
		try {
			Thread.sleep(12000);
			tq.print();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
}
