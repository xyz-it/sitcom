package com.xyzit.sitcom;


import android.animation.LayoutTransition;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.xyzit.sitcom.com.xyzit.sitcom.provider.SalesOrderProvider;
import com.xyzit.sitcom.controller.adapter.LeftMenuAdapter;
import com.xyzit.sitcom.view.fragment.MainFragment;
import com.xyzit.sitcom.view.fragment.SettingsFragment;

import org.robobinding.binder.BinderFactory;
import org.robobinding.binder.BinderFactoryBuilder;

//import android.widget.*;

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
public class MainActivity extends ActionBarActivity {
    private DrawerLayout mDrawerLayout;
    //private ListView mDrawerList;
    private ListView mDrawerList2;
    private View mFrameLayout;
    private LinearLayout mleftMenu;
    private Toolbar mToolbar;
    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    //private String[] mPlanetTitles;
    private String[] mTitles;
    private String[] mFrames;
    private TypedArray mLeftMenuItems;
    private TypedArray mIcons;
    private View mainFrame;

    private Fragment[] mFragments = new Fragment[7];

    private BinderFactory reusableBinderFactory;

    private static int barcodeRequestCode = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FE


        setContentView(R.layout.activity_main);


        reusableBinderFactory = new BinderFactoryBuilder().build();


        mTitle = mDrawerTitle = getTitle();
        mLeftMenuItems = getResources().obtainTypedArray(R.array.menu_items);
        mIcons = getResources().obtainTypedArray(R.array.icons);
        //mPlanetTitles = getResources().getStringArray(R.array.planets_array);
        mTitles = getResources().getStringArray(R.array.panel_titles);
        mFrames = getResources().getStringArray(R.array.frames);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        //mDrawerList = (ListView) findViewById(R.id.left_drawer);
        mleftMenu = (LinearLayout) findViewById(R.id.leftMenu);
        mDrawerList2 = (ListView) findViewById(R.id.left_drawer2);
        mFrameLayout = findViewById(R.id.content_frame);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        //findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener
        //mDrawerList.setAdapter(new ArrayAdapter<String>(this,
        //        R.layout.drawer_list_item, mPlanetTitles));
        //mDrawerList.setOnItemClickListener(new DrawerItemClickListener());


        String[] menuViewId = new String[7];
        for (int i = 0; i < mLeftMenuItems.length(); i++) {
            menuViewId[i] = mLeftMenuItems.getString(i);
        }


        mDrawerList2.setAdapter(new LeftMenuAdapter(this,
                R.layout.drawer_list_item, menuViewId));
		mDrawerList2.setOnItemClickListener(new DrawerItemClickListener());
		Toast.makeText(this, "click", Toast.LENGTH_LONG);
		
        // enable ActionBar app icon to behave as action to toggle nav drawer
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setHomeButtonEnabled(false);
        //getSupportActionBar().setDisplayUseLogoEnabled(false);
        getSupportActionBar().setLogo(R.drawable.ic_logo);
        //getSupportActionBar().setDisplayShowTitleEnabled(false);
        //getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_logo);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle("");


        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                mToolbar,
                //R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
        ) {
            public void onDrawerClosed(View view) {
                //getSupportActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                //getSupportActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        //mDrawerToggle.setDrawerIndicatorEnabled(false);
        //mDrawerToggle.setHomeAsUpIndicator(R.drawable.ic_logo);


        if (savedInstanceState == null) {
            selectItem(0);
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);

        SearchView searchView = (SearchView) (menu.findItem(R.id.action_websearch).getActionView());
        int searchBarId = searchView.getContext().getResources().getIdentifier("app:id/search_bar", null, null);

        LinearLayout testlayout = (LinearLayout) searchView.getChildAt(0);
        int searchBarId2 = searchView.getResources().getIdentifier("app:id/search_bar", null, null);
        LinearLayout searchBar = (LinearLayout) searchView.findViewById(searchBarId2);

        //searchBar.setLayoutTransition(new LayoutTransition());
        testlayout.setLayoutTransition(new LayoutTransition());

        return super.onCreateOptionsMenu(menu);
    }

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mleftMenu);
        //boolean drawerOpen = false;
        //menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
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
        switch (item.getItemId()) {
            case R.id.action_websearch:

                /*
                // create intent to perform web search for this planet
                Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
                intent.putExtra(SearchManager.QUERY, getSupportActionBar().getTitle());
                // catch event that there's no activity to handle intent
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                } else {
                    Toast.makeText(this, R.string.app_not_available, Toast.LENGTH_LONG).show();
                }
                return true;*/
                return super.onOptionsItemSelected(item);

            case R.id.action_barcode:

                Intent intent = new Intent("com.google.zxing.client.android.SCAN");
                startActivityForResult(intent, barcodeRequestCode);
                return true;


            case R.id.action_settings:
                displaySettings();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        // TODO Auto-generated method stub
        if (requestCode == barcodeRequestCode) {
            if (resultCode == RESULT_OK) {
                String articleId = intent.getStringExtra("SCAN_RESULT");
                Intent articleIntent = new Intent(Intent.ACTION_VIEW);
                articleIntent.setData(Uri.withAppendedPath(SalesOrderProvider.CONTENT_URI, articleId));
                this.startActivity(articleIntent);
            }
        }
        //super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    protected void onNewIntent(Intent intent) {
        // TODO Auto-generated method stub
        super.onNewIntent(intent);
        if (Intent.ACTION_VIEW.equals(intent.getAction()))
            //displayCustomer(intent);
        {
            Toast toast = Toast.makeText(this, intent.getDataString(), Toast.LENGTH_LONG);
            toast.show();
        }

       // else
            //displayEmpty();
        //setContentView(R.layout.empty);
    }


    private void selectItem(int position) {
        // update the main content by replacing fragments
        //Fragment fragment = new PlanetFragment();
        //Bundle args = new Bundle();
        //args.putInt(PlanetFragment.ARG_PLANET_NUMBER, position);
        //fragment.setArguments(args);

        //FragmentManager fragmentManager = getFragmentManager();
        //fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

        displayFrame(position);


        // update selected item and title, then close the drawer
        mDrawerList2.setItemChecked(position, true);
		//String text = Integer.toString(position);
		//Toast.makeText(this, text, Toast.LENGTH_LONG);

        //getSupportActionBar().setIcon(mIcons.getDrawable(position));
        //setTitle(mTitles[position]);


        mDrawerLayout.closeDrawer(mleftMenu);
    }

    private void displayFrame(int position) {

        int frameId = getResources().getIdentifier(mFrames[position],
                "layout", getPackageName());


        Fragment fragment;

        //MainFragment encapsulates the binding, and can be used in place of Fragment (inherited)
        if (mFragments[position] == null) {
            fragment = new MainFragment();


            Bundle args = new Bundle();
            args.putInt(MainFragment.ARG_FRAGMENT_ID, frameId);
            fragment.setArguments(args);
            mFragments[position] = fragment;
        }
        else {
            fragment = mFragments[position];
        }

        //Launch the frame
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.mainFrame, fragment).commit();

    }


    private void displaySettings() {

        SettingsFragment fragment = new SettingsFragment();

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.mainFrame, fragment).commit();

    }
    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
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


    public BinderFactory getReusableBinderFactory() {
        return reusableBinderFactory;
    }

/*
    private ViewBinder createViewBinder() {
        BinderFactory binderFactory = getReusableBinderFactory();
        return binderFactory.createViewBinder(this);
    }


    public void initializeContentView(int layoutId, Object presentationModel) {
        ViewBinder viewBinder = createViewBinder();
        View rootView = viewBinder.inflateAndBind(layoutId, presentationModel);
        setContentView(rootView);
    }*/


    /**
     * Fragment that appears in the "content_frame", shows a planet
     */
    /*public static class PlanetFragment extends Fragment {
        public static final String ARG_PLANET_NUMBER = "planet_number";

        public PlanetFragment() {
            // Empty constructor required for fragment subclasses
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_planet, container, false);
            int i = getArguments().getInt(ARG_PLANET_NUMBER);
            String planet = getResources().getStringArray(R.array.planets_array)[i];

            int imageId = getResources().getIdentifier(planet.toLowerCase(Locale.getDefault()),
                    "drawable", getActivity().getPackageName());
            ((ImageView) rootView.findViewById(R.id.image)).setImageResource(imageId);
            getActivity().setTitle(planet);
            return rootView;
        }
    }*/

    /* The click listner for ListView in the navigation drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Toast.makeText(view.getContext(), "click", Toast.LENGTH_LONG).show();
            selectItem(position);
        }
    }
}
