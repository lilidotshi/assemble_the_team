package com.eightbyeight.assemble.listeners;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class CustomListListener implements OnItemClickListener{

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        System.out.println("Kind of view "+view.getClass() + " and the parent " + parent.getClass());
        Toast.makeText(parent.getContext(), new String("Position: " + position), Toast.LENGTH_SHORT).show();
        if (position == 0){
                   }
    }

}
