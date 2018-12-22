package wordmemory.idalavye.com.wordmemory.utils;

import android.os.CountDownTimer;
import android.view.View;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import io.netopen.hotbitmapgg.library.view.RingProgressBar;

public class ExerciseCommon {

    private CountDownTimer countDownTimer = null;

    public void startTimer(final RingProgressBar view, final int max, int tickTime) {
        countDownTimer = new CountDownTimer(max, tickTime) {
            @Override
            public void onTick(long millisUntilFinished) {
                view.setProgress((int) (max - millisUntilFinished) / 1000);
            }

            @Override
            public void onFinish() {

            }
        }.start();
    }

    public void cancelTimer() {
        if (countDownTimer != null)
            countDownTimer.cancel();
    }

    public static String getDateNow() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_WEEK, new Date().getDay() + 1);
        DateFormat df = new SimpleDateFormat("ddMMyyyy");

        return df.format(c.getTime());
    }
}
