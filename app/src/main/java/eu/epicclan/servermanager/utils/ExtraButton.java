package eu.epicclan.servermanager.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.v7.widget.AppCompatImageButton;
import android.view.Display;

import eu.epicclan.servermanager.MainActivity;

public class ExtraButton extends AppCompatImageButton{

    private Bitmap bitmap;

    public ExtraButton(Context context, Bitmap bitmap){
        super(context);
        this.bitmap = bitmap;
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint buttonPaint = new Paint();
        buttonPaint.setColor(Color.rgb(43, 43, 43));
        buttonPaint.setStyle(Paint.Style.FILL);

        canvas.drawRect(0, 0, getLayoutParams().width, getLayoutParams().height, buttonPaint);
        canvas.drawBitmap(bitmap, 0, 0, buttonPaint);
    }
}
