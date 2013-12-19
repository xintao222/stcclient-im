package stc.foundation.codec.bean.xip;

import java.util.UUID;
//SSIP
//0               1               2               3                
//0 1 2 3 4 5 6 7 0 1 2 3 4 5 6 7 0 1 2 3 4 5 6 7 0 1 2 3 4 5 6 7  
//+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
//|  basic ver(1) |                    length(3)                  |
//+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
//|                                                               |
//|                       transaction(16)                         |
//|                                                               |
//|                                                               |
//+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
//|   type(1)     |                     reserved(3)               |
//+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
//|                           code(4)                             |
//+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
//|                           data length(4)                      |
//+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
//|application data field(...)                                    |
//|                                                               |
//|                                                               |
//|                                                               |
//+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

//字段名称    字段大小    取值范围    功能说明
//基础协议版本
//(basic ver) 1(byte) [1,255] 标识基础协议的版本号，不会频繁更新，以1为开始版本
//协议包长度
//(length)    3   [32,16777215]   整个协议包长度，最小值为协议头大小(32)，高位字节序
//事务标识
//(transaction)   16  GUID    参照GUID的生成算法，由请求或者通知的发送者保证其在任何时间、地点、平台唯一，请求和响应消息的事物标识必须一致。该标识用于保证请求和响应的唯一对应以及消息的不重复性
//原语类型
//(type)  1   [0,255] 目前支持请求(1)，响应(2)，通知（3），递送(4) 
//保留字段
//（reserved）  3   N/A 基础协议头的保留字段
//消息编码
//（code）  4   [0,2^32]    具体每种消息的编号，建议将对应的请求和响应消息连续编号，高位字节序，按照不同功能进行分组编号。
//消息长度    4   [0,2^32-1]  消息数据域的长度
//协议数据域
//(data field)    n   n/a 唯一对应某个消息编码

/**
 * @author tomato
 *
 */
public class SsipHeader {
    public static final int SSIP_HEADER_LENGTH = 32;
    public static final int XIP_REQUEST = 1;
    public static final int XIP_RESPONSE = 2;
    public static final int XIP_NOTIFY = 3;
    public static final int XIP_VER = 2;
       
	/**
	 * TLV的协议头固定为2
	 */
	private byte 	basicVer = XIP_VER;
    
	private int		length = 0;
    
	private long	firstTransaction;
    
    private long    secondTransaction;
    
    /**
	 * 目前无效
	 */
	//private	byte	type;
    
	/**
	 * 目前无效
	 */
	//private	int		reserved;
    
	private int 	messageCode;

	private int 	messageLength;

	/**
	 * @return the basicVer
	 */
	public byte getBasicVer() {
		return basicVer;
	}

	/**
	 * @return the length
	 */
	public int getLength() {
		return length;
	}
	/**
	 * @param length the length to set
	 */
	public void setLength(int length) {
		this.length = length;
	}
		
	
	/**
	 * @return the code
	 */
	public int getMessageCode() {
		return messageCode;
	}
	/**
	 * @param code the code to set
	 */
	public void setMessageCode(int code) {

		this.messageCode = code;
	}
	
	/**
	 * @return the messageLength
	 */
	public int getMessageLength() {
		return messageLength;
	}
	/**
	 * @param length the messageLength to set
	 */
	public void setMessageLength(int length) {
		messageLength = length;
	}
	
    /**
     * @return the firstTransaction
     */
    public long getFirstTransaction() {
        return firstTransaction;
    }

    /**
     * @param firstTransaction the firstTransaction to set
     */
    public void setFirstTransaction(long firstTransaction) {
        this.firstTransaction = firstTransaction;
    }

    /**
     * @return the secondTransaction
     */
    public long getSecondTransaction() {
        return secondTransaction;
    }

    /**
     * @param secondTransaction the secondTransaction to set
     */
    public void setSecondTransaction(long secondTransaction) {
        this.secondTransaction = secondTransaction;
    }
	
    public void setTransaction(UUID uuid) {
        this.firstTransaction = uuid.getMostSignificantBits();
        this.secondTransaction = uuid.getLeastSignificantBits();
    }
    
    public UUID getTransactionAsUUID() {
        return  new UUID(this.firstTransaction, this.secondTransaction);
    }

}
