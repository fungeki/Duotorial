package productions.ranuskin.meow.duotorial;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
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

       private ImageView ivStageImage;
       private TextView tvTitle;
       private TextView tvDescription;
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
                try {
                    tvTitle =findViewById(R.id.tvTitle);
                    tvDescription =findViewById(R.id.tvDescription);
                    ivStageImage=findViewById(R.id.ivStepImage);

                    tvTitle.animate().alpha(1f).setDuration(500);
                    JSONObject root = new JSONObject(s).getJSONObject("app");
                    step=0;
                    JSONObject introImage = root.getJSONObject("image");
                    String introImageURL = introImage.getString("url");
                    String title = root.getString("fulltitle");
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
    }
}
