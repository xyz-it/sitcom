package com.xyzit.sitcom.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xyzit.sitcom.model.DummySampleModel;

import org.robobinding.ViewBinder;

/**
 * Created by kimveasna on 01/02/2015.
 */
public class MainFragment extends AbstractFragment {

    public static final String ARG_FRAGMENT_ID = "fragment_id";

    private DummySampleModel model;

    private View innerView;

    //private static String[] mFrames;

    public MainFragment() {
        // Empty constructor required for fragment subclasses
        //mFrames = this.getActivity().getResources().getStringArray(R.array.frames);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(model == null)
            model = new DummySampleModel();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if(innerView!=null)
            return innerView;

        int frameId = getArguments().getInt(ARG_FRAGMENT_ID);


        //View rootView = inflater.inflate(frameId, container, false);

        ViewBinder viewBinder = createViewBinder();
        innerView = viewBinder.inflateAndBindWithoutAttachingToRoot(frameId, model, container);

        return innerView;
    }
}