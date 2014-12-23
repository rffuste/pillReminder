package rffsystems.es.pillreminder;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TimePicker;
import android.widget.Toast;

import java.sql.Time;
import java.util.Calendar;


public class CreatePillActivity extends Activity {

    private DbManager datasource;
    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_pill);

        NumberPicker pillNumDosis=
                (NumberPicker) findViewById(R.id.numberPickerDosis);
        pillNumDosis.setMaxValue(9);
        pillNumDosis.setMinValue(0);

        datasource = new DbManager(this);
        datasource.open();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_pill, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void insertPill(View view) {

        String pill_name;
        String pill_time;
        int pill_dosis;

        EditText name = (EditText) findViewById(R.id.editTextPillName);
        TimePicker time = (TimePicker) findViewById(R.id.timePickerHour);
        NumberPicker dosis = (NumberPicker) findViewById(R.id.numberPickerDosis);


        // get pill values to be inserted
        pill_name = name.getText().toString();

        int selectedHour=time.getCurrentHour();
        int selectedMin =time.getCurrentMinute();
        pill_time=selectedHour + ":" + selectedMin;

        pill_dosis = dosis.getValue();

        if (datasource.insertPill(pill_name,pill_time,pill_dosis)!= null)
        {
            Toast.makeText(
                getApplicationContext(),
                R.string.textPillInsertedOK,
                Toast.LENGTH_SHORT).show();

            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        }
        else
        {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.textPillInsertedOK,
                    Toast.LENGTH_SHORT).show();
        }

    }


}
