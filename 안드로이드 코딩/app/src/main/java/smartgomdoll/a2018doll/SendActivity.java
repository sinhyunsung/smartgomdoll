package smartgomdoll.a2018doll;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

public class SendActivity extends AppCompatActivity {
    static String FILE_NAME;
    private static final int CONNECTED = 1;
    private static final int DISCONNECTED = 2;
    private static final int MESSAGE_READ = 3;
    private static final int MESSAGE_WRITE = 4;

    private static final int REQUEST_CONNECT_DEVICE = 1;

    private ArrayAdapter<String> messageAdapter;

    private BluetoothAdapter bluetoothAdapter;
    private BluetoothChat chat;
    private Button buttonConnect;
    private Button buttonSend;

    private EditText editText;

    private UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);

        if ((bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()) == null) {
            Toast.makeText(SendActivity.this, "Bluetooth is not supported", Toast.LENGTH_SHORT).show();
        }
        // ACCESS_COARSE_LOCATION or ACCESS_FINE_LOCATION permission is required for Bluetooth from Marshmallow
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(getBaseContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        }

        buttonConnect = (Button) findViewById(R.id.bl);
        buttonConnect.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (bluetoothAdapter.isEnabled() == false) {
                    // Request to enable bluetooth
                    startActivity(new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE));
                    return;
                }
                if (chat == null) {
                    // Launch DeviceListActivity to search bluetooth device
                    startActivityForResult(new Intent(SendActivity.this, DeviceListActivity.class), REQUEST_CONNECT_DEVICE);
                } else {
                    chat.close();
                }
            }
        });

        buttonSend = (Button) findViewById(R.id.send);
        buttonSend.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                TextView textView = (TextView) findViewById(R.id.file);
                String str=textView.getText().toString();




                    chat.send(str.getBytes());




                editText.setText("");
            }
        });

        editText = (EditText) findViewById(R.id.file);
        messageAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (chat != null) {
            chat.close();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            // When DeviceListActivity returns with a device to connect
            case REQUEST_CONNECT_DEVICE:
                if (resultCode == Activity.RESULT_OK) {
                    // MAC address
                    String address = data.getExtras().getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);

                    BluetoothDevice device = bluetoothAdapter.getRemoteDevice(address);
                    BluetoothSocket socket;

                    try {
                        socket = device.createRfcommSocketToServiceRecord(uuid);
                    } catch (IOException e) {
                        break;
                    }
                    chat = new BluetoothChat(socket, handler);
                    chat.start();
                }
                break;
        }
    }

    // The Handler that gets information back from the BluetoothChat
    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CONNECTED:
                    buttonConnect.setText("연결정지");
                    buttonSend.setEnabled(true);
                    break;
                case DISCONNECTED:
                    buttonConnect.setText("블루투스");
                    buttonSend.setEnabled(false);
                    chat = null;
                    break;
                case MESSAGE_READ:
                    try {
                        // Encoding with "EUC-KR" to read 한글
                        messageAdapter.add("< " + new String((byte[]) msg.obj, 0, msg.arg1, "EUC-KR"));
                    } catch (UnsupportedEncodingException e) {
                    }
                    break;
                case MESSAGE_WRITE:
                    messageAdapter.add("> " + new String((byte[]) msg.obj));
                    break;
            }
        }
    };

    // This class connect with a bluetooth device and perform data transmissions when connected.
    private class BluetoothChat extends Thread {
        private BluetoothSocket socket;
        private Handler handler;
        private InputStream inputStream;
        private OutputStream outputStream;

        public BluetoothChat(BluetoothSocket socket, Handler handler) {
            this.socket = socket;
            this.handler = handler;
        }

        public void run() {
            try {
                socket.connect();
                inputStream = socket.getInputStream();
                outputStream = socket.getOutputStream();
            } catch (Exception e) {
                close();
                return;
            }
            handler.obtainMessage(CONNECTED, -1, -1).sendToTarget();

            while (true) {
                try {
                    byte buffer[] = new byte[1024];

                    int bytes = 0;

                    // Read single byte until '\0' is found
                    for (; (buffer[bytes] = (byte) inputStream.read()) != '\0'; bytes++) ;
                    handler.obtainMessage(MESSAGE_READ, bytes, -1, buffer).sendToTarget();
                } catch (IOException e) {
                    close();
                    break;
                }
            }
        }

        public void close() {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                }
                handler.obtainMessage(DISCONNECTED, -1, -1).sendToTarget();
            }
        }

        public void send(byte[] buffer) {
            try {
                outputStream.write(buffer);
                handler.obtainMessage(MESSAGE_WRITE, buffer.length, -1, buffer).sendToTarget();
            } catch (IOException e) {
                close();
            }
        }
    }
    public void fileload(View v){
        final EditText et=new EditText(SendActivity.this);

        AlertDialog.Builder adb=new AlertDialog.Builder(this);
        adb.setTitle("파일 불러오기");
        adb.setMessage("파일 이름을 입력하세요.");
        adb.setView(et);
        adb.setPositiveButton("파일 불러오기", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                FILE_NAME=(et.getText().toString())+".txt";
                load();


            }
        });
        adb.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        adb.show();
    }

    public void load(){
        try {
            FileInputStream fis = openFileInput(FILE_NAME);

            byte[] data = new byte[fis.available()];

            while(fis.read(data)!= -1) {;}
            fis.close();

            String strData = new String(data);
            editText.setText(strData);


        } catch (FileNotFoundException e) {

            Toast.makeText(SendActivity.this,"File Not Found",Toast.LENGTH_SHORT).show();

        } catch (IOException e) {

            e.getMessage();
        }
    }


    public void exit(View v){
        finish();
    }








}
