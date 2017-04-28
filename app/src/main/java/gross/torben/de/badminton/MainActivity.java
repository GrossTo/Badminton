package gross.torben.de.badminton;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import gross.torben.de.badminton.Connect.ConnectActivity;
import gross.torben.de.badminton.Connect.ConnectActivity2;
import gross.torben.de.badminton.Logger.Logger;
import gross.torben.de.badminton.Record.RecordActivity;
import gross.torben.de.badminton.Settings.SettingsActivity;


public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    private Button connect;
    private Button record;
    private Logger logger;
    private Button settings;
    private Toolbar toolbar;
    private MenuItem search;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        logger = new Logger(this.getClass().getSimpleName(), null ,"");

        connect = (Button) findViewById(R.id.btnConnect);
        record = (Button) findViewById(R.id.btnRecord);
        settings = (Button) findViewById(R.id.btnSettings);
        search = (MenuItem) findViewById(R.id.action_search);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        //myToolbar.setTitleTextColor(0xffffff);



        connect.setOnClickListener(this);
        record.setOnClickListener(this);
        settings.setOnClickListener(this);
        Log.e(Constants.LOG, "Created");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if (id == R.id.action_settings)
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v)
    {
        logger.log("Clicked");
        if (v == connect)
        {
            logger.log("Clicked on Connect");
            Intent intent = new Intent(this, ConnectActivity2.class);
            logger.log("NewIntent");
            startActivity(intent);

            logger.log("Ausführen ConnectActivity");
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
