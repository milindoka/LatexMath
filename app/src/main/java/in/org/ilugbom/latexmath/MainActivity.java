package in.org.ilugbom.latexmath;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompatExtras;
import android.text.Editable;
import android.util.Log;
import android.view.MotionEvent;
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
import android.widget.TextView;
import android.widget.Toast;


import java.io.File;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FileChooser filechooser;
    SaveAsDialog sad;
    String FullPath="";
    EditText e;
    private WebView w;
    Button BackSlashButton;
    private FileIO fio = new FileIO();
    int QN = 1;
    ArrayList<String> QnArray = new ArrayList<String>(); // Create an ArrayList object
    ArrayList<String> AnArray = new ArrayList<String>(); // Create an ArrayList object
    FloatingActionButton fab;
    private TextView FC; ///to set tex on fab
    boolean QnMode = true;
    //StorageChooser chooser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        w = (WebView) findViewById(R.id.webview);
        WebViewRenderer.prepareWebview(w);
        e = (EditText) findViewById(R.id.edit);
        fio.SetMA(this);




        w.setOnTouchListener(new OnSwipeTouchListener(MainActivity.this) {
            public void onSwipeTop() {
                //  Toast.makeText(MainActivity.this, "top", Toast.LENGTH_SHORT).show();
            }

            public void onSwipeRight() {
                OnPrev();
            }

            public void onSwipeLeft() {
                // Toast.makeText(MainActivity.this, "left", Toast.LENGTH_SHORT).show();
                OnNext();
            }

            public void onSwipeBottom() {
                //Toast.makeText(MainActivity.this, "bottom", Toast.LENGTH_SHORT).show();
            }

        });



        Button b1 = (Button) findViewById(R.id.bn1);
        b1.setOnClickListener(Listener); // calling onClick() method
        Button b2 = (Button) findViewById(R.id.bn2);
        b2.setOnClickListener(Listener);
        Button b3 = (Button) findViewById(R.id.bn3);
        b3.setOnClickListener(Listener); // calling onClick() method
        Button b4 = (Button) findViewById(R.id.bn4);
        b4.setOnClickListener(Listener);
        Button b5 = (Button) findViewById(R.id.bn5);
        b5.setOnClickListener(Listener); // calling onClick() method
        Button b6 = (Button) findViewById(R.id.bn6);
        b6.setOnClickListener(Listener);
        Button b7 = (Button) findViewById(R.id.bn7);
        b7.setOnClickListener(Listener); // calling onClick() method
        Button b8 = (Button) findViewById(R.id.bn8);
        b8.setOnClickListener(Listener);
        Button b9 = (Button) findViewById(R.id.bn9);
        b9.setOnClickListener(Listener); // calling onClick() method
        Button b0 = (Button) findViewById(R.id.bn0);
        b0.setOnClickListener(Listener);


        Button bx = (Button) findViewById(R.id.bnx);
        bx.setOnClickListener(Listener);
        Button by = (Button) findViewById(R.id.bny);
        by.setOnClickListener(Listener);
        Button bz = (Button) findViewById(R.id.bnz);
        bz.setOnClickListener(Listener);





        final Button backspaceButton = (Button) findViewById(R.id.buttonBackspace);
        backspaceButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {   Editable editable = e.getText();
                int charCount = e.getSelectionEnd();
                if (charCount > 0) {
                    editable.delete(charCount - 1, charCount);
                }
                show();
            }
        });

        final Button showButton = (Button) findViewById(R.id.buttonShow);
        showButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                show();
            }
        });

        BackSlashButton = (Button) findViewById(R.id.buttonBackslah);
        BackSlashButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Push("\\");
                ShowPopupMenu();
            }
        });
        final Button equalButton = (Button) findViewById(R.id.buttonEqual);
        equalButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                Push("="); show();
            }
        });
        final Button plusButton = (Button) findViewById(R.id.buttonPlus);
        plusButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {   Push("+");show();
                show();
            }
        });
        final Button minusButton = (Button) findViewById(R.id.buttonMinus);
        minusButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {   Push("-");
                show();
            }
        });

        final Button caretButton = (Button) findViewById(R.id.buttonCaret);
        caretButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                Push("^{2}");
                e.setSelection(e.getSelectionStart() - 2);
                show();
            }
        });

        final Button curlyleftButton = (Button) findViewById(R.id.buttoncurlyleft);
        curlyleftButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Push("{");
            }
        });

        final Button curlyrightButton = (Button) findViewById(R.id.buttonCurlyright);
        curlyrightButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Push("}");
            }
        });

//////Second Button Bar
        final Button clearButton = (Button) findViewById(R.id.buttonClear);
        clearButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                e.setText("");
                show();
            }
        });

        final Button frButton = (Button) findViewById(R.id.buttonFr);
        frButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Push("\\frac{3}{4}");
                e.setSelection(e.getSelectionStart() - 4);
                show();
            }
        });

        final Button bracketButton = (Button) findViewById(R.id.buttonBracket);
        bracketButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Push("\\left( \\right)");
                e.setSelection(e.getSelectionStart() - 7);
                show();
            }
        });

        final Button trigButton = (Button) findViewById(R.id.buttonTrig);
        trigButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Push("\\sin ");
                show();
            }
        });

        final Button matrixButton = (Button) findViewById(R.id.buttonMatrix);
        matrixButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Push("\\begin{bmatrix}\n" +
                        "1 & 2 & 3 \\\\ \n" +
                        "4 & 5 & 6 \\\\ \n" +
                        "7 & 8 & 9 \n" +
                        "\\end{bmatrix}\n");
                show();
            }
        });


        final Button casesButton = (Button) findViewById(R.id.buttonCases);
        casesButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Push("f(n) = \\begin{cases}\n" +
                        " \\frac{n}{2},  & \\text{if $n$ is even} \\\\[2ex]\n" +
                        "3n+1, & \\text{if $n$ is odd}\n" +
                        "\\end{cases}\n");
            }
        });

        ////////////////////////////////////////////

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        FC = (TextView) findViewById(R.id.FabText); ///To display text on FAB
        FC.setText("Q");
        fab.setOnTouchListener(new View.OnTouchListener() {


            float x, y;
            float x1, y1;
            float x2, y2;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_UP:
                        if (Math.abs(x2 - x1) < 10 && Math.abs(y2 - y1) < 10)

                        {
                            if (QnMode) {
                                fab.setBackgroundTintList(getResources().getColorStateList(R.color.colorGreen));
                                FC.setText("A");
                                QnMode = false; //Answer mode is set
                            } else {
                                fab.setBackgroundTintList(getResources().getColorStateList(R.color.colorPink));
                                FC.setText("Q");
                                QnMode = true; //Question mode is set
                            }

                        }

                        return true;
                    case MotionEvent.ACTION_MOVE:

                        x2 = fab.getX() + event.getX() - x;
                        y2 = fab.getY() + event.getY() - y;

                        fab.setX(x2);
                        fab.setY(y2);
                        return true;
                    case MotionEvent.ACTION_DOWN:
                        x = event.getX();
                        y = event.getY();
                        x1 = fab.getX() + event.getX() - x;
                        y1 = fab.getY() + event.getY() - y;
                        x2 = fab.getX();
                        y2 = fab.getY();
                        //   x1=x;
                        //  y1=y;
                        //   Msg.show(String.format("%d",event.getX()));
                        return true;
                }

                return false;
            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

 //??



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


        switch (id) {
            case R.id.action_new:
                OnNew();
                return true;
            case R.id.action_load:
                PickFile();
                return true;

            case R.id.action_load_last:
                OnLoadLastFile();
                return true;

            case R.id.action_save:
                //OnSave();
                //processFile();
                return true;
            case R.id.action_save_as:
                //OnSave();
                SaveAs();
                return true;

            case R.id.action_delete_question:

                return true;

            case R.id.action_settings:
                show("Settings");
                return true;
        }
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
    }  ///////////////////   end of oncreate bundle ////////////////////////////


    void OnLoadLastFile()
    {   FullPath=GetLastPath();
        show(FullPath);
        if(FullPath.length()>0) Load(FullPath);
        show();

    }

     void show() {
        //WebViewRenderer.renderLatex(w,"\\int (\\sin x dx");
        WebViewRenderer.renderLatex(w, e.getText().toString());
    }

    public void Push(String buttonString) {
        e.getText().insert(e.getSelectionStart(), buttonString);
    }


    void ShowPopupMenu() {
        //Creating the instance of PopupMenu
        PopupMenu popup = new PopupMenu(MainActivity.this, BackSlashButton);
        //Inflating the Popup using xml file
        popup.getMenuInflater().inflate(R.menu.backslashpopup, popup.getMenu());

        //registering popup with OnMenuItemClickListener
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) { //Toast.makeText(MainActivity.this,"You Clicked : " + item.getTitle(), Toast.LENGTH_SHORT).show();

                int option = item.getItemId();
                switch (option) {
                    case R.id.frac:
                        Push("frac{}{ }");
                        e.setSelection(e.getSelectionStart() - 4);
                        break;
                    case R.id.text:
                        Push("text{}");
                        e.setSelection(e.getSelectionStart() - 1);
                        break;
                    case R.id.sqrt:
                        Push("sqrt{}");
                        e.setSelection(e.getSelectionStart() - 1);
                        break;
                    case R.id.inte:
                        Push("int  \\;dx");
                        e.setSelection(e.getSelectionStart() - 5);
                        break;
                    case R.id.limi:
                        Push("lim_{x \\to }");
                        e.setSelection(e.getSelectionStart() - 1);
                        break;

                }
                return true;
            }
        }); //closing the setOnClickListener method

        popup.show();//showing popup menu
    }

    void show(String msg) {
        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
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
        String path = "";
        if (requestCode == 123) {
            Uri uri = data.getData();
            String FilePath = getRealPathFromURI(uri); // should the path be here in this string
            System.out.print("Path  = " + FilePath);
        }
    }

    public String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor == null) return null;
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }


    void OnNext() {

        String temp = e.getText().toString();

        if (QN == QnArray.size() + 1) {

            if (temp.length() == 0) {
                show("No more Questions");
                return;
            }
            QnArray.add(temp);
            QN++;
            e.setText("");
            return;
        }


        QnArray.set(QN - 1, temp);
        QN++;  ///since this is not last empty question
        if (QN == QnArray.size() + 1) e.setText("");
        else e.setText(QnArray.get(QN - 1).toString());
        show();
    }

    void OnPrev() {
        //show(String.format("%d",QN));
        //  show(String.format("%d",QnArray.size()));
        if (QN == 1) return;  //first question cannot go back
        //otherwise get content
        String temp = e.getText().toString();
        //

        if (QN == QnArray.size() + 1) //  last question
        {
            if (temp.length() != 0)
                QnArray.add(temp); // then it is to be appended to array
        } else {
            QnArray.set(QN - 1, temp);
        }

        QN--;
        e.setText(QnArray.get(QN - 1).toString());

        show();
    }


    void Load(String completepath) {
        QN = 1;
        QnArray.removeAll(QnArray);
        fio.OpenList(completepath);
        e.setText(QnArray.get(0));

    }


    void OnSave() {
          //fio.SaveFile();
        //  fio.SaveListDialog();
        fio.SaveFileDialog(this);
    }


    private void SaveAs()
    {sad = new SaveAsDialog(this);
        sad.setExtension(".tex");
        sad.setFileListener(new SaveAsDialog.FileSelectedListener() {
            @Override
            public void fileSelected(final File file) {
                // ....do something with the file
                String filename = file.getAbsolutePath();
                show(filename);
                fio.SaveFile(filename);

            }
        });

        sad.showDialog();
    }



void PickFile()
{       filechooser = new FileChooser(this);
    filechooser.setExtension(".tex");
    filechooser.setFileListener(new FileChooser.FileSelectedListener() {
        @Override
        public void fileSelected(final File file) {
            // ....do something with the file
            String filename = file.getAbsolutePath();
            //Log.d("File", filename);
            // then actually do something in another module
            Load(filename);

        }
    });
// Set up and filter my extension I am looking for

    filechooser.showDialog();
}

/*
    @Override
    protected void onStart() {   ///////Get Last File Path from pref & Load Last File
        super.onStart();

        FullPath=GetLastPath();
        show(FullPath);

        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(4000); // Sleep 4 seconds
                // Now change the color back. Needs to be done on the UI thread
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(FullPath.length()>0) Load(FullPath);
                        show();
                    }
                });
            }
        }).start();
    }
*/

    String GetLastPath()
{  SharedPreferences settings = MainActivity.this.getSharedPreferences("LastPath", MODE_PRIVATE);
    String lastpath = settings.getString("key1", "");
    //college = settings.getString("key2", "School/College");
    //teacher = settings.getString("key3", "Name");
    //subject=settings.getString("key4","Subject");
    //email = settings.getString("key5", "Email");
   return lastpath;
}

void OnNew()
{
show("new");
}



View.OnClickListener Listener = new View.OnClickListener()
{
        @Override
        public void onClick(View v) {

            switch(v.getId())
            {
                case R.id.bn1 : Push("1");break;
                case R.id.bn2 : Push("2");break;
                case R.id.bn3 : Push("3");break;
                case R.id.bn4 : Push("4");break;
                case R.id.bn5 : Push("5");break;
                case R.id.bn6 : Push("6");break;
                case R.id.bn7 : Push("7");break;
                case R.id.bn8 : Push("8");break;
                case R.id.bn9 : Push("9");break;
                case R.id.bn0 : Push("0");break;
                case R.id.bnx : Push("x");break;
                case R.id.bny : Push("y");break;
                case R.id.bnz : Push("z");break;

            }
            show();


        }

};





}