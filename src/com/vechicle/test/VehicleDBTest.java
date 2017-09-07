package com.vechicle.test;

import java.util.List;
import java.util.Properties;

import com.vechicle.db.DBHelper;
import com.vechicle.db.ImageDao;
import com.vechicle.db.VehDao;
import com.vechicle.domain.BasicInfo;
import com.vechicle.domain.Vehicle;
import com.vechicle.service.XmlParserService;
import com.vechicle.util.MyPropertiesUtil;

import android.test.AndroidTestCase;
import android.util.Log;

public class VehicleDBTest extends AndroidTestCase {
	
	public void testCreateDB() throws Exception{
		DBHelper dbOpenHelper = new DBHelper(getContext());
		dbOpenHelper.getWritableDatabase();
	}
	
	public void testInsert() throws Exception{
		DBHelper dbOpenHelper = new DBHelper(getContext());
		VehDao vehdao = new VehDao(getContext(), dbOpenHelper);
		Vehicle vehicle = new Vehicle();
		vehicle.setClsbdh("gslb0003dfdf");
		vehicle.setUseProperties("A");
		vehicle.setAlarmdevice(1);
		vehicle.setAspect(1);
		vehicle.setBrand(0);
		vehicle.setCertify(1);
		vehicle.setColor("A");
		vehicle.setConclusion(1);
		vehicle.setEnginenum(0);
		vehicle.setExtinguisher(1);
		vehicle.setGabarite(0);
		vehicle.setHammer(1);
		vehicle.setInsptype("check vehicle");
		vehicle.setLicence("苏J 88899");
		vehicle.setLictype("A普通");
		vehicle.setReflectlog(1);
		vehicle.setRemark("近期需再检");
		vehicle.setSaftyguard(1);
		vehicle.setSeats("12");
		vehicle.setSpraypaint(1);
		vehicle.setTachographs(0);
		vehicle.setTriangles(1);
		vehicle.setTyrecondition(0);
		vehicle.setTyrespec(0);
		vehicle.setVehcode(1);
		vehicle.setVehtype("1");
		vehdao.insert(vehicle);
	}
	
	public void testInsertImgs() throws Exception{
		DBHelper dbOpenHelper = new DBHelper(getContext());
		ImageDao dao = new ImageDao(getContext(), dbOpenHelper);
//		dao.insert("/mnt/sdcard/DCIM/img2001.jpg",10);
	}
	
	public void testUsrService()throws Exception{
		String str ="<Users><User><Check>1</Check><TypeCode></TypeCode>	<TypeName>1</TypeName></User></Users>";
		List<String> usrs = XmlParserService.getUsrList(str);
		final int size = usrs.size();
		String[] strarray = (String[]) usrs.toArray(new String[size]);
		Log.i("LoginActivity**********", strarray[0]);
	}
	

	public void testBasicInfoSer()throws Exception{
		String str ="<VehcleList><Vehcle><bh>asdfsd001</bh><hphm>dfafs933a</hphm><clxh>asdfsd001</clxh></Vehcle><Vehcle><bh>asdfsd001</bh><hphm>dfafs933a</hphm><clxh>asdfsd001</clxh></Vehcle></VehcleList>";
		List<BasicInfo> basicInfos = XmlParserService.getBasicInfoList(str);
		Log.i("testBasicInfoSer", basicInfos.get(0).getHphm()+"&&"+ basicInfos.get(0).getClxh());
	}
	
	public void testGetProperties(){
		Properties pu = MyPropertiesUtil.getProperties();
		System.out.println("11:"+pu.getProperty("largecar"));
		Log.e("11111", "!:"+pu.getProperty("largecar"));
	}
	
	
}
