package com.vechicle.domain;

import java.io.Serializable;

public class Vehicle implements Serializable{

	private static final long serialVersionUID = 3186441230981900088L;
	private long vehicleid;
	//���ƺ���
	private String licence;
	//���ܺ�
	private String clsbdh;
	
	//��������
	private String lictype;
	
	//ʹ������(����)��Ӫ�ˡ���Ӫ��
	private String useProperties;
	
	//ҵ������
	private String insptype;
	
	
	//����ʶ�����
	private Integer vehcode;
	//�������ͺź���
	private Integer enginenum;
	//����Ʒ��
	private Integer brand;
	private String color;
	
	//������ɫ�����棩
	private Integer isColorCheck;
	
	private String seats;
	
	//�˶����������棩
	private Integer isSeatsCheck;
	
	private String vehtype;
	
	//�������ͣ����棩
	private Integer isVehtypeCheck;
	
	//���Ƴ��������״
	private Integer aspect;
	//��̥������
	private Integer tyrecondition;
	//��ȫ�����Ǿ�����
	private Integer triangles;
	//�����ߴ�����
	private Integer gabarite;
	
	//��������(����)
	private Integer zbzl;
	
	//��̥���
	private Integer tyrespec;
	//��󲿷���װ��
	private Integer saftyguard;
	//�������ʶ
	private Integer reflectlog;
	//�����
	private Integer extinguisher;
	//��ʻ��¼װ��
	private Integer tachographs;
	//Ӧ������Ӧ�ô�
	private Integer hammer;
	//������
	private Integer alarmdevice;
	//�ⲿ��ʶ������
	private Integer spraypaint;
	//��ȫ��������ϸ�֤��
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
