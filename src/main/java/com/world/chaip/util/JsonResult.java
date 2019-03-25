package com.world.chaip.util;

public class JsonResult {
	
	public static final int SUCCESS = 0;
	public static final int ERROR = 1;
	
	/*private int state;*/
	/** 错误消息  */
	/*private String message;*/
	/** 返回正确时候的数据 */
	private Object data;


	public JsonResult(Object data){
		/*state = SUCCESS;*/
		this.data = data;
	}


	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}


	@Override
	public String toString() {
		return "JsonResult{" +
				"data=" + data +
				'}';
	}
}
