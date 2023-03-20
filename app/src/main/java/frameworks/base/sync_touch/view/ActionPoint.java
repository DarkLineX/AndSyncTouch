package frameworks.base.sync_touch.view;

public class ActionPoint {

    public long time;
    public float x;
    public float y;
    public int action;// 0按下 1滑动  2抬起

    public ActionPoint(long time, float x, float y, int action) {
        this.time = time;
        this.x = x;
        this.y = y;
        this.action = action;
    }
}