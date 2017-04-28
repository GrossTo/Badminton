package gross.torben.de.badminton.Connect;




import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import android.widget.Toast;
import android.widget.ToggleButton;

import gross.torben.de.badminton.R;

public class ConnectActivity2 extends Activity
{

    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            // Whenever a remote Bluetooth device is found
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Get the BluetoothDevice object from the Intent
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_NAME);
                // Add the name and address to an array adapter to show in a ListView
                adapter.add(device.getName() + "\n" + device.getAddress());
            }
        }
    };

    private BluetoothAdapter bluetoothAdapter;
    private ToggleButton toggleButton;
    private ListView listview;
    private ArrayAdapter adapter;
    private final static int ENABLE_BT_REQUEST_CODE = 1;
    private final static int DISCOVERABLE_BT_REQUEST_CODE = 2;
    private final static int DISCOVERABLE_DURATION = 300;

    public void onToggleClicked(View view)
    {
        adapter.clear();

        ToggleButton togglebutton = (ToggleButton) view;

        if (bluetoothAdapter == null)
        {
            Toast.makeText(this, "No Bluetooth are Supported", Toast.LENGTH_LONG).show();
            // Device does not support Bluetooth
            togglebutton.setChecked(false);
        } else {
            if (toggleButton.isChecked()) {
                if (!bluetoothAdapter.isEnabled()) {
                    Intent enableBluetoothIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(enableBluetoothIntent, ENABLE_BT_REQUEST_CODE);
                } else {
                    Toast.makeText(getApplicationContext(), "Your device has already been enabled." + "\n" + "Scanning for remote Bluetooth devices...", Toast.LENGTH_SHORT).show();
                    // To discover remote Bluetooth devices
                    discoverDevices();
                    // Make local device discoverable by other devices
                    makeDiscoverable();
                }
                // Any valid Bluetooth operations
            } else { // Turn off bluetooth
                bluetoothAdapter.disable();
                adapter.clear();
                Toast.makeText(getApplicationContext(), "Your device is now disabled.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bluetooth_test);
        toggleButton = (ToggleButton) findViewById(R.id.toggleButton);

        listview = (ListView) findViewById(R.id.listView);

        adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1);

        listview.setAdapter(adapter);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        this.registerReceiver(broadcastReceiver, filter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ENABLE_BT_REQUEST_CODE)
        {
            if (resultCode == Activity.RESULT_OK)
            {
                Toast.makeText(getApplicationContext(), "Ha! Bluetooth has been enabled.", Toast.LENGTH_SHORT).show();
                makeDiscoverable();
                discoverDevices();
            }
            else
            { // RESULT_CANCELED as user refuse or failed
                Toast.makeText(getApplicationContext(), "Bluetooth is not enabled.",
                        Toast.LENGTH_SHORT).show();
                toggleButton.setChecked(false);
            }
        }
        else if (requestCode == DISCOVERABLE_BT_REQUEST_CODE)
        {
            if (resultCode == DISCOVERABLE_DURATION)
            {
                Toast.makeText(getApplicationContext(), "Your device is now discoverable by other devices for " + DISCOVERABLE_DURATION + " seconds", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Fail to enable discoverability on your device.", Toast.LENGTH_SHORT).show();
            }
        }
    }


    protected void makeDiscoverable(){
        // Make local device discoverable
        Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, DISCOVERABLE_DURATION);
        startActivityForResult(discoverableIntent, DISCOVERABLE_BT_REQUEST_CODE);
    }

    protected void discoverDevices(){
        // To scan for remote Bluetooth devices
        if (bluetoothAdapter.startDiscovery()) {
            Toast.makeText(getApplicationContext(), "Discovering other bluetooth devices...",
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Discovery failed to start.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Register the BroadcastReceiver for ACTION_FOUND
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        this.registerReceiver(broadcastReceiver, filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.unregisterReceiver(broadcastReceiver);
    }

}