package productions.ranuskin.meow.duotorial;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ImageView tabBrowse;
    private ImageView tabFeatured;
    private ImageView tabMyDuoFragment;
    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();


    }

    private void initialize() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        tabBrowse= findViewById(R.id.ivBrowse);
        tabFeatured= findViewById(R.id.ivPopular);
        tabMyDuoFragment= findViewById(R.id.ivMyDuo);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        viewPagerChangeListener();
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mViewPager.setCurrentItem(1);
    }

    private void viewPagerChangeListener() {
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


            }

            @Override
            public void onPageSelected(int position) {


                switch (position){
                    case 0:
                        switchToBrowse();
                        break;
                    case 1:
                        switchToFeatured();
                        break;
                    case 2:
                        switchToMyDuo();
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {


            }
        });
    }

    private void switchToMyDuo() {
        tabBrowse.setImageResource(R.drawable.ic_browse_duo_gray);
        tabFeatured.setImageResource(R.drawable.ic_popular_duo_gray);
        tabMyDuoFragment.setAlpha(0f);
        tabMyDuoFragment.setImageResource(R.drawable.ic_my_duo_green);
        tabMyDuoFragment.animate().alpha(1f).setDuration(500);
    }

    private void switchToFeatured() {
        tabBrowse.setImageResource(R.drawable.ic_browse_duo_gray);
        tabFeatured.setAlpha(0f);
        tabFeatured.setImageResource(R.drawable.ic_popular_duo_green);
        tabFeatured.animate().alpha(1f).setDuration(500);
        tabMyDuoFragment.setImageResource(R.drawable.ic_my_duo_gray);
    }

    private void switchToBrowse() {
        tabBrowse.setAlpha(0f);
        tabBrowse.setImageResource(R.drawable.ic_browse_duo_green);
        tabBrowse.animate().alpha(1f).setDuration(500);
        tabFeatured.setImageResource(R.drawable.ic_popular_duo_gray);
        tabMyDuoFragment.setImageResource(R.drawable.ic_my_duo_gray);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void toBrowse(View view) {
            switchToBrowse();
            mViewPager.setCurrentItem(0);
    }

    public void toFeatured(View view) {
        switchToFeatured();
        mViewPager.setCurrentItem(1);
    }

    public void toMyDuo(View view) {
        switchToMyDuo();
        mViewPager.setCurrentItem(2);
    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {


        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);

        }
        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).

            // comm.response(position);
            switch (position){
                case 0:
                    return new BrowseFragment();
                case 1:
                    return new FeaturedFragment();
                case 2:
                    return new MyDuoFragment();

            }
            return null;
        }
        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

    }


}
