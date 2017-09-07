package com.vechicle.util;

import java.io.IOException;
import java.net.ConnectException;

import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Log;

public class WebserviceUtil {

	private static final String TAG = "WebserviceUtil";
	//WSDL中的命名空间
	private static final String NAMESPACE = "http://services.tyki.com";
	//WSDL中的URL
	private static String SERVICE_URL = ToolUtils.getServerURL() + "axis2/services/PDAService?wsdl";
	
	private static final String METHOD2 = "uploadImageNew";
	private static final String METHOD3 = "pDALogin";
	private static final String METHOD4 = "pDAGetTypeList";
	private static final String METHOD10 = "getGongGaoInfobyBHNew";
	private static final String METHOD11 = "getUnqualified";
	private static final String METHOD12 = "updatePower";
	
	
	public static String uploadCheckItemsAndImg(JSONObject jo) {

		SoapObject request = new SoapObject(NAMESPACE, METHOD2);
		request.addProperty("arg0", jo.toString());
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
		envelope.dotNet = true;
		envelope.bodyOut = request;
		envelope.setOutputSoapObject(request);

		HttpTransportSE ht = new HttpTransportSE(SERVICE_URL,0);
		ht.debug = false;  
		String soapAction = NAMESPACE + METHOD2;
		String ss = null;

		try {
			ht.call(soapAction, envelope);
			Object obj = envelope.bodyIn;
			if(obj instanceof SoapObject){
				SoapObject object = (SoapObject) envelope.bodyIn;
				ss = object.getProperty("return").toString();
			}else{
				SoapFault sf=(SoapFault)obj;
				ss = "400";
				Log.e("服务端异常", sf.getMessage(),sf);
			}
		} catch (IOException e) {
			Log.e("网络异常", "IOException", e);
			ss = "400";
		} catch (XmlPullParserException e) {
			Log.e("网络异常", "XmlPullParserException", e);
			ss = "400";
		}
		return ss;
	}
	
	
	public static String getLogin(String name,String pwd) {

		Log.i("webutil_server_url", SERVICE_URL);
		SoapObject request = new SoapObject(NAMESPACE, METHOD3);
		request.addProperty("arg0", name);
		request.addProperty("arg1", pwd);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
		envelope.dotNet = true;
		envelope.bodyOut = request;
		envelope.setOutputSoapObject(request);

		HttpTransportSE ht = new HttpTransportSE(SERVICE_URL,20000);
		ht.debug = true;  
		String soapAction = NAMESPACE + METHOD3;
		String ss = null;

		try {
			ht.call(soapAction, envelope);
			Object obj = envelope.bodyIn;
			if(obj instanceof SoapObject){
				SoapObject object = (SoapObject) envelope.bodyIn;
				ss = object.getProperty("return").toString();
			}else{
				SoapFault sf=(SoapFault)obj;
			}
		} catch (IOException e) {
			Log.e("服务端异常", "IOException", e);
		} catch (XmlPullParserException e) {
			Log.e("服务端异常", "XmlPullParserException", e);
		}
		return ss;
	}
	
	
	public static String getUsrList(String flag) {

		SoapObject request = new SoapObject(NAMESPACE, METHOD4);
		request.addProperty("arg0", flag);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
		envelope.dotNet = true;
		envelope.bodyOut = request;
		envelope.setOutputSoapObject(request);

		HttpTransportSE ht = new HttpTransportSE(SERVICE_URL,20000);
		ht.debug = true;  
		String soapAction = NAMESPACE + METHOD4;
		String ss = null;

		try {
			ht.call(soapAction, envelope);
			Object obj = envelope.bodyIn;
			if(obj instanceof SoapObject){
				SoapObject object = (SoapObject) envelope.bodyIn;
				ss = object.getProperty("return").toString();
			}else{
				SoapFault sf=(SoapFault)obj;
			}
		} catch (IOException e) {
			Log.e("服务端异常", "IOException", e);
		} catch (XmlPullParserException e) {
			Log.e("服务端异常", "XmlPullParserException", e);
		}
		return ss;
	}
	

	public static String getGongGaoByBhNew(String ggbh) throws Exception {
		SoapObject request = new SoapObject(NAMESPACE, METHOD10);
		request.addProperty("arg0", ggbh);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
		envelope.dotNet = true;
		envelope.bodyOut = request;
		envelope.setOutputSoapObject(request);

		HttpTransportSE ht = new HttpTransportSE(SERVICE_URL,20000);
		ht.debug = true;  
		String soapAction = NAMESPACE + METHOD10;
		String ss = null;
		try {
			ht.call(soapAction, envelope);
			Object object =  envelope.bodyIn;
			if (object instanceof SoapObject) {
				SoapObject so = (SoapObject) object;
				ss = so.getProperty("return").toString();
			}else{
				SoapFault sf = (SoapFault) object;
				Log.e("sf", "服务端异常", sf);
				throw new Exception(sf.getCause());
			}
		} catch (IOException e) {
			Log.e("网络异常", "IOException", e);
			throw e;
		} catch (XmlPullParserException e) {
			Log.e("网络异常", "XmlPullParserException", e);
			throw e;
		}
		return ss;
	}
	
	public static String getUnqualified() {
		SoapObject request = new SoapObject(NAMESPACE, METHOD11);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.bodyOut = request;
		envelope.setOutputSoapObject(request);

		HttpTransportSE ht = new HttpTransportSE(SERVICE_URL,20000);
		ht.debug = true;  
		String soapAction = NAMESPACE + METHOD11;
		String ss = null;
		try {
			ht.call(soapAction, envelope);
			Object object =  envelope.bodyIn;
			if (object instanceof SoapObject) {
				SoapObject so = (SoapObject) object;
				ss = so.getProperty("return").toString();
			}else{
				SoapFault sf = (SoapFault) object;
				Log.e("sf", "服务端异常", sf);
			}
		} catch (IOException e) {
			Log.e("网络异常", "IOException", e);
		} catch (Exception e) {
			Log.e("网络异常", "Exception", e);
		}
		return ss;
	}
	
	public static String updatePower(String ycid) {
		SoapObject request = new SoapObject(NAMESPACE, METHOD12);
		request.addProperty("arg0", ycid);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.bodyOut = request;
		envelope.setOutputSoapObject(request);

		HttpTransportSE ht = new HttpTransportSE(SERVICE_URL,20000);
		ht.debug = true;  
		String soapAction = NAMESPACE + METHOD12;
		String ss = null;
		try {
			ht.call(soapAction, envelope);
			Object object =  envelope.bodyIn;
			if (object instanceof SoapObject) {
				SoapObject so = (SoapObject) object;
				ss = so.getProperty("return").toString();
			}else{
				SoapFault sf = (SoapFault) object;
				Log.e("sf", "服务端异常", sf);
			}
		} catch (IOException e) {
			Log.e("网络异常", "IOException", e);
		} catch (Exception e) {
			Log.e("网络异常", "Exception", e);
		}
		return ss;
	}
	
	public static String invokeWebservice(String params,String method) throws Exception {
		String namespace = NAMESPACE ;
		String url = SERVICE_URL;
		
		
		SoapObject request = new SoapObject(namespace, method);
		request.addProperty("arg0", params);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
		envelope.dotNet = true;
		envelope.bodyOut = request;
		envelope.setOutputSoapObject(request);

		HttpTransportSE ht = new HttpTransportSE(url,20000);
		ht.debug = true;  
		String soapAction = namespace + method;
		String ss = null;
		try {
			ht.call(soapAction, envelope);
			Object object =  envelope.bodyIn;
			if (object instanceof SoapObject) {
				SoapObject so = (SoapObject) object;
				ss = so.getProperty("return").toString();
			}else{
				SoapFault sf = (SoapFault) object;
				Log.e("sf", "服务端异常", sf);
				throw new Exception(sf.getCause());
			}
		} catch (IOException e) {
			Log.e("网络异常", "IOException", e);
			throw e;
		} catch (XmlPullParserException e) {
			Log.e("网络异常", "XmlPullParserException", e);
			throw e;
		}
		return ss;
	}
	
	
}
