// NetworkChangeListener.java
package com.example.rgb.utility;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.widget.AppCompatButton;
import com.example.rgb.R;

public class NetworkChangeListener extends BroadcastReceiver {
    private NetworkChangeCallback callback;

    public NetworkChangeListener(NetworkChangeCallback callback) {
        this.callback = callback;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        WebCheckTask webCheckTask = new WebCheckTask() {
            @Override
            protected void onPostExecute(Boolean result) {
                if (result) {
                    Toast.makeText(context, "Qurilmaga muvaffaqiyatli bog'lanildi.", Toast.LENGTH_SHORT).show();
                    // Call the callback when the network is connected
                    callback.onNetworkConnected();
                } else {
                    Toast.makeText(context, "Qurilmaga bog'lanishni iloji bo'lmadi.", Toast.LENGTH_SHORT).show();
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    View layout_dialog = LayoutInflater.from(context).inflate(R.layout.check_internet_dialog, null);
                    builder.setView(layout_dialog);

                    AppCompatButton btnRetry = layout_dialog.findViewById(R.id.btn_retry);

                    AlertDialog dialog = builder.create();
                    dialog.show();
                    dialog.setCancelable(false);
                    dialog.getWindow().setGravity(Gravity.CENTER);

                    btnRetry.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                            onReceive(context, intent);
                        }
                    });
                }
            }
        };
        webCheckTask.execute("http://192.168.4.22");
    }

    public interface NetworkChangeCallback {
        void onNetworkConnected();
    }
}
