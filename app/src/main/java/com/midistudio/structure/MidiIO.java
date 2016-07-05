package com.midistudio.structure;

import android.util.Log;


import java.util.ArrayList;

import jp.kshoji.driver.midi.device.MidiInputDevice;
import jp.kshoji.driver.midi.device.MidiOutputDevice;
import jp.kshoji.driver.midi.util.UsbMidiDriver;
import jp.kshoji.javax.sound.midi.usb.UsbMidiDevice;

public class MidiIO  {

    public static ArrayList<UsbMidiDevice> usbMidiDevices;
    public static UsbMidiDriver midiDriver;
    public static ArrayList<MidiOutputDevice> midiout;
    public static ArrayList<MidiInputDevice> midiin;
    public static ArrayList<Integer> channelin;


    public static void sendControl(int chn,int id,int value){
        if (midiout.size() >=1 ) {
            midiout.get(0).sendMidiControlChange(0, chn - 1, id, value);
            Log.w("Control Changer", chn + " " + id + " " + value);
        }
    }
    public static void setProgram(int chn,int value){
        if (midiout.size() >=1 ){
            midiout.get(0).sendMidiProgramChange(0, chn - 1, value);
            Log.e("Program", chn + " " + value);
        }

    }
    public static void setLSB(int chn,int value){
        if (midiout.size() >=1 ) {
            midiout.get(0).sendMidiControlChange(0, chn - 1, 32, value);
            Log.d("LSB", (chn - 1 + " " + value));
        }

    }
    public static void setMSB(int chn,int value){
        if (midiout.size() >=1 ) {
            midiout.get(0).sendMidiControlChange(0, chn - 1, 0, value);
            Log.d("MSB", (chn - 1 + " " + value));
        }
    }
    public static void setVolume(int chn,int value){
        if (midiout.size()>=1 ) {
            midiout.get(0).sendMidiControlChange(0, chn - 1, 7, value);
        }
    }
    public static void setPitch(int chn,int value){
        if (midiout.size()>=1 ) {

            if ( chn +1 == GlobalV.channelIn[1] ) midiout.get(0).sendMidiPitchWheel(0, 0, value);
            if ( chn +1 == GlobalV.channelIn[2] ) midiout.get(0).sendMidiPitchWheel(0, 1, value);
            if ( chn +1 == GlobalV.channelIn[3] ) midiout.get(0).sendMidiPitchWheel(0, 2, value);
            if ( chn +1 == GlobalV.channelIn[4] ) midiout.get(0).sendMidiPitchWheel(0, 3, value);
            if ( chn +1 == GlobalV.channelIn[5] ) midiout.get(0).sendMidiPitchWheel(0, 4, value);
            if ( chn +1 == GlobalV.channelIn[6] ) midiout.get(0).sendMidiPitchWheel(0, 5, value);
            if ( chn +1 == GlobalV.channelIn[7] ) midiout.get(0).sendMidiPitchWheel(0, 6, value);
            if ( chn +1 == GlobalV.channelIn[8] ) midiout.get(0).sendMidiPitchWheel(0, 7, value);
            if ( chn +1 == GlobalV.channelIn[9] ) midiout.get(0).sendMidiPitchWheel(0, 8, value);
            if ( chn +1 == GlobalV.channelIn[10] ) midiout.get(0).sendMidiPitchWheel(0, 9, value);
            if ( chn +1 == GlobalV.channelIn[11] ) midiout.get(0).sendMidiPitchWheel(0, 10, value);
            if ( chn +1 == GlobalV.channelIn[12] ) midiout.get(0).sendMidiPitchWheel(0, 1, value);
            if ( chn +1 == GlobalV.channelIn[13] ) midiout.get(0).sendMidiPitchWheel(0, 12, value);
            if ( chn +1 == GlobalV.channelIn[14] ) midiout.get(0).sendMidiPitchWheel(0, 13, value);
            if ( chn +1 == GlobalV.channelIn[15] ) midiout.get(0).sendMidiPitchWheel(0, 14, value);
            if ( chn +1 == GlobalV.channelIn[16] ) midiout.get(0).sendMidiPitchWheel(0, 15, value);

            Log.d("Pitch",chn +" "+ value);
        }
    }
    public static void sendMixNote(int status,int chn,int dat,int value){

        if (!GlobalV.RtpMode) {
            //Log.d("midi", "Local");
            switch (status) {
                case 0:
                    if (chn + 1 == GlobalV.channelIn[1])
                        midiout.get(0).sendMidiNoteOff(0, 0, dat, value);
                    if (chn + 1 == GlobalV.channelIn[2])
                        midiout.get(0).sendMidiNoteOff(0, 1, dat, value);
                    if (chn + 1 == GlobalV.channelIn[3])
                        midiout.get(0).sendMidiNoteOff(0, 2, dat, value);
                    if (chn + 1 == GlobalV.channelIn[4])
                        midiout.get(0).sendMidiNoteOff(0, 3, dat, value);
                    if (chn + 1 == GlobalV.channelIn[5])
                        midiout.get(0).sendMidiNoteOff(0, 4, dat, value);
                    if (chn + 1 == GlobalV.channelIn[6])
                        midiout.get(0).sendMidiNoteOff(0, 5, dat, value);
                    if (chn + 1 == GlobalV.channelIn[7])
                        midiout.get(0).sendMidiNoteOff(0, 6, dat, value);
                    if (chn + 1 == GlobalV.channelIn[8])
                        midiout.get(0).sendMidiNoteOff(0, 7, dat, value);
                    if (chn + 1 == GlobalV.channelIn[9])
                        midiout.get(0).sendMidiNoteOff(0, 8, dat, value);
                    if (chn + 1 == GlobalV.channelIn[10])
                        midiout.get(0).sendMidiNoteOff(0, 9, dat, value);
                    if (chn + 1 == GlobalV.channelIn[11])
                        midiout.get(0).sendMidiNoteOff(0, 10, dat, value);
                    if (chn + 1 == GlobalV.channelIn[12])
                        midiout.get(0).sendMidiNoteOff(0, 11, dat, value);
                    if (chn + 1 == GlobalV.channelIn[13])
                        midiout.get(0).sendMidiNoteOff(0, 12, dat, value);
                    if (chn + 1 == GlobalV.channelIn[14])
                        midiout.get(0).sendMidiNoteOff(0, 13, dat, value);
                    if (chn + 1 == GlobalV.channelIn[15])
                        midiout.get(0).sendMidiNoteOff(0, 14, dat, value);
                    if (chn + 1 == GlobalV.channelIn[16])
                        midiout.get(0).sendMidiNoteOff(0, 15, dat, value);


                    break;
                case 1:


                    if (chn + 1 == GlobalV.channelIn[1]) if (GlobalV.active.get(1)) {
                        midiout.get(0).sendMidiNoteOn(0, 0, dat, value);
                    }
                    if (chn + 1 == GlobalV.channelIn[2]) if (GlobalV.active.get(2)) {
                        midiout.get(0).sendMidiNoteOn(0, 1, dat, value);
                    }
                    if (chn + 1 == GlobalV.channelIn[3]) if (GlobalV.active.get(3)) {
                        midiout.get(0).sendMidiNoteOn(0, 2, dat, value);
                    }
                    if (chn + 1 == GlobalV.channelIn[4]) if (GlobalV.active.get(4)) {
                        midiout.get(0).sendMidiNoteOn(0, 3, dat, value);
                    }
                    if (chn + 1 == GlobalV.channelIn[5]) if (GlobalV.active.get(5)) {
                        midiout.get(0).sendMidiNoteOn(0, 4, dat, value);
                    }
                    if (chn + 1 == GlobalV.channelIn[6]) if (GlobalV.active.get(6)) {
                        midiout.get(0).sendMidiNoteOn(0, 5, dat, value);
                    }
                    if (chn + 1 == GlobalV.channelIn[7]) if (GlobalV.active.get(7)) {
                        midiout.get(0).sendMidiNoteOn(0, 6, dat, value);
                    }
                    if (chn + 1 == GlobalV.channelIn[8]) if (GlobalV.active.get(8)) {
                        midiout.get(0).sendMidiNoteOn(0, 7, dat, value);
                    }
                    if (chn + 1 == GlobalV.channelIn[9]) if (GlobalV.active.get(9)) {
                        midiout.get(0).sendMidiNoteOn(0, 8, dat, value);
                    }
                    if (chn + 1 == GlobalV.channelIn[10]) if (GlobalV.active.get(10)) {
                        midiout.get(0).sendMidiNoteOn(0, 9, dat, value);
                    }
                    if (chn + 1 == GlobalV.channelIn[11]) if (GlobalV.active.get(11)) {
                        midiout.get(0).sendMidiNoteOn(0, 10, dat, value);
                    }
                    if (chn + 1 == GlobalV.channelIn[12]) if (GlobalV.active.get(12)) {
                        midiout.get(0).sendMidiNoteOn(0, 11, dat, value);
                    }
                    if (chn + 1 == GlobalV.channelIn[13]) if (GlobalV.active.get(13)) {
                        midiout.get(0).sendMidiNoteOn(0, 12, dat, value);
                    }
                    if (chn + 1 == GlobalV.channelIn[14]) if (GlobalV.active.get(14)) {
                        midiout.get(0).sendMidiNoteOn(0, 13, dat, value);
                    }
                    if (chn + 1 == GlobalV.channelIn[15]) if (GlobalV.active.get(15)) {
                        midiout.get(0).sendMidiNoteOn(0, 14, dat, value);
                    }
                    if (chn + 1 == GlobalV.channelIn[16]) if (GlobalV.active.get(16)) {
                        midiout.get(0).sendMidiNoteOn(0, 15, dat, value);
                    }


                    break;
                default:
            }

        }
    }


    public static void recivecontrols(int chn,int control,int value) {
        if (midiout.size() >= 1) {

            if (chn + 1 == GlobalV.channelIn[1]) midiout.get(0).sendMidiPitchWheel(0, 0, value);
            if (chn + 1 == GlobalV.channelIn[2]) midiout.get(0).sendMidiPitchWheel(0, 1, value);
            if (chn + 1 == GlobalV.channelIn[3]) midiout.get(0).sendMidiPitchWheel(0, 2, value);
            if (chn + 1 == GlobalV.channelIn[4]) midiout.get(0).sendMidiPitchWheel(0, 3, value);
            if (chn + 1 == GlobalV.channelIn[5]) midiout.get(0).sendMidiPitchWheel(0, 4, value);
            if (chn + 1 == GlobalV.channelIn[6]) midiout.get(0).sendMidiPitchWheel(0, 5, value);
            if (chn + 1 == GlobalV.channelIn[7]) midiout.get(0).sendMidiPitchWheel(0, 6, value);
            if (chn + 1 == GlobalV.channelIn[8]) midiout.get(0).sendMidiPitchWheel(0, 7, value);
            if (chn + 1 == GlobalV.channelIn[9]) midiout.get(0).sendMidiPitchWheel(0, 8, value);
            if (chn + 1 == GlobalV.channelIn[10]) midiout.get(0).sendMidiPitchWheel(0, 9, value);
            if (chn + 1 == GlobalV.channelIn[11]) midiout.get(0).sendMidiPitchWheel(0, 10, value);
            if (chn + 1 == GlobalV.channelIn[12]) midiout.get(0).sendMidiPitchWheel(0, 1, value);
            if (chn + 1 == GlobalV.channelIn[13]) midiout.get(0).sendMidiPitchWheel(0, 12, value);
            if (chn + 1 == GlobalV.channelIn[14]) midiout.get(0).sendMidiPitchWheel(0, 13, value);
            if (chn + 1 == GlobalV.channelIn[15]) midiout.get(0).sendMidiPitchWheel(0, 14, value);
            if (chn + 1 == GlobalV.channelIn[16]) midiout.get(0).sendMidiPitchWheel(0, 15, value);

        }
    }

    public static void panicAll(){
        if (midiout.size() >=1){
           for (int i=0;i<=16;i++ ) {
               midiout.get(0).sendMidiControlChange(0, i, 123, 127);
               midiout.get(0).sendMidiControlChange(0,i,120,127);
               Log.d("PANIC", "MIDI OFF ALL NOTES:" +i);
           }
        }
    }

}