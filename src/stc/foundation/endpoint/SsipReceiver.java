package stc.foundation.endpoint;

public interface SsipReceiver {
	/**
	 * 成功接受一个SSIP消息
	 * @param msg
	 */
    void messageReceived(Object msg);
    
    /**
     * 成功发送一个SSIP消息
     * @param msg
     */
    void messageSent(Object msg);
    
    /**
     * 发送一个SSIP消息失败，一般是socket异常造成发送超时
     * @param msg
     */
    void messageFailed(Object msg);
    
    /**
     * EP状态变更
     * @param stat
     */
    void statusChanged(SsipEndpoint.EP_STAT stat);
}
