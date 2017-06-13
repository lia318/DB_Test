package kr.hs.lia318e_mirim.db_test;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    EditText edit_group_name, edit_group_count, edit_result_name, edit_result_count;
    Button but_init, but_insert, but_select;
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
        edit_result_name = (EditText) findViewById(R.id.edit_result_name);
        edit_result_count = (EditText) findViewById(R.id.edit_result_count);

        // DB 생성
        myHelper = new MyDBHelper(this);
        // 기존의 테이블이 존재하면 삭제하고 테이블을 새로 생성한다.
        but_init.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqlDB = myHelper.getWritableDatabase();
                myHelper.onUpgrade(sqlDB, 1, 2);
                sqlDB.close();
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
            String sql = "drop table if exist idolTable";
            sqLiteDatabase.execSQL(sql);
            onCreate(sqLiteDatabase);
        }
    } // end of MyDBHelper
} // end of MainActivity
