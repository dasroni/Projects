package com.example.smarthub;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link emptyFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class emptyFrag extends Fragment {

    private MYViewModel myViewModel;
    private FragmentEmptyListener listener;





    private boolean flag = false;
    private boolean stoveFlag = false;
    private boolean gasFlag = false;
    int accessCode = 0;


    private Button emptyButton;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public emptyFrag() {
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
    public static emptyFrag newInstance(String param1, String param2) {
        emptyFrag fragment = new emptyFrag();
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


    public interface FragmentEmptyListener {

        void emptyFragSent(int code);


    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_empty,container,false);

        Bundle extras = getArguments();
        String one = extras.getString(ARG_PARAM1);
        String two = extras.getString(ARG_PARAM2);

        if(two.equals("containerA")){

           accessCode = 1;
        }else if(two.equals("containerB")){

            accessCode = 2;
        }else if(two.equals("containerC")){

            accessCode = 3;
        }
        else if(two.equals("containerD")){

            accessCode = 4;
        }

        emptyButton = v.findViewById(R.id.emptyButton);


        emptyButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                listener.emptyFragSent(accessCode);
                return true;
            }
        });







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




    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof FragmentEmptyListener){

            listener = (FragmentEmptyListener) context;

        }else{

            throw new RuntimeException(context.toString()+"must implement emptyFragmentListener");

        }


    }
}
