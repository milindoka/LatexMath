package in.org.ilugbom.latexmath;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by milind on 12/1/19.
 */


public class FileIO
{
  String tempstr;
  boolean modified=false;
    String DefaultDirectory="/sdcard/";
    MainActivity MA;
    void SetMA(MainActivity MA){this.MA=MA;}
    boolean OpenNow=false;



     void OpenFile()
    {	OpenNow=false;
        List<String> listItems = new ArrayList<String>();
        File mfile=new File("/sdcard");


        File[] list=mfile.listFiles();
        String tempupper;

        for(int i=0;i<mfile.listFiles().length;i++)
        {
            tempstr=list[i].getAbsolutePath();
            tempupper=tempstr.toUpperCase();
            if(tempupper.endsWith(".TEX") )
                listItems.add(list[i].getAbsolutePath());
        }


        final CharSequence[] items = listItems.toArray(new CharSequence[listItems.size()]);

        AlertDialog.Builder builder = new AlertDialog.Builder(MA);
        builder.setTitle("Select File To Open...");
        builder.setItems(items, new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int item)
            {String ttt= (String) items[item];
                MA.show(ttt);
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }



    void SaveFile(String CompletePath)
    {	//String FileNameWithPath="/sdcard/test.tex";
        modified=false;
        String tmpStr;
        String txtData = "\n";
        txtData+="\\documentclass[14pt]{extarticle}"; txtData+='\n';
        txtData+="\\usepackage{amsmath}"; txtData+='\n';
        txtData+="\\begin{document}"; txtData+="\n\n";


        //txtData+="\\section{Question}"; txtData+='\n';
            int size=MA.QnArray.size();
            if(size>0)
            {for(int i=0;i<size;i++)
                {

                    txtData+=MA.QnArray.get(i); txtData+='\n';
                    txtData+="\\section{Question}"; txtData+='\n';
                }

            }


        txtData+="\n\n\\end{document}\n";


        try {
            File myFile = new File(CompletePath);
            myFile.createNewFile();
            FileOutputStream fOut = new FileOutputStream(myFile);
            OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
            myOutWriter.append(txtData);
            myOutWriter.close();
            fOut.close();

            MA.show("Saved in Latex format");
            //	showtop(pdfname);

        } catch (Exception e) {
            MA.show(e.getMessage());
        }
      //  if(end) finish();
    }


    void OpenList(String fylenamewithpath)
    { int i; String temp[];
        String temp1="",temp2;
        try
        {

            File myFile = new File(fylenamewithpath);
            FileInputStream fIn = new FileInputStream(myFile);
            BufferedReader myReader = new BufferedReader(new InputStreamReader(fIn));
            String aDataRow = "";

            MA.e.setText("");

            while ((aDataRow = myReader.readLine()) != null)

            {   if(aDataRow.contains("\\begin{document}")) break;
               // temp1+=aDataRow;

            }
            while ((aDataRow = myReader.readLine()) != null)

            {   if(aDataRow.contains("\\end{document}")) break;
                temp1+=aDataRow;

            }

            myReader.close();

            temp=temp1.split("\\\\section\\{Question\\}");

            for(i=0;i<temp.length;i++)
                MA.QnArray.add(temp[i]);

        //    MA.e.setText(MA.QnArray[0]);

           /*
            String FileNameWithPath=fylenamewithpath;
            int start=fylenamewithpath.lastIndexOf("/");

            String tempfname=fylenamewithpath.substring(start+1);
            final TextView myTitleText = (TextView) findViewById(R.id.ttext);
            if (myTitleText != null)
             myTitleText.setText(tempfname);
             showtop(tempfname);
           */
            MA.show("Loaded From SD Card");
        }
        catch (Exception e)
        {
            MA.show(e.getMessage());
        }

        modified=false;

    }


    void SaveFileDialog(Context c) {
        final EditText filenem = new EditText(c);
        AlertDialog dialog = new AlertDialog.Builder(c)
                .setTitle("Save Article")
                .setMessage("Give File Name :")
                .setView(filenem)
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String filetitle = String.valueOf(filenem.getText());
                        String filewithpath="/sdcard/"+filetitle+".tex";
                        SaveFile(filewithpath);
                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
        dialog.show();
    }


}
