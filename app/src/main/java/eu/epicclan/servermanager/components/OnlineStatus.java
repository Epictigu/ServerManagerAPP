package eu.epicclan.servermanager.components;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

public class OnlineStatus extends View {

    private final Paint paint = new Paint();
    private final Rect bounds = new Rect();

    private final String status;
    private final int statusColor;

    public OnlineStatus(Context context){
        this(context, null, "Status", Color.RED);
    }

    public OnlineStatus(Context context, AttributeSet attributeSet, String status, int statusColor){
        super(context, attributeSet);

        this.status = status;
        this.statusColor = statusColor;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paint.setColor(statusColor);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRoundRect(0, 0, 200, 60, 10, 10, paint);

        paint.setColor(Color.WHITE);
        paint.setTextSize(40);

        paint.getTextBounds(status, 0, status.length(), bounds);

        canvas.drawText(status, (200f - bounds.width()) / 2, 60 - (60f - bounds.height()) / 2, paint);
    }
}
