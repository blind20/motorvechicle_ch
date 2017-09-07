package com.vechicle.ui;

import java.util.ArrayList;
import java.util.List;

import com.vechicle.R;
import com.vechicle.control.MenuItemAdapter;
import com.vechicle.domain.MenuItem;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.AdapterView.OnItemClickListener;

public class MenuActivity extends Activity {

	private GridView gv_menu;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		gv_menu = (GridView)findViewById(R.id.gv_menu);
		init();
	}


	private void init() {
		List<MenuItem> menus = new ArrayList<MenuItem>();
		menus.add(new MenuItem(R.drawable.menu_leidian, "机动车查验", "外观查验 照片上传"));
		menus.add(new MenuItem(R.drawable.menu_downloaded, "引车程序", "检测站专用"));
//		menus.add(new MenuItem(R.drawable.menu_photo, "功能3", ""));
//		menus.add(new MenuItem(R.drawable.menu_video, "功能4", ""));
		// 计算margin
		int margin = (int) (getResources().getDisplayMetrics().density * 14 * 13 / 9);
		MenuItemAdapter adapter = new MenuItemAdapter(MenuActivity.this, menus, margin);
		gv_menu.setAdapter(adapter);
		gv_menu.setOnItemClickListener(itemClick);
	}
	
	
	private OnItemClickListener itemClick = new OnItemClickListener(){
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			if(position == 0){
				jumpFunction(ReportActivity.class);
			}
			if(position == 1){
				jumpFunction(PullCarActivity.class);
			}
		}
	};
	
	private void jumpFunction(Class<?> cls) {
		Intent intent = new Intent();
		intent.setClass(MenuActivity.this, cls);
		startActivity(intent);
	}
}
