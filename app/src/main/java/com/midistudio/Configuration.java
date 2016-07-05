package com.midistudio;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.midistudio.structure.FileIO;
import com.midistudio.structure.GlobalV;
import com.midistudio.structure.MidiIO;
import com.midistudio.structure.toast;

public class Configuration extends Activity {




    Button rtpbuttonstart;
    Button rtpstatus;

    EditText newname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);
        toast.context= getBaseContext();
        newname=(EditText)findViewById(R.id.filename);

        if ( GlobalV.RtpMode){
            rtpstatus.setBackgroundColor(Color.GREEN);
            rtpstatus.setText("RTP: on");
        }else {
            rtpstatus.setBackgroundColor(Color.WHITE);
            rtpstatus.setText("RTP: off");
        }



    }


    public void save(View v){

        if (!newname.getText().toString().equals("")) {
            FileIO.saveobject(newname.getText().toString());
        }else{
            toast.show("write a name for file");
        }
    }

    public void read(View v){
//        FileIO.readobjects();
//        FileChooserDialog dialog = new FileChooserDialog(this);
//        dialog.show();

    }

    public void ntconfig (View v) {
    }
    public void localmidi(View v) {
        MidiIO.sendControl(0,122,0);

    }

    public void rtpstart (View v) {


    }

    void loadrtpspinner ()  {





    }


    public void rtpst(View v){
        if ( GlobalV.RtpMode){
            rtpstatus.setBackgroundColor(Color.WHITE);
            rtpstatus.setText("RTP: off");
            GlobalV.RtpMode=false;

        }else {
            rtpstatus.setBackgroundColor(Color.GREEN);
            rtpstatus.setText("RTP: on");
            GlobalV.RtpMode=true;


        }

    }




}