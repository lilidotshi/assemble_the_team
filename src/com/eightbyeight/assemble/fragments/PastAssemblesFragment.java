package com.eightbyeight.assemble.fragments;

import com.eightbyeight.assemble.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Will be a history of all ASSEMBLE! calls both sent and responded to.
 * @author shil
 *
 */
public class PastAssemblesFragment extends Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.past_assembles_fragment, container, false);
    }
}
