package gross.torben.de.badminton.Connect;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;


import gross.torben.de.badminton.Constants;
import gross.torben.de.badminton.R;

/**
 * Created by Torben on 04.04.2017.
 */

public class ConnectActivity extends Activity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setTitle(this.getClass().getSimpleName());
        setContentView(R.layout.activity_connect);
        Log.e(Constants.LOG, "ConnectActivity Created");
    }
}
