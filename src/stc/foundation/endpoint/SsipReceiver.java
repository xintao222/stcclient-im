package stc.foundation.endpoint;

import stc.foundation.codec.bean.AbstractCommonBean;

public interface SsipReceiver {
	/**
	 * 成功接受一个SSIP消息
	 * @param msg
	 */
    void messageReceived(AbstractCommonBean msg);
    
    /**
     * 成功发送一个SSIP消息
     * @param msg
     */
    void messageSent(AbstractCommonBean msg);
    
    /**
     * 发送一个SSIP消息失败，一般是socket异常造成发送超时
     * @param msg		对应发送的消息
     * @param reason	发送失败的原因
     * 
     */
    void messageFailed(AbstractCommonBean msg, SsipEndpoint.EP_REASON reason);
    
    /**
     * EP状态变更
     * @param stat
     */
    void statusChanged(SsipEndpoint.EP_STAT stat);
}
