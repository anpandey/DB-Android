package edu.grinnell.appdev.grinnelldirectory.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import java.io.Serializable;

import edu.grinnell.appdev.grinnelldirectory.R;
import edu.grinnell.appdev.grinnelldirectory.adapters.SearchPagerAdapter;


public class SearchPagerActivity extends AppCompatActivity implements Serializable {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_pager_activity);

        ViewPager searchPager = (ViewPager) findViewById(R.id.pager);
        searchPager.setAdapter(new SearchPagerAdapter(getSupportFragmentManager()));

        setAnimation();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            // send user to the login screen
            Intent intent = new Intent(this, LoginActivity.class);
            // clear the back stack
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.putExtra(getString(R.string.calling_class), SearchPagerActivity.class.toString());
            startActivity(intent);
            return true;
        } else if (id == R.id.action_about) {
            // pop up a dialog fragment that has a description of the app and how to use it.
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    public void setAnimation() {
        try {
            String callingClass = getIntent().getExtras().getString(getString(R.string.calling_class));
            if (callingClass != null) {
                if (callingClass.contains(getString(R.string.login_activity))) {
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
