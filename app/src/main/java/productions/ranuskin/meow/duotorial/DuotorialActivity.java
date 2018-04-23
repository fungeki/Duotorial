package productions.ranuskin.meow.duotorial;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Rect;
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
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.chrisbanes.photoview.PhotoView;
import com.github.chrisbanes.photoview.PhotoViewAttacher;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

        switch (id) {
            case R.id.nav_home:
                goToMainFragment(1);
                break;
            case R.id.nav_random:
                new RandomTask(this).execute();
                break;
           case R.id.nav_categories:
               goToMainFragment(0);
               break;
           /* case R.id.nav_manage:

                break;
            case R.id.nav_share:

                break;
            case R.id.nav_send:

                break;*/
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void goToMainFragment(Integer fragmentNumber) {
        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra("Fragment_Num",fragmentNumber.toString());
        intent.putExtra("TOASTED","true");
        startActivity(intent);
    }

    private class DuotorialTask extends AsyncTask<String,Void,Document> {
       private TextView tvStepsBackground;
       private TextView tvClassroomBackground;
       private SectionsPagerAdapterDuotorial mSectionsPagerAdapter;
       private ViewPager mViewPager;
       private ImageView ivStageImage;
       private TextView tvTitle;
       private TextView tvDescription;
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
       private ImageView ivExpanded;
       private TextView tvNext;
        private Animator mCurrentAnimator;
        private int mShortAnimationDuration;

        @Override
        protected Document doInBackground(String... strings) {
            introTitle = strings[0];
            introImageURL=strings[1];
            introDescription=strings[2];
            try {

                return Jsoup.connect("https://www.wikihow.com/"+ introTitle).timeout(9000).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Document document) {




            try {
                    initialize(document);
                    jsoupCoding(document);
                    pbLoad.setVisibility(View.GONE);
                    mViewPagerListener();
                    update();
                    next();
                    stepListListener();
                    tabListener();
                    final PhotoView pvExpanded = findViewById(R.id.ivExpanded);

                    ivStageImage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {



                            Picasso.with(DuotorialActivity.this).load(stages.get(currentStep).getImageURL()).into(pvExpanded);
                            pvExpanded.setVisibility(View.VISIBLE);
                           // ivExpanded.setVisibility(View.VISIBLE);


                        }
                    });
                    pvExpanded.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            pvExpanded.setVisibility(View.GONE);
                        }
                    });
                    /*ivExpanded.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ivExpanded.setVisibility(View.GONE);
                        }
                    });*/



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

        private void tabListener() {
            tvStepsBackground.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mViewPager.setCurrentItem(0);
                }
            });
            tvClassroomBackground.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mViewPager.setCurrentItem(1);
                }
            });
        }

        private void jsoupCoding(Document document) {
            Elements body = document.select("div#bodycontents");
            int stepNumber = 0;
            ArrayList<String> imgURL = new ArrayList<>();
            // for (Element element : body.select(".section_text")) {


            try {
                for (Element element : body.select(".section.steps")) {



                    String title = element.select("span.mw-headline").text();

                    String boldText="";
                    String imageURL="";
                    String stageDescription="";

                    for (Element step : element.select("li.hasimage")) {

                        imageURL = step.select(".content-spacer img").first().attr("data-src");
                        boldText = body.select(".step b").get(stepNumber).text();
                        stageDescription = body.select(".step").get(stepNumber).text();

                        dialogData.add(new DuotorialDialogPreview(boldText, imageURL));
                        stepNumber++;
                        stages.add(new DuotorialStep(stepNumber, title, stageDescription, imageURL));

                    }


                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void stepListListener() {
            ivStepsList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Dialog dialog = new Dialog(DuotorialActivity.this, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
                    View dialogView = getLayoutInflater().inflate(R.layout.dialog_list_duotorial, null);
                    ListView lvSteps = dialogView.findViewById(R.id.lvDialog);
                    DialogAdapter adapter = new DialogAdapter(dialogData,DuotorialActivity.this,currentStep);
                    lvSteps.setAdapter(adapter);
                    FloatingActionButton btnClose = dialogView.findViewById(R.id.fabExit);
                    if(currentStep>1) {
                        int h1 = lvSteps.getHeight();
                        int h2 = 300;

                        lvSteps.setSelectionFromTop(currentStep, h1/2+450);
                    }
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
        }

        private void mViewPagerListener() {
            mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {

                    if (position == 1){
                        toClassroom();
                    }else{
                        toSteps();
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        }

        private void next() {
            tvNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (currentStep<stages.size()-1){
                        currentStep++;
                        update();
                    }else {

                        tvNext.setBackgroundResource(android.R.color.transparent);
                       // Toast.makeText(DuotorialActivity.this, "last stage!", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }

        private void update() {
            tvTitle.setText(stages.get(currentStep).getTitle());
            tvDescription.setText(stages.get(currentStep).getDescription());
            if (currentStep<stages.size()-1){
                tvNext.setText(" To Step "+(currentStep+1)+ " ");
                tvNext.setBackgroundResource(R.drawable.rectangle);
            }else {
                tvNext.setText("Youre at the\n Last Step!");
                tvNext.setBackgroundResource(android.R.color.transparent);
            }


            Picasso.with(DuotorialActivity.this).load(stages.get(currentStep).getImageURL()).into(ivStageImage);
            pbLoad.setVisibility(View.GONE);
            tvTitle.animate().alpha(1f).setDuration(500);
            tvDescription.animate().alpha(1f).setDuration(500);
            ivStageImage.animate().alpha(1f).setDuration(500);
        }

        private void initialize(Document doc) {


            mSectionsPagerAdapter = new SectionsPagerAdapterDuotorial(getSupportFragmentManager());
            mViewPager = (ViewPager) findViewById(R.id.duotorial_container);
            mViewPager.setAdapter(mSectionsPagerAdapter);
            currentStep=0;
            tvTitle =findViewById(R.id.tvTitle);
            tvDescription =findViewById(R.id.tvDescription);
            ivStageImage=findViewById(R.id.ivStepImage);
            if (introDescription.length()==0){
                //introDescription = doc.select("div#bodycontents").select(".section p").first().text();
                Elements body = doc.select("div#intro>p");
                Element description = body.select("p").get(1);
                introDescription  =description.text();
            }
            introToDuotorial = new DuotorialStep(0,introTitle,introDescription,introImageURL);
            forIntro = new ArrayList<>();
            pbLoad=findViewById(R.id.pbLoad);
            tvNext=findViewById(R.id.tvNextStep);
            stages = new ArrayList<>();
            stages.add(introToDuotorial);
            dialogData=new ArrayList<>();
            tvDescription.setMovementMethod(new ScrollingMovementMethod());
            ivStepsList = findViewById(R.id.ivStepsList);
            tvClassroomBackground = findViewById(R.id.tvClassBackground);
            tvStepsBackground = findViewById(R.id.tvStepsBackground);



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
        private void toClassroom(){
            tvStepsBackground.animate().alpha(0f).setDuration(500);
            tvClassroomBackground.animate().alpha(1f).setDuration(500);
        }

        private void toSteps(){
            tvClassroomBackground.animate().alpha(0f).setDuration(500);
            tvStepsBackground.animate().alpha(1f).setDuration(500);
        }



    }

}
//unused code for debugging
//older versions of the App:

/*for (Element img : body.select(".content-spacer img")) {
                            if (img.attr("data-src").length()>10){
                                imgURL.add(img.attr("data-src"));

                            }
                        }
                         }}


                        String boldText = body.select(".step b").get(stepNumber).text();

                        *//*String stageHeadline = body.select(".mw-headline").get(stepNumber).text();*//*


                        stepNumber++;

                        String stageDescription = body.select(".step").get(stepNumber).text();
                        dialogData.add(new DuotorialDialogPreview(boldText,imgURL.get(stepNumber)));
                        stages.add(new DuotorialStep(stepNumber,"how to "+introTitle,stageDescription,imgURL.get(stepNumber)));

*/

/*if (stepNumber > 1) {//so stages wouldnt cancel itself at the first iterate
                                    if (stageDescription.equals(stages.get(1).getDescription())) {//stopping parameter for iter
                                        throw new Exception("end of data");
                                    }
                                }*/
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
