package eu.epicclan.servermanager.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import eu.epicclan.servermanager.MainActivity;
import eu.epicclan.servermanager.ParamsBuilder;
import eu.epicclan.servermanager.R;

public class Seperator extends RelativeLayout{

    public Seperator(Context context, String cName){super(context); buildSeperator(cName);}

    public void buildSeperator(String cName){
        View sepLeft = new View(MainActivity.main);
        sepLeft.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        sepLeft.setLayoutParams(new ParamsBuilder(200, 5).alignTo(-1, ALIGN_PARENT_LEFT).build());
        sepLeft.setY(20);
        addView(sepLeft);

        View sepRight = new View(MainActivity.main);
        sepRight.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        int screenWidth = MainActivity.main.getWindowManager().getDefaultDisplay().getWidth();
        sepRight.setLayoutParams(new ParamsBuilder(screenWidth, 5).alignTo(-1, ALIGN_PARENT_LEFT).build());
        sepRight.setY(20);
        sepRight.setX(screenWidth - 200);
        addView(sepRight);

        TextView name = new TextView(MainActivity.main);
        name.setText(cName);
        name.setTextColor(getResources().getColor(R.color.colorAccent));
        name.setTypeface(name.getTypeface(), Typeface.BOLD);
        name.setLayoutParams(new ParamsBuilder(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).alignTo(-1, CENTER_HORIZONTAL).build());
        addView(name);
    }

}
