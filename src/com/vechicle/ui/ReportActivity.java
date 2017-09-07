package com.vechicle.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.vechicle.R;
import com.vechicle.db.DBHelper;
import com.vechicle.db.VehDao;
import com.vechicle.domain.Vehicle;
import com.vechicle.service.UpdateManager;
import com.vechicle.util.HttpUtils;
import com.vechicle.util.MyPropertiesUtil;
import com.vechicle.util.SharePreUtil;
import com.vechicle.util.ToolUtils;

@SuppressLint("DefaultLocale")
public class ReportActivity extends Activity {

	private LinearLayout ll_hphm;
	private EditText licenceEdt;
	private LinearLayout lictype;
	private TextView tvLictype;
	private LinearLayout ll_useproperties;
	private TextView tv_useproperties;
	
	private LinearLayout ll_clsbdh;
	private EditText et_clsbdh;
	private LinearLayout insptype;
	private TextView tvInsptype;
	private ImageView iv_vehcode;
	private ImageView iv_enginenum;
	private ImageView iv_brand;
	// private RelativeLayout rl_color;
	private TextView tv_color;
	private EditText et_seats;
	// private RelativeLayout rl_vehtype;
	private TextView tv_vehtype;
	private ImageView iv_color;
	private ImageView iv_seats;
	private ImageView iv_vehtype;
	private ImageView iv_aspect;
	private ImageView iv_tyrecondition;
	private ImageView iv_triangles;
	private ImageView iv_gabarite;
	private ImageView iv_zbzl;
	private ImageView iv_tyrespec;
	private ImageView iv_saftyguard;
	private ImageView iv_reflectlog;
	private ImageView iv_extinguisher;
	private ImageView iv_tachographs;
	private ImageView iv_hammer;
	private ImageView iv_spraypaint;
	private ImageView iv_alarmdevice;
	private ImageView iv_certify;
	private RelativeLayout rl_conclusion;
	private TextView tv_conclusion;
	private EditText et_remark;
	private ProgressDialog mProgress; 
	
	private Button btn_chassis;
	private Button btn_verify;
	private Button btn_inusecar;
	private LinearLayout ll_toolbar;
	private String applyUnit;
	private String[] units;

	private String sSeats;
	private String sRemark;
	private Vehicle vehicle;

	private String clxh;
	private String csys;
	private String ggbh;
	private String fdjh;
	private String qlj;
	private String hlj;
	private String zj;
	private String lsh;
	private String hphm;
	private String dpid;
	

	private final static int DIALOG_LICTYPE = 1;
	private final static int DIALOG_INSPTYPE = 2;
	private final static int DIALOG_COLOR = 3;
	private final static int DIALOG_VEHTYPE = 4;
	private final static int DIALOG_USEPROPERTIES = 5;

	private final static int SCANNIN_GREQUEST_CODE = 101;
	

	boolean[] colorSelected = new boolean[] { false, false, false, false,
			false, false, false, false, false, false };
	
	private HashMap<String, Object> carinfoMap ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_report_table);
		initView();

		lictype.setOnClickListener(listen_iv);
		insptype.setOnClickListener(listen_iv);
		ll_useproperties.setOnClickListener(listen_iv);
		iv_vehcode.setOnClickListener(listen_iv);
		tv_color.setOnClickListener(listen_iv);
		tv_vehtype.setOnClickListener(listen_iv);
		iv_enginenum.setOnClickListener(listen_iv);
		iv_brand.setOnClickListener(listen_iv);
		iv_color.setOnClickListener(listen_iv);
		iv_seats.setOnClickListener(listen_iv);
		iv_vehtype.setOnClickListener(listen_iv);
		iv_aspect.setOnClickListener(listen_iv);
		iv_tyrecondition.setOnClickListener(listen_iv);
		iv_triangles.setOnClickListener(listen_iv);
		iv_gabarite.setOnClickListener(listen_iv);
		iv_zbzl.setOnClickListener(listen_iv);
		iv_tyrespec.setOnClickListener(listen_iv);
		iv_saftyguard.setOnClickListener(listen_iv);
		iv_reflectlog.setOnClickListener(listen_iv);
		iv_extinguisher.setOnClickListener(listen_iv);
		iv_tachographs.setOnClickListener(listen_iv);
		iv_hammer.setOnClickListener(listen_iv);
		iv_spraypaint.setOnClickListener(listen_iv);
		iv_alarmdevice.setOnClickListener(listen_iv);
		iv_certify.setOnClickListener(listen_iv);
		rl_conclusion.setOnClickListener(listen_iv);
		ll_hphm.setOnClickListener(listen_iv);

		Button iv_next = (Button) findViewById(R.id.next);
		iv_next.setOnClickListener(listen_iv);
		ImageView iv_scan = (ImageView) findViewById(R.id.scan);
		iv_scan.setOnClickListener(listen_iv);

		applyUnit = SharePreUtil.getSharepref("applyunits");
		units = getResources().getStringArray(R.array.applyunits);
		
		if(units[0].equals(applyUnit) || "".equals(applyUnit)){
			
			String usr = SharePreUtil.getSharepref("usrname");
			btn_chassis.setVisibility(View.VISIBLE);
			if ("陈林".equals(usr)) {
				btn_verify.setVisibility(View.VISIBLE);
			}
			
		}else{
			ll_toolbar.setVisibility(View.GONE);
			btn_inusecar.setVisibility(View.GONE);
			String[] insptypes = getResources().getStringArray(R.array.insptype);
			String[] insptype_codes = getResources().getStringArray(R.array.insptype_code);
			int len = insptypes.length;
			//业务类型默认为：年检（其他）
			vehicle.setInsptype(insptype_codes[len-1]);
			tvInsptype.setText(insptypes[len-1]);
			//
			String[] useproperties = getResources()
					.getStringArray(R.array.useproperties);
			String[] useproperties_code = getResources()
					.getStringArray(R.array.useproperties_code);
			vehicle.setUseProperties(useproperties_code[0]);
			tv_useproperties.setText(useproperties[0]);
		}
	}

	@SuppressLint("ResourceAsColor")
	private void initView() {
		mProgress = new ProgressDialog(this);
		
		ll_hphm = (LinearLayout) findViewById(R.id.ll_hphm);
		licenceEdt = (EditText) findViewById(R.id.add_car_number);
		et_seats = (EditText) findViewById(R.id.et_seats);
		lictype = (LinearLayout) findViewById(R.id.lictype);
		tvLictype = (TextView) findViewById(R.id.tvLictype);
		insptype = (LinearLayout) findViewById(R.id.insptype);
		tvInsptype = (TextView) findViewById(R.id.tvInsptype);
		ll_useproperties = (LinearLayout) findViewById(R.id.ll_useproperties);
		tv_useproperties = (TextView) findViewById(R.id.tv_useproperties);
		ll_clsbdh = (LinearLayout) findViewById(R.id.ll_clsbdh);
		et_clsbdh = (EditText) findViewById(R.id.et_clsbdh);

		iv_vehcode = (ImageView) findViewById(R.id.iv_vehcode);
		iv_enginenum = (ImageView) findViewById(R.id.iv_enginenum);
		iv_brand = (ImageView) findViewById(R.id.iv_brand);
		tv_color = (TextView) findViewById(R.id.tv_color);
		tv_vehtype = (TextView) findViewById(R.id.tv_vehtype);
		iv_color = (ImageView) findViewById(R.id.iv_color);
		iv_seats = (ImageView) findViewById(R.id.iv_seats);
		iv_vehtype = (ImageView) findViewById(R.id.iv_vehtype);
		iv_aspect = (ImageView) findViewById(R.id.iv_aspect);
		iv_tyrecondition = (ImageView) findViewById(R.id.iv_tyrecondition);
		iv_triangles = (ImageView) findViewById(R.id.iv_triangles);
		iv_gabarite = (ImageView) findViewById(R.id.iv_gabarite);
		iv_zbzl = (ImageView) findViewById(R.id.iv_zbzl);
		iv_tyrespec = (ImageView) findViewById(R.id.iv_tyrespec);
		iv_saftyguard = (ImageView) findViewById(R.id.iv_saftyguard);
		iv_reflectlog = (ImageView) findViewById(R.id.iv_reflectlog);
		iv_extinguisher = (ImageView) findViewById(R.id.iv_extinguisher);
		iv_tachographs = (ImageView) findViewById(R.id.iv_tachographs);
		iv_hammer = (ImageView) findViewById(R.id.iv_hammer);
		iv_spraypaint = (ImageView) findViewById(R.id.iv_spraypaint);
		iv_alarmdevice = (ImageView) findViewById(R.id.iv_alarmdevice);
		iv_certify = (ImageView) findViewById(R.id.iv_certify);
		rl_conclusion = (RelativeLayout) findViewById(R.id.rl_conclusion);
		tv_conclusion = (TextView) findViewById(R.id.tv_conclusion);
		et_remark = (EditText) findViewById(R.id.et_remark);
		btn_inusecar = (Button) findViewById(R.id.btn_inusecar);
		btn_chassis = (Button) findViewById(R.id.btn_chassis);
		btn_verify = (Button) findViewById(R.id.waitverify);
		ll_toolbar = (LinearLayout)findViewById(R.id.ll_toolbar);

		vehicle = new Vehicle();
		setImgBackgroundByStas(setVehicle("smallcar",1));

		vehicle.setConclusion(0);
		tv_conclusion.setText("合格");
 	}

	private OnClickListener listen_iv = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Integer nStats;
			switch (v.getId()) {
			case R.id.next:
				sSeats = et_seats.getText().toString().trim();
				sRemark = et_remark.getText().toString().trim();
				vehicle.setSeats(sSeats);
				vehicle.setRemark(sRemark);
				
				
				vehicle.setLicence(licenceEdt.getText().toString().trim().replaceAll(" ", "").toUpperCase());
				vehicle.setClsbdh(et_clsbdh.getText().toString().trim().replaceAll(" ", "").toUpperCase());
				
				if(!prompt(verifyInfo(vehicle),vehicle.getInsptype())){
					Log.e("next", "ss");
					return;
				}
				/**
				 * 如果业务类型是A，则检查颜色是否正确；并把车架号显示
				 * 否则，不检查颜色，另外显示号牌
				 */
				if("A".equals(vehicle.getInsptype())){
					String colorCode = getColorRight(csys);
					if (colorCode == null) {
						Toast.makeText(getApplicationContext(), "请选择正确的颜色",Toast.LENGTH_SHORT).show();
						return;
					} else {
						vehicle.setColor(colorCode);
					}	
				}
				
				DBHelper dbHelper = new DBHelper(getApplicationContext());
				VehDao vehdao = new VehDao(getApplicationContext(),dbHelper);
				long index = vehdao.insert(vehicle);
				Intent intent = new Intent();
				intent.setClass(ReportActivity.this,TakePictureActivity.class);
				vehicle.setVehicleid(index);
				
				/*
				 * 车管所版本每条记录的流水号采用UUID
				 * SharePreUtil.editSharepref("inpOrderNo", UUID.randomUUID().toString().replace("-", ""));
				 * 
				 * 检测线版本记录流水号采用传过来的数据
				 * 
				 */
				SharePreUtil.editSharepref("inpOrderNo", (String)(carinfoMap.get("jylsh")));
				Bundle bundle = new Bundle();
				bundle.putSerializable("vehicle", vehicle);
				intent.putExtras(bundle);
				intent.putExtra("lsh", lsh);
				intent.putExtra("carInfo", carinfoMap);
				startActivity(intent);
				break;
				
			case R.id.ll_hphm:
				Intent in = new Intent();
				in.setClass(v.getContext(), WaitSelectCarActivity.class);
				in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//				startActivity(in);
				startActivityForResult(in, 108);
				break;
				
			case R.id.scan:
				Intent intent2 = new Intent();
				intent2.setClass(ReportActivity.this, CaptureActivity.class);
				intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivityForResult(intent2, SCANNIN_GREQUEST_CODE);
				break;
				
			case R.id.lictype:
				showDialog(DIALOG_LICTYPE);
				break;
			case R.id.ll_useproperties:
				showDialog(DIALOG_USEPROPERTIES);
				break;
			case R.id.insptype:
				showDialog(DIALOG_INSPTYPE);
				break;
			case R.id.tv_color:
				showDialog(DIALOG_COLOR);
				break;
			case R.id.tv_vehtype:
				showDialog(DIALOG_VEHTYPE);
				break;
			case R.id.iv_vehcode:
				nStats = (vehicle.getVehcode() + 1) % 3;
				vehicle.setVehcode(nStats);
				v.setBackgroundResource(getDrawableRes(nStats));
				break;
			case R.id.iv_enginenum:
				nStats = (vehicle.getEnginenum() + 1) % 3;
				vehicle.setEnginenum(nStats);
				v.setBackgroundResource(getDrawableRes(nStats));
				break;
			case R.id.iv_brand:
				nStats = (vehicle.getBrand() + 1) % 3;
				vehicle.setBrand(nStats);
				v.setBackgroundResource(getDrawableRes(nStats));
				break;
			// **************************************************************
			case R.id.iv_color:
				nStats = (vehicle.getIsColorCheck() + 1) % 3;
				vehicle.setIsColorCheck(nStats);
				v.setBackgroundResource(getDrawableRes(nStats));
				break;
			case R.id.iv_seats:
				nStats = (vehicle.getIsSeatsCheck() + 1) % 3;
				vehicle.setIsSeatsCheck(nStats);
				v.setBackgroundResource(getDrawableRes(nStats));
				break;
			case R.id.iv_vehtype:
				nStats = (vehicle.getIsVehtypeCheck() + 1) % 3;
				vehicle.setIsVehtypeCheck(nStats);
				v.setBackgroundResource(getDrawableRes(nStats));
				break;
			// *************************************************************************
			case R.id.iv_aspect:
				nStats = (vehicle.getAspect() + 1) % 3;
				vehicle.setAspect(nStats);
				v.setBackgroundResource(getDrawableRes(nStats));
				break;
			case R.id.iv_tyrecondition:
				nStats = (vehicle.getTyrecondition() + 1) % 3;
				vehicle.setTyrecondition(nStats);
				v.setBackgroundResource(getDrawableRes(nStats));
				break;
			case R.id.iv_triangles:
				nStats = (vehicle.getTriangles() + 1) % 3;
				vehicle.setTriangles(nStats);
				v.setBackgroundResource(getDrawableRes(nStats));
				break;
			case R.id.iv_gabarite:
				nStats = (vehicle.getGabarite() + 1) % 3;
				vehicle.setGabarite(nStats);
				v.setBackgroundResource(getDrawableRes(nStats));
				break;
			case R.id.iv_zbzl:
				nStats = (vehicle.getZbzl() + 1) % 3;
				vehicle.setZbzl(nStats);
				v.setBackgroundResource(getDrawableRes(nStats));
				break;
			case R.id.iv_tyrespec:
				nStats = (vehicle.getTyrespec() + 1) % 3;
				vehicle.setTyrespec(nStats);
				v.setBackgroundResource(getDrawableRes(nStats));
				break;
			case R.id.iv_saftyguard:
				nStats = (vehicle.getSaftyguard() + 1) % 3;
				vehicle.setSaftyguard(nStats);
				v.setBackgroundResource(getDrawableRes(nStats));
				break;
			case R.id.iv_reflectlog:
				nStats = (vehicle.getReflectlog() + 1) % 3;
				vehicle.setReflectlog(nStats);
				v.setBackgroundResource(getDrawableRes(nStats));
				break;
			case R.id.iv_extinguisher:
				nStats = (vehicle.getExtinguisher() + 1) % 3;
				vehicle.setExtinguisher(nStats);
				v.setBackgroundResource(getDrawableRes(nStats));
				break;
			case R.id.iv_tachographs:
				nStats = (vehicle.getTachographs() + 1) % 3;
				vehicle.setTachographs(nStats);
				v.setBackgroundResource(getDrawableRes(nStats));
				break;
			case R.id.iv_hammer:
				nStats = (vehicle.getHammer() + 1) % 3;
				vehicle.setHammer(nStats);
				v.setBackgroundResource(getDrawableRes(nStats));
				break;
			case R.id.iv_alarmdevice:
				nStats = (vehicle.getAlarmdevice() + 1) % 3;
				vehicle.setAlarmdevice(nStats);
				v.setBackgroundResource(getDrawableRes(nStats));
				Log.i("nStats", "nStats19:"+nStats);
				Log.i("nStats", "Alarmdevice:"+vehicle.getAlarmdevice());
				break;
			case R.id.iv_spraypaint:
				nStats = (vehicle.getSpraypaint() + 1) % 3;
				vehicle.setSpraypaint(nStats);
				v.setBackgroundResource(getDrawableRes(nStats));
				break;
			case R.id.iv_certify:
				nStats = (vehicle.getCertify() + 1) % 3;
				vehicle.setCertify(nStats);
				v.setBackgroundResource(getDrawableRes(nStats));
				Log.i("nStats", "nStats20:"+nStats);
				Log.i("nStats", "Certify:"+vehicle.getCertify());
				break;
			case R.id.rl_conclusion:
				final String[] sConclusion = new String[] { "合格", "不合格" };
				new AlertDialog.Builder(ReportActivity.this)
						.setItems(sConclusion,
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										if (which == 0) {
											vehicle.setConclusion(0);
										} else {
											vehicle.setConclusion(2);
										}
										Log.i("onClick", vehicle.getConclusion()+","+sConclusion[which]);
										tv_conclusion.setText(sConclusion[which]);
									}
								}).create().show();
				break;
				
			}
		}

	};

	private int verifyInfo(Vehicle vehicle) {
		
		if (vehicle.getInsptype() == null){
			return 12;
		}
		if (vehicle.getUseProperties() == null || "null".equals(vehicle.getUseProperties())||"".equals(vehicle.getUseProperties())){
			return 13;
		}
		if ("".equals(vehicle.getLicence()) || vehicle.getLicence() == null){
			return 10;
		}
		if (vehicle.getLictype() == null){
			return 11;
		}
		
		if ("".equals(vehicle.getColor()) || vehicle.getColor() == null){
			
			return 20;
		}
		
		if ("".equals(vehicle.getVehtype()) || vehicle.getVehtype() == null){
			return 22;
		}
		return 100;
	}

	private boolean prompt(int nflag , String ywlxCode) {
		switch (nflag) {
		case 100:
			return true;
		case 12:
			Toast.makeText(getApplicationContext(), "请选择业务类型",
					Toast.LENGTH_LONG).show();
			break;
		case 10:
			if (!"A".equals(ywlxCode)) {
				Toast.makeText(getApplicationContext(), "请输入号牌号码",
						Toast.LENGTH_LONG).show();
				break;
			}else{
				return true;
			}
			
		case 11:
			Toast.makeText(getApplicationContext(), "请选择号牌种类",
					Toast.LENGTH_LONG).show();
			break;
			
		case 13:
			Toast.makeText(getApplicationContext(), "请选择使用性质",
					Toast.LENGTH_LONG).show();
			break;
			
		case 20:
			if ("A".equals(ywlxCode)||"D".equals(ywlxCode)) {
				Toast.makeText(getApplicationContext(), "请选择车身颜色",
						Toast.LENGTH_LONG).show();
				break;
			}else{
				return true;
			}
		
		case 22:
			if ("A".equals(ywlxCode)) {
				Toast.makeText(getApplicationContext(), "请选择车辆类型",
						Toast.LENGTH_LONG).show();
				break;
			}else{
				return true;
			}
		}
		return false;
	}

	protected Dialog onCreateDialog(int id) {
		Dialog dialog = null;
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		switch (id) {
		case DIALOG_LICTYPE:
			builder.setItems(R.array.lictype,
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							String[] sLictype = getResources().getStringArray(
									R.array.lictype);
							String[] sLictype_code = getResources()
									.getStringArray(R.array.lictype_code);
							vehicle.setLictype(sLictype_code[which]);
							tvLictype.setText(sLictype[which]);

							if (0 == which)
								setImgBackgroundByStas(setVehicle("largecar",1));
							if (1 == which)
								setImgBackgroundByStas(setVehicle("smallcar",1));
						}
					});
			dialog = builder.create();
			break;
		case DIALOG_USEPROPERTIES:
			builder.setItems(R.array.useproperties,
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							String sUseproperties = getResources()
									.getStringArray(R.array.useproperties)[which];
							String code = getResources()
									.getStringArray(R.array.useproperties_code)[which];
							vehicle.setUseProperties(code);
							tv_useproperties.setText(sUseproperties);
						}
					});
			dialog = builder.create();
			break;
		case DIALOG_INSPTYPE:
			builder.setItems(R.array.insptype,
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							String[] insptypes = getResources().getStringArray(
									R.array.insptype);
							String[] insptype_codes = getResources()
									.getStringArray(R.array.insptype_code);
							vehicle.setInsptype(insptype_codes[which]);
							tvInsptype.setText(insptypes[which]);
							changeViewByIsRegistBusiness(vehicle.getInsptype());
						}
					});
			dialog = builder.create();
			break;
		case DIALOG_COLOR:
			builder.setTitle("选择车身颜色");
			DialogInterface.OnMultiChoiceClickListener mutiListener = new DialogInterface.OnMultiChoiceClickListener() {
				@Override
				public void onClick(DialogInterface dialogInterface, int which,
						boolean isChecked) {
					colorSelected[which] = isChecked;
				}
			};
			builder.setMultiChoiceItems(R.array.color, colorSelected,mutiListener);
			DialogInterface.OnClickListener btnListener = new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialogInterface, int which) {
					String multicolors = "";
					String multicolors_code = "";
					int count = 0;
					for (int i = 0; i < colorSelected.length; i++) {
						if (colorSelected[i] == true) {
							count++;
							if ("".equals(multicolors)) {
								multicolors = getResources().getStringArray(
										R.array.color)[i];
								multicolors_code = getResources()
										.getStringArray(R.array.color_code)[i];
							} else {
								multicolors = multicolors
										+ ","
										+ getResources().getStringArray(
												R.array.color)[i];
								multicolors_code = multicolors_code
										+ ","
										+ getResources().getStringArray(
												R.array.color_code)[i];
							}
						}
					}
					Log.i("count", "count"+count);
					if (count > 3) {
						Toast.makeText(getApplicationContext(),
								"颜色不能超过三个,请重新选择,count" + count,
								Toast.LENGTH_LONG).show();
					} else {
						csys = multicolors;
						tv_color.setText(multicolors);
						vehicle.setColor(multicolors_code);
					}
				}
			};
			builder.setPositiveButton("确定", btnListener);
			dialog = builder.create();
			break;
		case DIALOG_VEHTYPE:
			builder.setItems(R.array.vehtype,
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							String sVehtype = getResources().getStringArray(
									R.array.vehtype)[which];
							 String sVehtypeCode=
							 getResources().getStringArray(R.array.vehtype_code)[which];
							vehicle.setVehtype(sVehtypeCode);
							tv_vehtype.setText(sVehtype);
						}
					});
			dialog = builder.create();
			break;
		}
		return dialog;
	}

	private int getDrawableRes(int stats) {
		if (stats == 0) {
			return getResources().getIdentifier("checkbox_0", "drawable",
					"com.vechicle");
		} else if (stats == 1) {
			return getResources().getIdentifier("checkbox_1", "drawable",
					"com.vechicle");
		} else
			return getResources().getIdentifier("checkbox_2", "drawable",
					"com.vechicle");
	}

	private void setImgBackgroundByStas(Vehicle vehicle) {
		iv_vehcode.setBackgroundResource(getDrawableRes(vehicle.getVehcode()));
		iv_enginenum.setBackgroundResource(getDrawableRes(vehicle
				.getEnginenum()));
		iv_brand.setBackgroundResource(getDrawableRes(vehicle.getBrand()));
		iv_color.setBackgroundResource(getDrawableRes(vehicle.getIsColorCheck()));
		iv_seats.setBackgroundResource(getDrawableRes(vehicle.getIsSeatsCheck()));
		iv_vehtype.setBackgroundResource(getDrawableRes(vehicle
				.getIsVehtypeCheck()));
		iv_aspect.setBackgroundResource(getDrawableRes(vehicle.getAspect()));
		iv_tyrecondition.setBackgroundResource(getDrawableRes(vehicle
				.getTyrecondition()));
		iv_triangles.setBackgroundResource(getDrawableRes(vehicle
				.getTriangles()));
		iv_gabarite
				.setBackgroundResource(getDrawableRes(vehicle.getGabarite()));
		iv_zbzl.setBackgroundResource(getDrawableRes(vehicle.getZbzl()));
		iv_tyrespec
				.setBackgroundResource(getDrawableRes(vehicle.getTyrespec()));
		iv_saftyguard.setBackgroundResource(getDrawableRes(vehicle
				.getSaftyguard()));
		iv_reflectlog.setBackgroundResource(getDrawableRes(vehicle
				.getReflectlog()));
		iv_extinguisher.setBackgroundResource(getDrawableRes(vehicle
				.getExtinguisher()));
		iv_tachographs.setBackgroundResource(getDrawableRes(vehicle
				.getTachographs()));
		iv_hammer.setBackgroundResource(getDrawableRes(vehicle.getHammer()));
		iv_alarmdevice.setBackgroundResource(getDrawableRes(vehicle
				.getAlarmdevice()));
		iv_spraypaint.setBackgroundResource(getDrawableRes(vehicle
				.getSpraypaint()));
		iv_certify.setBackgroundResource(getDrawableRes(vehicle.getCertify()));
	}

	public void announcement(View view) throws Exception {
		String hphm = licenceEdt.getText().toString().trim().replaceAll(" ", "").toUpperCase();
		String hpzl = vehicle.getLictype();
		String ywlxCode = vehicle.getInsptype();
		
		if(ywlxCode == null){
			Toast.makeText(getApplicationContext(), "请输入业务类型", Toast.LENGTH_LONG).show();
			return;
		}
		if(!"A".equals(ywlxCode)){
			if(ToolUtils.isEmptyString(hphm) || ToolUtils.isEmptyString(hpzl) || hphm.length()<5){
				Toast.makeText(getApplicationContext(), "请输入五位号牌号码和号牌种类", Toast.LENGTH_LONG).show();
				return;
			}
		}
		
		Intent intent = new Intent();
		intent.setClass(this, AnnounceActivity.class);
		intent.putExtra("ggbh", ggbh);
		intent.putExtra("fdjh", fdjh);
		intent.putExtra("qlj", qlj);
		intent.putExtra("hlj", hlj);
		intent.putExtra("zj", zj);
		intent.putExtra("hphm", hphm);
		intent.putExtra("hpzl", hpzl);
		startActivity(intent);

	}

	public void waitVerify(View view) throws Exception {
		Intent intent = new Intent();
		intent.setClass(this, VerifyListActivity.class);
		startActivity(intent);
	}
	
	public void inUseCarInfo(View view) throws Exception {
		String hphm = licenceEdt.getText().toString().trim().replaceAll(" ", "").toUpperCase();
		String hpzl = vehicle.getLictype();
		
		if("".equals(hphm)|| hphm==null || "".equals(hpzl) || hpzl ==null){
			Toast.makeText(getApplicationContext(), "请输入号牌号码和号牌种类", Toast.LENGTH_LONG).show();
			return;
		}
		Intent intent = new Intent();
		intent.setClass(this, InUseCarInfoActivity.class);
		intent.putExtra("hphm", hphm);
		intent.putExtra("hpzl", hpzl);
		startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
	}
	
	public void checkTemplate(View view) throws Exception {
//		String result;
//		String preUrl = ToolUtils.getServerURL()+"cms/baseManager!!getBeanList.action?bType=defaultItemTempletConfig";
//		List<BasicNameValuePair> bnvs = new ArrayList<BasicNameValuePair>();
//		result = HttpUtils.postRequest(preUrl, bnvs);
//		Log.i("checkTemplate", "result:"+result);
		Toast.makeText(getApplicationContext(), "ww", Toast.LENGTH_LONG);
		new CheckTemplateAsyn().execute();
	}
	
	
	
	public void chassisInfo(View view) throws Exception{
		String hphm = licenceEdt.getText().toString().trim().replaceAll(" ", "").toUpperCase();
		String hpzl = vehicle.getLictype();
		String ywlxCode = vehicle.getInsptype();
		
		if(ywlxCode == null){
			Toast.makeText(getApplicationContext(), "请输入业务类型", Toast.LENGTH_LONG).show();
			return;
		}
		
		if(dpid == null || "null".equals(dpid) ||"".equals(dpid)){
			Toast.makeText(getApplicationContext(), "没有底盘ID,没有底盘公告", Toast.LENGTH_LONG).show();
			return;
		}
		
		Intent intent = new Intent();
		intent.setClass(this, ChassisInfoActivity.class);
		if("A".equals(ywlxCode) && dpid != null){
			intent.putExtra("dpid", dpid);
		}else{
			intent.putExtra("hphm", hphm);
			intent.putExtra("hpzl", hpzl);
		}
		
		startActivity(intent);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case SCANNIN_GREQUEST_CODE:
			if (resultCode == 20) {
				clxh = data.getExtras().getString("clxh");
				String clsbdm = data.getExtras().getString("clsbdm");
				csys = data.getExtras().getString("csys");
				String dzrs = data.getExtras().getString("dzrs");
				String hpzlcode = data.getExtras().getString("hpzl");
				String ywlxcode = data.getExtras().getString("ywlx");
				String cllxcode = data.getExtras().getString("cllx");
				ggbh = data.getExtras().getString("ggbh");
				String useprop = data.getExtras().getString("useprop");
				fdjh = data.getExtras().getString("fdjh");
				qlj = data.getExtras().getString("qlj");
				hlj = data.getExtras().getString("hlj");
				zj = data.getExtras().getString("zj");
				lsh = data.getExtras().getString("lsh");
				hphm = data.getExtras().getString("hphm");
				dpid = data.getExtras().getString("dpid");

				//显示、存储"使用性质"
				vehicle.setUseProperties(useprop);
				tv_useproperties.setText(ToolUtils.getLableByCode(useprop,
						R.array.useproperties, R.array.useproperties_code));
				
				
				
				//显示、存储"业务类型";根据业务类型改变view
				changeViewByIsRegistBusiness(ywlxcode);
				
				vehicle.setInsptype(ywlxcode);
				tvInsptype.setText(ToolUtils.getLableByCode(ywlxcode,R.array.insptype,R.array.insptype_code));
				

				//存储显示号牌号码
				licenceEdt.setText(hphm);
				vehicle.setLicence(hphm);
				
				//存储显示车架号
				et_clsbdh.setText(clsbdm);
				vehicle.setClsbdh(clsbdm);
				
				tv_color.setText(csys);
				et_seats.setText(dzrs);

				vehicle.setLictype(hpzlcode);
				tvLictype.setText(ToolUtils.getLableByCode(hpzlcode, R.array.lictype,R.array.lictype_code));
				if ("01".equals(hpzlcode))
					setImgBackgroundByStas(setVehicle("largecar",1));
				if ("02" == hpzlcode)
					setImgBackgroundByStas(setVehicle("smallcar",1));


				vehicle.setVehtype(cllxcode);
				tv_vehtype.setText(ToolUtils.getLableByCode(cllxcode,R.array.vehtype, R.array.vehtype_code));

				
			}
			if(resultCode == 30){
				String clsbdh = data.getExtras().getString("clsbdh");
				String syxz = data.getExtras().getString("syxz");
				if(!"".equals(clsbdh) && clsbdh!=null){
					et_clsbdh.setText(clsbdh);
				}
				if(!"".equals(syxz) && syxz!=null){
					tv_useproperties.setText(syxz);
					vehicle.setUseProperties(ToolUtils.getCodeByLable(syxz,R.array.useproperties, R.array.useproperties_code));
				}
			}
			break;
			
		case 108:
			if(resultCode == 33){
				carinfoMap = (HashMap<String, Object>) data.getExtras().get("carinfo");
				
//				zpsList = (ArrayList<String>)(carinfoMap.get("zps"));
				
				licenceEdt.setText((String)carinfoMap.get("hphm"));
				
				String hpzlcode = (String)carinfoMap.get("hpzl");
				vehicle.setLictype(hpzlcode);
				tvLictype.setText(ToolUtils.getLableByCode(hpzlcode, R.array.lictype,R.array.lictype_code));
				
				String clsbdm = (String)carinfoMap.get("clsbdh");
				et_clsbdh.setText(clsbdm);
				vehicle.setClsbdh(clsbdm);
			}
			break;
		}
	}

	private Vehicle setVehicle(String str ,int mode) {
		String settings;
		if(mode==1){
			Properties pro = MyPropertiesUtil.getProperties();
			settings = pro.getProperty(str);
		}else{
			settings =str;
			settings.substring(0, settings.length()-1);
		}
		String[] array = settings.split("\\|");
		if (20 == array.length) {
			// 通用查验项
			vehicle.setVehcode(Integer.parseInt(array[0]));
			vehicle.setEnginenum(Integer.parseInt(array[1]));
			vehicle.setBrand(Integer.parseInt(array[2]));
			vehicle.setIsColorCheck(Integer.parseInt(array[3]));
			vehicle.setIsSeatsCheck(Integer.parseInt(array[4]));
			vehicle.setIsVehtypeCheck(Integer.parseInt(array[5]));
			vehicle.setAspect(Integer.parseInt(array[6]));
			vehicle.setTyrecondition(Integer.parseInt(array[7]));
			vehicle.setTriangles(Integer.parseInt(array[8]));

			// 货车挂车查验项
			vehicle.setGabarite(Integer.parseInt(array[9]));
			Log.i("setZbzl", "" + Integer.parseInt(array[10]));
			vehicle.setZbzl(Integer.parseInt(array[10]));
			vehicle.setTyrespec(Integer.parseInt(array[11]));
			vehicle.setSaftyguard(Integer.parseInt(array[12]));
			vehicle.setReflectlog(Integer.parseInt(array[13]));

			// 大中型客车
			vehicle.setExtinguisher(Integer.parseInt(array[14]));
			vehicle.setTachographs(Integer.parseInt(array[15]));
			vehicle.setHammer(Integer.parseInt(array[16]));
			vehicle.setSpraypaint(Integer.parseInt(array[17]));
			// 其他
			vehicle.setAlarmdevice(Integer.parseInt(array[18]));
			vehicle.setCertify(Integer.parseInt(array[19]));
		}
		return vehicle;
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		if ((Intent.FLAG_ACTIVITY_CLEAR_TOP & intent.getFlags()) != 0) {
			onCreate(null);
		}
	}

	private String getColorRight(String csys) {
		if(csys==null){
			return null;
		}
		String[] cc = csys.split(",");
		String[] colors = getResources().getStringArray(R.array.color);
		String[] colors_code = getResources().getStringArray(R.array.color_code);
		String code = "";
		for (String c : cc) {
			for (int i = 0; i < colors.length; i++) {
				if (c.equals(colors[i])) {
					code += colors_code[i]+",";
					break;
				}
			}
		}
		if(!"".equals(code)){
			code=code.substring(0,code.length()-1);
			if(code.split(",").length!=cc.length){
				return null;
			}
		}else{
			return null;
		}
		
		return code;
	}
	
	private void changeViewByIsRegistBusiness(String ywlxCode){
		if("A".equals(ywlxCode)){
			ll_hphm.setVisibility(View.GONE);
			ll_clsbdh.setVisibility(View.VISIBLE);
			tv_color.setVisibility(View.VISIBLE);
			tv_vehtype.setVisibility(View.VISIBLE);
			et_seats.setVisibility(View.VISIBLE);
		}else if("D".equals(ywlxCode)){
//			ll_clsbdh.setVisibility(View.GONE);
			ll_hphm.setVisibility(View.VISIBLE);
			tv_vehtype.setVisibility(View.GONE);
			et_seats.setVisibility(View.GONE);
			tv_color.setVisibility(View.VISIBLE);
		}else{
//			ll_clsbdh.setVisibility(View.GONE);
			ll_hphm.setVisibility(View.VISIBLE);
			tv_color.setVisibility(View.GONE);
			tv_vehtype.setVisibility(View.GONE);
			et_seats.setVisibility(View.GONE);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(Menu.NONE, Menu.FIRST+1, 1, R.string.menu_item1);
		menu.add(Menu.NONE, Menu.FIRST+2, 2, R.string.menu_item2);
		menu.add(Menu.NONE, Menu.FIRST+3, 2, R.string.menu_item3);
		menu.add(Menu.NONE, Menu.FIRST+4, 2, R.string.menu_item4);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case Menu.FIRST+1:
			SharePreUtil.editSharepref("usrname","");
			SharePreUtil.editSharepref("passwd","");
			Intent intent = new Intent();  
            intent.setClass(ReportActivity.this, LoginActivity.class);
            finish();
            startActivity(intent);
			break;

		case Menu.FIRST+2:
//			ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE); 
//			am.killBackgroundProcesses("com.vechicle");
			SharePreUtil.editSharepref("usrname","");
			SharePreUtil.editSharepref("passwd","");
			android.os.Process.killProcess(android.os.Process.myPid());
			break;
		case Menu.FIRST+3:
			new UpdateManager(ReportActivity.this).checkUpdate();
			break;
		case Menu.FIRST+4:
			int versionCode =0;
			try {
				versionCode = getApplicationContext().getPackageManager().getPackageInfo("com.vechicle", 0).versionCode;
			} catch (NameNotFoundException e) {
				e.printStackTrace();
			}
			Toast.makeText(getApplicationContext(), "当前版本代号为："+versionCode, Toast.LENGTH_LONG).show();
			break;
		}
		return false;
	}
	
	
	private class CheckTemplateAsyn extends AsyncTask<Void, Void, String> {

		@Override
		protected String doInBackground(Void... params) {
			String result = null;
			String preUrl = ToolUtils.getServerURL()+"cms/baseManager!!getBeanList.action?bType=defaultItemTempletConfig";
			List<BasicNameValuePair> bnvs = new ArrayList<BasicNameValuePair>();
			result = HttpUtils.postRequest(preUrl, bnvs);
			return result;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mProgress.setTitle("请稍等");
			mProgress.setMessage("获取查验模板...");
			mProgress.setCancelable(false);
			mProgress.show();
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			mProgress.dismiss();
			if(result == null){
				Toast.makeText(getApplicationContext(), "请检查网络", Toast.LENGTH_LONG);
			}
			try {
				JSONObject jo = new JSONObject(result);
				JSONArray ja = jo.getJSONArray("rows");
				String[] templetNames = new String[ja.length()];
				final String[] templets = new String[ja.length()];
				for(int i=0;i<ja.length();i++){
					jo = ja.getJSONObject(i);
					templetNames[i] = jo.getString("templetName");
					templets[i] = jo.getString("templet");
				}
				new AlertDialog.Builder(ReportActivity.this).setTitle("提示")
				.setItems(templetNames, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						String templet = templets[which];
						Log.i("templet","templet"+templet);
						setImgBackgroundByStas(setVehicle(templet,2));
					}}).create().show();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void remarkTemplate(View view){
		final String[] remarks = getResources().getStringArray(R.array.remarks);
		new AlertDialog.Builder(ReportActivity.this).setTitle("选择备注模板")
		.setItems(remarks, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				et_remark.setText(remarks[which]);
			}}).create().show();
	}
	
}
