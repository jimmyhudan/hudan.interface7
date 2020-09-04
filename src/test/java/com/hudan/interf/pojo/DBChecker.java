package com.hudan.interf.pojo;

public class DBChecker {
	@Override
	public String toString() {
		return "DBChecker [no=" + no + ", sql=" + sql + "]";
	}
	private String no;
	private String sql;
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
	
}
