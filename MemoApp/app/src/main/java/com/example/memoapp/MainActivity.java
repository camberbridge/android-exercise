package com.example.memoapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * メインアクティビティ
 * メモ帳アプリのメインプログラム
 */
public class MainActivity extends AppCompatActivity {

    // ----------fields----------

    /** タイトル入力用エディットテキスト */
    private EditText titleEditText;
    /** コメント入力用エディットテキスト */
    private EditText commentEditText;

    /** プリファレンス */
    private SharedPreferences preferences;

    /** タイトル保存用キー */
    private static final String KEY_TITLE = "title";
    /** コメント保存用キー */
    private static final String KEY_COMMENT = "comment";
    /** 何も保存されていない時に返す文字列 */
    private static final String NOT_FOUND_DATA = "データが見つかりません。";

    // ----------methods----------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // レイアウトよりEditTextを取得
        titleEditText = (EditText) findViewById(R.id.titleEditText);
        commentEditText = (EditText) findViewById(R.id.commentEditText);

        // プリファレンスをデフォルト名で作成
        preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        // プリファレンスにデータが保存されていれば、保存されているデータをセットする
        if (!preferences.getString(KEY_TITLE, NOT_FOUND_DATA).equals(NOT_FOUND_DATA)) {
            titleEditText.setText(preferences.getString(KEY_TITLE, NOT_FOUND_DATA));
        }
        if (!preferences.getString(KEY_COMMENT, NOT_FOUND_DATA).equals(NOT_FOUND_DATA)) {
            commentEditText.setText(preferences.getString(KEY_COMMENT, NOT_FOUND_DATA));
        }

        // 保存ボタンにリスナーを設定する
        findViewById(R.id.saveButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 入力されているデータを取得
                String title = titleEditText.getText().toString();
                String comment = commentEditText.getText().toString();

                // プリファレンスにデータを保存
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(KEY_TITLE, title);
                editor.putString(KEY_COMMENT, comment);
                editor.commit();

                // Toastを表示し、保存が完了した旨を通知する
                Toast.makeText(v.getContext(), "保存しました。", Toast.LENGTH_SHORT).show();
            }
        });

        // 削除ボタンにリスナーを設定する
        findViewById(R.id.deleteButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // プリファレンスからデータを削除
                SharedPreferences.Editor editor = preferences.edit();
                editor.remove(KEY_TITLE);
                editor.remove(KEY_COMMENT);
                editor.commit();

                // 入力欄を空にする
                titleEditText.setText("");
                commentEditText.setText("");

                // Toastを表示し、削除が完了した旨を通知する
                Toast.makeText(v.getContext(), "削除しました。", Toast.LENGTH_SHORT).show();
            }
        });
    }
}