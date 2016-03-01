package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Calendar;

/**
 * メインアクティビティ
 * 占いアプリのメインプログラム
 */
public class MainActivity extends AppCompatActivity {

    // ----------fields----------

    /** 年選択用スピナー */
    private Spinner yearSpinner;
    /** 月選択用スピナー */
    private Spinner monthSpinner;
    /** 日選択用スピナー */
    private Spinner daySpinner;

    /** 氏名入力用エディットテキスト */
    private EditText nameEditText;

    /** 開始年 */
    private static final int START_YEAR = 1950;

    /** 入力された名前 */
    public static final String INPUT_NAME = "InputName";
    /** 計算値 */
    public static final String RESULT_NUMBER = "ResultNumber";

    // ----------methods----------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // レイアウト定義ファイルのデータをアクティビティにセット
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // レイアウトよりビューを取得
        yearSpinner = (Spinner) findViewById(R.id.yaerSpinner);
        monthSpinner = (Spinner) findViewById(R.id.monthSpinner);
        daySpinner = (Spinner) findViewById(R.id.daySpinner);
        nameEditText = (EditText) findViewById(R.id.nameEditText);

        // Spinnerにデータをセットする
        yearSpinner.setAdapter(createYearAdapter());
        monthSpinner.setAdapter(createMonthAdapter());
        daySpinner.setAdapter(createDayAdapter());

        // 年選択用Spinnerにリスナーを設定する
        yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // 日設定用Spinnerの値を更新する
                daySpinner.setAdapter(createDayAdapter());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // 月選択用Spinnerにリスナーを設定する
        monthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // 日設定用Spinnerの値を更新する
                daySpinner.setAdapter(createDayAdapter());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // 実行ボタンにリスナーを設定する
        findViewById(R.id.divineButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 入力されている氏名を取得する
                String name = nameEditText.getText().toString();

                // 選択されている生年月日を取得する
                Integer year = (Integer) yearSpinner.getSelectedItem();
                Integer month = (Integer) monthSpinner.getSelectedItem();
                Integer day = (Integer) daySpinner.getSelectedItem();

                // 年、月、日を足しあわせた結果を文字列に変換する
                String str = String.valueOf(year + month + day);

                // 文字列の一番後ろ(1桁目)を取り出し、整数に変換する
                Integer resultNumber = Integer.parseInt(str.substring(str.length() - 1));

                // Intentクラスのオブジェクトを生成し、遷移先をResultActivityクラスとする
                Intent intent = new Intent(getApplicationContext(), ResultActivity.class);

                // 遷移先に渡す値をintentオブジェクトにセットする
                intent.putExtra(INPUT_NAME, name);
                intent.putExtra(RESULT_NUMBER, resultNumber);

                // 画面遷移開始
                startActivity(intent);
            }
        });
    }

    /**
     * 年のArrayAdapterを作成
     */
    private ArrayAdapter<Integer> createYearAdapter() {
        // ArrayAdapterの初期化
        //android.R.layout.simple_spinner_hogehogeについて. これは配置のデザインだ
        //http://developer.android.com/reference/android/R.layout.html, http://www.maibun.org/~nt/technicalnote/android/spinner.html
        ArrayAdapter<Integer> yearAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item);

        // ドロップダウン時の画面レイアウトを設定
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // 現在、西暦何年かを取得する
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);

        // for文を回してArrayAdapterに値をセット
        for (int i = START_YEAR; i <= year; i++) {
            yearAdapter.add(i);
        }

        return yearAdapter;
    }

    /**
     * 月のArrayAdapterを作成
     */
    private ArrayAdapter<Integer> createMonthAdapter() {
        // ArrayAdapterの初期化
        ArrayAdapter<Integer> monthAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item);

        // ドロップダウン時の画面レイアウトを設定
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // for文を回してArrayAdapterに値をセット
        for (int i = 1; i <= 12; i++) {
            monthAdapter.add(i);
        }

        return monthAdapter;
    }

    /**
     * 日のArrayAdapterを作成
     */
    private ArrayAdapter<Integer> createDayAdapter() {
        // ArrayAdapterの初期化
        ArrayAdapter<Integer> dayAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item);

        // ドロップダウン時の画面レイアウトを設定
        dayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // 選択されている月を取得する
        Integer month = (Integer) monthSpinner.getSelectedItem();

        Integer day = 31;
        // 選択されている月が4月、6月、9月、11月のいずれかの時、日の上限を30日とする
        if (month == 4 || month == 6 || month == 9 || month == 11) {
            day = 30;
        }
        // 選択されている月が2月の場合
        else if (month == 2) {

            // 選択されている年を取得する
            Integer year = (Integer) yearSpinner.getSelectedItem();

            // うるう年の場合は日の上限を29日、平年の場合は日の上限を28日とする
            if ((year % 100 != 0 && year % 4 == 0) || year % 400 == 0) {
                day = 29;
            } else {
                day = 28;
            }
        }

        // for文を回してArrayAdapterに値をセット
        for (int i = 1; i <= day; i++) {
            dayAdapter.add(i);
        }

        return dayAdapter;
    }
}