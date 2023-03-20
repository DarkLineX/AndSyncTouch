package frameworks.base.sync_touch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import frameworks.base.sync_touch.view.ActionPoint;
import frameworks.base.sync_touch.view.GsonUtils;
import frameworks.base.sync_touch.view.PaletteView;
import frameworks.base.sync_touch.view.TouchUtils;

public class MainActivity extends AppCompatActivity {

    private ArrayList<ActionPoint> actionPoints = new ArrayList<>();
    private float start_x = 0,start_y =0,end_x = 0,end_y=0 ;
    private String path = "{}";
    private String TAG = "MainActivity";

    private String pathJson = "[{\"action\":0,\"time\":0,\"x\":0.0,\"y\":0.0},{\"action\":2,\"time\":38,\"x\":6.48378,\"y\":0.0},{\"action\":2,\"time\":55,\"x\":28.324493,\"y\":-0.8817749},{\"action\":2,\"time\":105,\"x\":107.953995,\"y\":-4.9977417},{\"action\":2,\"time\":122,\"x\":129.64229,\"y\":-3.998169},{\"action\":2,\"time\":143,\"x\":170.7282,\"y\":-2.4434814},{\"action\":2,\"time\":160,\"x\":200.98067,\"y\":-1.6746826},{\"action\":2,\"time\":193,\"x\":264.35468,\"y\":-0.427063},{\"action\":2,\"time\":210,\"x\":305.5381,\"y\":0.99957275},{\"action\":2,\"time\":227,\"x\":345.77332,\"y\":2.3668823},{\"action\":2,\"time\":243,\"x\":390.1103,\"y\":1.9991455},{\"action\":2,\"time\":260,\"x\":426.38336,\"y\":2.9986572},{\"action\":2,\"time\":277,\"x\":461.05756,\"y\":3.5563354},{\"action\":2,\"time\":293,\"x\":491.52032,\"y\":4.9978027},{\"action\":2,\"time\":310,\"x\":517.3227,\"y\":4.9978027},{\"action\":2,\"time\":327,\"x\":539.9846,\"y\":4.9978027},{\"action\":2,\"time\":343,\"x\":557.1612,\"y\":4.9978027},{\"action\":2,\"time\":360,\"x\":569.3513,\"y\":4.5182495},{\"action\":2,\"time\":377,\"x\":575.46924,\"y\":3.99823},{\"action\":2,\"time\":393,\"x\":579.992,\"y\":3.99823},{\"action\":2,\"time\":410,\"x\":585.4692,\"y\":2.3285522},{\"action\":2,\"time\":422,\"x\":589.45416,\"y\":0.99957275},{\"action\":1,\"time\":430,\"x\":589.45416,\"y\":0.99957275}]";

    private PaletteView paletteView;
    private Button button;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        paletteView = (PaletteView) findViewById(R.id.paletteView);
        button = (Button) findViewById(R.id.button);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                TouchUtils.contentReTouch(MainActivity.this,200,800,pathJson);
            }
        },3000);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        long time = event.getEventTime()-event.getDownTime();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                actionPoints.clear();
                start_x = event.getX();
                start_y = event.getY();
                actionPoints.add(new ActionPoint(time,0.0f,0.0f,event.getAction()));
                break;
            case MotionEvent.ACTION_MOVE:
                actionPoints.add(new ActionPoint(time,event.getX()-start_x,event.getY()-start_y,event.getAction()));
                break;
            case MotionEvent.ACTION_UP:
                end_x = event.getX()-start_x;
                end_y = event.getY()-start_y;
                actionPoints.add(new ActionPoint(time,end_x,end_y,event.getAction()));
                path = GsonUtils.toJson(actionPoints);
                Log.e(TAG, path);
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(event);
    }

}