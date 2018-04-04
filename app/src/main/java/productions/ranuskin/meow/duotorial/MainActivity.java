package productions.ranuskin.meow.duotorial;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.hardware.input.InputManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.view.MenuInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

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
        /*tabBrowse= findViewById(R.id.ivBrowse);
        tabFeatured= findViewById(R.id.ivPopular);
        tabMyDuoFragment= findViewById(R.id.ivMyDuo);*/
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        viewPagerChangeListener();
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        new FeaturedTask().execute("&subcmd=featured");
        tabBrowse=findViewById(R.id.ivBrowse);
        tabFeatured=findViewById(R.id.ivPopular);
        tabMyDuoFragment = findViewById(R.id.ivMyDuo);
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
        final SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                new FeaturedTask().execute("&subcmd=search&q="+query);
                InputMethodManager inputManager = (InputMethodManager) getApplicationContext().
                        getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(searchView.getWindowToken(), 0);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
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

    /*public void toBrowse(View view) {
            tabsFragment.switchToBrowse();
            mViewPager.setCurrentItem(0);
    }

    public void toFeatured(View view) {
        tabsFragment.switchToFeatured();
        mViewPager.setCurrentItem(1);
    }

    public void toMyDuo(View view) {
        tabsFragment.switchToMyDuo();
        mViewPager.setCurrentItem(2);
    }
*/

    public void switchTabs(View view) {
        switch (view.getId()){
            case
                R.id.ivBrowse:
                switchToBrowse();
                mViewPager.setCurrentItem(0);
                break;
            case R.id.ivPopular:
                switchToFeatured();
                mViewPager.setCurrentItem(1);
                break;
            case R.id.ivMyDuo:
                switchToMyDuo();
                mViewPager.setCurrentItem(2);
                break;
        }
    }

    class FeaturedTask extends AsyncTask<String,Void,String> {


        @Override
        protected String doInBackground(String... strings) {
            try {
                return HttpIO.getJson("https://www.wikihow.com/api.php?action=app&format=json&num=50"+strings[0]);
            } catch (IOException e) {
                Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONObject root = new JSONObject(s).getJSONObject("app");
                final JSONArray articles = root.getJSONArray("articles");


                ArrayList<DuoIntro> featured = new ArrayList<>();

                for (int i = 0; i <50 ; i++) {

                    JSONObject introObject = articles.getJSONObject(i);
                    String title = introObject.getString("title");
                    title= "How to " + title;
                    String description = introObject.getString("abstract");
                    String imageURL = introObject.getJSONObject("image").getString("url");
                    featured.add(new DuoIntro(title,description,imageURL));

                }


                final ListView lvFeatured = findViewById(R.id.lvFeatured);
                IntroAdapter adapter = new IntroAdapter(featured,MainActivity.this);
                lvFeatured.setAdapter(adapter);
                lvFeatured.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        try {
                            JSONObject titleFetch = articles.getJSONObject(position);
                            String getTitle = titleFetch.getString("title");
                            Intent intent = new Intent(MainActivity.this,DuotorialActivity.class);
                            intent.putExtra("TITLE",getTitle);
                            startActivity(intent);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                /*lvFeatured.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        try {
                            JSONObject titleFetch = articles.getJSONObject(position);
                            String getTitle = titleFetch.getString("title");
                            Intent intent = new Intent(MainActivity.this,DuotorialActivity.class);
                            intent.putExtra("TITLE",getTitle);
                            startActivity(intent);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }*/

                    /*@Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });*/
                /*//*for (FeaturedTitles title : titles) {
                    fetch.add(title.getTitle());
                }*/



                /*for/* (FeaturedTitles title : titles) {
                Toast.makeText(MainActivity.this, titles.toString(), Toast.LENGTH_SHORT).show();
*/
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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
