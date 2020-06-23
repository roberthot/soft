package cn.hot.restApi;

import java.io.Serializable;

/**
 * description:返回Model
 */
public class Result implements Serializable {

	private Integer status = 1; //返回的状态
	private String msg;//返回的提示
	private String token; //登录用户信息
	private Object data;//返回的对象

	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer error) {
		this.status = error;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Object getData() {
		return data;
	}
	
	/**
	 * 该方法只接收String、Map、List、entity、Set这五种类型，其他类型需要增加判断
	 * @param model
	 */
	public void setData(Object model) {
			this.data = model;
	}

	public Result(){
		this.setStatus(0);
		this.setMsg("");
	}


	public Result(int status, String msg){
		this.setStatus(status);
		this.setMsg(msg);
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
}




