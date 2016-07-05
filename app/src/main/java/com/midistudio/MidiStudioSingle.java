package com.midistudio;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.usb.UsbDevice;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.midistudio.structure.ControlsTab;
import com.midistudio.structure.FileIO;
import com.midistudio.structure.GlobalV;
import com.midistudio.structure.MidiIO;

import java.util.ArrayList;

import jp.kshoji.driver.midi.activity.AbstractMultipleMidiActivity;
import jp.kshoji.driver.midi.device.MidiInputDevice;
import jp.kshoji.driver.midi.device.MidiOutputDevice;
import jp.kshoji.driver.midi.util.UsbMidiDriver;

/**
 * Created by kevn on 30/10/15.
 */
public class MidiStudioSingle extends AbstractMultipleMidiActivity {
    @Override
    public void onDeviceAttached(UsbDevice usbDevice) {

    }

    @Override
    public void onMidiInputDeviceAttached(MidiInputDevice midiInputDevice) {

        MidiIO.midiin.add(midiInputDevice);
    }
    @Override
    public void onMidiOutputDeviceAttached(MidiOutputDevice midiOutputDevice) {
        MidiIO.midiout.add(midiOutputDevice);
    }

    @Override
    public void onDeviceDetached(UsbDevice usbDevice) {

    }

    @Override
    public void onMidiInputDeviceDetached(MidiInputDevice midiInputDevice) {
        MidiIO.midiin.remove(midiInputDevice);
    }

    @Override
    public void onMidiOutputDeviceDetached(MidiOutputDevice midiOutputDevice) {
        MidiIO.midiout.remove(midiOutputDevice);
    }

    @Override
    public void onMidiMiscellaneousFunctionCodes(MidiInputDevice midiInputDevice, int i, int i1, int i2, int i3) {

    }

    @Override
    public void onMidiCableEvents(MidiInputDevice midiInputDevice, int i, int i1, int i2, int i3) {

    }

    @Override
    public void onMidiSystemCommonMessage(MidiInputDevice midiInputDevice, int i, byte[] bytes) {

    }

    @Override
    public void onMidiSystemExclusive(MidiInputDevice midiInputDevice, int i, byte[] bytes) {

    }

    @Override
    public void onMidiNoteOff(MidiInputDevice midiInputDevice, int i, int i1, int i2, int i3) {
        MidiIO.sendMixNote(0,i1,i2,i3);
    }

    @Override
    public void onMidiNoteOn(MidiInputDevice midiInputDevice, int i, int i1, int i2, int i3) {
        MidiIO.sendMixNote(1,i1,i2,i3);


    }




    @Override
    public void onMidiPolyphonicAftertouch(MidiInputDevice midiInputDevice, int i, int i1, int i2, int i3) {

    }

    @Override
    public void onMidiControlChange(MidiInputDevice midiInputDevice, int i, int i1, int i2, int i3) {
        Log.e("control", i1 + " " + i2 + " " + i3);
        readControl(i1, i2, i3);
    }

    @Override
    public void onMidiProgramChange(MidiInputDevice midiInputDevice, int i, int i1, int i2) {
        Log.e("program",i1 +" "+i2);
        readProgram(i1, i2);
    }

    @Override
    public void onMidiChannelAftertouch(MidiInputDevice midiInputDevice, int i, int i1, int i2) {

    }

    @Override
    public void onMidiPitchWheel(MidiInputDevice midiInputDevice, int i, int i1, int i2) {
        MidiIO.setPitch(i1,i2);
    }

    @Override
    public void onMidiSingleByte(MidiInputDevice midiInputDevice, int i, int i1) {

    }



    public static Context context;
    public static int currentTagEdit;
    private boolean editmodeon;
    Boolean[] channelactive;
    private boolean inedit = false;
    Button buttonedit;
    public static ArrayList<Button> channelbuttons;
    public static String[] buttonName;

    LinearLayout[] layouts;
    private UsbMidiDriver midiDriver;

    /*  Layouts  ID
    0 - Main
    1 - Channel Editor

     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_midi_studio);
        setContentView(R.layout.activity_midi_studio);
        FileIO.makeFolders();
        context = getBaseContext();
        layouts = new LinearLayout[3];
        layouts[0]= (LinearLayout)findViewById(R.id.Layout_Main);
        layouts[1]=(LinearLayout)findViewById(R.id.Layout_edit);
        layouts[1].setVisibility(View.INVISIBLE);
        channelactive= new Boolean[17];
        MidiIO.midiin = new ArrayList<>(16);
        MidiIO.midiout = new ArrayList<>(16);
        buttonName= new String[16];

        StartupVariables();
        StartupEditVariables();


    }

    public void StartupVariables() {

        GlobalV.gridButtonsChannels = new Button[16];
        GlobalV.setupAdapters(getBaseContext());
        GlobalV.controls = new ArrayList<>(17);
        GlobalV.channelIn=new int[17];
        GlobalV.programID = new int[128];
        GlobalV.active = new ArrayList<>(17);
        ControlsTab.controlChannelValue=new ArrayList<>(17);
        ControlsTab.controlChannelSelected = new ArrayList<>(17);
        ControlsTab.SpinnersOnControlsTab = new Spinner[5];
        ControlsTab.SeeksOnControlsTab=new SeekBar[16];
        ControlsTab.TextOnControlsTab=new TextView[16];
        ControlsTab.activeControl = new ArrayList<>(17);
        ControlsTab.controlTabCheckBox = new CheckBox[7];
        ControlsTab.activateChannelbox = new CheckBox[18];
        ControlsTab.activateChannelValues =new ArrayList<>(18);
        ControlsTab.deactivateChannelsBox = new CheckBox[18];
        ControlsTab.deactivateChannelsValues = new ArrayList<>(18);
        channelbuttons = new ArrayList<>(16);

        channelbuttons.add((Button) findViewById(R.id.ch1));
        channelbuttons.add((Button)findViewById(R.id.ch1));
        channelbuttons.add((Button)findViewById(R.id.ch2));
        channelbuttons.add((Button)findViewById(R.id.ch3));
        channelbuttons.add((Button)findViewById(R.id.ch4));
        channelbuttons.add((Button)findViewById(R.id.ch5));
        channelbuttons.add((Button)findViewById(R.id.ch6));
        channelbuttons.add((Button)findViewById(R.id.ch7));
        channelbuttons.add((Button)findViewById(R.id.ch8));
        channelbuttons.add((Button)findViewById(R.id.ch9));
        channelbuttons.add((Button)findViewById(R.id.ch10));
        channelbuttons.add((Button)findViewById(R.id.ch11));
        channelbuttons.add((Button)findViewById(R.id.ch12));
        channelbuttons.add((Button)findViewById(R.id.ch13));
        channelbuttons.add((Button)findViewById(R.id.ch14));
        channelbuttons.add((Button)findViewById(R.id.ch15));
        channelbuttons.add((Button) findViewById(R.id.ch16));



        for (int i=0;i<=16;i++){

            channelactive[i]=false;
            GlobalV.active.add(false);
            GlobalV.controls.add(new int[128]);
            GlobalV.controls.get(i)[7]=110;
            GlobalV.channelIn[i]=i;
            ControlsTab.controlChannelValue.add(new int[16]);
            ControlsTab.controlChannelSelected.add(new int[16]);
            ControlsTab.activeControl.add(new Boolean[17]);
            ControlsTab.activateChannelValues.add(new Boolean[18]);
            ControlsTab.deactivateChannelsValues.add(new Boolean[18]);


        }

    }

    public void StartupEditVariables() {
        ChannelEditor.sustain=(Button)findViewById(R.id.sustainBtn);
        ChannelEditor.volumeSeek = (SeekBar)findViewById(R.id.editVolumeSeek);
        ChannelEditor.volumetxt = (TextView)findViewById(R.id.editvolumetext);
        ChannelEditor.newname = (EditText)findViewById(R.id.newname);
        ChannelEditor.tabs = new Button[3];
        ChannelEditor.tabActive = new Boolean[3];
        ChannelEditor.tabActive[0]= true;
        ChannelEditor.tabActive[1]=ChannelEditor.tabActive[2]=false;
        ChannelEditor.tabs[0]= (Button)findViewById(R.id.mainTab);
        ChannelEditor.tabs[1]= (Button)findViewById(R.id.controlsTab);
        ChannelEditor.tabs[2]= (Button)findViewById(R.id.misctab);
        ChannelEditor.tabsObjs=new LinearLayout[3];
        ChannelEditor.tabsObjs[0]=(LinearLayout)findViewById(R.id.Tab_Main);
        ChannelEditor.tabsObjs[1]=(LinearLayout)findViewById(R.id.Tab_Controls);
        ChannelEditor.tabsObjs[1].setVisibility(View.INVISIBLE);
        ChannelEditor.tabsObjs[2]=(LinearLayout)findViewById(R.id.Tab_Misc);
        ChannelEditor.tabsObjs[2].setVisibility(View.INVISIBLE);
        ChannelEditor.channelSpinner =(Spinner)findViewById(R.id.spinnerchannelin);
        ChannelEditor.channelSpinner.setAdapter(GlobalV.mainTabAdapters[0]);
        ChannelEditor.programerSpinner =(Spinner)findViewById(R.id.programspinner);
        ChannelEditor.programerSpinner.setAdapter(GlobalV.mainTabAdapters[1]);
        ChannelEditor.lsbSpinner=(Spinner)findViewById(R.id.lsbSpinner);
        ChannelEditor.lsbSpinner.setAdapter(GlobalV.mainTabAdapters[2]);
        ChannelEditor.msbSpinner=(Spinner)findViewById(R.id.msbSpinner);
        ChannelEditor.msbSpinner.setAdapter(GlobalV.mainTabAdapters[2]);
        ChannelEditor.channelPicker=(NumberPicker)findViewById(R.id.channelpicker);
        ChannelEditor.programPicker=(NumberPicker)findViewById(R.id.programpicker);
        ChannelEditor.lsbPicker=(NumberPicker)findViewById(R.id.lsbankpicker);
        ChannelEditor.msbPicker=(NumberPicker)findViewById(R.id.msbankpicker);
        ChannelEditor.channelPicker.setMaxValue(16);
        ChannelEditor.programPicker.setMaxValue(128);
        ChannelEditor.lsbPicker.setMaxValue(127);
        ChannelEditor.msbPicker.setMaxValue(127);
        ChannelEditor.inBanks = (CheckBox)findViewById(R.id.inbanks);
        ChannelEditor.inProgram = (CheckBox)findViewById(R.id.in_program);


        ControlsTab.SpinnersOnControlsTab[0] = (Spinner)findViewById(R.id.controlSpinner1);
        ControlsTab.SpinnersOnControlsTab[1] = (Spinner)findViewById(R.id.controlSpinner2);
        ControlsTab.SpinnersOnControlsTab[2] = (Spinner)findViewById(R.id.controlSpinner3);
        ControlsTab.SpinnersOnControlsTab[3] = (Spinner)findViewById(R.id.controlSpinner4);
        ControlsTab.SpinnersOnControlsTab[4] = (Spinner)findViewById(R.id.controlSpinner5);
//        ControlsTab.SpinnersOnControlsTab[5] = (Spinner)findViewById(R.id.controlSpinner6);
        ControlsTab.SeeksOnControlsTab[0] = (SeekBar)findViewById(R.id.controlbar1);
        ControlsTab.SeeksOnControlsTab[1] = (SeekBar)findViewById(R.id.controlbar2);
        ControlsTab.SeeksOnControlsTab[2] = (SeekBar)findViewById(R.id.controlbar3);
        ControlsTab.SeeksOnControlsTab[3] = (SeekBar)findViewById(R.id.controlbar4);
        ControlsTab.SeeksOnControlsTab[4] = (SeekBar)findViewById(R.id.controlbar5);
//        ControlsTab.SeeksOnControlsTab[5] = (SeekBar)findViewById(R.id.controlbar6);
        ControlsTab.TextOnControlsTab[0] = (TextView)findViewById(R.id.controlvalue1);
        ControlsTab.TextOnControlsTab[0] = (TextView)findViewById(R.id.controlvalue1);
        ControlsTab.TextOnControlsTab[1] = (TextView)findViewById(R.id.controlvalue2);
        ControlsTab.TextOnControlsTab[2] = (TextView)findViewById(R.id.controlvalue3);
        ControlsTab.TextOnControlsTab[3] = (TextView)findViewById(R.id.controlvalue4);
        ControlsTab.TextOnControlsTab[4] = (TextView)findViewById(R.id.controlvalue5);
//        ControlsTab.TextOnControlsTab[5] = (TextView)findViewById(R.id.controlvalue6);
        ControlsTab.controlTabCheckBox[0] = (CheckBox)findViewById(R.id.controlenable1);
        ControlsTab.controlTabCheckBox[1] = (CheckBox)findViewById(R.id.controlenable2);
        ControlsTab.controlTabCheckBox[2] = (CheckBox)findViewById(R.id.controlenable3);
        ControlsTab.controlTabCheckBox[3] = (CheckBox)findViewById(R.id.controlenable4);
        ControlsTab.controlTabCheckBox[4] = (CheckBox)findViewById(R.id.controlenable5);
//        ControlsTab.controlTabCheckBox[5] = (CheckBox)findViewById(R.id.controlenable6);

        ControlsTab.activateChannelbox[1]= (CheckBox)findViewById(R.id.activatech1);
        ControlsTab.activateChannelbox[2]= (CheckBox)findViewById(R.id.activatech2);
        ControlsTab.activateChannelbox[3]= (CheckBox)findViewById(R.id.activatech3);
        ControlsTab.activateChannelbox[4]= (CheckBox)findViewById(R.id.activatech4);
        ControlsTab.activateChannelbox[5]= (CheckBox)findViewById(R.id.activatech5);
        ControlsTab.activateChannelbox[6]= (CheckBox)findViewById(R.id.activatech6);
        ControlsTab.activateChannelbox[7]= (CheckBox)findViewById(R.id.activatech7);
        ControlsTab.activateChannelbox[8]= (CheckBox)findViewById(R.id.activatech8);
        ControlsTab.activateChannelbox[9]= (CheckBox)findViewById(R.id.activatech9);
        ControlsTab.activateChannelbox[10]= (CheckBox)findViewById(R.id.activatech10);
        ControlsTab.activateChannelbox[11]= (CheckBox)findViewById(R.id.activatech11);
        ControlsTab.activateChannelbox[12]= (CheckBox)findViewById(R.id.activatech12);
        ControlsTab.activateChannelbox[13]= (CheckBox)findViewById(R.id.activatech13);
        ControlsTab.activateChannelbox[14]= (CheckBox)findViewById(R.id.activatech14);
        ControlsTab.activateChannelbox[15]= (CheckBox)findViewById(R.id.activatech15);
        ControlsTab.activateChannelbox[16]= (CheckBox)findViewById(R.id.activatech16);

        ControlsTab.deactivateChannelsBox[1]= (CheckBox)findViewById(R.id.deactivatech1);
        ControlsTab.deactivateChannelsBox[2]= (CheckBox)findViewById(R.id.deactivatech2);
        ControlsTab.deactivateChannelsBox[3]= (CheckBox)findViewById(R.id.deactivatech3);
        ControlsTab.deactivateChannelsBox[4]= (CheckBox)findViewById(R.id.deactivatech4);
        ControlsTab.deactivateChannelsBox[5]= (CheckBox)findViewById(R.id.deactivatech5);
        ControlsTab.deactivateChannelsBox[6]= (CheckBox)findViewById(R.id.deactivatech6);
        ControlsTab.deactivateChannelsBox[7]= (CheckBox)findViewById(R.id.deactivatech7);
        ControlsTab.deactivateChannelsBox[8]= (CheckBox)findViewById(R.id.deactivatech8);
        ControlsTab.deactivateChannelsBox[9]= (CheckBox)findViewById(R.id.deactivatech9);
        ControlsTab.deactivateChannelsBox[10]= (CheckBox)findViewById(R.id.deactivatech10);
        ControlsTab.deactivateChannelsBox[11]= (CheckBox)findViewById(R.id.deactivatech11);
        ControlsTab.deactivateChannelsBox[12]= (CheckBox)findViewById(R.id.deactivatech12);
        ControlsTab.deactivateChannelsBox[13]= (CheckBox)findViewById(R.id.deactivatech13);
        ControlsTab.deactivateChannelsBox[14]= (CheckBox)findViewById(R.id.deactivatech14);
        ControlsTab.deactivateChannelsBox[15]= (CheckBox)findViewById(R.id.deactivatech15);
        ControlsTab.deactivateChannelsBox[16]= (CheckBox)findViewById(R.id.deactivatech16);


        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int ntab = Integer.parseInt(v.getTag().toString());
                CheckBox checkBox = (CheckBox)v;
                ControlsTab.activeControl.get(currentTagEdit)[ntab]= checkBox.isChecked();

            }
        };

        SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int currentSeekBarr = Integer.parseInt(seekBar.getTag().toString());
                if( currentSeekBarr >=1){


                    if ( ControlsTab.activeControl.get(currentTagEdit)[currentSeekBarr] !=null) {
                        if (ControlsTab.activeControl.get(currentTagEdit)[currentSeekBarr]) {
                            ControlsTab.controlChannelValue.get(currentTagEdit)[currentSeekBarr] = progress;
                            ControlsTab.TextOnControlsTab[currentSeekBarr - 1].setText(progress + "");
                            MidiIO.sendControl(currentTagEdit, ControlsTab.controlChannelSelected.get(currentTagEdit)[currentSeekBarr], progress);
                        }
                    }
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        };

        AdapterView.OnItemSelectedListener selectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int currentSpinner= Integer.parseInt(parent.getTag().toString());
                if ( currentSpinner >= 1 ){
                    ControlsTab.controlChannelSelected.get(currentTagEdit)[currentSpinner]=position;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(),android.R.layout.simple_spinner_item,getResources().getStringArray(R.array.controls_List));
        for (int i=0 ; i<=ControlsTab.SpinnersOnControlsTab.length-1;i++){

            ControlsTab.SpinnersOnControlsTab[i].setAdapter(adapter);
            ControlsTab.SpinnersOnControlsTab[i].setOnItemSelectedListener(selectedListener);
            ControlsTab.SeeksOnControlsTab[i].setOnSeekBarChangeListener(seekBarChangeListener);
            ControlsTab.controlTabCheckBox[i].setOnClickListener(clickListener);
        }

        onResponseItems();
    }

    public void activateButtonChannel(int chn,Boolean value){

        for (int i=0;i<=16;i++){
            if (ControlsTab.activateChannelValues.get(chn)[i] !=null){
                if(ControlsTab.activateChannelValues.get(chn)[i] && value){
                    channelbuttons.get(i).setBackgroundColor(getResources().getColor(R.color.orangebutton));
                    GlobalV.active.set(i,value);
                }else if(ControlsTab.activateChannelValues.get(chn)[i]) {
                    channelbuttons.get(i).setBackgroundColor(Color.WHITE);
                    GlobalV.active.set(i,value);


                }
            }

            if (ControlsTab.deactivateChannelsValues.get(chn)[i] !=null ){
                if(ControlsTab.deactivateChannelsValues.get(chn)[i] && value ){
                    channelbuttons.get(i).setBackgroundColor(Color.WHITE);
                    channelactive[i]=false;
                    GlobalV.active.set(i,false);
                }else {


                }
            }
        }

    }
    public void onPressChannelButton(View v){
        currentTagEdit = Integer.parseInt(String.valueOf(v.getTag()));
        GlobalV.currentTagEdit= (Button) v;

        if(!editmodeon){
            if (!GlobalV.active.get(currentTagEdit)){
                GlobalV.active.set(currentTagEdit,true);

                v.setBackgroundColor(getResources().getColor(R.color.orangebutton));
                activateButtonChannel(currentTagEdit, true);

            }else{
                GlobalV.active.set(currentTagEdit,false);
                v.setBackgroundColor(Color.WHITE);
                activateButtonChannel(currentTagEdit,false);
            }



        }else {

            readValues();
            layouts[0].setVisibility(View.INVISIBLE);
            layouts[1].setVisibility(View.VISIBLE);

            buttonedit.setBackgroundColor(Color.LTGRAY);
            buttonedit.setText("Save");

            inedit = true;

        }
    }
    public void onEditPress(View v){
        buttonedit= (Button) v;
        if (!editmodeon) {
            editmodeon = true;
            v.setBackgroundColor(Color.BLUE);
        }else {
            editmodeon =false;
            v.setBackgroundColor(Color.LTGRAY);
        }

        if (inedit){

            if (ChannelEditor.newname.getText().toString().equals("")){
                GlobalV.currentTagEdit.setText(ChannelEditor.programerSpinner.getSelectedItem().toString());

            }else {

                GlobalV.currentTagEdit.setText(ChannelEditor.newname.getText());
            }


            layouts[1].setVisibility(View.INVISIBLE);
            layouts[0].setVisibility(View.VISIBLE);
            buttonedit.setText("Edit mode");
            ChannelEditor.inProgram.setChecked(false);
            ChannelEditor.inBanks.setChecked(false);
            inedit=false;
        }

    }
    public void panic(View v){
        MidiIO.panicAll();
    }
    public void configurationView(View v){
        Intent config = new Intent(this,Configuration.class);
        startActivity(config);
    }


    // -------------- Edit  Functions ----------

    public void readValues(){


        Runnable values = new Runnable() {
            @Override
            public void run() {
                ChannelEditor.currentChannelControlsValues = GlobalV.controls.get(currentTagEdit);
                ChannelEditor.volumeSeek.setProgress(ChannelEditor.currentChannelControlsValues[7]);
                sustainButton();
                ChannelEditor.channelSpinner.setSelection(GlobalV.channelIn[currentTagEdit]);
                ChannelEditor.programerSpinner.setSelection(GlobalV.programID[currentTagEdit]);
                ChannelEditor.msbSpinner.setSelection(ChannelEditor.currentChannelControlsValues[0]);
                ChannelEditor.lsbSpinner.setSelection(ChannelEditor.currentChannelControlsValues[32]);
                for (int i=0;i<=ControlsTab.SpinnersOnControlsTab.length-1;i++){
                    ControlsTab.SpinnersOnControlsTab[i].setSelection(ControlsTab.controlChannelSelected.get(currentTagEdit)[i + 1]);
                    ControlsTab.SeeksOnControlsTab[i].setProgress(ControlsTab.controlChannelValue.get(currentTagEdit)[i + 1]);
                    if (ControlsTab.activeControl.get(currentTagEdit)[i+1] != null){
                        ControlsTab.controlTabCheckBox[i].setChecked(ControlsTab.activeControl.get(currentTagEdit)[i + 1]);
                    }else {
                        ControlsTab.controlTabCheckBox[i].setChecked(false);
                    }
                    ControlsTab.TextOnControlsTab[i].setText(String.valueOf(ControlsTab.controlChannelValue.get(currentTagEdit)[i+1]));
                }

                for (int o=1; o<=17;o++){

                    if (ControlsTab.activateChannelValues.get(currentTagEdit)[o] != null)
                    {
                        ControlsTab.activateChannelbox[o].setChecked( ControlsTab.activateChannelValues.get(currentTagEdit)[o]);
                    }
                    if (ControlsTab.deactivateChannelsValues.get(currentTagEdit)[o] != null)
                    {
                        ControlsTab.deactivateChannelsBox[o].setChecked( ControlsTab.deactivateChannelsValues.get(currentTagEdit)[o]);
                    }

                }

            }
        };

//        AsyncTask.execute(values);
        runOnUiThread(values);
    }

    public void onResponseItems() {
        ChannelEditor.channelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ChannelEditor.channelPicker.setValue(i);
                GlobalV.channelIn[currentTagEdit] = i;
//                channelInput.set(currentTagEdit, i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        ChannelEditor.programerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ChannelEditor.programPicker.setValue(i);
                GlobalV.programID[currentTagEdit] = i;
                ChannelEditor.setprogram();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        ChannelEditor.msbSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ChannelEditor.msbPicker.setValue(i);
                saveControlValue(0, i);
                MidiIO.setMSB(currentTagEdit, i);
                ChannelEditor. setprogram();

//                midiio.setMSB(currentTagEdit, i);
//                midiio.setProgram(currentTagEdit, programerList.get(currentTagEdit));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        ChannelEditor.lsbSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ChannelEditor. lsbPicker.setValue(i);
                saveControlValue(32, i);
                MidiIO.setLSB(currentTagEdit, i);
                ChannelEditor.setprogram();
//                midiio.setLSB(currentTagEdit, i);
//                midiio.setProgram(currentTagEdit, programerList.get(currentTagEdit) - 1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        ChannelEditor.channelPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                ChannelEditor.channelSpinner.setSelection(i1);
            }
        });

        EditText channeltext = (EditText) ChannelEditor.channelPicker.getChildAt(1);
        channeltext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals(""))
                    ChannelEditor.channelSpinner.setSelection(Integer.parseInt(s.toString()));
            }
        });


        ChannelEditor. programPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                ChannelEditor.programerSpinner.setSelection(i1);
            }
        });

        EditText programtext = (EditText) ChannelEditor.programPicker.getChildAt(1);
        programtext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals(""))
                    ChannelEditor. programerSpinner.setSelection(Integer.parseInt(s.toString()));
            }
        });

        ChannelEditor.lsbPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                ChannelEditor.lsbSpinner.setSelection(i1);

            }
        });

        EditText lsbtext = (EditText) ChannelEditor.lsbPicker.getChildAt(1);
        lsbtext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals(""))
                    ChannelEditor.lsbSpinner.setSelection(Integer.parseInt(s.toString()));
            }
        });

        ChannelEditor.msbPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                ChannelEditor.msbSpinner.setSelection(i1);

            }
        });

        EditText msbtext = (EditText)ChannelEditor.msbPicker.getChildAt(1);
        msbtext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals(""))
                    ChannelEditor.msbSpinner.setSelection(Integer.parseInt(s.toString()));
            }
        });

        ChannelEditor.volumeSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                ChannelEditor.volumetxt.setText(i + "");
                ChannelEditor.currentChannelControlsValues[7] = i;
                MidiIO.setVolume(currentTagEdit, i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        ChannelEditor.inBanks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        ChannelEditor.inProgram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }


    public void tabSelect(View view){
        int ntab = Integer.parseInt(view.getTag().toString())-1;
        for (int i=0; i<=2;i++){
            if (ntab == i){
                ChannelEditor.tabs[i].setBackgroundColor(getResources().getColor(R.color.greenbutton));
                ChannelEditor.tabsObjs[i].setVisibility(View.VISIBLE);

            }else {
                ChannelEditor.tabs[i].setBackgroundColor(Color.WHITE);
                ChannelEditor.tabsObjs[i].setVisibility(View.INVISIBLE);
            }
        }

    }
    public void sustain(View v){

        if (ChannelEditor.currentChannelControlsValues[64] <=63) {

            saveControlValue(64,127);
            MidiIO.sendControl(currentTagEdit,64,127);
            sustainButton();

        }else {

            saveControlValue(64, 0);
            MidiIO.sendControl(currentTagEdit, 64, 0);
            sustainButton();

        }
    }
    public void sustainButton(){
        if (ChannelEditor.currentChannelControlsValues[64] <=63){
            ChannelEditor.sustain.setBackgroundColor(Color.WHITE);
        }else {
            ChannelEditor.sustain.setBackgroundColor(getResources().getColor(R.color.greenbutton));
        }

    }
    public void saveControlValue(int control,int value){
        GlobalV.controls.get(currentTagEdit)[control]=value;
    }
    public void channelActivation(View v){
        CheckBox checked = (CheckBox)v;
        int tag = Integer.parseInt(checked.getTag().toString());

        if (ControlsTab.activateChannelValues.get(currentTagEdit)[tag]==null){
            ControlsTab.activateChannelValues.get(currentTagEdit)[tag]=false;
        }
        if ( ControlsTab.deactivateChannelsValues.get(currentTagEdit)[tag]==null){
            ControlsTab.deactivateChannelsValues.get(currentTagEdit)[tag]=false;
        }

        if (ControlsTab.deactivateChannelsValues.get(currentTagEdit)[tag]){
            ControlsTab.deactivateChannelsValues.get(currentTagEdit)[tag]=false;
            ControlsTab.deactivateChannelsBox[tag].setChecked(false);
            ControlsTab.activateChannelValues.get(currentTagEdit)[tag]=checked.isChecked();

        }else {
            ControlsTab.activateChannelValues.get(currentTagEdit)[tag]=checked.isChecked();

        }


    }
    public void channelDeactivation(View v) {
        CheckBox checked = (CheckBox)v;
        int tag = Integer.parseInt(checked.getTag().toString());

        if (ControlsTab.activateChannelValues.get(currentTagEdit)[tag]==null){
            ControlsTab.activateChannelValues.get(currentTagEdit)[tag]=false;
        }
        if ( ControlsTab.deactivateChannelsValues.get(currentTagEdit)[tag]==null){
            ControlsTab.deactivateChannelsValues.get(currentTagEdit)[tag]=false;
        }

        if (ControlsTab.activateChannelValues.get(currentTagEdit)[tag]){
            ControlsTab.activateChannelValues.get(currentTagEdit)[tag]=false;
            ControlsTab.activateChannelbox[tag].setChecked(false);
            ControlsTab.deactivateChannelsValues.get(currentTagEdit)[tag]=checked.isChecked();
        }else {
            ControlsTab.deactivateChannelsValues.get(currentTagEdit)[tag]=checked.isChecked();

        }
    }
    public void readProgram(int chn, final int value){
        if (ChannelEditor.inProgram.isChecked()){
            if (chn == GlobalV.channelIn[currentTagEdit]  ) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ChannelEditor.programerSpinner.setSelection(value + 1);
                    }
                });

                MidiIO.setProgram(chn, value);
            }
        }

    }
    public void readControl(final int chn, final int control, final int value){

       runOnUiThread(new Runnable() {
           @Override
           public void run() {
               if (ChannelEditor.inBanks.isChecked()){
                   if (chn == GlobalV.channelIn[currentTagEdit]) {
                       switch (control) {
                           case 0:
                               ChannelEditor.msbSpinner.setSelection(value);
                               MidiIO.sendControl(chn, control, value);

                               break;

                           case 7:
                               ChannelEditor.volumeSeek.setProgress(value);
                               changecontrolvalue(7,value);

                               break;
                           case 32:
                               ChannelEditor.lsbSpinner.setSelection(value);
                               MidiIO.sendControl(chn, control, value);
                               break;
                           case 71:
                               changecontrolvalue(71,value);
                               break;

                           case 74:
                               changecontrolvalue(74, value);

                               break;
                           case 91:
                               changecontrolvalue(91,value);
                               break;
                           case 93:
                               changecontrolvalue(93,value);
                               break;

                           default:
                               MidiIO.sendControl(chn, control, value);

                       }
                   }

               }
           }

           public void changecontrolvalue(int control,int value) {

               if (ControlsTab.controlChannelSelected.get(currentTagEdit)[1] == control){ ControlsTab.SeeksOnControlsTab[0].setProgress(value);}
               if (ControlsTab.controlChannelSelected.get(currentTagEdit)[2] == control){ ControlsTab.SeeksOnControlsTab[1].setProgress(value);}
               if (ControlsTab.controlChannelSelected.get(currentTagEdit)[3] == control){ ControlsTab.SeeksOnControlsTab[2].setProgress(value);}
               if (ControlsTab.controlChannelSelected.get(currentTagEdit)[4] == control){ ControlsTab.SeeksOnControlsTab[3].setProgress(value);}
               if (ControlsTab.controlChannelSelected.get(currentTagEdit)[5] == control){ ControlsTab.SeeksOnControlsTab[4].setProgress(value);}
               MidiIO.sendControl(chn, control, value);

           }
       });


    }


    @Override
    public void onBackPressed() {

    }

}