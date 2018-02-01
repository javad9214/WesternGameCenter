package com.project.avanikan_pc_003.login.Western;

public class ExampleTimer extends Timer{
	
	public ExampleTimer() {
		super();
	}
	
	public ExampleTimer(long interval, long duration){
		super(interval, duration);
	}


	@Override
	protected void onTick() {

	}

	@Override
	protected void onFinish() {
		System.out.println("onFinish called!");
	}
	
}
