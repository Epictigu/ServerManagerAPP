package eu.epicclan.servermanager;

import android.view.Gravity;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParamsBuilder {

    private int width = 0;
    private int height = 0;

    private boolean center = false;

    public Map<Integer, Integer> rules = new HashMap<Integer, Integer>();

    public ParamsBuilder(int width, int height){
        this.width = width;
        this.height = height;
    }

    public RelativeLayout.LayoutParams build(){
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width, height);

        for(Integer i : rules.keySet()){
            int id = rules.get(i);
            if(id == -1){
                params.addRule(i);
                continue;
            }
            params.addRule(i, id);
        }
        if(center){

        }

        return params;
    }

    public ParamsBuilder alignTo(int id, int rule){
        rules.put(rule, id);
        return this;
    }

    public ParamsBuilder center(){
        center = true;
        return this;
    }

}
