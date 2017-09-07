package com.vechicle.service;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.vechicle.util.ToolUtils;

public class ImageService {

	/**
	 * 获取网络图片的数据
	 * @param path 网络图片路径
	 * @return
	 */
	public static byte[] getImage(String path) throws Exception{
		URL url = new URL(path);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();//基于HTTP协议连接对象
		conn.setConnectTimeout(5000);
		conn.setRequestMethod("GET");
		if(conn.getResponseCode() == 200){
			InputStream inStream = conn.getInputStream();
			return ToolUtils.read(inStream);
		}
		return null;
	}
}
