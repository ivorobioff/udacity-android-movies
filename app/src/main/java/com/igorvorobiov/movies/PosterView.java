package com.igorvorobiov.movies;

import android.content.Context;
import android.widget.ImageView;

/**
 * @author Igor Vorobiov<igor.vorobioff@gmail.com>
 */
public class PosterView extends ImageView {

    final private Double RATIO = 1.5;

    public PosterView(Context context) {
        super(context);

        setScaleType(ScaleType.CENTER_CROP);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        Integer width = MeasureSpec.getSize(widthMeasureSpec);
        Double height = width * RATIO;

        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height.intValue(), MeasureSpec.EXACTLY);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
