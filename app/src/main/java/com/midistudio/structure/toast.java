package com.midistudio.structure;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by kevn on 23/10/15.
 */
public class toast {

   public static Context context;

    public static void show(String text){
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();


    }

}