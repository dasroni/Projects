package com.example.smarthub;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TestFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TestFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ImageView testImage;
    private RelativeLayout myLel;
    private Switch mySwitch;
    boolean flag = false;
    boolean flag2 = false;
    boolean flag3 = false;




    public TestFragment() {
        // Required empty public constructor
    }




    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TestFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TestFragment newInstance(String param1, String param2) {
        TestFragment fragment = new TestFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.fragment_test,container,false);


            testImage = v.findViewById(R.id.imageView);
         //   testImage.setBackgroundResource(R.drawable.kitechen);
            myLel = v.findViewById(R.id.rel);
            mySwitch = v.findViewById(R.id.toggleSwitch);



            Bundle extras = getArguments();
            String one = extras.getString(ARG_PARAM1);
            String two = extras.getString(ARG_PARAM2);



            if(two.equals("livingroom")){
                testImage.setBackgroundResource(R.drawable.livingroom);
                mySwitch.setVisibility(View.GONE);

            }
          else if(two.equals("kitchen")){
            testImage.setBackgroundResource(R.drawable.kitechen);
                mySwitch.setVisibility(View.GONE);
           }
          else if(two.equals("bedroom")){
            testImage.setBackgroundResource(R.drawable.bedroom);
                mySwitch.setVisibility(View.GONE);
           }      else if(two.equals("airquality")){
                testImage.setBackgroundResource(R.drawable.airquality);
                mySwitch.setVisibility(View.GONE);
            }else if(two.equals("HUE_study")){
                    testImage.setBackgroundResource(R.drawable.studyroomnormal);
                    mySwitch.setChecked(true);
                    flag = true;
            }else if(two.equals("HUE_bedroom")){
                testImage.setBackgroundResource(R.drawable.bedroomhuenormal);
                mySwitch.setChecked(true);
                flag2 = true;
            }else if(two.equals("HUE_kitchen")){
                testImage.setBackgroundResource(R.drawable.kitchenhuenormal);
                mySwitch.setChecked(true);
                flag3 = true;
            }


        if(flag){
                    mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if(isChecked){

                                testImage.setBackgroundResource(R.drawable.studyroomnormal);

                            }
                            else{

                                testImage.setBackgroundResource(R.drawable.studyroombwl);
                            }
                        }
                    });




            }

        if(flag2){
            mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){

                        testImage.setBackgroundResource(R.drawable.bedroomhuenormal);

                    }
                    else{

                        testImage.setBackgroundResource(R.drawable.bedroomhuebw);
                    }
                }
            });




        }

        if(flag3){
            mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){

                        testImage.setBackgroundResource(R.drawable.kitchenhuenormal);

                    }
                    else{

                        testImage.setBackgroundResource(R.drawable.kitchenhuebw);
                    }
                }
            });




        }









        return v;
    }




}
