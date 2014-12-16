package rffsystems.es.pillreminder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TimePicker;
import android.widget.Toast;


public class DetailsActivity extends Activity {

    public String MESSAGE_PILL_DETAILS_NAME = "rffsystems.es.pillreminder.MESSAGE_PILL_DETAILS_NAME";
    public String MESSAGE_PILL_DETAILS_TIME = "rffsystems.es.pillreminder.MESSAGE_PILL_DETAILS_TIME";
    public String MESSAGE_PILL_DETAILS_DOSIS = "rffsystems.es.pillreminder.MESSAGE_PILL_DETAILS_DOSIS";
    public String MESSAGE_PILL_DETAILS_ID = "rffsystems.es.pillreminder.MESSAGE_PILL_DETAILS_ID";

    private DbManager datasource;

    String name,time;
    int dosis;
    long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_deatils);

        datasource = new DbManager(this);
        datasource.open();

        // Get the message from the intent
        Intent intent = getIntent();

        String  message_id = intent.getStringExtra(MESSAGE_PILL_DETAILS_ID);
        id = Long.parseLong(message_id);

        //values from main activity
        String message_pill_name = intent.getStringExtra(MESSAGE_PILL_DETAILS_NAME);

        // time example es 13:6
        String message_pill_time = intent.getStringExtra(MESSAGE_PILL_DETAILS_TIME);
        String message_pill_dosis = intent.getStringExtra(MESSAGE_PILL_DETAILS_DOSIS);

        EditText pillName = (EditText) findViewById(R.id.editTextPillNameDetails);
        TimePicker pillTime = (TimePicker) findViewById(R.id.timePickerHourDetails);
        NumberPicker pillDosis = (NumberPicker) findViewById(R.id.numberPickerDosisDetails);

        pillName.setText(message_pill_name);
        pillTime.setCurrentHour(Utils.getHour(message_pill_time));
        pillTime.setCurrentMinute(Utils.getMinutes(message_pill_time));

        pillDosis.setMaxValue(9);
        pillDosis.setMinValue(0);
        pillDosis.setValue(Integer.parseInt(message_pill_dosis));




    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_details, menu);
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

    public void savePill(View view){

        String pill_name;
        String pill_time;
        int pill_dosis;
        Pill p;

        EditText name = (EditText) findViewById(R.id.editTextPillNameDetails);
        TimePicker time = (TimePicker) findViewById(R.id.timePickerHourDetails);
        NumberPicker dosis = (NumberPicker) findViewById(R.id.numberPickerDosisDetails);

        // get pill values to be inserted
        pill_name = name.getText().toString();

        int selectedHour=time.getCurrentHour();
        int selectedMin =time.getCurrentMinute();
        pill_time=selectedHour + ":" + selectedMin;

        pill_dosis = dosis.getValue();

        p = new Pill(id,pill_name,pill_time,pill_dosis);

        datasource.updatePill(p);

        Toast.makeText(
            getApplicationContext(),
            R.string.textPillModifiedOK,
            Toast.LENGTH_SHORT).show();

        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);

    }
}
