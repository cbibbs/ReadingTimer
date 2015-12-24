package net.bibbs.readingtimer;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class TrackMinutes extends ActionBarActivity {
    CheckBox optSingleShot;
    Button btnStart, btnCancel;
    TextView textCounter;

    Timer timer;
    MyTimerTask myTimerTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_minutes);
        optSingleShot = (CheckBox)findViewById(R.id.singleshot);
        btnStart = (Button)findViewById(R.id.start);
        btnCancel = (Button)findViewById(R.id.cancel);
        textCounter = (TextView)findViewById(R.id.counter);

        btnStart.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View arg0) {

                if(timer != null){
                    timer.cancel();
                }

                //re-schedule timer here
                //otherwise, IllegalStateException of
                //"TimerTask is scheduled already"
                //will be thrown
                timer = new Timer();
                myTimerTask = new MyTimerTask();

                if(optSingleShot.isChecked()){
                    //singleshot delay 1000 ms
                    timer.schedule(myTimerTask, 1000);
                }else{
                    //delay 1000ms, repeat in 5000ms
                    timer.schedule(myTimerTask, 1000, 5000);
                }
            }});

        btnCancel.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View v) {
                if (timer!=null){
                    timer.cancel();
                    timer = null;
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_track_minutes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class MyTimerTask extends TimerTask {

        @Override
        public void run() {
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat simpleDateFormat =
                    new SimpleDateFormat("dd:MMMM:yyyy HH:mm:ss a");
            final String strDate = simpleDateFormat.format(calendar.getTime());

            runOnUiThread(new Runnable(){

                @Override
                public void run() {
                    textCounter.setText(strDate);
                }});
        }

    }

}
