package com.hudan.interf.pojo;

/**
 * 
 * 接口信息对应的对象
 * @author hudan
 *
 */
public class Case {

	@Override
	public String toString() {
		return "Case [caseId=" + caseId + ", apiId=" + apiId + ", requestData=" + requestData + ", expectedReponseData="
				+ expectedReponseData + ", actualResponseData=" + actualResponseData + ", preValidateSql="
				+ preValidateSql + ", preValidateResult=" + preValidateResult + ", afterValidateSql=" + afterValidateSql
				+ ", afterValidateResult=" + afterValidateResult + "]";
	}
	private String caseId;
	private String apiId;
	private String requestData;
	private String expectedReponseData;
	private String actualResponseData;
	public String getCaseId() {
		return caseId;
	}
	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}
	public String getApiId() {
		return apiId;
	}
	public void setApiId(String apiId) {
		this.apiId = apiId;
	}
	public String getRequestData() {
		return requestData;
	}
	public void setRequestData(String requestData) {
		this.requestData = requestData;
	}
	public String getExpectedReponseData() {
		return expectedReponseData;
	}
	public void setExpectedReponseData(String expectedReponseData) {
		this.expectedReponseData = expectedReponseData;
	}
	public String getActualResponseData() {
		return actualResponseData;
	}
	public void setActualResponseData(String actualResponseData) {
		this.actualResponseData = actualResponseData;
	}
	public String getPreValidateSql() {
		return preValidateSql;
	}
	public void setPreValidateSql(String preValidateSql) {
		this.preValidateSql = preValidateSql;
	}
	public String getPreValidateResult() {
		return preValidateResult;
	}
	public void setPreValidateResult(String preValidateResult) {
		this.preValidateResult = preValidateResult;
	}
	public String getAfterValidateSql() {
		return afterValidateSql;
	}
	public void setAfterValidateSql(String afterValidateSql) {
		this.afterValidateSql = afterValidateSql;
	}
	public String getAfterValidateResult() {
		return afterValidateResult;
	}
	public void setAfterValidateResult(String afterValidateResult) {
		this.afterValidateResult = afterValidateResult;
	}
	private String preValidateSql;
	private String preValidateResult;
	private String afterValidateSql;
	private String afterValidateResult;



}
