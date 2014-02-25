package com.jso.technologies.tarot.scorer.db;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public abstract class AbstractDatabaseRepository<T> implements IDatabaseRepository<T> {
	protected SQLiteOpenHelper databaseHelper;
	protected SQLiteDatabase db;
	protected Context context;

	public void openReadableDatabase(){
		db = databaseHelper.getReadableDatabase();
	}

	public void openWritableDatabase(){
		db = databaseHelper.getWritableDatabase();
	}

	public void close(){
		db.close();
	}

	public boolean exists(String table_name, String[] columns, String[] values){
		openReadableDatabase();
		
		StringBuffer selection = new StringBuffer(columns[0]);
		selection.append(DatabaseHelper.EQUALS_VARIABLE);
		for(int i = 1; i < columns.length; ++i){
			selection.append(" AND ");
			selection.append(columns[i]);
			selection.append(DatabaseHelper.EQUALS_VARIABLE);
		}

		String request = MessageFormat.format(DatabaseHelper.COUNT_REQUEST, table_name, selection);

		Cursor cursor = db.rawQuery(request, values);
		cursor.moveToFirst();
		int nbRows = cursor.getInt(0);
		cursor.close();
		
		close();
		
		return nbRows > 0;
	}

	public List<T> convertCursorListToBeanList(Cursor c) {
		List<T> result = new ArrayList<T>();
	
		if (c.getCount() != 0){
			c.moveToFirst();
			do {
				result.add(getBeanFromCurrentCursor(c));
			} while (c.moveToNext());
		}
		
		c.close();
	
		return result;
	}

	public T convertCursorToBean(Cursor c) {
		c.moveToFirst();
		T bean = getBeanFromCurrentCursor(c);
		c.close();
		return bean;
	}
	
	public Context getContext() {
		return context;
	}
}
