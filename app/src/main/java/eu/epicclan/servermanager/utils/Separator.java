package eu.epicclan.servermanager.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import eu.epicclan.servermanager.MainActivity;
import eu.epicclan.servermanager.ParamsBuilder;
import eu.epicclan.servermanager.R;

public class Separator extends RelativeLayout{

    public Separator(Context context, String cName){super(context); buildSeparator(cName);}

    public Separator(Context context){
        super(context);
    }

    public void buildSeparator(String cName){
        MarginLayoutParams layoutParams = new MarginLayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 5, 0, 5);
        this.setLayoutParams(layoutParams);

        View sepLeft = new View(MainActivity.main);
        sepLeft.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.mainColor));
        sepLeft.setLayoutParams(new ParamsBuilder(200, 5).alignTo(-1, ALIGN_PARENT_LEFT).build());
        sepLeft.setY(20);
        addView(sepLeft);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        MainActivity.main.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        View sepRight = new View(MainActivity.main);
        sepRight.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.mainColor));
        int screenWidth = displayMetrics.widthPixels;
        sepRight.setLayoutParams(new ParamsBuilder(screenWidth, 5).alignTo(-1, ALIGN_PARENT_LEFT).build());
        sepRight.setY(20);
        sepRight.setX(screenWidth - 200);
        addView(sepRight);

        TextView name = new TextView(MainActivity.main);
        name.setText(cName);
        name.setTextColor(ContextCompat.getColor(getContext(), R.color.mainColor));
        name.setTypeface(name.getTypeface(), Typeface.BOLD);
        name.setLayoutParams(new ParamsBuilder(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).alignTo(-1, CENTER_HORIZONTAL).build());
        addView(name);
    }

}
