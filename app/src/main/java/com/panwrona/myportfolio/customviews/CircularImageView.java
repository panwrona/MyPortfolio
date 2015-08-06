package com.panwrona.myportfolio.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.widget.ImageView;
import com.panwrona.myportfolio.R;

public class CircularImageView extends ImageView {
    private float strokeWidth;
    private float radius;
    private int backgroundColor;
    private Bitmap photoBitmap;
    private Paint paint;
    private int centerWidth;
    private int centerHeight;
    private boolean isImageSet = false;

    public CircularImageView(Context context) {
        super(context);
    }

    public CircularImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray array =
            context.obtainStyledAttributes(attrs, R.styleable.ProgressPhotoView, 0, 0);
        try {
            strokeWidth = array.getDimension(R.styleable.CircularImageView_strokeWidth, 0);
            backgroundColor = array.getColor(R.styleable.CircularImageView_strokeColor, 0);
            radius = array.getDimension(R.styleable.CircularImageView_circularRadius, 0);
        } finally {
            array.recycle();
        }

        paint = new Paint();
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(backgroundColor);
        paint.setStrokeWidth(strokeWidth);
        photoBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.portoflio);
    }

    @Override protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerWidth = w / 2;
        centerHeight = h / 2;
        if(!isImageSet) {
            setImageBitmap(transformBitmapIntoCircle(photoBitmap));
            isImageSet = true;
        }
    }

    @Override protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(centerWidth, centerHeight, radius, paint);
    }

    private Bitmap transformBitmapIntoCircle(Bitmap source) {
        int size = Math.min(source.getWidth(), source.getHeight());

        int x = (source.getWidth() - size) / 2;
        int y = (source.getHeight() - size) / 2;

        Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
        if (squaredBitmap != source) {
            source.recycle();
        }

        Bitmap bitmap = Bitmap.createBitmap(size, size, source.getConfig());

        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        BitmapShader shader = new BitmapShader(squaredBitmap, BitmapShader.TileMode.CLAMP,
            BitmapShader.TileMode.CLAMP);
        paint.setShader(shader);
        paint.setAntiAlias(true);

        float r = size / 2f;
        canvas.drawCircle(r, r, r - strokeWidth * 2, paint);

        squaredBitmap.recycle();
        return bitmap;
    }
}
