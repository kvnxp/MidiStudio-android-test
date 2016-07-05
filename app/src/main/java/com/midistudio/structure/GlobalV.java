package com.midistudio.structure;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.midistudio.R;

import java.util.ArrayList;

/**
 * Created by kevn on 19/10/15.
 */
public class GlobalV {
    public static Button currentTagEdit;
    public static Button[] gridButtonsChannels;
    public static ArrayList<int[]> controls ;
    public static ArrayAdapter[] mainTabAdapters;
    public static ArrayList<Boolean> active;
    public static int[] programID;
    public static int[] channelIn;
    public static Boolean RtpMode;

    public static Spinner ntspin;

    public static void setupAdapters(Context context){
        RtpMode = false;
        mainTabAdapters = new ArrayAdapter[4];
        mainTabAdapters[0]= new ArrayAdapter(context,android.R.layout.simple_spinner_dropdown_item,context.getResources().getStringArray(R.array.channels));
        mainTabAdapters[1]= new ArrayAdapter(context,android.R.layout.simple_spinner_dropdown_item,context.getResources().getStringArray(R.array.generalmid));
        mainTabAdapters[2]= new ArrayAdapter(context,android.R.layout.simple_spinner_dropdown_item,context.getResources().getStringArray(R.array.msblsb));
    }



}