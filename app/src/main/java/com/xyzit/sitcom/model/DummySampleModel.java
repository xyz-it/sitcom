package com.xyzit.sitcom.model;

import org.robobinding.annotation.PresentationModel;

/**
 * Created by kimveasna on 22/02/2015.
 */
@PresentationModel
public class DummySampleModel {

    private String test = "Test string";

    public String getTest() {
        return test;
    }

    public void setTest(String newTest) {
        test = newTest;
    }
}
