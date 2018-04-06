package productions.ranuskin.meow.duotorial;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

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
        String introImage = intent.getStringExtra("IMAGE");
        String description = intent.getStringExtra("DESCRIPTION");
        new DuotorialTask().execute(title,introImage,description);

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

        if (id == R.id.nav_random) {
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

    class DuotorialTask extends AsyncTask<String,Void,Document> {
       private SectionsPagerAdapterDuotorial mSectionsPagerAdapter;
       private ViewPager mViewPager;
       private ImageView ivStageImage;
       private TextView tvTitle;
       private TextView tvDescription;
       private TextView tvBackground;
       private int currentStep;
       private ArrayList<DuotorialStep> forIntro;
       private ArrayList<DuotorialDialogPreview> dialogData;
       private DuotorialStep introToDuotorial;
       private ArrayList<DuotorialStep> stages;
       private ImageView ivStepsList;
       private String introTitle;
       private String introDescription;
       private String introImageURL;
       private ProgressBar pbLoad;
       private FloatingActionButton fabNext;


        @Override
        protected Document doInBackground(String... strings) {
            introTitle = strings[0];
            introImageURL=strings[1];
            introDescription=strings[2];
            try {

                return Jsoup.connect("https://www.wikihow.com/"+ introTitle).timeout(6000).get();
            } catch (IOException e) {
                Toast.makeText(DuotorialActivity.this, "error", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Document document) {

            mSectionsPagerAdapter = new SectionsPagerAdapterDuotorial(getSupportFragmentManager());
            mViewPager = (ViewPager) findViewById(R.id.duotorial_container);
            mViewPager.setAdapter(mSectionsPagerAdapter);

                try {
                    initialize();




                    Elements body = document.select("div#bodycontents");
                    int stepNumber = 0;
                    ArrayList<String> imgURL = new ArrayList<>();
                    for (Element element : body.select(".section_text")) {


                        for (Element img : body.select(".content-spacer img")) {
                            if (img.attr("data-src").length()>10){
                                imgURL.add(img.attr("data-src"));

                            }
                        }

                        String boldText = body.select(".step b").get(stepNumber).text();

                        /*String stageHeadline = body.select(".mw-headline").get(stepNumber).text();*/


                        stepNumber++;


                        String stageDescription = body.select(".step").get(stepNumber).text();
                        dialogData.add(new DuotorialDialogPreview(boldText,imgURL.get(stepNumber)));
                        stages.add(new DuotorialStep(stepNumber,introTitle,stageDescription,imgURL.get(stepNumber)));




                            }

                    pbLoad.setVisibility(View.GONE);

                    update();
                    next();
                    ivStepsList.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final Dialog dialog = new Dialog(DuotorialActivity.this, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
                            View dialogView = getLayoutInflater().inflate(R.layout.dialog_list_duotorial, null);
                            ListView lvSteps = dialogView.findViewById(R.id.lvDialog);
                            DialogAdapter adapter = new DialogAdapter(dialogData,DuotorialActivity.this);
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
                                    currentStep = i;
                                    dialog.dismiss();
                                }
                            });
                            dialog.setContentView(dialogView);
                            dialog.show();
                            dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                @Override
                                public void onDismiss(DialogInterface dialogInterface) {
                                    update();
                                }
                            });
                        }
                    });





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

        }

        private void next() {
            fabNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (currentStep<stages.size()-1){
                        currentStep++;
                        update();
                    }else {
                        Toast.makeText(DuotorialActivity.this, "last stage!", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }

        private void update() {
            tvTitle.setText(stages.get(currentStep).getTitle());
            tvDescription.setText(stages.get(currentStep).getDescription());
            Picasso.with(DuotorialActivity.this).load(stages.get(currentStep).getImageURL()).into(ivStageImage);
            pbLoad.setVisibility(View.GONE);
            tvTitle.animate().alpha(1f).setDuration(500);
            tvDescription.animate().alpha(1f).setDuration(500);
            ivStageImage.animate().alpha(1f).setDuration(500);
        }

        private void initialize() {

            currentStep=0;
            tvTitle =findViewById(R.id.tvTitle);
            tvDescription =findViewById(R.id.tvDescription);
            ivStageImage=findViewById(R.id.ivStepImage);
            tvBackground = findViewById(R.id.tvBackground);
            introToDuotorial = new DuotorialStep(0,introTitle,introDescription,introImageURL);
            forIntro = new ArrayList<>();
            pbLoad=findViewById(R.id.pbLoad);
            fabNext=findViewById(R.id.fabNext);
            stages = new ArrayList<>();
            stages.add(introToDuotorial);
            dialogData=new ArrayList<>();
            tvDescription.setMovementMethod(new ScrollingMovementMethod());
            ivStepsList = findViewById(R.id.ivStepsList);
            if (introDescription.length()>100){
            dialogData.add(new DuotorialDialogPreview(introDescription.substring(0,100)+"...",introImageURL));
            } else{
                dialogData.add(new DuotorialDialogPreview(introDescription ,introImageURL));
            }
            /*stages.add(new DuotorialStage(forIntro,introTitle));*/

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

    }
}
//unused code for debugging
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
                /*String imageURL = body.select(".whcdn").get(stepNumber).attr("data-src");*/
                                /*for (int i = 0; i < 2; i++) {
                                    if (body.select("img").get(stepNumber+i).attr("data-src").length()>10){
                                        imageURL=body.select("img").get(stepNumber+i).attr("data-src");
                                    }
                                }*/
                                /*for (Element img : body.select("img")) {
                                    if (img.attr("data-src").length()>10){
                                        imageURL=img.attr("data-src");
                                    }

                                }*/
                                /*                    JSONObject root = new JSONObject(s).getJSONObject("app");
                    JSONObject introImage = root.getJSONObject("image");
                    String introImageURL = introImage.getString("url");
                    String title = root.getString("title");
                    title = "How to " + title;
                    String description = root.getString("abstract");*/
/*
                    tvTitle.setText(title);
                    tvDescription.setText(description);
                    Picasso.with(DuotorialActivity.this).load(introImageURL).into(ivStageImage);*/
