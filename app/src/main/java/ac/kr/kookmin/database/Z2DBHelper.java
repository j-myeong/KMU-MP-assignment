package ac.kr.kookmin.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Z2DBHelper extends SQLiteOpenHelper {

    public Z2DBHelper(@Nullable Context context) {
        super(context, "z2.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE users(id VARCHAR(20) PRIMARY KEY, pw VARCHAR(20) NOT NULL, name VARCHAR(30) NOT NULL, phone VARCHAR(12) NOT NULL, address VARCHAR(200) NOT NULL)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS users");
        onCreate(sqLiteDatabase);
    }

}
