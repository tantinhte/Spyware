package comyware.example.duongtan.spyware;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;

import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import Fragment.CallHistory;
import Fragment.Contact;
import Fragment.Default;
import Fragment.Message;
import Fragment.MessageTabDraft;
import Fragment.MessageTabInbox;
import Fragment.MessageTabSend;
import Fragment.Recorder;
import Fragment.image;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        CallHistory.OnCallFragmentInteractionListener,
        Contact.OnContactFragmentInteractionListener,
        image.OnImageFragmentInteractionListener,
        Message.OnMessageFragmentInteractionListener,
        Recorder.OnRecorderFragmentInteractionListener,
        Default.OnDefaultFragmentInteractionListener,
        MessageTabDraft.OnTabDraftFragmentInteractionListener,
        MessageTabSend.OnTabSendFragmentInteractionListener,
        MessageTabInbox.OnTabInboxFragmentInteractionListener
{

    FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Fragment fragment = new Default();
        fragmentManager.beginTransaction().add(R.id.fragment_container,fragment).commit();
        CheckPermission();
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
        String title = "";
        Fragment fragment = new Default();

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_image) {
            fragment = new image();
            title = "Image";
        }else if (id == R.id.nav_call) {
            fragment = new CallHistory();
            title = "Call History";
        } else if (id == R.id.nav_message) {
            fragment = new Message();
            title = "Message";
        } else if (id == R.id.nav_recorder) {
            fragment = new Recorder();
            title = "Recorder";
        } else if (id == R.id.nav_contact) {
            fragment = new Contact();
            title = "Contact";
        }

        fragmentManager.beginTransaction().replace(R.id.fragment_container,fragment).commit();
        getSupportActionBar().setTitle(title);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }
    @Override
    public void OnCallFragmentInteractionListener(Uri uri){

    }
    @Override
    public void OnContactFragmentInteractionListener(Uri uri){

    }
    @Override
    public void OnDefaultFragmentInteractionListener(Uri uri){

    }
    @Override
    public void OnImageFragmentInteractionListener(Uri uri){

    }
    @Override
    public void OnMessageFragmentInteractionListener(Uri uri){

    }
    @Override
    public void OnRecorderFragmentInteractionListener(Uri uri){

    }

    @Override
    public void OnTabSendFragmentInteractionListener(Uri uri){

    }
    @Override
    public void OnTabInboxFragmentInteractionListener(Uri uri){

    }
    @Override
    public void OnTabDraftFragmentInteractionListener(Uri uri){

    }

    public void CheckPermission(){
        int MyVersion = Build.VERSION.SDK_INT;
        if (MyVersion > Build.VERSION_CODES.LOLLIPOP_MR1) {
            if (!checkIfAlreadyhavePermission()) {
                requestForSpecificPermission();
            } else {

            }
        }
    }

    private boolean checkIfAlreadyhavePermission() {
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.GET_ACCOUNTS);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void requestForSpecificPermission() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.READ_SMS,
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE}, 101);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 101:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //granted

                } else {
                    //not granted
                    Toast.makeText(this, "You don't Grant permission ", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
