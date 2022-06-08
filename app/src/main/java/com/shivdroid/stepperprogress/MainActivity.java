package com.shivdroid.stepperprogress;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int steps = 6;
        int completed = 5;
        ArrayList<String> list = new ArrayList<>();
        ArrayList<String> sublist = new ArrayList<>();
        for (int i=1;i<=6;i++) {
            list.add("MileStone "+i);
            sublist.add("checkpoint "+i);
        }

        VerticalStepperProgress stepperProgress = findViewById(R.id.my_progress);
       stepperProgress.setProgressWidth(4);
        stepperProgress.setStepSize(30);
        stepperProgress.setTickImageSize(24);
        stepperProgress.createStepper(steps,list,sublist,completed);
    }
}