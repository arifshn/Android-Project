package com.deneme.arif2;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    EditText edit_name, edit_age ;
    TextView my_txt_id ,my_txt_name,my_txt_age ;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        edit_name = findViewById(R.id.editText_name);
        edit_age = findViewById(R.id.editText_age);

        my_txt_id = findViewById(R.id.textview_id);
        my_txt_name = findViewById(R.id.textview_name);
        my_txt_age = findViewById(R.id.textview_age);

        Button buttonInsert = findViewById(R.id.btn_insert);
        Button buttonUpdate = findViewById(R.id.btn_update);
        Button buttonDelete = findViewById(R.id.btn_delete);
        Button buttonPrint = findViewById(R.id.btn_getir);

        buttonInsert.setOnClickListener(this::insertData);
        buttonUpdate.setOnClickListener(this::updateData);
        buttonDelete.setOnClickListener(this::deleteData);
        buttonPrint.setOnClickListener(this::printData);

        SQLiteDatabase my_db = this.openOrCreateDatabase("New Students",MODE_PRIVATE,null);
        my_db.execSQL("CREATE TABLE IF NOT EXISTS Table_Students(student_id INTEGER PRIMARY KEY AUTOINCREMENT, student_name NVARCHAR, student_age INTEGER)");
    }

    private void printData(View view) {
        SQLiteDatabase my_db = this.openOrCreateDatabase("New Students",MODE_PRIVATE,null);
        Cursor my_cursor= my_db.rawQuery("SELECT * FROM Table_Students",null);

        while (my_cursor.moveToNext()){
            @SuppressLint("Range") int index_id = my_cursor.getInt(my_cursor.getColumnIndex("student_id"));
            @SuppressLint("Range") String index_name = my_cursor.getString(my_cursor.getColumnIndex("student_name"));
            @SuppressLint("Range") int index_age = my_cursor.getInt(my_cursor.getColumnIndex("student_age"));

            System.out.println("ID = "+index_id);
            System.out.println("Name = "+index_name);
            System.out.println("Age = "+index_age);

            my_txt_id.setText("ID = "+index_id);
            my_txt_name.setText("Name = "+index_name);
            my_txt_age.setText("Age = "+index_age);
        }
        my_cursor.close();
    }

    private void deleteData(View view) {
        int s_id  = Integer.parseInt(edit_age.getText().toString());

        SQLiteDatabase my_db = this.openOrCreateDatabase("New Students",MODE_PRIVATE,null);
        my_db.execSQL("DELETE FROM Table_Students WHERE student_id = ?",new Object[]{s_id});
    }

    private void updateData(View view) {
        String s_name = edit_name.getText().toString();
        int s_id  = Integer.parseInt(edit_age.getText().toString());

        SQLiteDatabase my_db = this.openOrCreateDatabase("New Students",MODE_PRIVATE,null);
        my_db.execSQL("UPDATE Table_Students SET student_name = ? WHERE student_id = ? ",new Object[]{s_name,s_id});
    }

    private void insertData(View view) {
        String s_name = edit_name.getText().toString();
        String s_age  = edit_age.getText().toString();

        int new_age = Integer.parseInt(s_age);

        SQLiteDatabase my_db = this.openOrCreateDatabase("New Students",MODE_PRIVATE,null);
        my_db.execSQL("INSERT INTO Table_Students(student_name, student_age) VALUES(?,?)",new Object[]{s_name,new_age});
    }
}