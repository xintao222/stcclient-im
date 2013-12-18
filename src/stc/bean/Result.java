package stc.bean;

import stc.foundation.codec.bean.tlv.annotation.TLVAttribute;


@TLVAttribute(tag = 11020001)
public class Result {
	@TLVAttribute(tag=11010063)
	private int errorCode;
	@TLVAttribute(tag=11010064,description="错误信息")
	private String errorMessage;
	
	public Result(){
		errorCode=-1;
	}
	
	public Result(int code) {
		errorCode = code;
		errorMessage = String.valueOf(code);
	}
	
	public int getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
		this.errorMessage = String.valueOf(errorCode);
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
 
	public String toString() {
		return  "errorCode="+errorCode+" ,errorMessage="+errorMessage;
	}
}
