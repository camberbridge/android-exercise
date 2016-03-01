package com.example.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        // レイアウトよりビューを取得
        TextView nameTextView = (TextView) findViewById(R.id.nameTextView);
        TextView resultTextView = (TextView) findViewById(R.id.resultTextView);

        // 遷移元でセットした氏名を取得し、TextViewに設定する
        String name = getIntent().getStringExtra(MainActivity.INPUT_NAME);
        nameTextView.setText(name);

        // 遷移元でセットした計算値を取得し、それに対応する結果をTextViewに設定する
        Integer resultNumber = getIntent().getIntExtra(MainActivity.RESULT_NUMBER, 0);
        String resultStr = getResources().getStringArray(R.array.results)[resultNumber];
        resultTextView.setText(resultStr);
    }
}