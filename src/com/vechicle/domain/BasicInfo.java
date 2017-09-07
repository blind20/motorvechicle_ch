package com.vechicle.domain;

public class BasicInfo {

	//唯一编号
	private String bh;
	//号牌号码（或车辆识别号）
	private String hphm;
	//号牌种类
	private String hpzl;
	//车辆型号
	private String clxh;
	
	public String getBh() {
		return bh;
	}
	public void setBh(String bh) {
		this.bh = bh;
	}
	public String getHphm() {
		return hphm;
	}
	public void setHphm(String hphm) {
		this.hphm = hphm;
	}
	public String getClxh() {
		return clxh;
	}
	public void setClxh(String clxh) {
		this.clxh = clxh;
	}
	public String getHpzl() {
		return hpzl;
	}
	public void setHpzl(String hpzl) {
		this.hpzl = hpzl;
	}
	
}
