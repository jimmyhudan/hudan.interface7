package com.hudan.interf.pojo;

public class Param {
	/*一开始没有写map用的类，可以删除*/
	private String user;
	private String price;
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	@Override
	public String toString() {
		return "Param [user=" + user + ", price=" + price + "]";
	}

	 
	 

}
