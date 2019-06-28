package com.world.chaip.util;

/**
 * @author 毕泽浩
 * @Description:
 * @time 2019/6/28 13:26
 */
public class CallResult {

	private int initValue;

	private int insertResult = 0;

	private ResultCall resultCall;

	public CallResult(int initValue,ResultCall resultCall) {
		this.initValue = initValue;
		this.resultCall = resultCall;
	}

	public synchronized void addResult(){
		insertResult++;
		if(insertResult == initValue){
			resultCall.run();
		}
	}

	public interface ResultCall{
		void run();
	}
}
