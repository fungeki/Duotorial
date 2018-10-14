package productions.ranuskin.meow.duotorial;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ImageView tabBrowse;
    private ImageView tabFeatured;
    private ImageView tabMyDuoFragment;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private Integer currentFragment;
    private FirebaseUser user;
    private boolean toasted;

    private DatabaseReference usersDatabase;


    private ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
        final String id = user.getUid();

        usersDatabase.child(id).
                addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if (!dataSnapshot.exists()) {
                            String userName = user.getDisplayName();
                            String email = user.getEmail();
                            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                            String lastLogin = timestamp.toString();
                            User mUser = new User(userName, email, 0, lastLogin);

                            usersDatabase.child(id).setValue(mUser);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

    }

    private void
    initialize() {
        usersDatabase = FirebaseDatabase.getInstance().getReference("users");
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        viewPagerChangeListener();
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        if (HttpIO.isOnline(MainActivity.this)) {
            new FeaturedTask().execute("&subcmd=featured");
        } else {
            AlertDialog.Builder builder =new AlertDialog.Builder(this);
            builder.setTitle("No internet Connection");
            builder.setMessage("Please turn on internet connection to continue");
            builder.setPositiveButton("connected", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    initialize();
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
        currentFragment = 1;
        tabBrowse = findViewById(R.id.ivBrowse);
        tabFeatured = findViewById(R.id.ivPopular);
        tabMyDuoFragment = findViewById(R.id.ivMyDuo);
        Intent intent = getIntent();
        if (intent.getStringExtra("Fragment_Num") != null) {
            switchToBrowse();
            currentFragment = Integer.parseInt(intent.getStringExtra("Fragment_Num"));
        }
        mViewPager.setCurrentItem(currentFragment);
        user = FirebaseAuth.getInstance().getCurrentUser();
        toasted = false;
        if (getIntent().getStringExtra("TOASTED") != null) {
            toasted = true;
        }
        if (user != null) {
            // User is signed in
            if (!toasted) {
                Toast.makeText(this, "Welcome " + user.getDisplayName(), Toast.LENGTH_SHORT).show();
            }
        } else {
            // No user is signed in
            Intent backIntent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(backIntent);
        }
    }

    private void viewPagerChangeListener() {
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


            }

            @Override
            public void onPageSelected(int position) {


                switch (position) {
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
        tabBrowse.animate().alpha(0f).setDuration(500);
        //tabBrowse.setImageResource(R.drawable.ic_browse_duo_gray);
        tabFeatured.animate().alpha(0f).setDuration(500);
        //tabFeatured.setImageResource(R.drawable.ic_popular_duo_gray);
        tabMyDuoFragment.setAlpha(0f);
        tabMyDuoFragment.setImageResource(R.drawable.ic_my_duo_green);
        tabMyDuoFragment.animate().alpha(1f).setDuration(500);
    }

    private void switchToFeatured() {
        tabBrowse.animate().alpha(0f).setDuration(500);
        // tabBrowse.setImageResource(R.drawable.ic_browse_duo_gray);
        tabFeatured.setAlpha(0f);

        tabFeatured.setImageResource(R.drawable.ic_popular_duo_green);
        tabFeatured.animate().alpha(1f).setDuration(500);
        tabMyDuoFragment.animate().alpha(0f).setDuration(500);
        //tabMyDuoFragment.setImageResource(R.drawable.ic_my_duo_gray);
    }

    private void switchToBrowse() {
        tabBrowse.setAlpha(0f);
        tabBrowse.setImageResource(R.drawable.ic_browse_duo_green);
        tabBrowse.animate().alpha(1f).setDuration(500);

        tabFeatured.animate().alpha(0f).setDuration(500);
        // tabFeatured.setImageResource(R.drawable.ic_popular_duo_gray);
        tabMyDuoFragment.animate().alpha(0f).setDuration(500);
        //tabMyDuoFragment.setImageResource(R.drawable.ic_my_duo_gray);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Are you sure you want to exit?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            finishAffinity();
                            System.exit(0);


                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();

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
                new FeaturedTask().execute("&subcmd=search&q=" + query);
                InputMethodManager inputManager = (InputMethodManager) getApplicationContext().
                        getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(searchView.getWindowToken(), 0);
                mViewPager.setCurrentItem(1);
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

        switch (id) {
            case R.id.nav_home:
                mViewPager.setCurrentItem(1);
                break;
            case R.id.nav_random:
                new RandomTask(this).execute();
                break;
            case R.id.nav_categories:
                switchToBrowse();
                mViewPager.setCurrentItem(0);
                break;
            case R.id.nav_bookmarks:
                Intent intent = new Intent(MainActivity.this, BookmarkActivity.class);
                startActivity(intent) ;
                break;
            case R.id.nav_about:
                Intent aboutIntent = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(aboutIntent);
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void switchTabs(View view) {
        switch (view.getId()) {
            case R.id.ivBrowse:
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


    private class FeaturedTask extends AsyncTask<String, Void, String> {

        private DatabaseReference mDatabase;

        @Override
        protected String doInBackground(String... strings) {

            try {
                return HttpIO.getJson("https://www.wikihow.com/api.php?action=app&format=json&num=50" + strings[0]);
            } catch (IOException e) {
                Toast.makeText(MainActivity.this, "error! no connection", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                mDatabase = FirebaseDatabase.getInstance().getReference();
                JSONObject root = new JSONObject(s).getJSONObject("app");
                final JSONArray articles = root.getJSONArray("articles");


                ArrayList<DuoIntro> featured = new ArrayList<>();

                for (int i = 0; i < 50; i++) {

                    JSONObject introObject = articles.getJSONObject(i);
                    String title = introObject.getString("title");
                    title = "How to " + title;
                    String description = introObject.getString("abstract");
                    description = Jsoup.parse(description).text();
                    String imageURL = introObject.getJSONObject("image").getString("url");
                    featured.add(new DuoIntro(title, description, imageURL));

                }


                final ListView lvFeatured = findViewById(R.id.lvFeatured);
                IntroAdapter adapter = new IntroAdapter(featured, MainActivity.this);
                lvFeatured.setAdapter(adapter);

                lvFeatured.setClickable(false);
                lvFeatured.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        try {
                            JSONObject titleFetch = articles.getJSONObject(position);
                            final String getTitle = titleFetch.getString("title");
                            String getDescription = titleFetch.getString("abstract");
                            getDescription = Jsoup.parse(getDescription).text();
                            String getImage = titleFetch.getJSONObject("image").getString("url");
                            final Intent intent = new Intent(MainActivity.this, DuotorialActivity.class);
                            intent.putExtra("TITLE", getTitle);
                            intent.putExtra("DESCRIPTION", getDescription);
                            intent.putExtra("IMAGE", getImage);

                            addTheDuotorialToDatabase(getTitle,getImage);
//                            addTheDuotorialToDatabase(getTitle, getImage);
                            startActivity(intent);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void addTheDuotorialToDatabase(final String getTitle, final String getImage) {

            final DatabaseReference userDuoAmount = FirebaseDatabase.getInstance().getReference().child("users")
                    .child(user.getUid()).child("duotorialAmount");
            userDuoAmount.runTransaction(new Transaction.Handler() {
                @Override
                public Transaction.Result doTransaction(final MutableData mutableData) {
                    Integer currentValue = mutableData.getValue(Integer.class);
                    /*if (currentValue == null) {
                        mutableData.setValue(0);
                    } else {
                        currentValue++;
                        mutableData.setValue(currentValue);
                    }*/


                    final DatabaseReference duoRef = FirebaseDatabase.getInstance().getReference().child("users")
                            .child(user.getUid()).child("history");
                    if (currentValue >= 50) {
                        currentValue %= 50;
                        duoRef.child(currentValue.toString()).setValue(null);
                    }
                    final Integer finalCurrentValue = currentValue;
                    duoRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Integer i = 0;
                            boolean flag = false;

                            while (dataSnapshot.child(i.toString()).hasChildren()) {
                                if (dataSnapshot.child(i.toString()).hasChild(getTitle)) {
                                    duoRef.child(i.toString()).child(getTitle).setValue(getImage);
                                    //duoRef.child(i.toString()).child(getImage).setValue(1);
                                    flag = true;
                                    break;
                                }
                                i++;

                            }
                            if (!flag) {
                                duoRef.child(finalCurrentValue.toString()).child(getTitle).setValue(getImage);
                                // duoRef.child(finalCurrentValue.toString()).child(getImage).setValue(1);
                                userDuoAmount.setValue(finalCurrentValue + 1);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


                    return Transaction.success(mutableData);
                }

                @Override
                public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {

                }
            });

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
            switch (position) {
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
//unused code for future debugging
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
//from ver0.2 tab switch
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