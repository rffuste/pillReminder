package rffsystems.es.pillreminder;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;



public class MainActivity extends ListActivity {

    private ListView listview;
    private DbManager datasource;
    private List<Pill> values;
    private ArrayAdapter<Pill> adapter;
    private Pill select;

    public String MESSAGE_PILL_DETAILS_NAME = "rffsystems.es.pillreminder.MESSAGE_PILL_DETAILS_NAME";
    public String MESSAGE_PILL_DETAILS_TIME = "rffsystems.es.pillreminder.MESSAGE_PILL_DETAILS_TIME";
    public String MESSAGE_PILL_DETAILS_DOSIS = "rffsystems.es.pillreminder.MESSAGE_PILL_DETAILS_DOSIS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        datasource = new DbManager(this);
        datasource.open();

        values = datasource.getAllPills();


        // use the SimpleCursorAdapter to show the
        // elements in a ListView
        adapter = new ArrayAdapter<Pill>(this,
           android.R.layout.simple_list_item_1, values);


        if (!adapter.isEmpty())
            setListAdapter(adapter);

        /* Ja que no tenim en si un listview definit podem fer directamentr un getListView
         * i ens dona el que hi ha per defecte
         */
        ListView lv = getListView();
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> av, View v, int pos, long id)
            {
                final int p;

                p = pos;
                select = (Pill) getListAdapter().getItem(p);

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                builder.setTitle(select.getPillName() + ": " + getString(R.string.textConfirmDelePill));
                builder.setMessage(getString(R.string.textDeletePillQuestion));

                builder.setPositiveButton(getString(R.string.textYes), new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing but close the dialog

                        Pill select = (Pill) getListAdapter().getItem(p);


                datasource.deletePill(select);

                Toast.makeText(
                        getApplicationContext(),
                        getString(R.string.textPillDeleted),
                        Toast.LENGTH_SHORT).show();

                updateListView();


                        dialog.dismiss();
                    }

                });

                builder.setNegativeButton(getString(R.string.textNo), new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();

                return true;
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int arg2,long itemID) {


                //View itemView = view;
                //String fromDate = ((TextView)itemView.findViewById(R.id.fromDate)).getText().toString();
                int position = (int) arg0.getSelectedItemId();

                //obtain pill data and send it to the details/modify activity
                select = (Pill) getListAdapter().getItem(position);


                //open details activity os selected pill item.
                openDetails(select);


            }
        });


    }

    public void openDetails(Pill p)
    {
        String pill_name;
        String pill_time;
        int pill_dosis;

        pill_name = select.getPillName();
        pill_time = select.getPillTime();
        pill_dosis = select.getPillDosis();

        Intent intent = new Intent(this, DetailsActivity.class);

        intent.putExtra(MESSAGE_PILL_DETAILS_NAME, pill_name);
        intent.putExtra(MESSAGE_PILL_DETAILS_TIME, pill_time);
        intent.putExtra(MESSAGE_PILL_DETAILS_DOSIS, String.valueOf(pill_dosis));

        startActivity(intent);


    }


    @Override
    protected void onResume() {
        datasource.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        datasource.close();
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
        else
        {
            if (id == R.id.action_create) {
                Intent intent = new Intent(this, CreatePillActivity.class);
                startActivity(intent);
            }
        }


        return super.onOptionsItemSelected(item);
    }


    public void updateListView() {
        final List<Pill> values = datasource.getAllPills();
        final ArrayAdapter<Pill> adapter = new ArrayAdapter<Pill>(this,
                android.R.layout.simple_list_item_1, values);

        setListAdapter(adapter);
    }


}
