package com.xdja.vpnservice;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;


public class VPNSocket {
	private String ip = null;
	private int   port = 0;
	private Socket conn = null;
	private InputStream sin = null; // 网络输入流
	private OutputStream sou = null;// 网络输出流
	private int timeout = 30*1000*1000;
	public VPNSocket(String ip, int port)
	{
		this.ip = ip;
		this.port = port;
	}
	
	public int connect() {
		try {
			conn = new Socket(ip, port);
			conn.setSoTimeout(timeout);
			sin = conn.getInputStream();
			sou = conn.getOutputStream();
			return 0;
		} catch (Exception e) {
			e.getStackTrace();
			return -1;
		}
	}
	public int sendData(String data) {
		if (sin == null || sou == null) {
			return -1;
		}
    	try {
			sou.write(data.getBytes());
			sou.flush();
			sin.reset();
			return 0;
		} catch (Exception e) {
			e.getStackTrace();
			return -1;
		}
	}
	
	public int recvData(byte[] data) {
		if (sin == null) {
			return -1;
		}
		try {
			int dataLen = 0;
			int currentLen = 0;
			int tempLen = 0;
			int headLen = 0;
			
			byte[] len = new byte[4];// 头四个字节是数据长度+ 后面数据内容		
			while (tempLen != -1) {//获取数据长度缓冲区
				headLen += tempLen;
				if (headLen < 4) {
					tempLen = sin.read(len, headLen, 4 - headLen);
				} else {
					break;
				}
			}
			if (tempLen == -1) {
				return -1;
			}
			
			dataLen = Integer.parseInt(new String(len));//转换数据长度
			tempLen = 0;
			while (tempLen != -1) {//获取数据内容
				currentLen += tempLen;
				if (currentLen < dataLen) {
					tempLen = sin.read(data, currentLen, dataLen - currentLen);
				} else {
					break;
				}
			}
			if (tempLen == -1) {
				return -1;
			}
			
			return dataLen;
		} catch (IOException e) {
			e.getStackTrace();
			return -1;
		} catch (NumberFormatException e) {
			e.getStackTrace();
			return -1;
		}
	}
	
	public void close()
	{
		try {
			if (sin != null) {
			sin.close();
			}
			if (sou != null) {
			sou.close();
			}
			if (conn != null) {
			conn.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
