package eu.torsteneriksson.dintido;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DisplayTimeActivity extends Activity {
    boolean toggleColor = false;
    Date lastTime = new Date();
    int textColor = Color.BLACK;
    int bgColor = Color.WHITE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        final int mUIFlag =
//                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                        | View.SYSTEM_UI_FLAG_LOW_PROFILE
//                        | View.SYSTEM_UI_FLAG_FULLSCREEN
//                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                        | View.SYSTEM_UI_FLAG_IMMERSIVE;
//
//        getWindow().getDecorView().setSystemUiVisibility(mUIFlag);
        setContentView(R.layout.activity_display_time);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        runTimer();
    }

    // Toggle screen colors when screen is touched.
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Date now = new Date();
        if(now.getTime() - lastTime.getTime() > 400) {
            if (toggleColor) {
                bgColor = Color.BLACK;
                textColor = Color.WHITE;
            } else {
                bgColor = Color.WHITE;
                textColor = Color.BLACK;
            }
            toggleColor = !toggleColor;
            lastTime = now;
        }

        return super.onTouchEvent(event);
    }

    @Override
    public void onBackPressed(){
        //Toast.MakeText(getApplicationContext(),"You Are Not Allowed to Exit the App", Toast.LENGTH_SHORT).show();
    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_HOME) {
            //Log.i("TEST", "Home Button");  // here you'll have to do something to prevent the button to go to the home screen
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    // Update the screen every second.
    private void runTimer() {
        final TextView weekday = (TextView) findViewById(R.id.weekday);
        final TextView date = (TextView) findViewById(R.id.date);
        final TextView year = (TextView) findViewById(R.id.year);
        final TextView time = (TextView) findViewById(R.id.time);
        final TextView partofday = (TextView) findViewById(R.id.partofday);

        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                getWindow().getDecorView().setBackgroundColor(bgColor);
                weekday.setTextColor(textColor);
                date.setTextColor(textColor);
                year.setTextColor(textColor);
                time.setTextColor(textColor);
                partofday.setTextColor(textColor);
                Date now = new Date();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(now);
                int hours = calendar.get(Calendar.HOUR_OF_DAY);
                int weekdayId = calendar.get(Calendar.DAY_OF_WEEK);
                // Do this way so weekday can start with uppercase
                switch (weekdayId) {
                    case Calendar.MONDAY: weekday.setText(R.string.monday);
                        break;
                    case Calendar.TUESDAY:weekday.setText(R.string.tuesday);
                        break;
                    case Calendar.WEDNESDAY:weekday.setText(R.string.wednesday);
                        break;
                    case Calendar.THURSDAY:weekday.setText(R.string.thursday);
                        break;
                    case Calendar.FRIDAY:weekday.setText(R.string.friday);
                        break;
                    case Calendar.SATURDAY:weekday.setText(R.string.saturday);
                        break;
                    case Calendar.SUNDAY:weekday.setText(R.string.sunday);
                        break;
                }
                //weekday.setText(new SimpleDateFormat("EEEE").format(now));
                date.setText(new SimpleDateFormat("dd MMMM").format(now));
                year.setText(getString(R.string.year)+
                        " " +
                        new SimpleDateFormat("yyyy").format(now));
                time.setText(new SimpleDateFormat("HH:mm").format(now));
                if(hours >= 22 || hours < 6) {
                    partofday.setText(R.string.night);
                }
                if(hours >=6  & hours < 9) {
                    partofday.setText(R.string.morning);
                }
                if(hours >= 9 & hours < 18) {
                    partofday.setText(R.string.day);
                }
                if(hours >= 18 & hours < 22) {
                    partofday.setText(R.string.evening);
                }
                handler.postDelayed(this, 500);
            }
        });
    }
}
