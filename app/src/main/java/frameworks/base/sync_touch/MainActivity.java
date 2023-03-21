package frameworks.base.sync_touch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import frameworks.base.sync_touch.net.ApiClient;
import frameworks.base.sync_touch.view.ActionPoint;
import frameworks.base.sync_touch.view.GsonUtils;
import frameworks.base.sync_touch.view.PaletteView;
import frameworks.base.sync_touch.view.TouchImageView;
import frameworks.base.sync_touch.view.TouchUtils;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {


    private String TAG = "MainActivity";
    private ArrayList<ActionPoint> actionPoints = new ArrayList<>();
    private String path = "{}";
    private TouchImageView image;
    private Button sendSlide;
    private Button startSlide;
    private Button loadPic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sendSlide = (Button) findViewById(R.id.send_slide);
        startSlide = (Button) findViewById(R.id.start_slide);
        loadPic = (Button) findViewById(R.id.load_pic);
        image = (TouchImageView) findViewById(R.id.image);


        loadPic.setOnClickListener(v -> {
            actionPoints.clear();
            Glide.with(getBaseContext()).load("http://192.168.20.229:8000/image/").skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE).into(image);

        });

        startSlide.setOnClickListener(v -> {
            actionPoints.clear();
        });

        sendSlide.setOnClickListener(v -> {
            path = GsonUtils.toJson(actionPoints);
            Log.e(TAG, path);
        });

        image.setOnTouchListener((v, event) -> {
            //Log.e(TAG,"touch view = " + image.getClass().getName() + " action = " + event.getAction() + " x = " + event.getX() + " y = " + event.getY());
            long time = event.getEventTime() - event.getDownTime();
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    actionPoints.add(new ActionPoint(time, event.getX(), event.getY(), event.getAction()));
                    break;
                case MotionEvent.ACTION_MOVE:
                    actionPoints.add(new ActionPoint(time, event.getX(), event.getY(), event.getAction()));
                    break;
                case MotionEvent.ACTION_UP:
                    actionPoints.add(new ActionPoint(time, event.getX(), event.getY(), event.getAction()));
                    break;
                default:
                    break;
            }
            return true;
        });

    }



}