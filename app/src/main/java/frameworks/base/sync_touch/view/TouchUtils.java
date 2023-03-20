package frameworks.base.sync_touch.view;

import android.app.Activity;
import android.content.Context;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class TouchUtils {


    public static List<MotionEvent> getMotionEvents(int start_x,int start_y,String pathJson){
        List<MotionEvent> motionEvents  = new ArrayList<>();
        Type type = new TypeToken<ArrayList<ActionPoint>>(){}.getType();
        List<ActionPoint> actionPointList =  GsonUtils.listFromJson(pathJson,type);
        long downTime_start = SystemClock.uptimeMillis();
        for(ActionPoint actionPoint:actionPointList){
            motionEvents.add(MotionEvent.obtain(downTime_start,downTime_start+actionPoint.time,actionPoint.action,actionPoint.x+start_x,actionPoint.y+start_y,0));
        }
        return motionEvents;
    }

    public static void viewReTouch(View view,int start_x,int start_y,String pathJson){
        List<MotionEvent> motionEvents = getMotionEvents(start_x,start_y,pathJson);
        for(MotionEvent motionEvent:motionEvents){
            view.dispatchTouchEvent(motionEvent);
        }
    }

    public static void contentReTouch(Activity activity, int start_x, int start_y, String pathJson){
        List<MotionEvent> motionEvents = getMotionEvents(start_x,start_y,pathJson);
        for(MotionEvent motionEvent:motionEvents){
            activity.dispatchTouchEvent(motionEvent);
        }
    }

}
