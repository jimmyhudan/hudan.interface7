package com.hudan.interf.pojo;

import java.util.Map;

public class CheckResult {
 private String no;
 private Map<String,Object>clumandVlaueMap;
@Override
public String toString() {
	return "CheckResult [no=" + no + ", clumandVlaueMap=" + clumandVlaueMap + "]";
}
public String getNo() {
	return no;
}
public void setNo(String no) {
	this.no = no;
}
public Map<String, Object> getClumandVlaueMap() {
	return clumandVlaueMap;
}
public void setClumandVlaueMap(Map<String, Object> clumandVlaueMap) {
	this.clumandVlaueMap = clumandVlaueMap;
}
}
