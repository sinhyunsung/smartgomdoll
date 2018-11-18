package smartgomdoll.a2018doll;

import android.content.Context;
import android.content.DialogInterface;
import android.nfc.Tag;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

import static android.R.id.list;

public class CreateMotion extends AppCompatActivity {
    private ListView listmotion;
    private ArrayList<String> items;
    private ArrayAdapter<String> adapter;
    static String FILE_NAME;
    static int moternum=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_motion);

        items= new ArrayList<String>();
        adapter= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_single_choice,items);
        listmotion=(ListView) findViewById(R.id.listmotion);

        listmotion.setAdapter(adapter);

    }

    public void createMotion(View v){
        final String items[] = { "왼팔 축", "왼팔 중간", "왼팔 끝","오른팔 축","오른팔 중간"
        ,"오른팔 끝","이동"};


        AlertDialog.Builder adb=new AlertDialog.Builder(this);
        adb.setTitle("제어 할 모터를 설정하세요.");
        adb.setSingleChoiceItems(items, 0,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        moternum=whichButton+1;
                        // 각 리스트를 선택했을때
                    }
                }).setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // OK 버튼 클릭시 , 여기서 선택한 값을 메인 Activity 로 넘기면 된다.

                        createMotion2();

                    }
                }).setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Cancel 버튼 클릭시
                        moternum=1;
                    }
                });
        adb.show();


    }

    public void createMotion2() {
        final EditText et=new EditText(CreateMotion.this);
        AlertDialog.Builder adb=new AlertDialog.Builder(this);
        if(moternum<=6) {
            adb.setTitle("모터의 각도를 입력하세요.");
            adb.setMessage("0~180도");
            adb.setView(et);
            adb.setPositiveButton("모션 넣기", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                        items.add(moternum + "," + et.getText().toString());
                        adapter.notifyDataSetChanged();

                    moternum = 1;

                }
            });

        }else{
            final String item[] = { "전진", "후진", "우회전","좌회전","정지"};
            adb.setSingleChoiceItems(item, 0,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            moternum=whichButton+1;
                        }
                    }).setPositiveButton("Ok",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            // OK 버튼 클릭시 , 여기서 선택한 값을 메인 Activity 로 넘기면 된다.
                            switch(moternum){
                                case 1:{
                                    items.add("f");
                                    adapter.notifyDataSetChanged();
                                    break;
                                }
                                case 2:{
                                    items.add("b");
                                    adapter.notifyDataSetChanged();
                                    break;
                                }
                                case 3:{
                                    items.add("r");
                                    adapter.notifyDataSetChanged();
                                    break;
                                }
                                case 4:{
                                    items.add("l");
                                    adapter.notifyDataSetChanged();
                                    break;
                                }
                                case 5:{
                                    items.add("p");
                                    adapter.notifyDataSetChanged();
                                    break;
                                }

                            }
                            moternum=1;

                        }
                    }).setNegativeButton("Cancel",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            // Cancel 버튼 클릭시
                            moternum=1;
                        }
                    });
        }
        ;
        adb.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                moternum=1;
            }
        });
        adb.show();
    }

    public void createDelay(View v){

        final EditText et=new EditText(CreateMotion.this);

        AlertDialog.Builder adb=new AlertDialog.Builder(this);
        adb.setTitle("딜레이 설정");
        adb.setMessage("딜레이를 입력하세요.");
        adb.setView(et);
        adb.setPositiveButton("딜레이 넣기", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                items.add("d,"+et.getText().toString());
                adapter.notifyDataSetChanged();

            }
        });
        adb.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        adb.show();

    }

    public void delete(View v){
        int checked = listmotion.getCheckedItemPosition();
        int count = adapter.getCount();

        if(checked >-1 && checked<count){
            items.remove(checked);
            listmotion.clearChoices();
            adapter.notifyDataSetChanged();
        }
    }

    public void filesave2(){
        try {
            FileOutputStream fos = openFileOutput(FILE_NAME, Context.MODE_APPEND);

            String str = "";

            if(listmotion.getCount() != 0) {
                for(int i=0; i<listmotion.getCount(); i++) {

                    str += (String)listmotion.getItemAtPosition(i) ; // "," 부호를 끼워 넣어서 저장

                        str+= ">";

                }

            }

            fos.write(str.getBytes());
            fos.close();

            Toast.makeText(CreateMotion.this,"저장완료",Toast.LENGTH_SHORT).show();

        } catch (IOException e) {

            e.getMessage();
        }
    }



    public void filesave(View v){
        final EditText et=new EditText(CreateMotion.this);

        AlertDialog.Builder adb=new AlertDialog.Builder(this);
        adb.setTitle("파일 저장");
        adb.setMessage("파일 이름을 입력하세요.");
        adb.setView(et);
        adb.setPositiveButton("파일 저장", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

               FILE_NAME=(et.getText().toString())+".txt";
                filesave2();


            }
        });
        adb.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        adb.show();
    }
    public void fileload(View v){
        final EditText et=new EditText(CreateMotion.this);

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

            while(strData.indexOf(">")!=-1){

                items.add(strData.substring(0,strData.indexOf(">")));
                strData=strData.substring(strData.indexOf(">")+1);
            }

            adapter.notifyDataSetChanged();
        } catch (FileNotFoundException e) {

            Toast.makeText(CreateMotion.this,"File Not Found",Toast.LENGTH_SHORT).show();

        } catch (IOException e) {

            e.getMessage();
        }
    }
    public void exit(View v){
        finish();
    }
}
