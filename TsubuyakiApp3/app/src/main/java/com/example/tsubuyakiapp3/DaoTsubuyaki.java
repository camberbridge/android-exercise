package com.example.tsubuyakiapp3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * DAOクラス
 * Created by Hitoshi on 2015/10/07.
 */
public class DaoTsubuyaki {

    // ----------fields----------

    /** テーブル名 */
    public static String TABLE_NAME = "tsubuyaki";
    /** ID */
    public static String COLUMN_ID = "_id";
    /** コメント */
    public static String COLUMN_COMMENT = "comment";

    // ----------methods----------

    /**
     * テーブルを作成する
     * @return
     */
    public static String create() {
        return "create table " + TABLE_NAME + "(" +
                COLUMN_ID + " integer primary key autoincrement not null, " +
                COLUMN_COMMENT + " text not null" +
                ");";
    }

    /**
     * テーブルからすべてのデータを取得
     * @param context
     * @return
     */
    public static List<String> findAll(Context context) {
        // データベース取得
        SQLiteDatabase db = getReadableDB(context);

        List<String> listTsubuyaki = new ArrayList<String>();

        // テーブルからすべてのデータを取得
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME +
                " order by " + COLUMN_ID, null);

        // データが存在する限り、Listにデータを追加し続ける
        if (cursor.moveToFirst()) {
            do {
                listTsubuyaki.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }
        cursor.close();

        return listTsubuyaki;
    }

    /**
     * テーブルにデータを追加
     * @param context
     * @param comment
     * @return
     */
    public static long insert(Context context, String comment) {
        // データベース取得
        SQLiteDatabase db = getWritableDB(context);

        ContentValues values = new ContentValues();
        values.put(COLUMN_COMMENT, comment);

        return db.insert(TABLE_NAME, null, values);
    }

    /**
     * 読み取り専用でデータベースを取得
     * @param context
     * @return
     */
    private static SQLiteDatabase getReadableDB(Context context) {
        DatabaseOpenHelper helper = new DatabaseOpenHelper(context);
        return helper.getReadableDatabase();
    }

    /**
     * 書き込み可能でデータベースを取得
     * @param context
     * @return
     */
    private static SQLiteDatabase getWritableDB(Context context) {
        DatabaseOpenHelper helper = new DatabaseOpenHelper(context);
        return  helper.getWritableDatabase();
    }
}