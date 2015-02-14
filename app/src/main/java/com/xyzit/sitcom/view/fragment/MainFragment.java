package com.xyzit.sitcom.view.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by kimveasna on 01/02/2015.
 */
public class MainFragment extends Fragment {

    public static final String ARG_FRAGMENT_ID = "fragment_id";


    //private static String[] mFrames;

    public MainFragment() {
        // Empty constructor required for fragment subclasses
        //mFrames = this.getActivity().getResources().getStringArray(R.array.frames);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        int frameId = getArguments().getInt(ARG_FRAGMENT_ID);


        View rootView = inflater.inflate(frameId, container, false);



        return rootView;
    }
}