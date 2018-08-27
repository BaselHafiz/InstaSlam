package com.bmacode17.instaslam.activities;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import android.widget.Toast;

import com.bmacode17.instaslam.R;
import com.bmacode17.instaslam.adapters.GalaryAdapter;
import com.bmacode17.instaslam.fragments.FeedActivityFragment;
import com.bmacode17.instaslam.fragments.HomeFragment;
import com.bmacode17.instaslam.fragments.ProfileFragment;
import com.bmacode17.instaslam.fragments.SearchFragment;
import com.bmacode17.instaslam.models.InstaGalary;
import com.bmacode17.instaslam.services.DataService;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    
    private final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;
    private static final String TAG = MainActivity.class.getSimpleName();
    public ArrayList<InstaGalary> myItems;
    RecyclerView recyclerView;

    private static MainActivity mainActivity;

    public static MainActivity getMainActivity() {
        return mainActivity;
    }

    private static void setMainActivity(MainActivity mainActivity) {
        MainActivity.mainActivity = mainActivity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainActivity.setMainActivity(this);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_instaGalary);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setImageDrawable(ContextCompat.getDrawable(getBaseContext(),R.drawable.new_post_btn));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              /*  Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                Intent intent = new Intent(getApplicationContext(),MediaActivity.class);
                startActivity(intent);
            }
        });

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {

            retrieveImages();
        } else {
            checkReadExternalStoragePermission();
        }
    }

    private void checkReadExternalStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {

                new AlertDialog.Builder(this)
                        .setTitle("Storage Permission Needed")
                        .setMessage("This app needs the storage permission, please accept to use storage functionality")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(MainActivity.this,
                                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                        MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                            }
                        })
                        .create()
                        .show();
            } else {
                
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    retrieveImages();
                } else {
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
        }
    }

    public ArrayList<InstaGalary> retrieveImages(){

        myItems = new ArrayList<>();
        String [] projection = new String[] { MediaStore.Images.Media.DISPLAY_NAME,MediaStore.Images.Media.DATA };
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor = getApplicationContext().getContentResolver()
                .query(uri ,projection,null, null,null);
        if(cursor != null){
            Log.d(TAG, "retrieveImages: Start Retrieving !!!");
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToPosition(i);
                String imageName = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME));
                String imagePath = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                myItems.add(new InstaGalary(imageName,imagePath));
                Log.d(TAG, "retrieveImages: " + imageName + " , " + imagePath);
            }
            cursor.close();
        }
//        return myItems;
        return DataService.getInstance().getGalaryImages();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent = new Intent(getApplicationContext(),SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    return HomeFragment.newInstance();
                case 1:
                    return SearchFragment.newInstance();
                case 2:
                    return FeedActivityFragment.newInstance();
                default:
                    return ProfileFragment.newInstance();
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 4;
        }
    }
}
