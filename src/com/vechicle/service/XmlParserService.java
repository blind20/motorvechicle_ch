package com.vechicle.service;

import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;

import com.vechicle.domain.BasicInfo;
import com.vechicle.util.ToolUtils;

import android.util.Xml;

public class XmlParserService {
	
	public static List<String> getUsrList(String xml) throws Exception{
		List<String> list = null;
		String usrname = null;
		XmlPullParser pullParser = Xml.newPullParser();
		pullParser.setInput(ToolUtils.string2IpInputStream(xml), "UTF-8");
		int event = pullParser.getEventType();
		while(event != XmlPullParser.END_DOCUMENT){
			switch (event) {
			case XmlPullParser.START_DOCUMENT:
				list = new ArrayList<String>();
				break;
				
			case XmlPullParser.START_TAG:
				if("User".equals(pullParser.getName())){
				}
				if("TypeName".equals(pullParser.getName())){
					usrname = pullParser.nextText();;
				}
				break;
			case XmlPullParser.END_TAG:
				if("User".equals(pullParser.getName())){
					list.add(usrname);
					usrname = null;
				}
				break;
			}
			event = pullParser.next();
		}
		return list;
	}

	/*public static GongGao getGongGao(String xml) throws Exception{
		List<String> list = null;
		GongGao gongGao = null;
		XmlPullParser pullParser = Xml.newPullParser();
		pullParser.setInput(ToolUtils.string2IpInputStream(xml), "UTF-8");
		int event = pullParser.getEventType();
		while(event != XmlPullParser.END_DOCUMENT){
			switch (event) {
			case XmlPullParser.START_TAG:
				if("GongGao".equals(pullParser.getName())){
					gongGao = new GongGao();
				}
				if("bh".equals(pullParser.getName())){
					String bh = pullParser.nextText();
					gongGao.setBh(bh);
				}
				if("clxh".equals(pullParser.getName())){
					String clxh = pullParser.nextText();
					gongGao.setClxh(clxh);
				}
				if("ggrq".equals(pullParser.getName())){
					String ggrq = pullParser.nextText();
					gongGao.setGgrq(ggrq);
				}
				if("fdjxh".equals(pullParser.getName())){
					String fdjxh = pullParser.nextText();
					gongGao.setFdjxh(fdjxh);
				}
				if("sbdhxl".equals(pullParser.getName())){
					String sbdhxl = pullParser.nextText();
					gongGao.setSbdhxl(sbdhxl);
				}
				if("cwkc".equals(pullParser.getName())){
					String cwkc = pullParser.nextText();
					gongGao.setCwkc(cwkc);
				}
				if("cwkk".equals(pullParser.getName())){
					String cwkk = pullParser.nextText();
					gongGao.setCwkk(cwkk);
				}
				if("cwkg".equals(pullParser.getName())){
					String cwkg = pullParser.nextText();
					gongGao.setCwkg(cwkg);
				}
				if("zs".equals(pullParser.getName())){
					String zs = pullParser.nextText();
					gongGao.setZs(zs);
				}
				if("ltgg".equals(pullParser.getName())){
					String ltgg = pullParser.nextText();
					gongGao.setLtgg(ltgg);
				}
				if("zzl".equals(pullParser.getName())){
					String zzl = pullParser.nextText();
					gongGao.setZzl(zzl);
				}
				if("hdzk".equals(pullParser.getName())){
					String hdzk = pullParser.nextText();
					gongGao.setHdzk(hdzk);
				}
				if("zps".equals(pullParser.getName())){
					list = new ArrayList<String>();
				}
				if("zp".equals(pullParser.getName())){
					String zp = pullParser.nextText();
					list.add(zp);
				}
				break;
			case XmlPullParser.END_TAG:
				if("zps".equals(pullParser.getName())){
					gongGao.setList(list);
				}
				break;
			}
			event = pullParser.next();
		}
		return gongGao;
	}
	
	public static List<GongGao> getGongGaoList(String xml) throws Exception{
		List<GongGao> gongGaos = null;
		GongGao gongGao = null;
		XmlPullParser pullParser = Xml.newPullParser();
		pullParser.setInput(ToolUtils.string2IpInputStream(xml), "UTF-8");
		int event = pullParser.getEventType();
		while(event != XmlPullParser.END_DOCUMENT){
			switch (event) {
			case XmlPullParser.START_DOCUMENT:
				gongGaos = new ArrayList<GongGao>();
				break;
				
			case XmlPullParser.START_TAG:
				if("GongGao".equals(pullParser.getName())){
					gongGao = new GongGao();
				}
				if("bh".equals(pullParser.getName())){
					String bh = pullParser.nextText();
					gongGao.setBh(bh);
				}
				if("clxh".equals(pullParser.getName())){
					String clxh = pullParser.nextText();
					gongGao.setClxh(clxh);
				}
				if("ggrq".equals(pullParser.getName())){
					String ggrq = pullParser.nextText();
					gongGao.setGgrq(ggrq);
				}
				break;
			case XmlPullParser.END_TAG:
				if("GongGao".equals(pullParser.getName())){
					gongGaos.add(gongGao);
					gongGao = null;
				}
				break;
			}
			event = pullParser.next();
		}
		return gongGaos;
	}*/
	
	public static List<BasicInfo> getBasicInfoList(String xml) throws Exception{
		List<BasicInfo> basicInfos = null;
		BasicInfo basicInfo = null;
		XmlPullParser pullParser = Xml.newPullParser();
		pullParser.setInput(ToolUtils.string2IpInputStream(xml), "UTF-8");
		int event = pullParser.getEventType();
		
		while(event != XmlPullParser.END_DOCUMENT){
			switch (event) {
			case XmlPullParser.START_DOCUMENT:
				basicInfos = new ArrayList<BasicInfo>();
				break;
				
			case XmlPullParser.START_TAG:
				if("Vehcle".equals(pullParser.getName())){
					basicInfo = new BasicInfo();
				}
				if("bh".equals(pullParser.getName())){
					String bh = pullParser.nextText();
					basicInfo.setBh(bh);
				}
				if("hphm".equals(pullParser.getName())){
					String hphm = pullParser.nextText();
					basicInfo.setHphm(hphm);
				}
				if("hpzl".equals(pullParser.getName())){
					String hpzl = pullParser.nextText();
					basicInfo.setHpzl(hpzl);
				}
				if("clxh".equals(pullParser.getName())){
					String clxh = pullParser.nextText();
					basicInfo.setClxh(clxh);
				}
				break;
			case XmlPullParser.END_TAG:
				if("Vehcle".equals(pullParser.getName())){
					basicInfos.add(basicInfo);
					basicInfo = null;
				}
				break;
			}
			event = pullParser.next();
		}
		return basicInfos;
	}
	
	
	
}
