package com.vechicle.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.util.EncodingUtils;

import com.vechicle.R;
import com.vechicle.ui.AnnounceActivity;
import com.vechicle.ui.LoginActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

public class ToolUtils {

	public Context context;
	public final static String PREF_NAME = "sharepref_values";
	
	public static String getServerURL() {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ContextUtil.getInstance());
		String ip = prefs.getString("ip_edit_preference", "127.0.0.1");
		String port = prefs.getString("port_edit_preference", "8088");
		String url = "http://" + ip + ":" + port +"/";
		return url;
	}

	@SuppressLint("SimpleDateFormat")
	public static String getCurDate() {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return dateFormat.format(date);
	}
	
	public static String deviceImei(){
		Context context = ContextUtil.getInstance();
		String devImei;
		TelephonyManager tm = (TelephonyManager)context.getSystemService(
				Context.TELEPHONY_SERVICE);
		try {
			devImei = tm.getDeviceId();
		} catch (Exception e) {
			devImei = "";
		}
		return devImei;
	}
	
	 /** 
     * MD5 加密 
     */  
	private static String getMd5Str(String str) {  
        MessageDigest messageDigest = null;  
        try {  
            messageDigest = MessageDigest.getInstance("MD5");  
            messageDigest.reset();  
            messageDigest.update(str.getBytes("UTF-8"));  
        } catch (NoSuchAlgorithmException e) {  
            System.out.println("NoSuchAlgorithmException caught!");  
            System.exit(-1);  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
        }  
        byte[] byteArray = messageDigest.digest();  
        StringBuffer md5StrBuff = new StringBuffer();  
        for (int i = 0; i < byteArray.length; i++) {              
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)  
                md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));  
            else  
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));  
        }  
        return md5StrBuff.toString();  
    }
	
	public static String getEncodeStr(String str){
		String sEncode = getMd5Str(str);
		StringBuffer buffer = new StringBuffer(sEncode);
		sEncode = buffer.reverse().toString().substring(0, 10);
		return sEncode;
	}
	
	public static InputStream string2IpInputStream(String str){
		ByteArrayInputStream inputstream = new ByteArrayInputStream(str.getBytes());
		return inputstream;
	}
	
	@SuppressLint("SimpleDateFormat")
	public static Date stringToDate(String dateStr,String format) {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date = null;
        try {
			date = sdf.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
        return date;
    }
	
	public static String dateToString(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String str = format.format(date);
		return str;
    }
	
	public static String dateToStringHMS(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = format.format(date);
		return str;
    }
	
	
	public static String getLableByCode(String code,int nLables, int nCodes){
		String[] lables = ContextUtil.getInstance().getResources().getStringArray(nLables);
		String[] codes = ContextUtil.getInstance().getResources().getStringArray(nCodes);
		String lable = null;
		for(int i=0;i<codes.length;i++){
			if(code.equals(codes[i])){
				lable = lables[i];
				break;
			}
		}
		return lable;
	}
	
	
	public static String getCodeByLable(String lable, int nLables, int nCodes){
		String[] lables = ContextUtil.getInstance().getResources().getStringArray(nLables);
		String[] codes = ContextUtil.getInstance().getResources().getStringArray(nCodes);
		String code = null;
		for(int i=0;i<lables.length;i++){
			if(lable.equals(lables[i])){
				code = codes[i];
				break;
			}
		}
		return code;
	}
	
	/**
	 * 读取流中的数据
	 * @param inStream
	 * @return
	 * @throws Exception
	 */
	public static byte[] read(InputStream inStream) throws Exception{
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while( (len = inStream.read(buffer)) != -1){
			outStream.write(buffer, 0, len);
		}
		inStream.close();
		return outStream.toByteArray();
	}
	
	public static String readFileByRandomAccess(String fileName) {
        RandomAccessFile randomFile = null;
        String content = "";
        try {
            randomFile = new RandomAccessFile(fileName, "r");
            randomFile.seek(0);
            byte[] bytes = new byte[10];
            int byteread = 0;
            while ((byteread = randomFile.read(bytes)) != -1) {
            	content += new String(bytes,0,byteread);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (randomFile != null) {
                try {
                    randomFile.close();
                } catch (IOException e1) {
                }
            }
        }
        return content;
    }
	
	/**
	 * 保存文件到应用本地 /data/data/com.vechicle
	 * @param str
	 * @param fileName
	 */
	public static void saveFile(String str,String fileName){
		FileOutputStream fos = null ;
		Context context = ContextUtil.getInstance();
		try {
			fos = context.openFileOutput(fileName, Activity.MODE_PRIVATE);
			fos.write(str.getBytes());
			fos.flush();
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(fos!=null){
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				fos = null;
			}
		}
	}
	
	/**
	 * 读取本地应用文件 /data/data/com.vechicle
	 * @param str
	 * @param fileName
	 */
	public static String readFile(String fileName){
		FileInputStream fis = null;
		String str = null;
		Context context = ContextUtil.getInstance();
		try {
			fis = context.openFileInput(fileName);
			int lenth = fis.available();
			byte[] buffer = new byte[lenth];
			fis.read(buffer);
			str = EncodingUtils.getString(buffer, "UTF-8");
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(fis!=null){
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				fis = null;
			}
		}
		return str;
	}
	
	public static boolean isNotEmptyString(String str){
		if(str == null || "".equals(str.trim()) || "null".equals(str.trim())){
			return false;
		}
		return true;
	}
	
	public static boolean isEmptyString(String str){
		if(str == null || "".equals(str.trim()) || "null".equals(str.trim())){
			return true;
		}
		return false;
	}
	
	public static void alertDlg(Context con, String title, String msg){
		new AlertDialog.Builder(con).setTitle(title)
		.setMessage(msg).setPositiveButton("确定", null)
		.create().show();
	}
	
	public static void toastText(Context con,String msg){
		Toast toast = Toast.makeText(con, msg, Toast.LENGTH_LONG);
		toast.setGravity(Gravity.TOP, 10, 500);
		toast.show();
	}
	
	public static boolean isVehManageDpt(Context con){
    	String[] units = con.getResources().getStringArray(R.array.applyunits);
    	
    	//检测站专用版本只需加这句代码
    	SharePreUtil.editSharepref("applyunits", units[1]);
    	
    	String applyUnit = SharePreUtil.getSharepref("applyunits");
    	if(units[0].equals(applyUnit) || "".equals(applyUnit)){
    		return true;
    	}
    	return false;
    }
	
}
