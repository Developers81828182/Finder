package ru.seva.finder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TrackStatus extends AppCompatActivity {
    Button stop_btn;
    TextView tracking_stopped, sms_sent_text, sms_remained_text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_status);

        stop_btn = (Button) findViewById(R.id.stop_tracking);
        tracking_stopped = (TextView) findViewById(R.id.tracking_stopped);
        sms_sent_text = (TextView) findViewById(R.id.sent_text);
        sms_remained_text = (TextView) findViewById(R.id.status_text);
        updater();
        //ресивер обновления полей
        LocalBroadcastManager.getInstance(this).registerReceiver(Upd, new IntentFilter("update_fields"));
    }

    //приёмник для обновления до свежих данных при отправке SMS
    private BroadcastReceiver Upd = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            updater();
        }
    };

    public void updater() {  //обновление данных полей
        sms_sent_text.setText(getString(R.string.sms_sent, Tracking.sms_counter));
        sms_remained_text.setText(getString(R.string.remaining_mes, (Tracking.sms_number-Tracking.sms_counter)));
        if ((Tracking.sms_number-Tracking.sms_counter) == 0) {
            stop_btn.setEnabled(false);
            tracking_stopped.setVisibility(View.VISIBLE);
        }
    }

    public void stop(View view) {
        if (Tracking.tracking_running) {
            Intent intent = new Intent(this, Tracking.class);
            stopService(intent);

            stop_btn.setEnabled(false);
            tracking_stopped.setVisibility(View.VISIBLE);
        }
    }
}