package com.vechicle.domain;

import java.io.Serializable;

public class Vehicle implements Serializable{

	private static final long serialVersionUID = 3186441230981900088L;
	private long vehicleid;
	//号牌号码
	private String licence;
	//车架号
	private String clsbdh;
	
	//号牌种类
	private String lictype;
	
	//使用性质(新增)：营运、非营运
	private String useProperties;
	
	//业务类型
	private String insptype;
	
	
	//车辆识别代号
	private Integer vehcode;
	//发动机型号号码
	private Integer enginenum;
	//车辆品牌
	private Integer brand;
	private String color;
	
	//车身颜色（勾叉）
	private Integer isColorCheck;
	
	private String seats;
	
	//核定人数（勾叉）
	private Integer isSeatsCheck;
	
	private String vehtype;
	
	//车辆类型（勾叉）
	private Integer isVehtypeCheck;
	
	//号牌车辆外观形状
	private Integer aspect;
	//轮胎完好情况
	private Integer tyrecondition;
	//安全带三角警告牌
	private Integer triangles;
	//外廓尺寸轴数
	private Integer gabarite;
	
	//整备质量(新增)
	private Integer zbzl;
	
	//轮胎规格
	private Integer tyrespec;
	//侧后部防护装置
	private Integer saftyguard;
	//车身反光标识
	private Integer reflectlog;
	//灭火器
	private Integer extinguisher;
	//行驶记录装置
	private Integer tachographs;
	//应急出口应用锤
	private Integer hammer;
	//警报器
	private Integer alarmdevice;
	//外部标识、喷漆
	private Integer spraypaint;
	//安全技术检验合格证明
	private Integer certify;
	private Integer conclusion;
	private String remark;
	
	public Vehicle(){	
	}
	
	public Vehicle(long vehicleid, String licence, String clsbdh,
			String lictype, String useProperties, String insptype,
			Integer vehcode, Integer enginenum, Integer brand, String color,
			Integer isColorCheck, String seats, Integer isSeatsCheck,
			String vehtype, Integer isVehtypeCheck, Integer aspect,
			Integer tyrecondition, Integer triangles, Integer gabarite,
			Integer zbzl, Integer tyrespec, Integer saftyguard,
			Integer reflectlog, Integer extinguisher, Integer tachographs,
			Integer hammer, Integer alarmdevice, Integer spraypaint,
			Integer certify, Integer conclusion, String remark) {
		super();
		this.vehicleid = vehicleid;
		this.licence = licence;
		this.clsbdh = clsbdh;
		this.lictype = lictype;
		this.useProperties = useProperties;
		this.insptype = insptype;
		this.vehcode = vehcode;
		this.enginenum = enginenum;
		this.brand = brand;
		this.color = color;
		this.isColorCheck = isColorCheck;
		this.seats = seats;
		this.isSeatsCheck = isSeatsCheck;
		this.vehtype = vehtype;
		this.isVehtypeCheck = isVehtypeCheck;
		this.aspect = aspect;
		this.tyrecondition = tyrecondition;
		this.triangles = triangles;
		this.gabarite = gabarite;
		this.zbzl = zbzl;
		this.tyrespec = tyrespec;
		this.saftyguard = saftyguard;
		this.reflectlog = reflectlog;
		this.extinguisher = extinguisher;
		this.tachographs = tachographs;
		this.hammer = hammer;
		this.alarmdevice = alarmdevice;
		this.spraypaint = spraypaint;
		this.certify = certify;
		this.conclusion = conclusion;
		this.remark = remark;
	}

	public Vehicle(String licence, String clsbdh, String lictype,
			String useProperties, String insptype, Integer vehcode,
			Integer enginenum, Integer brand, String color,
			Integer isColorCheck, String seats, Integer isSeatsCheck,
			String vehtype, Integer isVehtypeCheck, Integer aspect,
			Integer tyrecondition, Integer triangles, Integer gabarite,
			Integer zbzl, Integer tyrespec, Integer saftyguard,
			Integer reflectlog, Integer extinguisher, Integer tachographs,
			Integer hammer, Integer alarmdevice, Integer spraypaint,
			Integer certify, Integer conclusion, String remark) {
		super();
		this.licence = licence;
		this.clsbdh = clsbdh;
		this.lictype = lictype;
		this.useProperties = useProperties;
		this.insptype = insptype;
		this.vehcode = vehcode;
		this.enginenum = enginenum;
		this.brand = brand;
		this.color = color;
		this.isColorCheck = isColorCheck;
		this.seats = seats;
		this.isSeatsCheck = isSeatsCheck;
		this.vehtype = vehtype;
		this.isVehtypeCheck = isVehtypeCheck;
		this.aspect = aspect;
		this.tyrecondition = tyrecondition;
		this.triangles = triangles;
		this.gabarite = gabarite;
		this.zbzl = zbzl;
		this.tyrespec = tyrespec;
		this.saftyguard = saftyguard;
		this.reflectlog = reflectlog;
		this.extinguisher = extinguisher;
		this.tachographs = tachographs;
		this.hammer = hammer;
		this.alarmdevice = alarmdevice;
		this.spraypaint = spraypaint;
		this.certify = certify;
		this.conclusion = conclusion;
		this.remark = remark;
	}

	public String getClsbdh() {
		return clsbdh;
	}


	public void setClsbdh(String clsbdh) {
		this.clsbdh = clsbdh;
	}

	public long getVehicleid() {
		return vehicleid;
	}

	public void setVehicleid(long vehicleid) {
		this.vehicleid = vehicleid;
	}

	public String getLicence() {
		return licence;
	}

	public void setLicence(String licence) {
		this.licence = licence;
	}

	public String getLictype() {
		return lictype;
	}

	public void setLictype(String lictype) {
		this.lictype = lictype;
	}

	public String getInsptype() {
		return insptype;
	}

	public void setInsptype(String insptype) {
		this.insptype = insptype;
	}

	public Integer getVehcode() {
		return vehcode;
	}

	public void setVehcode(Integer vehcode) {
		this.vehcode = vehcode;
	}

	public Integer getEnginenum() {
		return enginenum;
	}

	public void setEnginenum(Integer enginenum) {
		this.enginenum = enginenum;
	}

	public Integer getBrand() {
		return brand;
	}

	public void setBrand(Integer brand) {
		this.brand = brand;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Integer getIsColorCheck() {
		return isColorCheck;
	}

	public void setIsColorCheck(Integer isColorCheck) {
		this.isColorCheck = isColorCheck;
	}

	public String getSeats() {
		return seats;
	}

	public void setSeats(String seats) {
		this.seats = seats;
	}

	public Integer getIsSeatsCheck() {
		return isSeatsCheck;
	}

	public void setIsSeatsCheck(Integer isSeatsCheck) {
		this.isSeatsCheck = isSeatsCheck;
	}

	public String getVehtype() {
		return vehtype;
	}

	public void setVehtype(String vehtype) {
		this.vehtype = vehtype;
	}

	public Integer getIsVehtypeCheck() {
		return isVehtypeCheck;
	}

	public void setIsVehtypeCheck(Integer isVehtypeCheck) {
		this.isVehtypeCheck = isVehtypeCheck;
	}

	public Integer getAspect() {
		return aspect;
	}

	public void setAspect(Integer aspect) {
		this.aspect = aspect;
	}

	public Integer getTyrecondition() {
		return tyrecondition;
	}

	public void setTyrecondition(Integer tyrecondition) {
		this.tyrecondition = tyrecondition;
	}

	public Integer getTriangles() {
		return triangles;
	}

	public void setTriangles(Integer triangles) {
		this.triangles = triangles;
	}

	public Integer getGabarite() {
		return gabarite;
	}

	public void setGabarite(Integer gabarite) {
		this.gabarite = gabarite;
	}

	public Integer getTyrespec() {
		return tyrespec;
	}

	public void setTyrespec(Integer tyrespec) {
		this.tyrespec = tyrespec;
	}

	public Integer getSaftyguard() {
		return saftyguard;
	}

	public void setSaftyguard(Integer saftyguard) {
		this.saftyguard = saftyguard;
	}

	public Integer getReflectlog() {
		return reflectlog;
	}

	public void setReflectlog(Integer reflectlog) {
		this.reflectlog = reflectlog;
	}

	public Integer getExtinguisher() {
		return extinguisher;
	}

	public void setExtinguisher(Integer extinguisher) {
		this.extinguisher = extinguisher;
	}

	public Integer getTachographs() {
		return tachographs;
	}

	public void setTachographs(Integer tachographs) {
		this.tachographs = tachographs;
	}

	public Integer getHammer() {
		return hammer;
	}

	public void setHammer(Integer hammer) {
		this.hammer = hammer;
	}

	public Integer getAlarmdevice() {
		return alarmdevice;
	}

	public void setAlarmdevice(Integer alarmdevice) {
		this.alarmdevice = alarmdevice;
	}

	public Integer getSpraypaint() {
		return spraypaint;
	}

	public void setSpraypaint(Integer spraypaint) {
		this.spraypaint = spraypaint;
	}

	public Integer getCertify() {
		return certify;
	}

	public void setCertify(Integer certify) {
		this.certify = certify;
	}

	public Integer getConclusion() {
		return conclusion;
	}

	public void setConclusion(Integer conclusion) {
		this.conclusion = conclusion;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}


	public String getUseProperties() {
		return useProperties;
	}


	public void setUseProperties(String useProperties) {
		this.useProperties = useProperties;
	}


	public Integer getZbzl() {
		return zbzl;
	}


	public void setZbzl(Integer zbzl) {
		this.zbzl = zbzl;
	}
	
	

}
