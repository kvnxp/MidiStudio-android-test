package com.midistudio.structure;

import android.content.Context;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by kvnxp on 20/10/15.
 */
public class ControlsTab  {
    public static Context context;
    public static ArrayList<Boolean[]> activeControl;
    public static CheckBox[] controlTabCheckBox;// control enable
    public static ArrayList<int[]> controlChannelValue;// values from control
    public static ArrayList<int[]> controlChannelSelected;// control selected in spinner
    public static Spinner[] SpinnersOnControlsTab;
    public static SeekBar[] SeeksOnControlsTab;
    public static TextView[] TextOnControlsTab;

    public static CheckBox[] activateChannelbox;
    public static ArrayList<Boolean[]> activateChannelValues;
    public static CheckBox[] deactivateChannelsBox;
    public static ArrayList<Boolean[]> deactivateChannelsValues;


}