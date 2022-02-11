package com.sarbesh.alarmgps.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.view.View;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AndroidUtils {
    /**
     * @param view         View to animate
     * @param toVisibility Visibility at the end of animation
     * @param toAlpha      Alpha at the end of animation
     * @param duration     Animation duration in ms
     */
    public static void animateView(final View view, final int toVisibility, float toAlpha, int duration) {
        boolean show = toVisibility == View.VISIBLE;
        if (show) {
            view.setAlpha(0);
        }
        view.setVisibility(View.VISIBLE);
        view.animate()
                .setDuration(duration)
                .alpha(show ? toAlpha : 0)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        view.setVisibility(toVisibility);
                    }
                });
    }

    public static String utcToLocal(SimpleDateFormat inputFormat, SimpleDateFormat outputFormat, String input) throws ParseException {
        Date date = inputFormat.parse(input);
        return outputFormat.format(date);
    }

    public static void showToast(Context mContext, String message){
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }
}
