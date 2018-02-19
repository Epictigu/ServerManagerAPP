package eu.epicclan.servermanager;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.IOException;

import eu.epicclan.servermanager.backend.manager.ServerManager;
import eu.epicclan.servermanager.backend.manager.utils.Server;
import eu.epicclan.servermanager.utils.ExtraButton;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "";

    public static ServerManager manager;
    public static MainActivity main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        main = this;
        new ServerManager();
    }

    public static void buildLayout(){
        RelativeLayout layout = new RelativeLayout(main);
        int count = 0;

        for(Server s : manager.serverList){
            RelativeLayout sLayout = new RelativeLayout(main);
            final Server sF = s;

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inScaled = false;

            ExtraButton stopButton = new ExtraButton(main, BitmapFactory.decodeResource(main.getResources(), R.drawable.button_stop, options));
            stopButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        manager.conM.exec("sh " + sF.stopPath);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            int stopButtonId = stopButton.generateViewId();
            stopButton.setId(stopButtonId);

            ExtraButton startButton = new ExtraButton(main, BitmapFactory.decodeResource(main.getResources(), R.drawable.button_start, options));
            startButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        manager.conM.exec("sh " + sF.startPath);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            int startButtonId = startButton.generateViewId();
            startButton.setId(startButtonId);

            TextView desc = new TextView(main);
            desc.setText(s.desc);

            RelativeLayout.LayoutParams descParam = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            descParam.addRule(RelativeLayout.ALIGN_BOTTOM,  startButton.getId());
            desc.setLayoutParams(descParam);

            desc.setId(RelativeLayout.generateViewId());

            TextView name = new TextView(main);
            name.setText(s.name);
            name.setTypeface(name.getTypeface(), Typeface.BOLD);

            RelativeLayout.LayoutParams nameParam = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            nameParam.addRule(RelativeLayout.ABOVE, desc.getId());
            name.setLayoutParams(nameParam);

            sLayout.addView(stopButton);
            sLayout.addView(startButton);
            sLayout.addView(name);
            sLayout.addView(desc);

            stopButton.setLayoutParams(getNewParams(200, 200, -1));
            startButton.setLayoutParams(getNewParams(200, 200, stopButton.getId()));

            View v = new View(main);
            v.setBackgroundColor(Color.BLACK);

            RelativeLayout.LayoutParams vParam = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, 5);
            vParam.addRule(RelativeLayout.BELOW, startButton.getId());
            v.setLayoutParams(vParam);

            sLayout.addView(v);

            sLayout.setY(count * 205);
            layout.addView(sLayout);

            count++;
        }

        main.setContentView(layout);
    }

    public void sendMessage(View view){
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.editText);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    public static RelativeLayout.LayoutParams getNewParams(int width, int heigth, int right){
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(width, heigth);
        if(right == -1){
            lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        } else {
            lp.addRule(RelativeLayout.LEFT_OF, right);
        }

        return lp;
    }

}
