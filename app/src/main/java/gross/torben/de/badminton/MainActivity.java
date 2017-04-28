package gross.torben.de.badminton;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import gross.torben.de.badminton.Connect.ConnectActivity;
import gross.torben.de.badminton.Record.RecordActivity;
import gross.torben.de.badminton.Settings.SettingsActivity;


public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    private Button connect;
    private Button record;
    private Button settings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        connect = (Button) findViewById(R.id.btnConnect);
        record = (Button) findViewById(R.id.btnRecord);
        settings = (Button) findViewById(R.id.btnSettings);

        connect.setOnClickListener(this);
        record.setOnClickListener(this);
        settings.setOnClickListener(this);
        Log.e(Constants.LOG, "Created");
    }

    @Override
    public void onClick(View v)
    {
        Log.e(Constants.LOG, "Clicked");
        if (v == connect)
        {
            Log.e(Constants.LOG, "Clicked on Connect");
            Intent intent = new Intent(this, ConnectActivity.class);
            Log.e(Constants.LOG, "NewIntent");
            startActivity(intent);

            Log.e(Constants.LOG, "Ausführen ConnectActivity");
        }
        else if (v == record)
        {
            Intent intent = new Intent (this, RecordActivity.class);
            startActivity(intent);
        }
        else if (v == settings)
        {
            Intent intent = new Intent (this, SettingsActivity.class);
            startActivity(intent);
        }
    }


}
