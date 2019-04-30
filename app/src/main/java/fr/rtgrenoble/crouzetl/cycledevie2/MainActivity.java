package fr.rtgrenoble.crouzetl.cycledevie2;

import android.app.Activity;
import android.app.Notification;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    static final String COMPTEUR = "nbclick";
    static final String CHECK = "showToast";
    private static final String TAG = "CycleDeVie";
    private int i, count;
    private TextView nbclic;
    private boolean showToast = false;
    Notification.Builder notif = new Notification.Builder(this);



    private void showToast(String msg, int gravity) {
        if (this.showToast) {
            Log.d(TAG, i + " " + msg + " called");
            Toast t = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
            t.setGravity(gravity, 0, 0);
            t.show();
            i++;
        }
    }

    private void updateMessage() {
        String temp = getResources().getString(R.string.nbclic);
        Log.d(TAG, "updateMessage() called");
        nbclic.setText(temp + count);
    }

    public void clickCheck(View v) {
        showToast = !showToast;
    }

    public void clickPlusOne(View v) {
        count++;
        updateMessage();
        Toast clic = Toast.makeText(this, getResources().getString(R.string.numclic) + count, Toast.LENGTH_SHORT);
        clic.setGravity(Gravity.CENTER, 0, 0);
        clic.show();
        notif.setSmallIcon(R.mipmap.ic_launcher);
        notif.setContentTitle("Notification !");
        notif.setContentText("Vous avez clicker "+count+" fois !");
        if (count/10 == (1,2,3,4,5,6,7,8,9)){

        }
    }

    public void clickRAZ(View v) {
        this.count = 0;
        updateMessage();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final CheckBox checkbox = (CheckBox) findViewById(R.id.checkBox);
        nbclic = (TextView) findViewById(R.id.textView);
        SharedPreferences settings = getPreferences(MODE_PRIVATE);
        count = settings.getInt(COMPTEUR, 0);
        showToast = settings.getBoolean(CHECK, false);
        if (showToast) {
            checkbox.setChecked(true);
        }
        showToast("onCreate()", Gravity.TOP | Gravity.LEFT);
        i++;
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateMessage();
        showToast("onStart()", Gravity.TOP | Gravity.CENTER_HORIZONTAL);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        showToast("onRestart()", Gravity.LEFT | Gravity.CENTER_VERTICAL);
    }

    @Override
    protected void onResume() {
        super.onResume();
        showToast("onResume()", Gravity.TOP | Gravity.RIGHT);
    }

    @Override
    protected void onPause() {
        super.onPause();
        showToast("onPause()", Gravity.BOTTOM | Gravity.LEFT);
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt(COMPTEUR, count);
        savedInstanceState.putBoolean(CHECK, showToast);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        count = savedInstanceState.getInt(COMPTEUR);
        showToast = savedInstanceState.getBoolean(CHECK);
    }

    @Override
    protected void onStop() {
        super.onStop();
        showToast("onStop()", Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
        SharedPreferences settings = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(COMPTEUR, count);
        editor.putBoolean(CHECK, showToast);
        editor.apply();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        showToast("onDestroy()", Gravity.BOTTOM | Gravity.RIGHT);
    }
}
