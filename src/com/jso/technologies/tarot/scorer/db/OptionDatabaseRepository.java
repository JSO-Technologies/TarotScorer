package com.jso.technologies.tarot.scorer.db;

import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.jso.technologies.tarot.scorer.common.bean.Option;

public class OptionDatabaseRepository extends AbstractDatabaseRepository<Option> {
	
	public OptionDatabaseRepository(Context context) {
		databaseHelper = new DatabaseHelper(context, null);
	}

	@Override
	public List<Option> getAll() {
		openReadableDatabase();
		Cursor cursor = db.query(DatabaseHelper.OPTIONS_TABLE_NAME,
				                new String[] { 	DatabaseHelper.OPTION_COLUMN_ID,
						                        DatabaseHelper.OPTION_COLUMN_VALUE}, 
				                null, null, null, null, 
				                DatabaseHelper.OPTION_COLUMN_ID);
		
		List<Option> optionList = convertCursorListToBeanList(cursor);
		close();
		return optionList;
	}

	@Override
	public Option getById(int id) {
		openReadableDatabase();
		Cursor cursor = db.query(DatabaseHelper.OPTIONS_TABLE_NAME,
				                new String[] { 	DatabaseHelper.OPTION_COLUMN_ID,
						                        DatabaseHelper.OPTION_COLUMN_VALUE}, 
				                DatabaseHelper.OPTION_COLUMN_ID + DatabaseHelper.EQUALS_VARIABLE, 
				                new String[]{String.valueOf(id)}, 
				                null, null, null);

		Option option = convertCursorToBean(cursor);
		close();
		return option;
	}

	@Override
	public void save(Option entite) {
		openWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put(DatabaseHelper.OPTION_COLUMN_ID, entite.getId());
		contentValues.put(DatabaseHelper.OPTION_COLUMN_VALUE, entite.getValue());
		db.insert(DatabaseHelper.OPTIONS_TABLE_NAME, null, contentValues);
		close();
	}

	@Override
	public void update(Option entite) {
		openWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put(DatabaseHelper.OPTION_COLUMN_VALUE, entite.getValue());
		db.update(	DatabaseHelper.OPTIONS_TABLE_NAME, 
					contentValues, 
					DatabaseHelper.OPTION_COLUMN_ID + DatabaseHelper.EQUALS_VARIABLE,
					new String[] { String.valueOf(entite.getId()) });
		close();
	}

	@Override
	public void delete(int id) {
		openWritableDatabase();
		db.delete(	DatabaseHelper.OPTIONS_TABLE_NAME, 
					DatabaseHelper.OPTION_COLUMN_ID + DatabaseHelper.EQUALS_VARIABLE,
					new String[] { String.valueOf(id) });
		close();
	}

	@Override
	public Option getBeanFromCurrentCursor(Cursor c){
		
		return new Option(
				c.getInt(DatabaseHelper.OPTION_NUM_COLUMN_ID),
				c.getString(DatabaseHelper.OPTION_NUM_COLUMN_VALUE));
	}

}
