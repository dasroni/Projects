package com.example.smarthub;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link interActiveFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class interActiveFragment extends Fragment {

    private MYViewModel myViewModel;
    private TextView thisText;
    private ProgressBar progressBarAnimation;
    private ObjectAnimator progressAnimator;
    private ImageView background;
    private TextView motionText;
    private boolean flag = false;
    private boolean stoveFlag = false;
    private boolean gasFlag = false;



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public interActiveFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment interActiveFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static interActiveFragment newInstance(String param1, String param2) {
        interActiveFragment fragment = new interActiveFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_inter_active,container,false);

        background = v.findViewById(R.id.gasImageView);
        thisText = v.findViewById(R.id.gasImageText);
        progressBarAnimation = v.findViewById(R.id.progressBar);
        motionText = v.findViewById(R.id.motionText);


        Bundle extras = getArguments();
        String one = extras.getString(ARG_PARAM1);
        String two = extras.getString(ARG_PARAM2);

        if(two.equals("livingroom")){
           background.setBackgroundResource(R.drawable.livingroom);
           progressBarAnimation.setVisibility(View.GONE);
           motionText.setVisibility(View.GONE);
           flag = true;
        }
        else if(two.equals("gasleak")){
            background.setBackgroundResource(R.drawable.gasleak);
            motionText.setVisibility(View.GONE);
            gasFlag = true;

        }else if(two.equals("stoves")){
            background.setBackgroundResource(R.drawable.stoves);
            progressBarAnimation.setVisibility(View.GONE);
            stoveFlag = true;
        }







        //  progressAnimator = ObjectAnimator.ofInt(progressBarAnimation,"progress",1,0);

/*

        progressAnimator.setDuration(1);
        progressAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                progressBarAnimation.setVisibility(View.GONE);
            }

        });
*/




        return v;
    }

 /*   @SuppressLint("FragmentLiveDataObserve")
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        myViewModel = new ViewModelProvider(requireActivity()).get(MYViewModel.class);
        myViewModel.getMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                thisText.setText(s);
            }
        });
    }*/

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        myViewModel = new ViewModelProvider(requireActivity()).get(MYViewModel.class);
        myViewModel.getMessage().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(flag){
                    String res =  processData(s,0);
                    String TemperatureMeasurementStr = res+ "\u2109";
                    thisText.setText(TemperatureMeasurementStr);
                }else if(stoveFlag){
                    String res =  processData(s,1);
                    String text = "CLOSE";

                    if(res.equals("1")){

                        String motion = processData(s, 3);
                        if(motion.equals("4095.00")){
                            text = "OPEN";
                            motionText.setText("Motion Detected");
                            motionText.setTextColor(getResources().getColor(R.color.good));

                        }
                        else {


                          // motionText.setVisibility(View.GONE);
                            motionText.setText("");
                           //motionText.setTextColor(getResources().getColor(R.color.warning));
                        }

                    }



                    thisText.setText(text);
                }
                else if(gasFlag){

                    String res =  processData(s,2);
                    int prog = progressBar(res);

                    thisText.setText(prog+"%");
                    progressBarAnimation.setProgress(prog);
                }


            }

            public String processData(String s, int choice){
                String result= null;
                String[] tokens = s.split(",");

                switch (choice){
                    case 0: result = tokens[5];
                        break;
                    case 1: result = tokens[7];
                        break;
                    case 2: result = tokens[3];
                        break;
                    case 3: result = tokens[1];
                        break;


                }

                return  result;
            }

            public int progressBar(String s){
                int progress = 0;
                double numb = Double.parseDouble(s);
                double result = numb-1700;

                for(double i =0 ; i <  result; i= i + 3.4){

                    progress++;
                }

                if(progress > 99)
                     progress = 100;



                return progress;
            }



        });




    }


    private void simulateFive() {
        final Handler handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                       // myViewModel.sendMessage("How are You ?");
                    }
                });

            }
        }).start();
    }



}
