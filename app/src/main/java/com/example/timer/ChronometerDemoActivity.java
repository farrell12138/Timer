package com.example.timer;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

public class ChronometerDemoActivity extends Activity implements View.OnClickListener {
    public static int status = 0;
    private TimerTextView clock;
    private EditText text;
    long[] times = {0, 0, 0};
    private final static int SHOW_DIALOG = 1;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    final String[] items = {"很好", "不错", "糟糕"};
                    final String[] res = {"保持哦(*^▽^*)", "努力ヾ(◍°∇°◍)ﾉﾞ", "别放弃(•́へ•́╬)"};
                    final AlertDialog.Builder builder = new AlertDialog.Builder(ChronometerDemoActivity.this);
                    builder.setIcon(R.drawable.warning);
                    builder.setSingleChoiceItems(items, 0, null);
                    builder.setTitle("时间到，表现怎么样？");
                    builder.setSingleChoiceItems(items, 1, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(ChronometerDemoActivity.this, res[which], Toast.LENGTH_SHORT).show();
                            dialog.cancel();
                        }
                    });
                    builder.create();
                    builder.show();
                    break;
            }
        }
    };

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btnStart).setOnClickListener(this);
        findViewById(R.id.btnStop).setOnClickListener(this);
        findViewById(R.id.btnReset).setOnClickListener(this);
        findViewById(R.id.submit).setOnClickListener(this);
        text = findViewById(R.id.edt_settime);
        clock = findViewById(R.id.clock);
    }

    private void setTimes(long[] Time) {
        String Text = text.getText().toString();
        if (!(Text.equals("") && Text != null)) {
            long ss = Long.parseLong(text.getText().toString());
            Time[0] = ss / 3600;
            Time[1] = ss / 60;
            Time[2] = ss % 60 + 1;
        } else {
            Time[0] = Time[1] = Time[2] = 0;
        }
    }

    private void Listener() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (clock.getmSecond() == 0 && clock.getmMin() == 0 && clock.getmHour() == 0) {
                        Message message = new Message();
                        message.what = SHOW_DIALOG;
                        mHandler.sendMessage(message);
                        break;
                    }
                }
            }
        }).start();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnStart:
                if(clock.isRun() == true || status == 1) {
                    break;
                }
                clock.beginRun();
                clock.setPause(false);
                break;
            case R.id.btnStop:
                if(clock.isRun() == false)   {
                    break;
                }
                clock.stopRun();
                status = 1;
                clock.setPause(true);
                break;
            case R.id.btnReset:
                if(text.getText().toString().equals(""))
                    break;
                clock.onDestroy();
                if(clock.isRun() == false && clock.isPause() == false)  Listener();
                setTimes(times);
                clock.setTimes(times);
                clock.beginRun();
                break;
            case R.id.submit:
                if(text.getText().toString().equals(""))
                    break;
                clock.onDestroy();
                if(clock.isRun() == false && clock.isPause() == false)  Listener();
                setTimes(times);
                clock.setTimes(times);
                clock.beginRun();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                break;
            default:
                break;
        }
    }
}