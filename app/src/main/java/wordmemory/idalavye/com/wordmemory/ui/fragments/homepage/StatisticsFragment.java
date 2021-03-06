package wordmemory.idalavye.com.wordmemory.ui.fragments.homepage;

import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.db.chart.animation.Animation;
import com.db.chart.model.Bar;
import com.db.chart.model.BarSet;
import com.db.chart.model.LineSet;
import com.db.chart.renderer.AxisRenderer;
import com.db.chart.tooltip.Tooltip;
import com.db.chart.util.Tools;
import com.db.chart.view.LineChartView;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.DoubleBounce;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import wordmemory.idalavye.com.wordmemory.R;
import wordmemory.idalavye.com.wordmemory.controllers.StatisticController;
import wordmemory.idalavye.com.wordmemory.controllers.WordListItemController;
import wordmemory.idalavye.com.wordmemory.models.DailyStatistics;
import wordmemory.idalavye.com.wordmemory.models.StatisticModel;

public class StatisticsFragment extends Fragment {

    private TextView f_statistics_totalWord, f_statistics_totalLearnedWord, f_statistics_totalRepeated, f_statistics_totalCorrectRepeated;
    private LineChartView mChart;
    private LinearLayout spin_layout_statistics, statistics_layout;
    private Context mContext;
    private final String[] mLabels = new String[9];

    private final float[][] mValues = {new float[9],
            {4.5f, 2.5f, 2.5f, 12f, 4.5f, 9.5f, 5f, 10f, 1.8f}};

    private Tooltip mTip;
    Paint gridPaint;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_statistics, container, false);

        init(view);
        setXLabel();
        YLabels();

        mChart.clearAnimation();
        // Tooltip
        mTip = new Tooltip(mContext, R.layout.linechart_three_tooltip, R.id.value);
        mTip.setVerticalAlignment(Tooltip.Alignment.BOTTOM_TOP);
        mTip.setDimensions((int) Tools.fromDpToPx(58), (int) Tools.fromDpToPx(25));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {

            mTip.setEnterAnimation(PropertyValuesHolder.ofFloat(View.ALPHA, 1),
                    PropertyValuesHolder.ofFloat(View.SCALE_Y, 1f),
                    PropertyValuesHolder.ofFloat(View.SCALE_X, 1f)).setDuration(200);

            mTip.setExitAnimation(PropertyValuesHolder.ofFloat(View.ALPHA, 0),
                    PropertyValuesHolder.ofFloat(View.SCALE_Y, 0f),
                    PropertyValuesHolder.ofFloat(View.SCALE_X, 0f)).setDuration(200);

            mTip.setPivotX(Tools.fromDpToPx(65) / 2);
            mTip.setPivotY(Tools.fromDpToPx(25));
        }
        LineSet dataset = new LineSet(mLabels, mValues[0]);
//        // Data
//
//        dataset.setColor(Color.parseColor("#758cbb"))
//                .setDotsColor(Color.parseColor("#758cbb"))
//                .setThickness(4)
//                .setDashed(new float[]{10f, 10f})
//                .beginAt(5);
//        mChart.addData(dataset);

        dataset = new LineSet(mLabels, mValues[0]);
        dataset.setColor(Color.parseColor("#b3b5bb"))
                .setDotsColor(Color.parseColor("#ffc755"))
                .setThickness(4);
        mChart.addData(dataset);

        dataset = new LineSet(mLabels, mValues[1]);
        dataset.setColor(Color.parseColor("#758cbb"))
                .setDotsColor(Color.parseColor("#758cbb"))
                .setThickness(4);
        mChart.addData(dataset);

        gridPaint = new Paint();
        gridPaint.setColor(Color.parseColor("#ffffff"));
        gridPaint.setStyle(Paint.Style.STROKE);
        gridPaint.setAntiAlias(true);
        gridPaint.setStrokeWidth(Tools.fromDpToPx(.10f));

        mChart.setAxisBorderValues(0, 20)
                .setYLabels(AxisRenderer.LabelPosition.NONE)
                .setTooltips(mTip)
                .setGrid(10, 0, gridPaint);


        statistics_layout.setVisibility(View.GONE);
        spin_layout_statistics.setVisibility(View.VISIBLE);

        StatisticController.INSTANCE.addListenerForStatisticItemDataChange(() -> {

            if (!StatisticController.INSTANCE.getLoading()) {

                statistics_layout.setVisibility(View.VISIBLE);
                spin_layout_statistics.setVisibility(View.GONE);

                StatisticModel model = StatisticController.INSTANCE.getStatisticsForCurrentUser();
                float ratio = (float) model.getTotalCorrectRepeated() / (float) model.getTotalRepeated() * 100f;
                f_statistics_totalWord.setText(String.valueOf(model.getTotalWord()));
                f_statistics_totalLearnedWord.setText(String.valueOf(model.getTotalLearnedWord()));
                f_statistics_totalRepeated.setText(String.valueOf(model.getTotalRepeated()));
                f_statistics_totalCorrectRepeated.setText(String.valueOf(model.getTotalCorrectRepeated()) + "(" + String.format("%.2f", ratio) + "%)");

                mChart.show(new Animation().setInterpolator(new BounceInterpolator())
                        .fromAlpha(0));
            }
        });

        StatisticController.INSTANCE.getUserStatistics();
        return view;
    }


    public void init(View view) {
        mContext = view.getContext();
        mChart = view.findViewById(R.id.chart);
        f_statistics_totalWord = view.findViewById(R.id.f_statistics_totalWord);
        f_statistics_totalLearnedWord = view.findViewById(R.id.f_statistics_totalLearnedWord);
        f_statistics_totalRepeated = view.findViewById(R.id.f_statistics_totalRepeated);
        f_statistics_totalCorrectRepeated = view.findViewById(R.id.f_statistics_totalCorrectRepeated);
        spin_layout_statistics = view.findViewById(R.id.spin_layout_statistics);
        statistics_layout = view.findViewById(R.id.statistics_layout);

        spin_layout_statistics.setVisibility(View.VISIBLE);
        statistics_layout.setVisibility(View.GONE);

        ProgressBar progressBar = view.findViewById(R.id.spin_kit_for_statistics);
        Sprite doubleBounce = new DoubleBounce();
        progressBar.setIndeterminateDrawable(doubleBounce);
    }

    public void setXLabel() {

        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_WEEK, new Date().getDay() + 1);
        System.out.println(c.toString());
        DateFormat df = new SimpleDateFormat("dd/MM");
        for (int i = 0; i < 9; i++) {
            System.out.println(df.format(c.getTime()));
            c.add(Calendar.DATE, -1);
            mLabels[8 - i] = df.format(c.getTime());
        }
    }

    public void YLabels() {
        StatisticModel model = StatisticController.INSTANCE.getStatisticsForCurrentUser();

        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_WEEK, new Date().getDay() + 1);
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        int i = 0;

    }
}
