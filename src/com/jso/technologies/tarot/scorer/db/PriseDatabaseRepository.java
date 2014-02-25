package com.jso.technologies.tarot.scorer.db;

import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.jso.technologies.tarot.scorer.common.bean.Player;
import com.jso.technologies.tarot.scorer.common.bean.Prise;
import com.jso.technologies.tarot.scorer.common.enumeration.ChelemEnum;
import com.jso.technologies.tarot.scorer.common.enumeration.PetiteAuBoutEnum;
import com.jso.technologies.tarot.scorer.common.enumeration.PoigneeEnum;
import com.jso.technologies.tarot.scorer.common.enumeration.PriseEnum;

public class PriseDatabaseRepository extends AbstractDatabaseRepository<Prise> {

	public PriseDatabaseRepository(Context context) {
		databaseHelper = new DatabaseHelper(context, null);
	}

	@Override
	public List<Prise> getAll() {
		openReadableDatabase();
		Cursor cursor = db.query(DatabaseHelper.PRISES_TABLE_NAME,
								new String[] { 	
									DatabaseHelper.PRISE_COLUMN_ID,
									DatabaseHelper.PRISE_COLUMN_GAME_ID,
									DatabaseHelper.PRISE_COLUMN_PRENEUR_ID,
									DatabaseHelper.PRISE_COLUMN_APPEL_ID,
									DatabaseHelper.PRISE_COLUMN_PRISE,
									DatabaseHelper.PRISE_COLUMN_POINTS,
									DatabaseHelper.PRISE_COLUMN_NB_OUDLERS,
									DatabaseHelper.PRISE_COLUMN_PETITE_AU_BOUT,
									DatabaseHelper.PRISE_COLUMN_POIGNEE,
									DatabaseHelper.PRISE_COLUMN_CHELEM
								}, 
								null, null, null, null, 
								DatabaseHelper.PRISE_COLUMN_ID);

		List<Prise> priseList = convertCursorListToBeanList(cursor);
		close();
		return priseList;
	}

	@Override
	public Prise getById(int id) {
		openReadableDatabase();
		Cursor cursor = db.query(DatabaseHelper.PLAYERS_TABLE_NAME,
								new String[] { 	
									DatabaseHelper.PRISE_COLUMN_ID,
									DatabaseHelper.PRISE_COLUMN_GAME_ID,
									DatabaseHelper.PRISE_COLUMN_PRENEUR_ID,
									DatabaseHelper.PRISE_COLUMN_APPEL_ID,
									DatabaseHelper.PRISE_COLUMN_PRISE,
									DatabaseHelper.PRISE_COLUMN_POINTS,
									DatabaseHelper.PRISE_COLUMN_NB_OUDLERS,
									DatabaseHelper.PRISE_COLUMN_PETITE_AU_BOUT,
									DatabaseHelper.PRISE_COLUMN_POIGNEE,
									DatabaseHelper.PRISE_COLUMN_CHELEM
								},  
								DatabaseHelper.PRISE_COLUMN_ID + DatabaseHelper.EQUALS_VARIABLE, 
								new String[]{String.valueOf(id)}, 
								null, null, null);

		Prise prise = convertCursorToBean(cursor);
		close();
		return prise;
	}

	@Override
	public void save(Prise entite) {
		openWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put(DatabaseHelper.PRISE_COLUMN_GAME_ID, entite.getGameId());
		contentValues.put(DatabaseHelper.PRISE_COLUMN_PRENEUR_ID, entite.getPreneur().getId());
		contentValues.put(DatabaseHelper.PRISE_COLUMN_APPEL_ID, entite.getAppel() == null ? null : entite.getAppel().getId());
		contentValues.put(DatabaseHelper.PRISE_COLUMN_PRISE, entite.getPrise().getId());
		contentValues.put(DatabaseHelper.PRISE_COLUMN_POINTS, entite.getPoints());
		contentValues.put(DatabaseHelper.PRISE_COLUMN_NB_OUDLERS, entite.getNbOudlers());
		contentValues.put(DatabaseHelper.PRISE_COLUMN_PETITE_AU_BOUT, entite.getPetiteAuBout().getId());
		contentValues.put(DatabaseHelper.PRISE_COLUMN_POIGNEE, entite.getPoignee().getId());
		contentValues.put(DatabaseHelper.PRISE_COLUMN_CHELEM, entite.getChelem().getId());
		Long id = db.insert(DatabaseHelper.PRISES_TABLE_NAME, null, contentValues);
		close();

		entite.setId(id.intValue());
	}

	@Override
	public void update(Prise entite) {
		openWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put(DatabaseHelper.PRISE_COLUMN_GAME_ID, entite.getGameId());
		contentValues.put(DatabaseHelper.PRISE_COLUMN_PRENEUR_ID, entite.getPreneur().getId());
		contentValues.put(DatabaseHelper.PRISE_COLUMN_APPEL_ID, entite.getAppel() == null ? null : entite.getAppel().getId());
		contentValues.put(DatabaseHelper.PRISE_COLUMN_PRISE, entite.getPrise().getId());
		contentValues.put(DatabaseHelper.PRISE_COLUMN_POINTS, entite.getPoints());
		contentValues.put(DatabaseHelper.PRISE_COLUMN_NB_OUDLERS, entite.getNbOudlers());
		contentValues.put(DatabaseHelper.PRISE_COLUMN_PETITE_AU_BOUT, entite.getPetiteAuBout().getId());
		contentValues.put(DatabaseHelper.PRISE_COLUMN_POIGNEE, entite.getPoignee().getId());
		contentValues.put(DatabaseHelper.PRISE_COLUMN_CHELEM, entite.getChelem().getId());
		db.update(	DatabaseHelper.PRISES_TABLE_NAME, 
				contentValues, 
				DatabaseHelper.PRISE_COLUMN_ID + DatabaseHelper.EQUALS_VARIABLE,
				new String[] { String.valueOf(entite.getId()) });
		close();
	}

	@Override
	public void delete(int id) {
		openWritableDatabase();
		db.delete(	DatabaseHelper.PRISES_TABLE_NAME, 
				DatabaseHelper.PRISE_COLUMN_ID + DatabaseHelper.EQUALS_VARIABLE,
				new String[] { String.valueOf(id) });
		close();
	}

	@Override
	public Prise getBeanFromCurrentCursor(Cursor c){
		
		int id = c.getInt(DatabaseHelper.PRISE_NUM_COLUMN_ID);
		int gameId = c.getInt(DatabaseHelper.PRISE_NUM_COLUMN_GAME_ID);
		int preneurId = c.getInt(DatabaseHelper.PRISE_NUM_COLUMN_PRENEUR_ID);
		Integer appelId = c.getInt(DatabaseHelper.PRISE_NUM_COLUMN_APPEL_ID);
		int priseId = c.getInt(DatabaseHelper.PRISE_NUM_COLUMN_PRISE);
		int points = c.getInt(DatabaseHelper.PRISE_NUM_COLUMN_POINTS);
		int nbOudlers = c.getInt(DatabaseHelper.PRISE_NUM_COLUMN_NB_OUDLERS);
		Integer petiteAuBoutId = c.getInt(DatabaseHelper.PRISE_NUM_COLUMN_PETITE_AU_BOUT);
		int poigneeId = c.getInt(DatabaseHelper.PRISE_NUM_COLUMN_POIGNEE);
		int chelemId = c.getInt(DatabaseHelper.PRISE_NUM_COLUMN_CHELEM);
		
		return Prise.builder()
				.withId(id)
				.withGameId(gameId)
				.withPreneur(new Player(preneurId, null, null, true))
				.withAppel(appelId == null || appelId == 0 ? null : new Player(appelId, null, null, true))
				.withPrise(PriseEnum.fromValue(priseId))
				.withPoints(points)
				.withNbOudlers(nbOudlers)
				.withPetiteAuBout(PetiteAuBoutEnum.fromValue(petiteAuBoutId))
				.withPoignee(PoigneeEnum.fromValue(poigneeId))
				.withChelem(ChelemEnum.fromValue(chelemId))
				.build();
	}

	public List<Prise> getAllByGame(Integer gameID) {
		openReadableDatabase();
		Cursor cursor = db.query(DatabaseHelper.PRISES_TABLE_NAME,
								new String[] { 	
									DatabaseHelper.PRISE_COLUMN_ID,
									DatabaseHelper.PRISE_COLUMN_GAME_ID,
									DatabaseHelper.PRISE_COLUMN_PRENEUR_ID,
									DatabaseHelper.PRISE_COLUMN_APPEL_ID,
									DatabaseHelper.PRISE_COLUMN_PRISE,
									DatabaseHelper.PRISE_COLUMN_POINTS,
									DatabaseHelper.PRISE_COLUMN_NB_OUDLERS,
									DatabaseHelper.PRISE_COLUMN_PETITE_AU_BOUT,
									DatabaseHelper.PRISE_COLUMN_POIGNEE,
									DatabaseHelper.PRISE_COLUMN_CHELEM
								}, 
								DatabaseHelper.PRISE_COLUMN_GAME_ID + DatabaseHelper.EQUALS_VARIABLE, 
								new String[]{String.valueOf(gameID)}, 
								null, null, 
								DatabaseHelper.PRISE_COLUMN_ID);

		List<Prise> priseList = convertCursorListToBeanList(cursor);
		close();
		return priseList;
	}

	public void deleteAllByGame(Integer gameId) {
		openWritableDatabase();
		db.delete(	DatabaseHelper.PRISES_TABLE_NAME, 
				DatabaseHelper.PRISE_COLUMN_GAME_ID + DatabaseHelper.EQUALS_VARIABLE,
				new String[] { String.valueOf(gameId) });
		close();
	}

}
