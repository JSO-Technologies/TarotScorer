package com.jso.technologies.tarot.scorer.db;

import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.jso.technologies.tarot.scorer.common.bean.Player;

public class PlayerDatabaseRepository extends AbstractDatabaseRepository<Player> {
	
	public PlayerDatabaseRepository(Context context) {
		databaseHelper = new DatabaseHelper(context, null);
	}

	@Override
	public List<Player> getAll() {
		openReadableDatabase();
		Cursor cursor = db.query(DatabaseHelper.PLAYERS_TABLE_NAME,
				                new String[] { 	DatabaseHelper.PLAYER_COLUMN_ID,
						                        DatabaseHelper.PLAYER_COLUMN_PSEUDO,
						                        DatabaseHelper.PLAYER_COLUMN_IMAGE,
						                        DatabaseHelper.PLAYER_COLUMN_ENABLED},
						        DatabaseHelper.PLAYER_COLUMN_ENABLED + DatabaseHelper.EQUALS_VARIABLE,
						        new String[]{"1"},
				                null, null, 
				                DatabaseHelper.PLAYER_COLUMN_PSEUDO);
		
		List<Player> playerList = convertCursorListToBeanList(cursor);
		close();
		return playerList;
	}
	
	public List<Player> getAllInactive() {
		openReadableDatabase();
		Cursor cursor = db.query(DatabaseHelper.PLAYERS_TABLE_NAME,
				                new String[] { 	DatabaseHelper.PLAYER_COLUMN_ID,
						                        DatabaseHelper.PLAYER_COLUMN_PSEUDO,
						                        DatabaseHelper.PLAYER_COLUMN_IMAGE,
						                        DatabaseHelper.PLAYER_COLUMN_ENABLED},
						        DatabaseHelper.PLAYER_COLUMN_ENABLED + DatabaseHelper.EQUALS_VARIABLE,
						        new String[]{"0"},
				                null, null, 
				                DatabaseHelper.PLAYER_COLUMN_PSEUDO);
		
		List<Player> playerList = convertCursorListToBeanList(cursor);
		close();
		return playerList;
	}

	@Override
	public Player getById(int id) {
		openReadableDatabase();
		Cursor cursor = db.query(DatabaseHelper.PLAYERS_TABLE_NAME,
				                new String[] { 	DatabaseHelper.PLAYER_COLUMN_ID,
						                        DatabaseHelper.PLAYER_COLUMN_PSEUDO,
						                        DatabaseHelper.PLAYER_COLUMN_IMAGE, 
						                        DatabaseHelper.PLAYER_COLUMN_ENABLED}, 
				                DatabaseHelper.PLAYER_COLUMN_ID + DatabaseHelper.EQUALS_VARIABLE, 
				                new String[]{String.valueOf(id)}, 
				                null, null, null);

		Player player = convertCursorToBean(cursor);
		close();
		return player;
	}

	@Override
	public void save(Player entite) {
		openWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put(DatabaseHelper.PLAYER_COLUMN_PSEUDO, entite.getPseudo());
		contentValues.put(DatabaseHelper.PLAYER_COLUMN_IMAGE, entite.getImage());
		contentValues.put(DatabaseHelper.PLAYER_COLUMN_ENABLED, entite.isEnabled());
		Long id = db.insert(DatabaseHelper.PLAYERS_TABLE_NAME, null, contentValues);
		close();
		
		entite.setId(id.intValue());
	}

	@Override
	public void update(Player entite) {
		openWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put(DatabaseHelper.PLAYER_COLUMN_PSEUDO, entite.getPseudo());
		contentValues.put(DatabaseHelper.PLAYER_COLUMN_IMAGE, entite.getImage());
		contentValues.put(DatabaseHelper.PLAYER_COLUMN_ENABLED, entite.isEnabled());
		db.update(	DatabaseHelper.PLAYERS_TABLE_NAME, 
					contentValues, 
					DatabaseHelper.PLAYER_COLUMN_ID + DatabaseHelper.EQUALS_VARIABLE,
					new String[] { String.valueOf(entite.getId()) });
		close();
	}

	@Override
	public void delete(int id) {
		openWritableDatabase();
		db.delete(	DatabaseHelper.PLAYERS_TABLE_NAME, 
					DatabaseHelper.PLAYER_COLUMN_ID + DatabaseHelper.EQUALS_VARIABLE,
					new String[] { String.valueOf(id) });
		close();
	}

	@Override
	public Player getBeanFromCurrentCursor(Cursor c){
		
		return new Player(
				c.getInt(DatabaseHelper.PLAYER_NUM_COLUMN_ID),
				c.getString(DatabaseHelper.PLAYER_NUM_COLUMN_PSEUDO),
				c.getBlob(DatabaseHelper.PLAYER_NUM_COLUMN_IMAGE),
				c.getInt(DatabaseHelper.PLAYER_NUM_COLUMN_ENABLED) == 1 ? true : false);
	}

}
