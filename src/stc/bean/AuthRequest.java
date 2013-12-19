package stc.bean;

/**
 * @author zhenyu.zheng
 * 
 */

import stc.foundation.codec.bean.AbstractCommonBean;
import stc.foundation.codec.bean.tlv.TLVSignal;
import stc.foundation.codec.bean.tlv.annotation.TLVAttribute;

@TLVAttribute(tag = 1110002)
public class AuthRequest extends AbstractCommonBean implements TLVSignal{
	@TLVAttribute(tag=11010002, description = "斯凯ID")
	private int skyId;

	@TLVAttribute(tag=11010014, description = "授权令牌")
	private String token;

	//验证前的应用@验证的应�?
	@TLVAttribute(tag=11010012, description = "登录来源") 
	private String source;
	
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public int getSkyId() {
		return skyId;
	}

	public void setSkyId(int skyId) {
		this.skyId = skyId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
