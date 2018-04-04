package productions.ranuskin.meow.duotorial;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.IOException;

public class DuotorialActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duotorial);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Intent intent = getIntent();
        String title = intent.getStringExtra("TITLE");
        String modifiedTitle= title.replace("_"," ");
        new DuotorialTask().execute(modifiedTitle);

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
        getMenuInflater().inflate(R.menu.duotorial, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


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

    class DuotorialTask extends AsyncTask<String,Void,String> {
       private SectionsPagerAdapterDuotorial mSectionsPagerAdapter;
       private ViewPager mViewPager;
       private ImageView ivStageImage;
       private TextView tvTitle;
       private TextView tvDescription;
       private TextView tvBackground;
       private int halfScreen;
        private int step;
        @Override
        protected String doInBackground(String... strings) {
            try {
                return HttpIO.getJson("https://www.wikihow.com/api.php?action=app&format=json&num=50&subcmd=article&name="+strings[0]);
            } catch (IOException e) {
                Toast.makeText(DuotorialActivity.this, "error", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {

            mSectionsPagerAdapter = new SectionsPagerAdapterDuotorial(getSupportFragmentManager());
            mViewPager = (ViewPager) findViewById(R.id.duotorial_container);
            mViewPager.setAdapter(mSectionsPagerAdapter);

                try {
                    initialize();
                    tvTitle.animate().alpha(1f).setDuration(500);
                    JSONObject root = new JSONObject(s).getJSONObject("app");
                    step=0;
                    JSONObject introImage = root.getJSONObject("image");
                    String introImageURL = introImage.getString("url");
                    String title = root.getString("title");
                    title = "How to " + title;
                    String description = root.getString("abstract");

                    tvTitle.setText(title);
                    tvDescription.setText(description);
                    Picasso.with(DuotorialActivity.this).load(introImageURL).into(ivStageImage);
                    tvTitle.animate().alpha(1f).setDuration(500);
                    tvDescription.animate().alpha(1f).setDuration(500);
                    ivStageImage.animate().alpha(1f).setDuration(500);


                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    }

                    @Override
                    public void onPageSelected(int position) {


                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                });
                /*final JSONArray articles = root.getJSONArray("articles");


                ArrayList<DuoIntro> featured = new ArrayList<>();

                for (int i = 0; i <50 ; i++) {

                    JSONObject introObject = articles.getJSONObject(i);
                    String title = introObject.getString("fulltitle");
                    String description = introObject.getString("abstract");
                    String imageURL = introObject.getJSONObject("image").getString("url");
                    featured.add(new DuoIntro(title,description,imageURL));

                }/*
                /*//*for (FeaturedTitles title : titles) {
                    fetch.add(title.getTitle());
                }*/



                /*for/* (FeaturedTitles title : titles) {
                Toast.makeText(MainActivity.this, titles.toString(), Toast.LENGTH_SHORT).show();
*/
        }

        private void initialize() {

            tvTitle =findViewById(R.id.tvTitle);
            tvDescription =findViewById(R.id.tvDescription);
            ivStageImage=findViewById(R.id.ivStepImage);
            tvBackground = findViewById(R.id.tvBackground);
            Configuration configuration = DuotorialActivity.this.getResources().getConfiguration();
            halfScreen = configuration.screenWidthDp/2;


        }

        public class SectionsPagerAdapterDuotorial extends FragmentPagerAdapter {


            public SectionsPagerAdapterDuotorial(FragmentManager fm) {
                super(fm);

            }
            @Override
            public Fragment getItem(int position) {
                // getItem is called to instantiate the fragment for the given page.
                // Return a PlaceholderFragment (defined as a static inner class below).

                // comm.response(position);
                switch (position){
                    case 0:
                        return new StepsFragment();
                    case 1:
                        return new ClassroomFragment();

                }
                return null;
            }
            @Override
            public int getCount() {
                // Show 3 total pages.
                return 2;
            }

        }
        /*private void openDialog() {
            final Dialog dialog = new Dialog(DuotorialActivity.this, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
            View dialogView = getLayoutInflater().inflate(R.layout.dialog_list_duotorial, null);
            ListView lvSteps = dialogView.findViewById(R.id.lvDialog);
            lvSteps.setAdapter(adapter);
            FloatingActionButton btnClose = dialogView.findViewById(R.id.fabExit);
            btnClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            lvSteps.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    current = i;
                    updateStage();
                    dialog.dismiss();
                }
            });
            dialog.setContentView(dialogView);
            dialog.show();
            dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {
                    updateStage();
                }
            });
        }*/
    }
}