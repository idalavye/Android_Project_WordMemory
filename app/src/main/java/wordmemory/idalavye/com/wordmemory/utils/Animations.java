package wordmemory.idalavye.com.wordmemory.utils;

import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class Animations {

    public static Animation createFadeInAnimation(Context context,int duration){
        Animation animation = AnimationUtils.loadAnimation(context,android.R.anim.fade_in);
        animation.setDuration(duration);

        return animation;
    }
}
