package in.org.ilugbom.latexmath;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

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



    void SaveFile()
    {	String FileNameWithPath="/sdcard/test.tex";
        modified=false;
        String tmpStr;
        String txtData = "\n";
        txtData+="\\documentclass[14pt]{extarticle}"; txtData+='\n';
        txtData+="\\usepackage{amsmath}"; txtData+='\n'; // collegename2
        txtData+="\\begin{document}\n\n";

        txtData+=MA.e.getText();

        txtData+="\n\n\\end{document}\n\n";


        try {
            File myFile = new File(FileNameWithPath);
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
    { int i;
        String temp2;
        try
        {

            File myFile = new File(fylenamewithpath);
            FileInputStream fIn = new FileInputStream(myFile);
            BufferedReader myReader = new BufferedReader(new InputStreamReader(fIn));
            String aDataRow = "";
            String[] temp;
            MA.e.setText("");
            i=0;
            while ((aDataRow = myReader.readLine()) != null)

            { MA.e.append(aDataRow);
                i++;
            }
            myReader.close();
            String FileNameWithPath=fylenamewithpath;
            int start=fylenamewithpath.lastIndexOf("/");

            String tempfname=fylenamewithpath.substring(start+1);

           // final TextView myTitleText = (TextView) findViewById(R.id.ttext);
           // if (myTitleText != null)
           //     myTitleText.setText(tempfname);
           // showtop(tempfname);
            MA.show("Loaded From SD Card");
        }
        catch (Exception e)
        {
            MA.show(e.getMessage());
        }

        modified=false;

    }





}
