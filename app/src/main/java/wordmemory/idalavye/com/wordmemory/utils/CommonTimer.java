package wordmemory.idalavye.com.wordmemory.utils;

import android.os.CountDownTimer;
import android.view.View;

import io.netopen.hotbitmapgg.library.view.RingProgressBar;

public class CommonTimer {

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
}
