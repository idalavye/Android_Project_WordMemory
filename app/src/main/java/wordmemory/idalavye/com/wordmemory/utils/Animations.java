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

    public static Animation createSlideInLeft(Context context,int duration){
        Animation animation = AnimationUtils.loadAnimation(context,android.R.anim.slide_in_left);
        animation.setDuration(duration);
        return animation;
    }
}
