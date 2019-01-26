package in.org.ilugbom.latexmath;

/*
This simple and nice single java class file chooser is by Roger Keays.
Taken from his blog :

https://rogerkeays.com/simple-android-file-chooser

Thanks to Roger

*/

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.ColorSpace;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;

public class SaveAsDialog {
    private static final String PARENT_DIR = "..";

    private final Activity activity;
    private ListView list;
    private Dialog dialog;
    private File currentPath;


    private EditText etfnem;
    private LinearLayout ll;
    private Button SevButton;

    // filter on file extension
    private String extension = null;
    public void setExtension(String extension) {
        this.extension = (extension == null) ? null :
                extension.toLowerCase();
    }

    // file selection event handling
    public interface FileSelectedListener {
        void fileSelected(File file);
    }
    public SaveAsDialog setFileListener(FileSelectedListener fileListener) {
        this.fileListener = fileListener;
        return this;
    }
    private FileSelectedListener fileListener;

    public SaveAsDialog(final Activity activity) {
        this.activity = activity;
        dialog = new Dialog(activity);
        list = new ListView(activity);
        etfnem=new EditText(activity);
        etfnem.setBackgroundColor(Color.YELLOW);

        SevButton=new Button(activity);
        SevButton.setText("Save");



        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override public void onItemClick(AdapterView<?> parent, View view, int which, long id) {
                String fileChosen = (String) list.getItemAtPosition(which);
                File chosenFile = getChosenFile(fileChosen);
                if (chosenFile.isDirectory()) {
                    refresh(chosenFile);
                }
                /*
                else {
                    if (fileListener != null) {
                        fileListener.fileSelected(chosenFile);
                    }
                    dialog.dismiss();
                }*/
            }
        });



        SevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                    if (fileListener != null) {

                       File fff=new  File(currentPath,etfnem.getText()+".tex" );
                        fileListener.fileSelected(fff);
                    }
                    dialog.dismiss();
               // Toast.makeText(activity,currentPath.getPath()+"/"+etfnem.getText()+".tex".toString(), Toast.LENGTH_SHORT).show();
            }
        });



        ll = new LinearLayout(activity);
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.addView(list);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        lp.weight=5f;
        list.setLayoutParams(lp);
        LinearLayout.LayoutParams le = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        le.weight=0f;
        etfnem.setLayoutParams(le);

        LinearLayout.LayoutParams lb = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        lb.weight=0f;
        SevButton.setLayoutParams(lb);

        ll.addView(etfnem);
        ll.addView(SevButton);
        dialog.setContentView(ll);
       dialog.getWindow().setLayout(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT);
        refresh(Environment.getExternalStorageDirectory());
    }

    public void showDialog()
    {
        dialog.show();
        refresh(Environment.getExternalStorageDirectory());
    }


    /**
     * Sort, filter and display the files for the given path.
     */
    private void refresh(File path) {
        this.currentPath = path;
        if (path.exists()) {
            File[] dirs = path.listFiles(new FileFilter() {
                @Override public boolean accept(File file) {
                    return (file.isDirectory() && file.canRead());
                }
            });
            File[] files = path.listFiles(new FileFilter() {
                @Override public boolean accept(File file) {
                    if (!file.isDirectory()) {
                        if (!file.canRead()) {
                            return false;
                        } else if (extension == null) {
                            return false;
                        } else {
                            return false; ///no files in directory dialog
                        }
                    } else {
                        return false;
                    }
                }
            });

            // convert to an array
            int i = 0;
            String[] fileList;
            if (path.getParentFile() == null) {
                fileList = new String[dirs.length + files.length];
            } else {
                fileList = new String[dirs.length + files.length + 1];
                fileList[i++] = PARENT_DIR;
            }
            Arrays.sort(dirs);
            Arrays.sort(files);
            for (File dir : dirs) { fileList[i++] = dir.getName(); }
            for (File file : files ) { fileList[i++] = file.getName(); }

            // refresh the user interface
            dialog.setTitle(currentPath.getPath());
            list.setAdapter(new ArrayAdapter(activity,
                    android.R.layout.simple_list_item_1, fileList) {
                @Override public View getView(int pos, View view, ViewGroup parent) {
                    view = super.getView(pos, view, parent);
                    ((TextView) view).setSingleLine(true);
                    return view;
                }
            });
        }
    }


    /**
     * Convert a relative filename into an actual File object.
     */
    private File getChosenFile(String fileChosen) {
        if (fileChosen.equals(PARENT_DIR)) {
            return currentPath.getParentFile();
        } else {
            return new File(currentPath, fileChosen);
        }
    }
}