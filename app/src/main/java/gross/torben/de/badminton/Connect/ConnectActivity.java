package gross.torben.de.badminton.Connect;


import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import java.util.Set;

import gross.torben.de.badminton.Constants;
import gross.torben.de.badminton.Logger.Logger;
import gross.torben.de.badminton.R;

/**
 * Created by Torben on 04.04.2017.
 */

public class ConnectActivity extends AppCompatActivity implements View.OnClickListener
{

    private Button searchDevices;
    private Logger logger;
    private TextView tvDevices;
    private TextView tvLog;
    private BluetoothAdapter myBluetoothAdapter;
    private final static int REQUEST_ENABLE_BT = 1;
    private final static int REQUEST_ENABLE_BT_VISIBLE = 2;
    private final static int VISIBLE_TIME = 300;

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action))
            {
                // Discovery has found a device. Get the BluetoothDevice
                // object and its info from the Intent.
                logger.log("Eingang");
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                String deviceName = device.getName();
                String deviceHardwareAddress = device.getAddress(); // MAC address
                tvDevices.append(deviceName + "\n");
                tvDevices.append(deviceHardwareAddress + "\n");
            }
        }
    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_ENABLE_BT)
        {
            if(resultCode == RESULT_OK)
            {
                logger.log("Bluetooth wurde eingeschalten");
                makeDeviceVisible();
            }
            else
            {
                finish();
            }
        }
        if(requestCode == REQUEST_ENABLE_BT_VISIBLE)
        {
            if(resultCode == VISIBLE_TIME)
            {
                logger.log("Geräte wurde für andere Geräte sichtbar gemacht");

                if (myBluetoothAdapter.isDiscovering())
                {
                    myBluetoothAdapter.cancelDiscovery();
                    logger.log("Device is not discovering");
                }
                if (myBluetoothAdapter.startDiscovery())
                {
                    logger.log("Device is discovering");
                }
            }
            else
            {
                finish();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setTitle(this.getClass().getSimpleName());
        setContentView(R.layout.activity_connect);
        tvDevices = (TextView) findViewById(R.id.tvDevices);
        tvLog = (TextView) findViewById(R.id.tvLog);
        logger = new Logger(this.getClass().getSimpleName(), tvLog ,"");
        logger.log("onCreate: Connect Activity");
        Log.d(Constants.LOG, "ConnectActivity Created");

        searchDevices = (Button) findViewById(R.id.search_devices);
        searchDevices.setOnClickListener(this);
        // Register for broadcasts when a device is discovered.
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReceiver, filter);

    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }

    @Override
    public void onClick(View v)
    {
        if(v == searchDevices)
        {
            myBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            if (myBluetoothAdapter == null)
            {
                logger.log("Device does not support Bluetooth");
                finish();
            }
            if (!myBluetoothAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
                logger.log("Enable Bluetooth");
            } else
            {
                makeDeviceVisible();
            }



            Set<BluetoothDevice> pairedDevices = myBluetoothAdapter.getBondedDevices();
            logger.log("Show all paired Devices");
            if (pairedDevices.size() > 0) {
                // There are paired devices. Get the name and address of each paired device.
                for (BluetoothDevice device : pairedDevices) {
                    String deviceName = device.getName();
                    String deviceHardwareAddress = device.getAddress(); // MAC address

                    tvDevices.append(deviceName + "\n");
                    tvDevices.append(deviceHardwareAddress + "\n");
                }
            }
            myBluetoothAdapter.startDiscovery();


        }
    }

    private void makeDeviceVisible()
    {
        Intent discoverableIntent =
                new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, VISIBLE_TIME);
        startActivityForResult(discoverableIntent, REQUEST_ENABLE_BT_VISIBLE);
        logger.log("Device sichtbar machen");
    }
}
