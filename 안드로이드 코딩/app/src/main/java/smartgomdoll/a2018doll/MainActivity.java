package smartgomdoll.a2018doll;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void motionClick(View v){
        Intent intent=new Intent(MainActivity.this,CreateMotion.class);
        startActivity(intent);
    }

    public void sendClick(View v){
        Intent intent=new Intent(MainActivity.this,SendActivity.class);
        startActivity(intent);
    }

}
