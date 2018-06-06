package com.db.williamchartdemo.linechart;

import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.db.chart.animation.Animation;
import com.db.chart.model.LineSet;
import com.db.chart.tooltip.Tooltip;
import com.db.chart.util.Tools;
import com.db.chart.view.LineChartView;
import com.db.williamchartdemo.CardController;
import com.db.williamchartdemo.R;
import com.db.williamchartdemo.data.DataGenerator;


public class LineCardThree extends CardController {

    private final Context mContext;
    private final LineChartView mChart;

    private String[] mLabels = {"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""};

    private float[][] mValues = {
            {0f, 0.1f, 0.3f, 0.7f, 2f, 4f, 6f, 8f, 9f, 10f, 10.5f, 11f, 10.5f, 10f, 9f, 8f, 6f, 4f, 2f, 0.7f, 0.3f, 0.1f, 0f},
            //{0f, 2f, 1.4f, 4.f, 3.5f, 4.3f, 2f, 4f, 6.f},
            //{1.5f, 2.5f, 1.5f, 5f, 4f, 5f, 4.3f, 2.1f, 1.4f}
    };


    public LineCardThree(CardView card, Context context) {

        super(card);
        mContext = context;
        mChart = (LineChartView) card.findViewById(R.id.chart);
    }


    @Override
    public void show(Runnable action) {

        super.show(action);

        mLabels = DataGenerator.labels(100);
        final float[] values = DataGenerator.randomGaussianData(100);

        LineSet dataset = new LineSet(mLabels, values);
        dataset.setColor(Color.parseColor("#53c1bd"))

                .setThickness(13)
                .setSmooth(true)
                .setFill(Color.parseColor("#3d6c73"))
                .setGradientFill(new int[]{Color.parseColor("#364d5a"), Color.parseColor("#3f7178")},
                        null);
        mChart.addData(dataset);



        final Tooltip tip = new Tooltip(mContext, R.layout.card_toolbar_grey);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {

            tip.setEnterAnimation(PropertyValuesHolder.ofFloat(View.ALPHA, 1f),
                    PropertyValuesHolder.ofFloat(View.SCALE_X, 1f),
                    PropertyValuesHolder.ofFloat(View.SCALE_Y, 1f)).setDuration(200);

            tip.setExitAnimation(PropertyValuesHolder.ofFloat(View.ALPHA, 0),
                    PropertyValuesHolder.ofFloat(View.SCALE_X, 0f),
                    PropertyValuesHolder.ofFloat(View.SCALE_Y, 0f)).setDuration(200);
        }
        tip.setVerticalAlignment(Tooltip.Alignment.BOTTOM_TOP);
        tip.setDimensions((int) Tools.fromDpToPx(25), (int) Tools.fromDpToPx(25));
        tip.setMargins(0, 0, 0, (int) Tools.fromDpToPx(10));

        final Runnable baseAction = action;
        Runnable chartAction = new Runnable() {
            @Override
            public void run() {
                baseAction.run();
                tip.prepare(mChart.getEntriesArea(0).get(150), values[150]);
                mChart.showTooltip(tip, true);
            }
        };

        mChart.setTooltips(tip).show(new Animation()
                .setInterpolator(new DecelerateInterpolator())
                .setDuration(2500)
                .withEndAction(chartAction));
    }


    @Override
    public void update() {

        super.update();

        mChart.dismissAllTooltips();
        if (firstStage) mChart.updateValues(0, mValues[1]);
        else mChart.updateValues(0, mValues[0]);
        mChart.notifyDataUpdate();
    }


    @Override
    public void dismiss(Runnable action) {

        super.dismiss(action);

        mChart.dismissAllTooltips();
        mChart.dismiss(new Animation().withEndAction(action));
    }

    private void showTooltip() {

        // Tooltip
        Tooltip tip = new Tooltip(mContext, R.layout.square_tooltip);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {

            tip.setEnterAnimation(PropertyValuesHolder.ofFloat(View.ALPHA, 1f),
                    PropertyValuesHolder.ofFloat(View.SCALE_X, 1f),
                    PropertyValuesHolder.ofFloat(View.SCALE_Y, 1f)).setDuration(200);

            tip.setExitAnimation(PropertyValuesHolder.ofFloat(View.ALPHA, 0),
                    PropertyValuesHolder.ofFloat(View.SCALE_X, 0f),
                    PropertyValuesHolder.ofFloat(View.SCALE_Y, 0f)).setDuration(200);
        }
        tip.setVerticalAlignment(Tooltip.Alignment.BOTTOM_TOP);
        tip.setDimensions((int) Tools.fromDpToPx(25), (int) Tools.fromDpToPx(25));
        tip.setMargins(0, 0, 0, (int) Tools.fromDpToPx(10));
        // tip.prepare(mChart.getEntriesArea(0).get(0), 0);

        mChart.showTooltip(tip, true);
    }
}
