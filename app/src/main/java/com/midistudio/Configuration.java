package com.midistudio;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.midistudio.structure.FileIO;
import com.midistudio.structure.MidiIO;
import com.midistudio.structure.toast;

public class Configuration extends Activity {
    EditText newname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);
        toast.context= getBaseContext();
        newname=(EditText)findViewById(R.id.filename);

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
}