package stc.bean;
/**
 * 
 */


import stc.foundation.codec.bean.tlv.annotation.TLVAttribute;

/**
 * @author jason.zheng
 *
 */
@TLVAttribute(tag = 10020002)
public class UaInfo {
	
	@TLVAttribute(tag=10000001)
	private	Integer	appid;
	
	@TLVAttribute(tag=10000002)
	private	String	appname;
	
	@TLVAttribute(tag=10000003)
	private	Integer	appver;
	
	@TLVAttribute(tag=10000004)
	private	Integer	sppver;
	
	@TLVAttribute(tag=10000005)
	private	String	hsman;
	
	@TLVAttribute(tag=10000006)
	private	String	hstype;
	
	@TLVAttribute(tag=10000007)
	private	String	plat;
	
	@TLVAttribute(tag=10000008)
	private	Integer	swidth;
	
	@TLVAttribute(tag=10000009)
	private	Integer	shight;
	
	@TLVAttribute(tag=10000010)
	private	Integer	memsize;
	
	@TLVAttribute(tag=10000011)
	private	Integer	touch;
	
	@TLVAttribute(tag=10000012)
	private	String	imei;
	
	@TLVAttribute(tag=10000013)
	private	String	imsi;
	
	@TLVAttribute(tag=10000014)
	private	Integer	oprator;
	
	@TLVAttribute(tag=10000015)
	private	String	smsc;
	
	@TLVAttribute(tag=10000016)
	private	Integer	portver;
	
	@TLVAttribute(tag=10000017)
	private	Integer	vmver;
	
	@TLVAttribute(tag=10000018)
	private	Integer	entryid;
	
	
	@TLVAttribute(tag=10010026)
	private	Integer	fatherappid;
	
    public Integer getFatherappid() {
		return fatherappid;
	}



	public void setFatherappid(Integer fatherappid) {
		this.fatherappid = fatherappid;
	}



	public Integer getAppid() {
		return appid;
	}



	public void setAppid(Integer appid) {
		this.appid = appid;
	}



	public String getAppname() {
		return appname;
	}



	public void setAppname(String appname) {
		this.appname = appname;
	}



	public Integer getAppver() {
		return appver;
	}



	public void setAppver(Integer appver) {
		this.appver = appver;
	}



	public Integer getSppver() {
		return sppver;
	}



	public void setSppver(Integer sppver) {
		this.sppver = sppver;
	}



	public String getHsman() {
		return hsman;
	}



	public void setHsman(String hsman) {
		this.hsman = hsman;
	}



	public String getHstype() {
		return hstype;
	}



	public void setHstype(String hstype) {
		this.hstype = hstype;
	}



	public String getPlat() {
		return plat;
	}



	public void setPlat(String plat) {
		this.plat = plat;
	}



	public Integer getSwidth() {
		return swidth;
	}



	public void setSwidth(Integer swidth) {
		this.swidth = swidth;
	}



	public Integer getShight() {
		return shight;
	}



	public void setShight(Integer shight) {
		this.shight = shight;
	}



	public Integer getMemsize() {
		return memsize;
	}



	public void setMemsize(Integer memsize) {
		this.memsize = memsize;
	}



	public Integer getTouch() {
		return touch;
	}



	public void setTouch(Integer touch) {
		this.touch = touch;
	}



	public String getImei() {
		return imei;
	}



	public void setImei(String imei) {
		this.imei = imei;
	}



	public String getImsi() {
		return imsi;
	}



	public void setImsi(String imsi) {
		this.imsi = imsi;
	}



	public Integer getOprator() {
		return oprator;
	}



	public void setOprator(Integer oprator) {
		this.oprator = oprator;
	}



	public String getSmsc() {
		return smsc;
	}



	public void setSmsc(String smsc) {
		this.smsc = smsc;
	}



	public Integer getPortver() {
		return portver;
	}



	public void setPortver(Integer portver) {
		this.portver = portver;
	}



	public Integer getVmver() {
		return vmver;
	}



	public void setVmver(Integer vmver) {
		this.vmver = vmver;
	}



	public Integer getEntryid() {
		return entryid;
	}



	public void setEntryid(Integer entryid) {
		this.entryid = entryid;
	}
}
