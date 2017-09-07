package com.vechicle.control;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;

public class MyViewPagerAdapter extends FragmentPagerAdapter {

	public List<Fragment> fragments;
	public FragmentManager fm;
	
	public MyViewPagerAdapter(FragmentManager fm,List<Fragment> fragmentList) {
		super(fm);
		this.fm = fm;
		this.fragments = fragmentList;
	}
	
	@Override
	public Fragment getItem(int arg0) {
		return fragments.get(arg0);
	}

	@Override
	public int getCount() {
		return fragments.size();
	}

	public void destroyFragments() {
		if (this.fragments != null) {
			FragmentTransaction ft = fm.beginTransaction();
			for (Fragment f : this.fragments) {
				ft.remove(f);
			}
			ft.commit();
			ft = null;
			fm.executePendingTransactions();
			
		}
	}

}
