package com.example.lab3mobilepart2;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class StudentListActivity extends AppCompatActivity {
    private DatabaseHelper dbHelper;
    private TextView studentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);

        dbHelper = new DatabaseHelper(this);
        studentList = findViewById(R.id.studentList);

        displayStudentData();
    }

    private void displayStudentData() {
        Cursor cursor = dbHelper.getAllStudents();
        if (cursor != null && cursor.moveToFirst()) {
            StringBuilder sb = new StringBuilder();
            sb.append(String.format("%-5s %-20s %-20s\n", "ID", "ФИО", "Время добавления"));
            sb.append("-----------------------------------------------------\n");

            do {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("ID"));
                @SuppressLint("Range") String lastName = cursor.getString(cursor.getColumnIndex("Фамилия"));
                @SuppressLint("Range") String firstName = cursor.getString(cursor.getColumnIndex("Имя"));
                @SuppressLint("Range") String patronymic = cursor.getString(cursor.getColumnIndex("Отчество"));
                @SuppressLint("Range") String dateAdded = DatabaseHelper.formatTimestamp(cursor.getLong(cursor.getColumnIndex("Время_добавления")));

                String fullName = lastName + " " + firstName + " " + patronymic;
                sb.append(String.format("%-5d %-20s %-20s\n", id, fullName, dateAdded));
            } while (cursor.moveToNext());

            studentList.setText(sb.toString());
            cursor.close();
        } else {
            studentList.setText("Нет данных для отображения.");
        }
    }
}
