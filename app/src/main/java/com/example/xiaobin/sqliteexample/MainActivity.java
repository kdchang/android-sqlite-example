package com.example.xiaobin.sqliteexample;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {
    Button createBtn, readBtn, updateBtn, deleteBtn;
    private SQLiteDatabase db;
    private MyDBHelper dbHelper;
    EditText tid, name, score, newScore, sql;
    TextView output;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createBtn = (Button)findViewById(R.id.button);
        readBtn = (Button)findViewById(R.id.button2);
        updateBtn = (Button)findViewById(R.id.button3);
        deleteBtn = (Button)findViewById(R.id.button4);

        tid = (EditText)findViewById(R.id.editText);
        name = (EditText)findViewById(R.id.editText2);
        score = (EditText)findViewById(R.id.editText3);

        output = (TextView)findViewById(R.id.textView6);

        dbHelper = new MyDBHelper(this);
        db = dbHelper.getWritableDatabase();

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long id;
                ContentValues cv = new ContentValues();
                cv.put("_id", Integer.parseInt(tid.getText().toString()));
                cv.put("name", name.getText().toString());
                cv.put("score", Double.parseDouble(score.getText().toString()));
                id = db.insert("todos", null, cv);
                output.setText("新增記錄成功" + id);
            }
        });

        readBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SqlQuery("SELECT * FROM " + "todos");
            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count;
                int id = Integer.parseInt(tid.getText().toString());
                ContentValues cv = new ContentValues();
                cv.put("score", Double.parseDouble(score.getText().toString()));
                count = db.update("todos", cv, "_id=" + id, null);
                output.setText("更新記錄成功" + count);
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count;
                int id = Integer.parseInt(tid.getText().toString());
                count = db.delete("todos" +
                        "", "_id=" + id, null);
                output.setText("刪除記錄成功" + count);
            }
        });
    }

    public void SqlQuery(String sql) {
        String[] colNames;
        String str = "";
        Cursor c = db.rawQuery(sql, null);
        colNames = c.getColumnNames();
        for(int i = 0; i < colNames.length; i++){
            str += colNames[i] + "\t\t";
        }
        str += "\n";
        c.moveToFirst();
        for(int i = 0; i < c.getCount(); i++) {
            str += c.getString(0) + "\t";
            str += c.getString(1) + "\t";
            str += c.getString(2) + "\n";
            c.moveToNext();
        }
        output.setText(str.toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
