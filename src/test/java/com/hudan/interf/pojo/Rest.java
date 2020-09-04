package com.hudan.interf.pojo;

/**
 * 
 * 接口信息对应的对象
 * @author hudan
 *
 */
public class Rest {
	@Override
	public String toString() {
		return "Rest [apiId=" + apiId + ", apiName=" + apiName + ", type=" + type + ", url=" + url + ", type=" + type
				+ "]";
	}
	private String apiId;
	private String apiName;
	private String post;
	private String url;
	private String type;

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getApiId() {
		return apiId;
	}
	public void setApiId(String apiId) {
		this.apiId = apiId;
	}
	public String getApiName() {
		return apiName;
	}
	public void setApiName(String apiName) {
		this.apiName = apiName;
	}
	public String getPost() {
		return post;
	}
	public void setPost(String post) {
		this.post = post;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
}
