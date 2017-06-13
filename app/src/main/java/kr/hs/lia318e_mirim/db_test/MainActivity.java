package kr.hs.lia318e_mirim.db_test;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
