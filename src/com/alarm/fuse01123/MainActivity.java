/*
 * Copyright 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.alarm.fuse01123;

import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.SearchManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.navigationdrawerexample.R;

/**
 * This example illustrates a common usage of the DrawerLayout widget
 * in the Android support library.
 * <p/>
 * <p>When a navigation (left) drawer is present, the host activity should detect presses of
 * the action bar's Up affordance as a signal to open and close the navigation drawer. The
 * ActionBarDrawerToggle facilitates this behavior.
 * Items within the drawer should fall into one of two categories:</p>
 * <p/>
 * <ul>
 * <li><strong>View switches</strong>. A view switch follows the same basic policies as
 * list or tab navigation in that a view switch does not create navigation history.
 * This pattern should only be used at the root activity of a task, leaving some form
 * of Up navigation active for activities further down the navigation hierarchy.</li>
 * <li><strong>Selective Up</strong>. The drawer allows the user to choose an alternate
 * parent for Up navigation. This allows a user to jump across an app's navigation
 * hierarchy at will. The application should treat this as it treats Up navigation from
 * a different task, replacing the current task stack using TaskStackBuilder or similar.
 * This is the only form of navigation drawer that should be used outside of the root
 * activity of a task.</li>
 * </ul>
 * <p/>
 * <p>Right side drawers should be used for actions, not navigation. This follows the pattern
 * established by the Action Bar that navigation should be to the left and actions to the right.
 * An action should be an operation performed on the current contents of the window,
 * for example enabling or disabling a data overlay on top of the current content.</p>
 */

public class MainActivity extends FragmentActivity  {
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] mMenuTitles;
    
    AlarmPagerAdapter mAlarmPagerAdapter;
    ViewPager mViewPager;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAlarmPagerAdapter = new AlarmPagerAdapter(
				getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mAlarmPagerAdapter);
        mTitle = mDrawerTitle = getTitle();
        mMenuTitles = getResources().getStringArray(R.array.MenuList);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mMenuTitles));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        // enable ActionBar app icon to behave as action to toggle nav drawer
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
                ) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            selectItem(0);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         // The action bar home/up action should open or close the drawer.
         // ActionBarDrawerToggle will take care of this.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action buttons
        switch(item.getItemId()) {
        case R.id.action_websearch:
            // create intent to perform web search for this planet
            Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
            intent.putExtra(SearchManager.QUERY, getActionBar().getTitle());
            // catch event that there's no activity to handle intent
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            } else {
                Toast.makeText(this, R.string.app_not_available, Toast.LENGTH_LONG).show();
            }
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }

    /* The click listner for ListView in the navigation drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {

        // update the main content by replacing fragments
    	System.out.println("Items" + position +"is Selected from Menu ");
    	
    	//Menu  Select
    	switch (position){
        case 0: 
        	 break;
        	 
        case 1: 
  
       	 Fragment fragment = new AlarmPoolFragment();
         Bundle args = new Bundle();
         args.putInt(AlarmPoolFragment.ARG_PAGER_NUMBER, position);
         fragment.setArguments(args);
         FragmentManager fragmentManager = getFragmentManager();
       	 fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

       	 break;
       
        case 2: 
       	 break;
     
        case 3: 
       	 break;
       
        }
 /*       Fragment fragment = new SlideMenu();
        Bundle args = new Bundle();
        args.putInt(SlideMenu.ARG_MENU_NUMBER, position);
        
        fragment.setArguments(args);

        FragmentManager fragmentManager = getFragmentManager();
 //       fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit(); 
  * 
  */
        // update selected item and title, then close the drawer
        mDrawerList.setItemChecked(position, true);
        setTitle(mMenuTitles[position]);
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    
    /**
     * Fragment Adapter that appears in the "content_frame", shows a planet
     */
    
    
    /**
     * Fragment that appears in the "content_frame", shows a planet
     */
//    public static class SlideMenu extends Fragment {
//        public static final String ARG_MENU_NUMBER = "Menu_number";
//
//        public SlideMenu() {
//            // Empty constructor required for fragment subclasses
//        }
//
//        @Override
//        public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                Bundle savedInstanceState) {
//            View rootView = inflater.inflate(R.layout.fragment_planet, container, false);
//            int i = getArguments().getInt(ARG_MENU_NUMBER);
//            String menulist = getResources().getStringArray(R.array.MenuList)[i];
//            System.out.println("i = " + i);     
////
////            int imageId = getResources().getIdentifier(planet.toLowerCase(Locale.getDefault()),
////                            "drawable", getActivity().getPackageName());
////            ((ImageView) rootView.findViewById(R.id.image)).setImageResource(imageId);
//            getActivity().setTitle(menulist);
//            return rootView;
//        }
//    }
//    
    public class AlarmPagerAdapter extends FragmentPagerAdapter {

    	public AlarmPagerAdapter(android.support.v4.app.FragmentManager fm) {
			super(fm);
			// TODO Auto-generated constructor stub
		}
//its not support library fragment
//		public AlarmPagerAdapte(Fragmen fm) {
//    		super(fm);
//    		// TODO Auto-generated constructor stub
//    	}

    	
    	@Override
    	public Fragment getItem(int card) {
    		// TODO Auto-generated method stub
    		//getItem is called to instantiate the fragment for the given page.
    		//Return a AlarmPoolFragment with the pager number as its
    		//Fragment fragment = new com.alarm.fuse01123.MainActivity.AlarmPoolFragment();
    		Fragment fragment = new AlarmPoolFragment();
    		
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
    			return getString(R.string.FuseName1).toUpperCase(l);
    		case 1:
    			return getString(R.string.FuseName2).toUpperCase(l);
    		case 2:
    			return getString(R.string.FuseName3).toUpperCase(l);
    		}
    		return null;
    	}
    }
    
    public class AlarmPoolFragment extends Fragment {
    	public static final String ARG_PAGER_NUMBER = "page_number";
    	public AlarmPoolFragment() {
		}
    	@Override
    	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saBundle){
			View rootView = inflater.inflate(R.layout.bigcardbottom, container, false);
					TextView dummyTextView = (TextView) rootView.findViewById(R.id.FuseName);
			dummyTextView.setText(Integer.toString(getArguments().getInt(
					ARG_PAGER_NUMBER)));
    		return rootView;
    		
    	}
    }

//    public AlarmPagerAdapter extend FragmentPagerAdapter  {
//    	public AlarmPagerAdapter(FragmentManager fm){
//    		super(fm);
//    	}
//    	@Override
//    	public 
//    }
    
}