package com.shivdroid.stepperprogress;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import java.util.ArrayList;
import java.util.List;

public class VerticalStepperProgress extends RelativeLayout {

    private int stepCount;
    private int stepsCompleted;
    private List<String> labelList;
    private List<String> subLabelList;
    private ProgressBar mProgress;
    private LinearLayout labelLayout;
    private Context mContext;
    private View parentView;
    private int TICK_IMAGE_SIZE = 16;
    private float STEP_SIZE = 50;
    private int PROGRESS_WIDTH = 4;
    private int LABEL_COLOR_ID = -1;
    private int SUB_LABEL_COLOR_ID = -1;
    private int LABEL_TEXT_SIZE = 14;
    private int SUB_LABEL_TEXT_SIZE = 12;
    private int progress_margin = 0;

    public VerticalStepperProgress(Context context) {
        super(context);
        LayoutInflater mInflater = LayoutInflater.from(context);
        View view = mInflater.inflate(R.layout.vertical_stepper_layout  , this, false);
        mContext = context;
        initViews(view);
    }

    public VerticalStepperProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater mInflater = LayoutInflater.from(context);
        View view = mInflater.inflate(R.layout.vertical_stepper_layout  , this, false);
        mContext = context;
        initViews(view);
    }

    public VerticalStepperProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater mInflater = LayoutInflater.from(context);
        View view = mInflater.inflate(R.layout.vertical_stepper_layout  , this, false);
        mContext = context;
        initViews(view);
    }

    public VerticalStepperProgress(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    private void initViews(View view){
        parentView = view;
        mProgress = view.findViewById(R.id.progress_bar);
        labelLayout = view.findViewById(R.id.labelLayout);
    }

    public void createStepper(int stepCount, ArrayList<String> labelsList, int stepDone){
        this.stepCount = stepCount;
        this.labelList = labelsList;
        this.stepsCompleted = stepDone;
        drawSteps();
    }

    public void createStepper(int stepCount, ArrayList<String> labelsList, ArrayList<String> subLabelList, int stepDone){
        this.stepCount = stepCount;
        this.labelList = labelsList;
        this.subLabelList = subLabelList;
        this.stepsCompleted = stepDone;
        drawSteps();
    }

    private void drawSteps(){
        drawLabels();
        //setProgressBarProperties();
        int progress = (int) (((double)stepsCompleted-1) / ((double) stepCount-1)*100);
        Log.d("Label","progress is " + (stepsCompleted-1) / (stepCount-1)*100 + " and " + progress );
        mProgress.setProgress(progress);
        this.addView(parentView);
    }

    private void drawLabels(){
        LayoutInflater inflater = (LayoutInflater)
                mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        for(int i=0;i<stepCount;i++){
            View view  = inflater.inflate(R.layout.vertical_stepper_label, null);
            view.setId(100+i);
            TextView labelName = view.findViewById(R.id.labelText);
            TextView subLabelName = view.findViewById(R.id.subLabelText);
            ImageView labelTick = view.findViewById(R.id.tickIcon);
            setLabelProperties(labelName,subLabelName,labelTick);
            labelName.setText(labelList.get(i));
            if(subLabelList!= null && subLabelList.size()>0){
                subLabelName.setVisibility(View.VISIBLE);
                subLabelName.setText(subLabelList.get(i));
            }
            if(i<stepsCompleted){
                labelTick.setImageDrawable(ResourcesCompat.getDrawable(getResources(),R.drawable.ic_check_enable,null));
            }
            if(i!=0) {
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.topMargin = (int) convertDpToPx(STEP_SIZE);
                view.setLayoutParams(params);
            }else{
                view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        progress_margin = view.getHeight() / 2; //height is ready
                        Log.d("Label","margin set is " + progress_margin );
                        setProgressBarProperties();
                    }
                });
            }
            labelLayout.addView(view);
        }
    }

    private void setProgressBarProperties(){
        RelativeLayout.LayoutParams params = (LayoutParams) mProgress.getLayoutParams();
        params.width = (int)convertDpToPx(PROGRESS_WIDTH);
        params.topMargin = progress_margin;
        params.bottomMargin = progress_margin;
        params.leftMargin = (int) convertDpToPx((float)(TICK_IMAGE_SIZE - PROGRESS_WIDTH)/2);
        mProgress.setLayoutParams(params);
        Log.d("Label","margin is " + progress_margin );
    }

    private void setLabelProperties(TextView label, TextView subLabel, ImageView tickIcon){
        label.setTextSize(TypedValue.COMPLEX_UNIT_SP,(float) LABEL_TEXT_SIZE);
        subLabel.setTextSize(TypedValue.COMPLEX_UNIT_SP,(float) SUB_LABEL_TEXT_SIZE);
        if(LABEL_COLOR_ID != -1)label.setTextColor(ContextCompat.getColor(mContext,LABEL_COLOR_ID));
        if(SUB_LABEL_COLOR_ID != -1)subLabel.setTextColor(ContextCompat.getColor(mContext,SUB_LABEL_COLOR_ID));
        tickIcon.getLayoutParams().width = (int) convertDpToPx(TICK_IMAGE_SIZE);
        tickIcon.getLayoutParams().height = (int) convertDpToPx(TICK_IMAGE_SIZE);
        tickIcon.requestLayout();
    }

    public void setTickImageSize(int imageSize){
        TICK_IMAGE_SIZE = imageSize;
    }

    public void setStepSize(int stepSize){
        STEP_SIZE = stepSize;
    }

    public void setProgressWidth(int width){
        PROGRESS_WIDTH = width;
    }

    public void setLabelTextColor(int labelColor){
        LABEL_COLOR_ID = labelColor;
    }

    public void setSubLabelColor(int subLabelColor){
        SUB_LABEL_COLOR_ID = subLabelColor;
    }

    public float convertDpToPx(float dp) {
        return dp * mContext.getResources().getDisplayMetrics().density;
    }
}
