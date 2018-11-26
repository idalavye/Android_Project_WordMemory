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
import android.widget.TextView;

import com.db.chart.animation.Animation;
import com.db.chart.model.Bar;
import com.db.chart.model.BarSet;
import com.db.chart.model.LineSet;
import com.db.chart.renderer.AxisRenderer;
import com.db.chart.tooltip.Tooltip;
import com.db.chart.util.Tools;
import com.db.chart.view.BarChartView;
import com.db.chart.view.ChartView;
import com.db.chart.view.LineChartView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import wordmemory.idalavye.com.wordmemory.R;

public class StatisticsFragment extends Fragment {

    private LineChartView mChart;
    private Context mContext;
    private final String[] mLabels = {"Jan", "Fev", "Mar", "Apr", "Jun", "May", "Jul", "Aug", "Sep"};

    private final float[][] mValues = {{3.5f, 4.7f, 4.3f, 8f, 6.5f, 9.9f, 7f, 8.3f, 7.0f},
            {4.5f, 2.5f, 2.5f, 9f, 4.5f, 9.5f, 5f, 8.3f, 1.8f}};

    private Tooltip mTip;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_statistics, container, false);

        mContext = view.getContext();
        mChart = view.findViewById(R.id.chart);

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

        Paint gridPaint = new Paint();
        gridPaint.setColor(Color.parseColor("#ffffff"));
        gridPaint.setStyle(Paint.Style.STROKE);
        gridPaint.setAntiAlias(true);
        gridPaint.setStrokeWidth(Tools.fromDpToPx(.10f));

        mChart.setAxisBorderValues(0, 20)
                .setYLabels(AxisRenderer.LabelPosition.NONE)
                .setTooltips(mTip)
                .setGrid(10, 0, gridPaint)
                .show(new Animation().setInterpolator(new BounceInterpolator())
                        .fromAlpha(0));

        return view;
    }


}
