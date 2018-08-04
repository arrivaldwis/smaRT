package id.smart.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import id.smart.R;
import id.smart.config.Config;
import id.smart.model.SlideshowModel;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener{

    private SliderLayout mSlider;
    private TextView tvDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mSlider = (SliderLayout)findViewById(R.id.slider);
        tvDescription = (TextView) findViewById(R.id.tvDescription);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        fab.setVisibility(View.GONE);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        loadSlideshow();
    }

    private void loadSlideshow() {
        Config.refSlideshow.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for (DataSnapshot ds:dataSnapshot.getChildren()) {
                    SlideshowModel model = ds.getValue(SlideshowModel.class);
                    TextSliderView textSliderView = new TextSliderView(MainActivity.this);
                    // initialize a SliderLayout
                    textSliderView
                            .description(model.getTitle())
                            .image(model.getImg_url())
                            .setScaleType(BaseSliderView.ScaleType.Fit)
                            .setOnSliderClickListener(MainActivity.this);

                    mSlider.addSlider(textSliderView);
                }

                mSlider.setDuration(4000);
                mSlider.addOnPageChangeListener(MainActivity.this);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });
    }

    @Override
    protected void onStop() {
        mSlider.stopAutoCycle();
        super.onStop();
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

    Intent i = new Intent();

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_find) {
            i = new Intent(MainActivity.this, FindActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_announcement) {
            i = new Intent(MainActivity.this, AnnouncementActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_critic) {
            Toast.makeText(this, "Dalam tahap pengembangan", Toast.LENGTH_SHORT).show();
            startActivity(i);
        } else if (id == R.id.nav_chat) {
            Toast.makeText(this, "Dalam tahap pengembangan", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_contact) {
            i = new Intent(MainActivity.this, ContactActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_add) {
            i = new Intent(MainActivity.this, AddActivity.class);
            i.putExtra("menu", "tambah");
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if(position==0) {
            tvDescription.setText("In participating the World Cup yesterday in Russia, our RT participated in a way to decorate the surrounding environment with the theme of the world cup. Because in our opinion a rare event like this is 4 years once and should be celebrated.");
        } else if (position == 1) {
            tvDescription.setText("In participating in the 2018 Asian Games event that took place in Indonesia, we as the people of Indonesia especially Jakarta feel very fortunate to be a host because this is a very rare event. For that we participate as in the picture above.");
        } else if (position == 2) {
            tvDescription.setText("The routine activity at our RT is taking turns \"ronda malam\" for night guard and security together also doing the \"kerja bakti\" on Sundays, this work aims to make a cleaner and healthier environment. While others such activities as mosquitoes fogging every 2 weeks and the contribution of cash for all citizens once a week.");
        } else if (position == 3) {
            tvDescription.setText("It is appropriate that we as citizens of Indonesia must have a high sense of nationalism to the nation and state. And we should participate in holding the Indonesian Independence Day on August 17th. There are many things that can be done, for example in the scope of our RT that we enliven them by means of traditional games to strengthen unity such as “Panjat Pinang” whose prize is at the top, “lomba balap karung”, “lomba tarik tambang”, “lomba makan kerupuk” without using hands, and many more.");
        }
    }

    @Override
    public void onPageSelected(int position) {
        if(position==0) {
            tvDescription.setText("In participating the World Cup yesterday in Russia, our RT participated in a way to decorate the surrounding environment with the theme of the world cup. Because in our opinion a rare event like this is 4 years once and should be celebrated.");
        } else if (position == 1) {
            tvDescription.setText("In participating in the 2018 Asian Games event that took place in Indonesia, we as the people of Indonesia especially Jakarta feel very fortunate to be a host because this is a very rare event. For that we participate as in the picture above.");
        } else if (position == 2) {
            tvDescription.setText("The routine activity at our RT is taking turns \"ronda malam\" for night guard and security together also doing the \"kerja bakti\" on Sundays, this work aims to make a cleaner and healthier environment. While others such activities as mosquitoes fogging every 2 weeks and the contribution of cash for all citizens once a week.");
        } else if (position == 3) {
            tvDescription.setText("It is appropriate that we as citizens of Indonesia must have a high sense of nationalism to the nation and state. And we should participate in holding the Indonesian Independence Day on August 17th. There are many things that can be done, for example in the scope of our RT that we enliven them by means of traditional games to strengthen unity such as “Panjat Pinang” whose prize is at the top, “lomba balap karung”, “lomba tarik tambang”, “lomba makan kerupuk” without using hands, and many more.");
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
