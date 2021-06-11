package com.example.timer;
import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

@SuppressLint("AppCompatCustomView")
public class TimerTextView extends TextView implements Runnable {

    public TimerTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    private long mHour = 0, mMin = 0, mSecond = 0;// 小时，分钟，秒
    private boolean run = false; //是否启动了
    private boolean pause = false; // 是否暂停,其实状态不认为是暂停
    public long getmHour() {
        return mHour;
    }

    public long getmMin() {
        return mMin;
    }

    public long getmSecond() {
        return mSecond;
    }

    public void setTimes(long[] times) {
        mHour = times[0];
        mMin = times[1];
        mSecond = times[2];

    }

    /**
     * 倒计时计算
     */
    private void ComputeTime() {
        mSecond--;
        if (mSecond < 0) {
            mMin--;
            mSecond = 59;
            if (mMin < 0) {
                mMin = 59;
                mHour--;
                if (mHour < 0) {
                    // 倒计时结束，一天有24个小时
                    mHour = 23;

                }
            }

        }

    }

    public boolean isRun() {
        return run;
    }

    public void beginRun() {
        this.run = true;
        run();
    }

    public void stopRun() {
        this.run = false;
    }

    public boolean isPause() {
        return pause;
    }

    public void setPause(boolean pause){
        this.pause = pause;
    }
    public void onDestroy(){
        removeCallbacks(this);
    }

    @Override
    public void run() {
        //标示已经启动
        if (run && (mHour != 0 || mMin != 0 || mSecond != 0)) {
            ComputeTime();
            String strTime = mHour + ":" + mMin + ":" + mSecond ;
            this.setText(strTime);
            postDelayed(this, 1000);
        } else {
            ChronometerDemoActivity.status = 0;
            this.run = false;
            removeCallbacks(this);
        }
    }

}