package com.vechicle;

/**
 * @Title: Constant.java
 * @Description: ���������� ��װһЩ����
 * @author 
 * @version V1.0 Copyright (c) 2015 Company,Inc. All Rights Reserved.
 * 
 */
public class Constant {
	// http����״̬���־
	public final static int STATAS_OK = 200;// ����OK
	public final static int NO_RESPONSE = 400;// ��������Ӧ �Ҳ�����Ӧ��Դ
	public final static int S_EXCEPTION = 500;// ����������
	public final static int RESPONESE_EXCEPTION = 160;// ��Ӧ�쳣
	public final static int TIMEOUT = 101;// ����ʱ
	public final static int NO_NETWORK = 102;// û�ÿ�������
	public final static int NULLPARAMEXCEPTION = 103;// ����Ϊ���쳣
	public final static int RESPONSE_OK = 2000;
	public final static int RELOGIN = 4001;
	public final static int SERVER_EXCEPTION = 5001;
	public final static int NULLPARAM=4006;//����ֵΪ��
	public final static int LOSEPARAM=4005;//ȱ�ٲ���
	public final static String SAVAVERSIONCODE="savaVersionCode";//���������һ�������õİ汾
	// ���ʵ�����
	public final static String HOST = "http://api.qcbis.com";
	public final static String USERAPI = HOST + "/User.aspx";
	public final static String PROJECTAPI = HOST + "/Project.aspx";
	public final static String LOANAPI = HOST + "/Loan.aspx";

	public final static String DOWNLOADURL="http://api.qcbis.com/Download.aspx/";
	// ����ͼƬ���ļ���
	public final static String ROOT = "OrongImages";
	// ����ͷ��
	public final static String USERICFODER = ROOT + "/user";
	// ������ĿͼƬ
	public final static String PROJECTIMG = ROOT + "/project";

	// sharedPeferences
	public final static String ORPREFERENCES = "orongConfig";
	public final static String ISSAVEPW = "isSavepassword";
	public final static String USERNAME = "userName";
	public final static String PASSWORD = "passWord";
	public final static String USERICONPATH = "usericonpath";// �û�ͼ�񱣴��url  =qrcode+path;//�û���url+�洢·��
	// end sharedPeferences

	// ��������
	public final static String ENCODEPASSWORD = "AAAcom.orongaaa";
	//
	public final static int PAGESIZE = 12;
	
	public final static Long MAXFIESZISE=1048576L*6L; //���ɱ���6M
	public final static int MAXFIESNUM=80;//���������80��ͼƬ
	public final static int DELFIESNUM=30;//ÿ��ɾ����ͼƬ����

}
