package com.midistudio;

import android.app.Activity;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.midistudio.structure.GlobalV;
import com.midistudio.structure.MidiIO;


/**
 * Created by kvnxp on 17/10/15.
 */
public class ChannelEditor extends Activity{

    public static SeekBar volumeSeek;
    public static EditText newname;
    public static Button sustain;
    public static Button[] tabs;
    public static TextView volumetxt;
    public static Boolean[] tabActive;
    public static LinearLayout[] tabsObjs;
    public static int[] currentChannelControlsValues;
    public static Spinner channelSpinner, lsbSpinner, programerSpinner, msbSpinner;
    public static NumberPicker channelPicker, programPicker, lsbPicker, msbPicker;
    public static CheckBox inProgram,inBanks;
    public static void setprogram() {
        MidiIO.setProgram(MidiStudioSingle.currentTagEdit, GlobalV.programID[MidiStudioSingle.currentTagEdit] - 1);

    }

    public static void readcontrol(int chn,int control,int value){

        if (inBanks.isChecked()){
            MidiIO.sendControl(chn,control,value);
        }

    }
    public static void readProgram(int chn,int value){
        if (inProgram.isChecked()){
            MidiIO.setProgram(chn,value);

        }

    }

}