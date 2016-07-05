package com.midistudio.structure;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Created by kevn on 23/10/15.
 */
public class FileIO {

    public static ArrayList<Object> objects;
    public static Context context;


    public static void makeFolders(){

        String enviromentSD = Environment.getExternalStorageDirectory().toString();
        if (Environment.getExternalStorageState().equals("mounted")) {
            new File(enviromentSD + "/MidiStudio").mkdir();
            new File(enviromentSD + "/MidiStudio/save").mkdirs();
        }

    }


    public static void saveobject(String name){

            String enviromentSD = Environment.getExternalStorageDirectory().toString();
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(enviromentSD+"/MidiStudio/save/"+name+".mst"));

            outputStream.writeObject("MidiStudio");
            outputStream.writeInt(1);
            outputStream.writeInt(0);
//            outputStream.writeObject(MidiStudio.buttonName);
            outputStream.writeObject(GlobalV.channelIn);
            outputStream.writeObject(GlobalV.programID);
            outputStream.writeObject(GlobalV.controls);
            outputStream.writeObject(ControlsTab.activateChannelValues);
            outputStream.writeObject(ControlsTab.activeControl);
            outputStream.writeObject(ControlsTab.controlChannelSelected);
            outputStream.writeObject(ControlsTab.controlChannelValue);
            outputStream.writeObject(ControlsTab.deactivateChannelsValues);
            outputStream.close();
            toast.show("the file: " +name+ ".mst \nsave complete");

        } catch (IOException e) {
            e.printStackTrace();
            toast.show("error to save file");
        }

    }

    public static  void readobjects(){

        try {
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("/sdcard/objects.txt"));

            String a= (String) inputStream.readObject();
            int versionMajor = (int) inputStream.readInt();
            int versionMinor = (int) inputStream.readInt();
//            MidiStudio.buttonName = (String[]) inputStream.readObject();
            GlobalV.channelIn = (int[]) inputStream.readObject();
            GlobalV.programID = (int[]) inputStream.readObject();
            GlobalV.controls = (ArrayList<int[]>) inputStream.readObject();
            ControlsTab.activateChannelValues = (ArrayList<Boolean[]>) inputStream.readObject();
            ControlsTab.activeControl= (ArrayList<Boolean[]>) inputStream.readObject();
            ControlsTab.controlChannelSelected = (ArrayList<int[]>) inputStream.readObject();
            ControlsTab.controlChannelValue = (ArrayList<int[]>) inputStream.readObject();
            ControlsTab.deactivateChannelsValues = (ArrayList<Boolean[]>) inputStream.readObject();
            toast.show("file read complete");

        } catch (IOException e) {
            e.printStackTrace();
            toast.show("error to read file");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            toast.show("error to read file");

        }


    }




}