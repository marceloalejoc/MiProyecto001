package tk.macx.miproyecto001.servicios;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by usuario on 14-07-18.
 */

public class Message {
    public static void toast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}
