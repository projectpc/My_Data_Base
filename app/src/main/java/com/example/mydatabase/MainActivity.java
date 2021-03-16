package com.example.mydatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.mydatabase.data.DBClass;
import com.example.mydatabase.data.DbHelper;
public class MainActivity extends AppCompatActivity {
    private final DbHelper mDbHelper = new DbHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }


    private void displayDatabaseInfo() {
        // Создадим и откроем для чтения базу данных
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // Зададим условие для выборки - список столбцов
        String[] projection = {
                DBClass.GuestEntry._ID,
                DBClass.GuestEntry.COLUMN_NAME,
                DBClass.GuestEntry.COLUMN_CITY,
                DBClass.GuestEntry.COLUMN_GENDER,
                DBClass.GuestEntry.COLUMN_AGE };

        // Делаем запрос
        Cursor cursor = db.query(
                DBClass.GuestEntry.TABLE_NAME,   // таблица
                projection,            // столбцы
                null,                  // столбцы для условия WHERE
                null,                  // значения для условия WHERE
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);                   // порядок сортировки

        TextView displayTextView = (TextView) findViewById(R.id.text_view_info);

        try {
            displayTextView.setText("Таблица содержит " + cursor.getCount() + " гостей.\n\n");
            displayTextView.append(DBClass.GuestEntry._ID + " - " +
                    DBClass.GuestEntry.COLUMN_NAME + " - " +
                    DBClass.GuestEntry.COLUMN_CITY + " - " +
                    DBClass.GuestEntry.COLUMN_GENDER + " - " +
                    DBClass.GuestEntry.COLUMN_AGE + "\n");

            // Узнаем индекс каждого столбца
            int idColumnIndex = cursor.getColumnIndex(DBClass.GuestEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(DBClass.GuestEntry.COLUMN_NAME);
            int cityColumnIndex = cursor.getColumnIndex(DBClass.GuestEntry.COLUMN_CITY);
            int genderColumnIndex = cursor.getColumnIndex(DBClass.GuestEntry.COLUMN_GENDER);
            int ageColumnIndex = cursor.getColumnIndex(DBClass.GuestEntry.COLUMN_AGE);

            // Проходим через все ряды
            while (cursor.moveToNext()) {
                // Используем индекс для получения строки или числа
                int currentID = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                String currentCity = cursor.getString(cityColumnIndex);
                int currentGender = cursor.getInt(genderColumnIndex);
                int currentAge = cursor.getInt(ageColumnIndex);
                // Выводим значения каждого столбца
                displayTextView.append(("\n" + currentID + " - " +
                        currentName + " - " +
                        currentCity + " - " +
                        currentGender + " - " +
                        currentAge));
            }
        } finally {
            // Всегда закрываем курсор после чтения
            cursor.close();
        }
    }

    private void insertGuest() {

        // Gets the database in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        // Создаем объект ContentValues, где имена столбцов ключи,
        // а информация о госте является значениями ключей
        ContentValues values = new ContentValues();
        values.put(DBClass.GuestEntry.COLUMN_NAME, "Мурзик");
        values.put(DBClass.GuestEntry.COLUMN_CITY, "Мурманск");
        values.put(DBClass.GuestEntry.COLUMN_GENDER, DBClass.GuestEntry.GENDER_MALE);
        values.put(DBClass.GuestEntry.COLUMN_AGE, 7);

        long newRowId = db.insert(DBClass.GuestEntry.TABLE_NAME, null, values);
    }

    public void But_Click(View view) {
        insertGuest();
        displayDatabaseInfo();

    }
}