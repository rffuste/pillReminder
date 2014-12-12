package rffsystems.es.pillreminder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TimePicker;


public class DetailsActivity extends Activity {

    public String MESSAGE_PILL_DETAILS_NAME = "rffsystems.es.pillreminder.MESSAGE_PILL_DETAILS_NAME";
    public String MESSAGE_PILL_DETAILS_TIME = "rffsystems.es.pillreminder.MESSAGE_PILL_DETAILS_TIME";
    public String MESSAGE_PILL_DETAILS_DOSIS = "rffsystems.es.pillreminder.MESSAGE_PILL_DETAILS_DOSIS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String name,time;
        int dosis;

        setContentView(R.layout.activity_deatils);

        // Get the message from the intent
        Intent intent = getIntent();

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

        pillDosis.setValue(Integer.valueOf(message_pill_dosis));



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
}
