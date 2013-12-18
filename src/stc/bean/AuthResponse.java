package stc.bean;

import stc.foundation.codec.bean.AbstractCommonBean;
import stc.foundation.codec.bean.tlv.TLVSignal;
import stc.foundation.codec.bean.tlv.annotation.TLVAttribute;

/**
 * @author zhenyu.zheng
 * 
 */
@TLVAttribute(tag = 2110002)
public class AuthResponse extends AbstractCommonBean implements TLVSignal{
	@TLVAttribute(tag=11020001, description="")
	private Result result;

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}
}
