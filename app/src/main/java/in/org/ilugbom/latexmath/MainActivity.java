package in.org.ilugbom.latexmath;

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
import android.webkit.*;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private EditText e;
    private WebView w;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        w = (WebView) findViewById(R.id.webview);
        WebViewRenderer.prepareWebview(w);
        e = (EditText) findViewById(R.id.edit);


        final Button showButton = (Button) findViewById(R.id.buttonShow);
        showButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {   show();
            }
        });

        final Button BackSlashButton = (Button) findViewById(R.id.buttonBackslah);
        BackSlashButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {   Push("\\");
            }
        });

        final Button plusButton = (Button) findViewById(R.id.buttonPlus);
        plusButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {   Push("+");
            }
        });
        final Button minusButton = (Button) findViewById(R.id.buttonMinus);
        minusButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {   Push("-");
            }
        });

        final Button curlyleftButton = (Button) findViewById(R.id.buttoncurlyleft);
        curlyleftButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {   Push("{");
            }
        });

        final Button curlyrightButton = (Button) findViewById(R.id.buttonCurlyright);
        curlyrightButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {   Push("}");
            }
        });



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

      //  WebViewRenderer.renderLatex(w, e.getText().toString());

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
        if (id == R.id.action_settings)
        {
    //        WebViewRenderer.renderLatex(w,e.getText().toString());
            WebViewRenderer.renderLatex(w,"\\int (\\sin x dx");
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


    private void show()
    {
        //WebViewRenderer.renderLatex(w,"\\int (\\sin x dx");
        WebViewRenderer.renderLatex(w,e.getText().toString());
    }

    public void Push(String buttonString)
    {       e.getText().insert(e.getSelectionStart(), buttonString);
    }

}
