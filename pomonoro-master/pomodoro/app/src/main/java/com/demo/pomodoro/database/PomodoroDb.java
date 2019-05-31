package com.demo.pomodoro.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.database.sqlite.SQLiteStatement;

import com.demo.pomodoro.activity.done.Done;
import com.demo.pomodoro.activity.todo.Todo;
import com.demo.pomodoro.utils.Constant;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

public class PomodoroDb extends SQLiteAssetHelper {

    public PomodoroDb(Context context) {
        super(context, Constant.DB_NAME, null, Constant.DB_VER);
    }

    public List<Todo> getAllToDo() {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        String[] sqlSelect = {Constant.JOB_ID, Constant.JOB_TITLE, Constant.JOB_NAME,
                Constant.JOB_COUNT};
        String table = Constant.TBL_JOB;
        qb.setTables(table);
        Cursor c = qb.query(db, sqlSelect, null, null, null, null, "Id DESC");
        List<Todo> todoList = new ArrayList<>();
        if (c.moveToFirst()) {
            do {
                Todo todo = new Todo();
                todo.setId(c.getInt(c.getColumnIndex(Constant.JOB_ID)));
                todo.setTitle(c.getString(c.getColumnIndex(Constant.JOB_TITLE)));
                todo.setDescription(c.getString(c.getColumnIndex(Constant.JOB_NAME)));
                todo.setCount(c.getInt(c.getColumnIndex(Constant.JOB_COUNT)));
                todoList.add(todo);
            } while (c.moveToNext());
        }
        return todoList;
    }

    public List<Done> getAllDone() {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        String[] sqlSelect = {Constant.JOB_ID, Constant.DONE_TITLE, Constant.DONE_DESCRIPTION};
        String table = Constant.TBL_DONE;
        qb.setTables(table);
        Cursor c = qb.query(db, sqlSelect, null, null, null, null, "Id DESC");
        List<Done> doneList = new ArrayList<>();
        if (c.moveToFirst()) {
            do {
                Done done = new Done();
                done.setId(c.getInt(c.getColumnIndex(Constant.JOB_ID)));
                done.setTitle(c.getString(c.getColumnIndex(Constant.DONE_TITLE)));
                done.setDescription(c.getString(c.getColumnIndex(Constant.DONE_DESCRIPTION)));
                doneList.add(done);
            } while (c.moveToNext());
        }
        return doneList;
    }

    public void addJob(String title, String description, int count, String date, int currentmonth,int CountCircle) {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "INSERT INTO Job VALUES(NULL,?,?,?,?,?,?)";
        SQLiteStatement statement = db.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, title);
        statement.bindString(2, description);
        statement.bindLong(3, count);
        statement.bindString(4, date);
        statement.bindLong(5, currentmonth);
        statement.bindLong(6, CountCircle);
        statement.executeInsert();
    }

    public void updateJob(int count, int id) {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "UPDATE Job SET CountJob = ? WHERE Id = ?";
        SQLiteStatement statement = db.compileStatement(sql);
        statement.clearBindings();

        statement.bindLong(1, count);
        statement.bindLong(2, id);
        statement.executeInsert();
    }

    public void updateCountDay(int countjob, int id) {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "UPDATE Job SET CountJob = ? WHERE Id = ?";
        SQLiteStatement statement = db.compileStatement(sql);
        statement.clearBindings();

        statement.bindLong(1, countjob);
        statement.bindLong(2, id);
        statement.executeInsert();
    }

    public void updateDate(String date, int count, int currentmonth, int id) {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "UPDATE Job SET DateJob = ?,CountJob = ?,CurrentMonth = ? WHERE Id = ?";
        SQLiteStatement statement = db.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, date);
        statement.bindLong(2, count);
        statement.bindLong(3, currentmonth);
        statement.bindLong(4, id);
        statement.executeInsert();
    }


    public Todo getCount(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Constant.TBL_JOB,
                new String[]{Constant.JOB_COUNT},
                "Id=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare note object
        Todo todo = new Todo(
                cursor.getInt(cursor.getColumnIndex(Constant.JOB_COUNT)));
        // close the db connection
        cursor.close();
        return todo;
    }

    public Todo getCountDay(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Constant.TBL_JOB,
                new String[]{"TitleJob", "CountCircle"},
                "Id=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare note object
        Todo todo = new Todo(
                cursor.getString(cursor.getColumnIndex("TitleJob")),
                cursor.getInt(cursor.getColumnIndex("CountCircle")));
        // close the db connection
        cursor.close();
        return todo;
    }


    public Todo getDate(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Constant.TBL_JOB,
                new String[]{Constant.DATE_JOB},
                "Id=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare note object
        Todo todo = new Todo(
                cursor.getString(cursor.getColumnIndex(Constant.DATE_JOB)));
        // close the db connection
        cursor.close();
        return todo;
    }

    public Todo getCurrentMonth(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Constant.TBL_JOB,
                new String[]{"Id","CountJob", "CurrentMonth"},
                "Id=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare note object
        Todo todo = new Todo( cursor.getInt(cursor.getColumnIndex("Id")),
                cursor.getInt(cursor.getColumnIndex("CountJob")),
                cursor.getInt(cursor.getColumnIndex("CurrentMonth")));
        // close the db connection
        cursor.close();
        return todo;
    }

    public float getSumCountDay(String date) {
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT SUM(CountJob) From Job WHERE DateJob = ?";
        Cursor c = db.rawQuery(query, new String[]{date});
        c.moveToFirst();
        float SumValue = c.getInt(0);
        return SumValue;
    }

    public void addStats(String date, int count, int month) {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "INSERT INTO Stats VALUES(NULL,?,?,?)";
        SQLiteStatement statement = db.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, date);
        statement.bindLong(2, count);
        statement.bindLong(3, month);
        statement.executeInsert();
    }

    public Todo getDone(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Constant.TBL_JOB,
                new String[]{Constant.JOB_TITLE, Constant.JOB_NAME},
                "Id=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare note object
        Todo todo = new Todo(
                cursor.getString(cursor.getColumnIndex(Constant.JOB_TITLE)),
                cursor.getString(cursor.getColumnIndex(Constant.JOB_NAME)));

        // close the db connection
        cursor.close();
        return todo;
    }

    public void addDone(String title, String description) {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "INSERT INTO Done VALUES(NULL,?,?)";
        SQLiteStatement statement = db.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, title);
        statement.bindString(2, description);
        statement.executeInsert();
    }

    public int getCountJob() {
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT COUNT(Id) From Job";
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        int AverageValue = c.getInt(0);
        return AverageValue;
    }

    public int getCountDone() {
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT COUNT(Id) From Done";
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        int AverageValue = c.getInt(0);
        return AverageValue;
    }


    public float getSumStats(int month) {
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT SUM(CountJob) From Stats WHERE MonthJob = ?";
        Cursor c = db.rawQuery(query, new String[]{String.valueOf(month)});
        c.moveToFirst();
        float AverageValue = c.getFloat(0);
        return AverageValue;
    }

    public void  removeFromTodo(String id){
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("DELETE FROM Job WHERE Id = '%s';",id);
        db.execSQL(query);
    }

    public void addComplete(String monthCurrent) {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "INSERT INTO Complete VALUES(NULL,?)";
        SQLiteStatement statement = db.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, monthCurrent);
        statement.executeInsert();
    }


    public float getCountMonth(String monthcurrent) {
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT COUNT(Id) From Complete WHERE MonthCurrent = ?";
        Cursor c = db.rawQuery(query, new String[]{monthcurrent});
        c.moveToFirst();
        float SumValue = c.getInt(0);
        return SumValue;
    }

    public void updateContDay(int CountCircle, int id) {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "UPDATE Job SET CountCircle = ? WHERE Id = ?";
        SQLiteStatement statement = db.compileStatement(sql);
        statement.clearBindings();

        statement.bindLong(1, CountCircle);
        statement.bindLong(2, id);
        statement.executeInsert();
    }
}
