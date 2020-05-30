package com.example.smarthub;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;


import androidx.appcompat.widget.Toolbar;
import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;


import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.iot.AWSIotMqttClientStatusCallback;
import com.amazonaws.mobileconnectors.iot.AWSIotMqttManager;
import com.amazonaws.mobileconnectors.iot.AWSIotMqttNewMessageCallback;
import com.amazonaws.mobileconnectors.iot.AWSIotMqttQos;
import com.amazonaws.regions.Regions;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements emptyFrag.FragmentEmptyListener {
    private MYViewModel myViewModel;
    private FrameLayout container_one;
    private FrameLayout container_two;
    private FrameLayout container_three;
    private FrameLayout container_four;
    private LinearLayout background;
    public static int fragmentsCount_one = 1;
    private Fragment fragments_in_one[] = new Fragment[10];;
    private Fragment framement_in_four[] = new Fragment[10];
    private Fragment framement_in_three[] = new Fragment[10];
    private Fragment framement_in_two[] = new Fragment[10];

    private int index_A;
    private int index_B;
    private int index_C;
    private int index_D;
    public int myPcode = 0;


    private interActiveFragment one;
    private interActiveFragment five;
    private interActiveFragment four;
    public static TestFragment two;
    public static TestFragment three;
    private emptyFrag six;

    private final String LOG = "MyActivity_One_TWO";

    ///**********Amazon Variables*************//
    private static final String CUSTOMER_SPECIFIC_ENDPOINT = "a171htxr2tkph2-ats.iot.us-east-1.amazonaws.com";

    // Cognito pool ID. For this app, pool needs to be unauthenticated pool with
    // AWS IoT permissions.
    private static final String COGNITO_POOL_ID = "us-east-1:8e03783a-6de8-45c9-8ce0-f28af58bbb2c";

    // Region of AWS IoT
    private static final Regions MY_REGION = Regions.US_EAST_1;
    AWSIotMqttManager mqttManager;
    String clientId;

    CognitoCachingCredentialsProvider credentialsProvider;



    private String recivedMess;
    private final static String topic = "sensor_Data";
    private final static String publish = "control_devices";

    ///**************************************//

    private boolean isConnected = false;

    private Button connectBt;

    ///NEW CODE/////
    private ScrollView mainScroll;
    float downX, upX;
    private TextView title;
    Animation animation;



    ///END NEW CODE//





    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getColor(R.color.statusbar));
        }


        setContentView(R.layout.activity_main);
        background = findViewById(R.id.thisScroll);
        background.setBackgroundResource(R.drawable.back2);
        container_one = findViewById(R.id.container);
        container_two = findViewById(R.id.container2);
        container_three =findViewById(R.id.container3);
        container_four = findViewById(R.id.container4);
        connectBt = findViewById(R.id.button_connect);


        Toolbar myToolbar = (Toolbar) findViewById(R.id.tool);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);


       // animation = AnimationUtils.loadAnimation(this,R.anim.title_anim);
        //title.setAnimation(animation);


        myViewModel  = new ViewModelProvider(this).get(MYViewModel.class);
        myViewModel.init();

       testFrameLayout();
        initialize_views();

        /*
        mainScroll.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()){
                    case MotionEvent.ACTION_DOWN:{
                        downX = event.getX();}
                    case MotionEvent.ACTION_UP:{
                        upX = event.getX();

                        float deltaX = downX - upX;

                        if(Math.abs(deltaX)>0){
                            if(deltaX>=0){
                               // swipeToRight();
                                Toast.makeText(MainActivity.this, "Right Swipe", Toast.LENGTH_LONG).show();
                                return true;
                            }else{
                                //swipeToLeft();
                                Toast.makeText(MainActivity.this, "LeftSwipe Swipe", Toast.LENGTH_LONG).show();
                                return  true;
                            }
                        }
                    }
                }

                return false;
            }
        });

        */




        container_one.setOnTouchListener(new OnSwipeTouchListener(MainActivity.this) {
            public void onSwipeRight() {
                    int count = 0;
                    for(int i = 0; i < fragments_in_one.length;i++){
                        if(fragments_in_one[i] == null){break;}
                        else{count++;}

                    }
                Log.e(LOG, "count = " + count);

                    if(index_A >= count){ index_A = 0; }


                Log.e(LOG, "INDEX_A " + index_A);

                getSupportFragmentManager().beginTransaction().replace(R.id.firstFrame,fragments_in_one[(count- index_A)-1]).commit();
                index_A++;

                Log.e(LOG, "INDEX_A After " + index_A);
            }
            public void onSwipeLeft() {

                int count = 0;
                for(int i = 0; i < fragments_in_one.length;i++){
                    if(fragments_in_one[i] == null){break;}
                    else{count++;}

                }
                Log.e(LOG, "count = " + count);

                if(index_A >= count  ){index_A = 0 ;}


                Log.e(LOG, "INDEX_A " + index_A);


                getSupportFragmentManager().beginTransaction().replace(R.id.firstFrame,fragments_in_one[index_A]).commit();
                index_A++;

                Log.e(LOG, "INDEX_A After " + index_A);

            }


        });



        container_two.setOnTouchListener(new OnSwipeTouchListener(MainActivity.this) {
            public void onSwipeRight() {
                int count = 0;
                for(int i = 0; i < framement_in_two.length;i++){
                    if(framement_in_two[i] == null){break;}
                    else{count++;}

                }

                if(index_B >= count){ index_B = 0; }

                getSupportFragmentManager().beginTransaction().replace(R.id.four,framement_in_two[(count- index_B)-1]).commit();
                index_B++;
            }
            public void onSwipeLeft() {

                int count = 0;
                for(int i = 0; i < framement_in_two.length;i++){
                    if(framement_in_two[i] == null){break;}
                    else{count++;}

                }

                if(index_B >= count){ index_B = 0; }

                getSupportFragmentManager().beginTransaction().replace(R.id.four,framement_in_two[index_B]).commit();
                index_B++;

            }


        });


        container_three.setOnTouchListener(new OnSwipeTouchListener(MainActivity.this) {
            public void onSwipeRight() {
                int count = 0;
                for(int i = 0; i < framement_in_three.length;i++){
                    if(framement_in_three[i] == null){break;}
                    else{count++;}

                }

                if(index_C >= count){ index_C = 0; }

                getSupportFragmentManager().beginTransaction().replace(R.id.five,framement_in_three[(count- index_C)-1]).commit();
                index_C++;
            }
            public void onSwipeLeft() {

                int count = 0;
                for(int i = 0; i < framement_in_three.length;i++){
                    if(framement_in_three[i] == null){break;}
                    else{count++;}

                }

                if(index_C >= count){ index_C = 0; }

                getSupportFragmentManager().beginTransaction().replace(R.id.five,framement_in_three[index_C]).commit();
                index_C++;

            }


        });


        container_four.setOnTouchListener(new OnSwipeTouchListener(MainActivity.this) {
            public void onSwipeRight() {
                int count = 0;
                for(int i = 0; i < framement_in_four.length;i++){
                    if(framement_in_four[i] == null){break;}
                    else{count++;}

                }

                if(index_D >= count){ index_D = 0; }

                getSupportFragmentManager().beginTransaction().replace(R.id.six,framement_in_four[(count- index_D)-1]).commit();
                index_D++;
            }
            public void onSwipeLeft() {

                int count = 0;
                for(int i = 0; i < framement_in_four.length;i++){
                    if(framement_in_four[i] == null){break;}
                    else{count++;}

                }

                if(index_D >= count){ index_D = 0; }

                getSupportFragmentManager().beginTransaction().replace(R.id.six,framement_in_four[index_D]).commit();
                index_D++;

            }


        });







        //***********Amazon Codes ***/////
        clientId = UUID.randomUUID().toString();

        // Initialize the AWS Cognito credentials provider
        credentialsProvider = new CognitoCachingCredentialsProvider(
                getApplicationContext(), // context
                COGNITO_POOL_ID, // Identity Pool ID
                MY_REGION // Region
        );

        // MQTT Client
        mqttManager = new AWSIotMqttManager(clientId, CUSTOMER_SPECIFIC_ENDPOINT);
         connectToAWS();
        connectBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Log.d(LOG, "topic = " + topic);

                try {
                    mqttManager.subscribeToTopic("sensor_Data", AWSIotMqttQos.QOS0,
                            new AWSIotMqttNewMessageCallback() {
                                @Override
                                public void onMessageArrived(final String topic, final byte[] data) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                String message = new String(data, "UTF-8");
                                                Log.d(LOG, "Message arrived:");
                                                Log.d(LOG, "   Topic: " + topic);
                                                Log.d(LOG, " Message: " + message);


                                                myViewModel.sendMessage(message);

                                            } catch (UnsupportedEncodingException e) {
                                                Log.e(LOG, "Message encoding error.", e);
                                            }
                                        }
                                    });
                                }
                            });
                } catch (Exception e) {
                    Log.e(LOG, "Subscription error.", e);
                }

            }
        });


        // subscribeToAWS();



        //publishToAWS();



        ///**********************************//


    }

    private void testFrameLayout() {
        FrameLayout myContainernew = new FrameLayout(this);

        Fragment testFragment = interActiveFragment.newInstance("this","livingroom");
       // getSupportFragmentManager().beginTransaction().replace(myContainernew,four).commit();



    }


    private void disConnectFromAWS() {
        try {
            mqttManager.disconnect();
        } catch (Exception e) {
            Log.e(LOG, "Disconnect error.", e);
        }
    }

    private void publishToAWS() {

        final String topic = "sensor_Data";
        final String msg = "Hello World!";

        try {
            mqttManager.publishString(msg, topic, AWSIotMqttQos.QOS0);
        } catch (Exception e) {
            Log.e(LOG, "Publish error.", e);
        }

    }

    private void subscribeToAWS() {


        try {

            mqttManager.subscribeToTopic(topic, AWSIotMqttQos.QOS0,
                    new AWSIotMqttNewMessageCallback() {

                        @Override
                        public void onMessageArrived(final String topic, final byte[] data) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    try {

                                        String message = new String(data, "UTF-8");
                                        Log.e(LOG, "Message arrived:");
                                        Log.e(LOG, "   Topic: " + topic);
                                        Log.e(LOG, " Message: " + message);



                                    } catch (UnsupportedEncodingException e) {
                                        Log.e(LOG, "Message encoding error.", e);

                                    }
                                }
                            });
                        }
                    });
        } catch (Exception e) {

            Log.e(LOG, "Subscription error.", e);
        }

    }

    private void connectToAWS() {


        try {
            mqttManager.connect(credentialsProvider, new AWSIotMqttClientStatusCallback() {
                @Override
                public void onStatusChanged(final AWSIotMqttClientStatus status, final Throwable throwable) {


                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (status == AWSIotMqttClientStatus.Connecting) {


                            } else if (status == AWSIotMqttClientStatus.Connected) {



                            } else if (status == AWSIotMqttClientStatus.Reconnecting) {
                                if (throwable != null) {

                                }

                            } else if (status == AWSIotMqttClientStatus.ConnectionLost) {
                                if (throwable != null) {

                                    throwable.printStackTrace();
                                }

                            } else {


                            }
                        }
                    });
                }
            });
        } catch (final Exception e) {
            Log.e(LOG, "Connection error.", e);

        }

    }



    private void simulateFive() {
        final Handler handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        myViewModel.sendMessage("26");
                    }
                });

            }
        }).start();
    }

    private void initialize_views() {

        index_A = index_B = index_C = index_D = 0;
        fragments_in_one[0] = interActiveFragment.newInstance("this","livingroom");
        fragments_in_one[1] = TestFragment.newInstance("this","bedroom");
        fragments_in_one[2] = emptyFrag.newInstance("sdasdasd","containerA");

        getSupportFragmentManager().beginTransaction().replace(R.id.firstFrame,fragments_in_one[0]).commit();




        framement_in_two[0] = interActiveFragment.newInstance("dasdasd","gasleak");
        framement_in_three[0] = interActiveFragment.newInstance("dasd","stoves");
        framement_in_four[0] =  emptyFrag.newInstance("thid","containerD");

        framement_in_two[1] = emptyFrag.newInstance("thid","containerB");
        framement_in_three[1] = emptyFrag.newInstance("thid","containerC");


        one= interActiveFragment.newInstance("this", "livingroom");
        two = TestFragment.newInstance("now", "kitchen");
        three = TestFragment.newInstance("asd","bedroom");



        getSupportFragmentManager().beginTransaction().replace(R.id.four,framement_in_two[0]).commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.five,framement_in_three[0]).commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.six,framement_in_four[0]).commit();

    }

    @Override
    public void onDestroy() {

        super.onDestroy();

        disConnectFromAWS();

    }


    @Override
    public void emptyFragSent(int code) {
       // Toast.makeText(this,code+"",Toast.LENGTH_LONG).show();
        Log.e(LOG, "I AM HERE " );
        int count = 0;

        switch (code){
            case 1:

                for(int i = 0; i < fragments_in_one.length;i++){
                    if(fragments_in_one[i] == null){break;}
                    else{count++;}
                }
                Log.e(LOG, "count = "+ count );
                if(count > 9){

                    Log.e(LOG, "no more room " );
                }else{
                    fragments_in_one[count] = fragments_in_one[count-1];
                    fragments_in_one[count-1] = TestFragment.newInstance("this","kitchen");
                    getSupportFragmentManager().beginTransaction().replace(R.id.firstFrame,fragments_in_one[count-1]).commit();

                }


                break;
            case 2:

                for(int i = 0; i < framement_in_two.length;i++){
                    if(framement_in_two[i] == null){break;}
                    else{count++;}
                }

                if(count > 9){

                    Log.e(LOG, "no more room " );
                }else{
                    framement_in_two[count] = framement_in_two[count-1];
                    framement_in_two[count-1] = TestFragment.newInstance("this","kitchen");
                    getSupportFragmentManager().beginTransaction().replace(R.id.four,framement_in_two[count-1]).commit();

                }

                break;
            case 3:

                for(int i = 0; i < framement_in_three.length;i++){
                    if(framement_in_three[i] == null){break;}
                    else{count++;}
                }

                if(count > 9){

                    Log.e(LOG, "no more room " );
                }else{
                    framement_in_three[count] = framement_in_three[count-1];
                    framement_in_three[count-1] = interActiveFragment.newInstance("dasd","stoves");
                    getSupportFragmentManager().beginTransaction().replace(R.id.five,framement_in_three[count-1]).commit();

                }

                break;
            case 4:
                for(int i = 0; i < framement_in_four.length;i++){
                    if(framement_in_four[i] == null){break;}
                    else{count++;}
                }

                if(count > 9){

                    Log.e(LOG, "no more room " );
                }else{
                    framement_in_four[count] = framement_in_four[count-1];

                    if(myPcode == 0){
                    framement_in_four[count-1] = TestFragment.newInstance("this","HUE_study");
                    myPcode++;
                    }
                    else if(myPcode == 1){
                        myPcode++;
                        framement_in_four[count-1] = TestFragment.newInstance("this","HUE_bedroom");}
                      else if(myPcode == 2){
                        myPcode = 0;
                         framement_in_four[count-1] = TestFragment.newInstance("this","HUE_kitchen");}

                    getSupportFragmentManager().beginTransaction().replace(R.id.six,framement_in_four[count-1]).commit();

                }

                break;


        }

    }


}
