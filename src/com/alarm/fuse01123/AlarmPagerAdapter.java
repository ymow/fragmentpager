/*package com.alarm.fuse01123;

import java.util.Locale;




import android.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class AlarmPagerAdapter extends FragmentPagerAdapter {

	public AlarmPagerAdapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Fragment getItem(int card) {
		// TODO Auto-generated method stub
		//getItem is called to instantiate the fragment for the given page.
		//Return a AlarmPoolFragment with the pager number as its
		//Fragment fragment = new com.alarm.fuse01123.MainActivity.AlarmPoolFragment();
		Fragment fragment = new com.alarm.fuse01123.MainActivity.AlarmPoolFragment();
		
		Bundle args = new Bundle();
		args.putInt(MainActivity.AlarmPoolFragment.ARG_PAGER_NUMBER, card + 1);
		fragment.setArguments(args);
		
		return fragment;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 3;
	}
	@Override
	public CharSequence getPageTitle(int position) {
		Locale l = Locale.getDefault();
		switch (position) {
		case 0:
			return getString(R.string.fuseName1).toUpperCase(l);
		case 1:
			return getString(R.string.FuseName2).toUpperCase(l);
		case 2:
			return getString(R.string.FuseName3).toUpperCase(l);
		}
		return null;
	}
}
*/