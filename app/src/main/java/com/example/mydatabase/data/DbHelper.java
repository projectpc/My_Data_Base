package com.example.mydatabase.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;


public class DbHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = DbHelper.class.getSimpleName();

    /**
     * Имя файла базы данных
     */
    private static final String DATABASE_NAME = "hotel.db";

    /**
     * Версия базы данных. При изменении схемы увеличить на единицу
     */
    private static final int DATABASE_VERSION = 1;

    /**
     * Конструктор .
     *
     * @param context Контекст приложения
     */
    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Строка для создания таблицы
        String SQL_CREATE_GUESTS_TABLE = "CREATE TABLE " + DBClass.GuestEntry.TABLE_NAME + " ("
                + DBClass.GuestEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DBClass.GuestEntry.COLUMN_NAME + " TEXT NOT NULL, "
                + DBClass.GuestEntry.COLUMN_CITY + " TEXT NOT NULL, "
                + DBClass.GuestEntry.COLUMN_GENDER + " INTEGER NOT NULL DEFAULT 3, "
                + DBClass.GuestEntry.COLUMN_AGE + " INTEGER NOT NULL DEFAULT 0);";

        // Запускаем создание таблицы
        db.execSQL(SQL_CREATE_GUESTS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
       // Запишем в журнал
        Log.w("SQLite", "Обновляемся с версии " + oldVersion + " на версию " + newVersion);

        // Удаляем старую таблицу и создаём новую
        db.execSQL("DROP TABLE IF EXISTS " + "DATABASE_TABLE");
        // Создаём новую таблицу
        onCreate(db);
    }
}
