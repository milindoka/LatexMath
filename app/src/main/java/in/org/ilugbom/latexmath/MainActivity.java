package in.org.ilugbom.latexmath;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import android.widget.PopupMenu;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    EditText e;
    private WebView w;
    Button BackSlashButton;
    private FileIO fio=new FileIO();
    int QN=0;
    ArrayList<String> QnArray = new ArrayList<String>(); // Create an ArrayList object


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        w = (WebView) findViewById(R.id.webview);
        WebViewRenderer.prepareWebview(w);
        e = (EditText) findViewById(R.id.edit);
        fio.SetMA(this);


        final Button showButton = (Button) findViewById(R.id.buttonShow);
        showButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {   show();
            }
        });

        BackSlashButton = (Button) findViewById(R.id.buttonBackslah);
        BackSlashButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {   Push("\\");
                ShowPopupMenu();
            }
        });
        final Button equalButton = (Button) findViewById(R.id.buttonEqual);
        equalButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {   Push("=");
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

        final Button caretButton = (Button) findViewById(R.id.buttonCaret);
        caretButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {   Push("^");
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

//////Second Button Bar


        final Button loadButton = (Button) findViewById(R.id.buttonLoad);
        loadButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {

                fio.OpenList("/sdcard/test.tex");

            }
        });

        final Button saveButton = (Button) findViewById(R.id.buttonSave);
        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {// show("save");
                fio.SaveFile();
            }
        });

        final Button nextButton = (Button) findViewById(R.id.buttonNext);
        nextButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {

                String temp = e.getText().toString();

                if(QN==QnArray.size()){

                                        if (temp.length() == 0) {  show("No more Questions");
                                                                    return;
                                                                }
                                        QnArray.add(temp);
                                        QN++;
                                        e.setText("");
                                        return;
                                       }

                if(QN<QnArray.size()) {
                    QnArray.set(QN,temp);
                    QN++;  ///since this is not last empty question
                    e.setText(QnArray.get(QN-1).toString());
                }
            }
        });


        final Button prevButton = (Button) findViewById(R.id.buttonPrev);
        prevButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                String temp=e.getText().toString();
                if(QN==QnArray.size())
                        { if(temp.length()!=0) QnArray.add(temp); }
                if(QN>0)  QN--; else return;

                e.setText(QnArray.get(QN).toString());

            }
        });

        ////////////////////////////////////////////

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


        fio.SetMA(this);

        return true;
    }  ///end of oncreate bundle


    private void show()
    {
        //WebViewRenderer.renderLatex(w,"\\int (\\sin x dx");
        WebViewRenderer.renderLatex(w,e.getText().toString());
    }

    public void Push(String buttonString)
    {       e.getText().insert(e.getSelectionStart(), buttonString);
    }


    void ShowPopupMenu()
    {
        //Creating the instance of PopupMenu
        PopupMenu popup = new PopupMenu(MainActivity.this, BackSlashButton);
        //Inflating the Popup using xml file
        popup.getMenuInflater().inflate(R.menu.backslashpopup, popup.getMenu());

        //registering popup with OnMenuItemClickListener
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item)
            { //Toast.makeText(MainActivity.this,"You Clicked : " + item.getTitle(), Toast.LENGTH_SHORT).show();

                int option=item.getItemId();
                switch(option)
                { case R.id.frac : Push("frac{}{}");
                                   e.setSelection(e.getSelectionStart()-3); break;
                  case R.id.text : Push("text{}");
                                   e.setSelection(e.getSelectionStart()-1); break;
                  case R.id.sqrt : Push("sqrt{}");
                                   e.setSelection(e.getSelectionStart()-1); break;
                  case R.id.inte : Push("int  \\;dx");
                                   e.setSelection(e.getSelectionStart()-5); break;
                    case R.id.limi : Push("lim_{x \\to }");
                        e.setSelection(e.getSelectionStart()-1); break;

                }
                return true;
            }
        }); //closing the setOnClickListener method

        popup.show();//showing popup menu
    }

    void show(String msg)
    {
        Toast.makeText(MainActivity.this,msg, Toast.LENGTH_SHORT).show();
    }


    public void onBrowse(View view) {
        Intent chooseFile;
        Intent intent;
        chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
        chooseFile.addCategory(Intent.CATEGORY_OPENABLE);
        chooseFile.setType("text/plain");
        intent = Intent.createChooser(chooseFile, "Choose a file");
        startActivityForResult(intent, 123);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) return;
        String path     = "";
        if(requestCode == 123)
        {
            Uri uri = data.getData();
            String FilePath = getRealPathFromURI(uri); // should the path be here in this string
            System.out.print("Path  = " + FilePath);
        }
    }

    public String getRealPathFromURI(Uri contentUri) {
        String [] proj      = {MediaStore.Images.Media.DATA};
        Cursor cursor       = getContentResolver().query( contentUri, proj, null, null,null);
        if (cursor == null) return null;
        int column_index    = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }


}
