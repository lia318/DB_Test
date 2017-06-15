package kr.hs.lia318e_mirim.db_test;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText edit_group_name, edit_group_count, edit_result_name, edit_result_count;
    Button but_init, but_insert, but_select, but_updata;
    MyDBHelper myHelper;
    SQLiteDatabase sqlDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edit_group_name = (EditText) findViewById(R.id.edit_group_name);
        edit_group_count = (EditText) findViewById(R.id.edit_group_count);
        but_init = (Button) findViewById(R.id.but_init);
        but_insert = (Button) findViewById(R.id.but_insert);
        but_select = (Button) findViewById(R.id.but_select);
        but_updata = (Button) findViewById(R.id.but_updata);
        edit_result_name = (EditText) findViewById(R.id.edit_result_name);
        edit_result_count = (EditText) findViewById(R.id.edit_result_count);

        // DB 생성
        myHelper = new MyDBHelper(this); // 기존의 테이블이 존재하면 삭제하고 테이블을 새로 생성한다.

        but_init.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqlDB = myHelper.getWritableDatabase();
                myHelper.onUpgrade(sqlDB, 1, 2);
                sqlDB.close();
            }
        });

        // 데이터 삽입
        but_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqlDB = myHelper.getWritableDatabase();
                String sql = "insert into idolTable values('"+edit_group_name.getText()+"',"+edit_group_count.getText()+")"; // integer : "" 사용
                sqlDB.execSQL(sql);
                sqlDB.close();
                Toast.makeText(MainActivity.this, "저장됨", Toast.LENGTH_SHORT).show();

                edit_group_name.setText("");
                edit_group_count.setText("");
            }
        });

        // 데이터를 조회
        but_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqlDB = myHelper.getReadableDatabase();
                String sql = "select * from idolTable";
                Cursor cursor = sqlDB.rawQuery(sql, null); // 반환하는 값 : 결과행에 있는 데이터 사용을 위해 Cursor 필요
                String names = "Idol Name"+"\r\n"+"==================="+"\r\n"; // \r : 커서를 첫번째?로 이동
                String counts = "Idol Count"+"\r\n"+"==================="+"\r\n";
                while(cursor.moveToNext()){
                    names += cursor.getString(0) + "\r\n";
                    counts += cursor.getInt(1) + "\r\n"; // 자료형에 맞춰서 반환
                }
                edit_result_name.setText(names);
                edit_result_count.setText(counts);

                cursor.close();
                sqlDB.close();
            }
        });

        // 데이터 수정
        but_updata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqlDB = myHelper.getWritableDatabase();
                String sql = "updata idolTable set idolCount = "+edit_group_count.getText()+" where idolName = '"+edit_group_name.getText()+"'";
                // 두번째 ""에서 공백 필수

                sqlDB.execSQL(sql);
                sqlDB.close();
                Toast.makeText(MainActivity.this, "수정됨", Toast.LENGTH_SHORT).show();
            }
        });
    } // end of onCreate

    class MyDBHelper extends SQLiteOpenHelper{
        // idolDB라는 이름의 DB가 생성
        public MyDBHelper(Context context) {
            super(context, "idolDB", null, 1);
        }
        @Override // idolTable라는 이름의 Table 생성
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            String sql = "create table idolTable(idolName text not null primary key, idolCount integer)";
            sqLiteDatabase.execSQL(sql);
        }
        @Override // 이미 idolTable라는 Table 존재 시 기존 Table 삭제 후 새로 Table을 만들 때 사용
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int ii) {
            String sql = "drop table if exists idolTable";
            sqLiteDatabase.execSQL(sql);
            onCreate(sqLiteDatabase);
        }
    } // end of MyDBHelper
} // end of MainActivity
