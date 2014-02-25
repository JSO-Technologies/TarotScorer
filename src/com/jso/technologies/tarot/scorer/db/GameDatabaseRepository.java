package com.jso.technologies.tarot.scorer.db;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.jso.technologies.tarot.scorer.common.bean.Game;
import com.jso.technologies.tarot.scorer.common.bean.Player;

public class GameDatabaseRepository extends AbstractDatabaseRepository<Game> {

	public GameDatabaseRepository(Context context) {
		this.context = context;
		databaseHelper = new DatabaseHelper(context, null);
	}

	@Override
	public List<Game> getAll() {
		openReadableDatabase();
		Cursor cursor = db.query(DatabaseHelper.GAMES_TABLE_NAME,
								new String[] { 	
									DatabaseHelper.GAME_COLUMN_ID,
									DatabaseHelper.GAME_COLUMN_DATE,
									DatabaseHelper.GAME_COLUMN_PLAYER_1_ID,
									DatabaseHelper.GAME_COLUMN_PLAYER_2_ID,
									DatabaseHelper.GAME_COLUMN_PLAYER_3_ID,
									DatabaseHelper.GAME_COLUMN_PLAYER_4_ID,
									DatabaseHelper.GAME_COLUMN_PLAYER_5_ID,
									DatabaseHelper.GAME_COLUMN_PLAYER_1_POINTS,
									DatabaseHelper.GAME_COLUMN_PLAYER_2_POINTS,
									DatabaseHelper.GAME_COLUMN_PLAYER_3_POINTS,
									DatabaseHelper.GAME_COLUMN_PLAYER_4_POINTS,
									DatabaseHelper.GAME_COLUMN_PLAYER_5_POINTS,
									DatabaseHelper.GAME_COLUMN_PLAYER_1_CLASSIFICATION,
									DatabaseHelper.GAME_COLUMN_PLAYER_2_CLASSIFICATION,
									DatabaseHelper.GAME_COLUMN_PLAYER_3_CLASSIFICATION,
									DatabaseHelper.GAME_COLUMN_PLAYER_4_CLASSIFICATION,
									DatabaseHelper.GAME_COLUMN_PLAYER_5_CLASSIFICATION
								}, 
								null, null, null, null, 
								DatabaseHelper.GAME_COLUMN_DATE + " DESC");

		List<Game> gameList = convertCursorListToBeanList(cursor);
		close();
		return gameList;
	}

	@Override
	public Game getById(int id) {
		openReadableDatabase();
		Cursor cursor = db.query(DatabaseHelper.GAMES_TABLE_NAME,
								new String[] { 	
									DatabaseHelper.GAME_COLUMN_ID,
									DatabaseHelper.GAME_COLUMN_DATE,
									DatabaseHelper.GAME_COLUMN_PLAYER_1_ID,
									DatabaseHelper.GAME_COLUMN_PLAYER_2_ID,
									DatabaseHelper.GAME_COLUMN_PLAYER_3_ID,
									DatabaseHelper.GAME_COLUMN_PLAYER_4_ID,
									DatabaseHelper.GAME_COLUMN_PLAYER_5_ID,
									DatabaseHelper.GAME_COLUMN_PLAYER_1_POINTS,
									DatabaseHelper.GAME_COLUMN_PLAYER_2_POINTS,
									DatabaseHelper.GAME_COLUMN_PLAYER_3_POINTS,
									DatabaseHelper.GAME_COLUMN_PLAYER_4_POINTS,
									DatabaseHelper.GAME_COLUMN_PLAYER_5_POINTS,
									DatabaseHelper.GAME_COLUMN_PLAYER_1_CLASSIFICATION,
									DatabaseHelper.GAME_COLUMN_PLAYER_2_CLASSIFICATION,
									DatabaseHelper.GAME_COLUMN_PLAYER_3_CLASSIFICATION,
									DatabaseHelper.GAME_COLUMN_PLAYER_4_CLASSIFICATION,
									DatabaseHelper.GAME_COLUMN_PLAYER_5_CLASSIFICATION
								},  
								DatabaseHelper.GAME_COLUMN_ID + DatabaseHelper.EQUALS_VARIABLE, 
								new String[]{String.valueOf(id)}, 
								null, null, null);

		Game game = convertCursorToBean(cursor);
		close();
		return game;
	}

	@Override
	public void save(Game entite) {
		List<Player> players = entite.getPlayers();
		Map<Player, Integer> score = entite.getScore();
		Map<Player, Integer> classification = entite.getClassification();
		
		openWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put(DatabaseHelper.GAME_COLUMN_DATE, new Date().getTime());
		contentValues.put(DatabaseHelper.GAME_COLUMN_PLAYER_1_ID, players.get(0).getId());
		contentValues.put(DatabaseHelper.GAME_COLUMN_PLAYER_2_ID, players.get(1).getId());
		contentValues.put(DatabaseHelper.GAME_COLUMN_PLAYER_3_ID, players.get(2).getId());
		contentValues.put(DatabaseHelper.GAME_COLUMN_PLAYER_1_POINTS, score.get(players.get(0)));
		contentValues.put(DatabaseHelper.GAME_COLUMN_PLAYER_2_POINTS, score.get(players.get(1)));
		contentValues.put(DatabaseHelper.GAME_COLUMN_PLAYER_3_POINTS, score.get(players.get(2)));
		contentValues.put(DatabaseHelper.GAME_COLUMN_PLAYER_1_CLASSIFICATION, classification.get(players.get(0)));
		contentValues.put(DatabaseHelper.GAME_COLUMN_PLAYER_2_CLASSIFICATION, classification.get(players.get(1)));
		contentValues.put(DatabaseHelper.GAME_COLUMN_PLAYER_3_CLASSIFICATION, classification.get(players.get(2)));
		if(players.size() > 3) {
			contentValues.put(DatabaseHelper.GAME_COLUMN_PLAYER_4_ID, players.get(3).getId());
			contentValues.put(DatabaseHelper.GAME_COLUMN_PLAYER_4_POINTS, score.get(players.get(3)));
			contentValues.put(DatabaseHelper.GAME_COLUMN_PLAYER_4_CLASSIFICATION, classification.get(players.get(3)));
		}
		if(players.size() > 4) {
			contentValues.put(DatabaseHelper.GAME_COLUMN_PLAYER_5_ID, players.get(4).getId());
			contentValues.put(DatabaseHelper.GAME_COLUMN_PLAYER_5_POINTS, score.get(players.get(4)));
			contentValues.put(DatabaseHelper.GAME_COLUMN_PLAYER_5_CLASSIFICATION, classification.get(players.get(4)));
		}
		Long id = db.insert(DatabaseHelper.GAMES_TABLE_NAME, null, contentValues);
		close();

		entite.setId(id.intValue());
	}

	@Override
	public void update(Game entite) {
		List<Player> players = entite.getPlayers();
		Map<Player, Integer> score = entite.getScore();
		Map<Player, Integer> classification = entite.getClassification();
		
		openWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put(DatabaseHelper.GAME_COLUMN_PLAYER_1_ID, players.get(0).getId());
		contentValues.put(DatabaseHelper.GAME_COLUMN_PLAYER_2_ID, players.get(1).getId());
		contentValues.put(DatabaseHelper.GAME_COLUMN_PLAYER_3_ID, players.get(2).getId());
		contentValues.put(DatabaseHelper.GAME_COLUMN_PLAYER_1_POINTS, score.get(players.get(0)));
		contentValues.put(DatabaseHelper.GAME_COLUMN_PLAYER_2_POINTS, score.get(players.get(1)));
		contentValues.put(DatabaseHelper.GAME_COLUMN_PLAYER_3_POINTS, score.get(players.get(2)));
		contentValues.put(DatabaseHelper.GAME_COLUMN_PLAYER_1_CLASSIFICATION, classification.get(players.get(0)));
		contentValues.put(DatabaseHelper.GAME_COLUMN_PLAYER_2_CLASSIFICATION, classification.get(players.get(1)));
		contentValues.put(DatabaseHelper.GAME_COLUMN_PLAYER_3_CLASSIFICATION, classification.get(players.get(2)));
		if(players.size() > 3) {
			contentValues.put(DatabaseHelper.GAME_COLUMN_PLAYER_4_ID, players.get(3).getId());
			contentValues.put(DatabaseHelper.GAME_COLUMN_PLAYER_4_POINTS, score.get(players.get(3)));
			contentValues.put(DatabaseHelper.GAME_COLUMN_PLAYER_4_CLASSIFICATION, classification.get(players.get(3)));
		}
		if(players.size() > 4) {
			contentValues.put(DatabaseHelper.GAME_COLUMN_PLAYER_5_ID, players.get(4).getId());
			contentValues.put(DatabaseHelper.GAME_COLUMN_PLAYER_5_POINTS, score.get(players.get(4)));
			contentValues.put(DatabaseHelper.GAME_COLUMN_PLAYER_5_CLASSIFICATION, classification.get(players.get(4)));
		}
		db.update(DatabaseHelper.GAMES_TABLE_NAME, 
				contentValues, 
				DatabaseHelper.GAME_COLUMN_ID + DatabaseHelper.EQUALS_VARIABLE,
				new String[] { String.valueOf(entite.getId()) });
		close();
	}

	@Override
	public void delete(int id) {
		openWritableDatabase();
		db.delete(	DatabaseHelper.GAMES_TABLE_NAME, 
				DatabaseHelper.PLAYER_COLUMN_ID + DatabaseHelper.EQUALS_VARIABLE,
				new String[] { String.valueOf(id) });
		close();
	}

	@Override
	public Game getBeanFromCurrentCursor(Cursor c){
		//joueurs
		List<Player> players = new ArrayList<Player>();
		players.add(new Player(c.getInt(DatabaseHelper.GAME_NUM_COLUMN_PLAYER_1_ID), null, null, true));
		players.add(new Player(c.getInt(DatabaseHelper.GAME_NUM_COLUMN_PLAYER_2_ID), null, null, true));
		players.add(new Player(c.getInt(DatabaseHelper.GAME_NUM_COLUMN_PLAYER_3_ID), null, null, true));
		Integer player_4_id = c.getInt(DatabaseHelper.GAME_NUM_COLUMN_PLAYER_4_ID);
		Integer player_5_id = c.getInt(DatabaseHelper.GAME_NUM_COLUMN_PLAYER_5_ID);
		if(player_4_id != null && player_4_id > 0) {
			players.add(new Player(player_4_id, null, null, true));
		}
		if(player_5_id != null && player_5_id > 0) {
			players.add(new Player(player_5_id, null, null, true));
		}
		
		//scores et classement
		Map<Player, Integer> scores = new HashMap<Player, Integer>();
		scores.put(players.get(0), c.getInt(DatabaseHelper.GAME_NUM_COLUMN_PLAYER_1_POINTS));
		scores.put(players.get(1), c.getInt(DatabaseHelper.GAME_NUM_COLUMN_PLAYER_2_POINTS));
		scores.put(players.get(2), c.getInt(DatabaseHelper.GAME_NUM_COLUMN_PLAYER_3_POINTS));
		Map<Player, Integer> classement = new HashMap<Player, Integer>();
		classement.put(players.get(0), c.getInt(DatabaseHelper.GAME_NUM_COLUMN_PLAYER_1_CLASSIFICATION));
		classement.put(players.get(1), c.getInt(DatabaseHelper.GAME_NUM_COLUMN_PLAYER_2_CLASSIFICATION));
		classement.put(players.get(2), c.getInt(DatabaseHelper.GAME_NUM_COLUMN_PLAYER_3_CLASSIFICATION));
		if(players.size() > 3) {
			scores.put(players.get(3), c.getInt(DatabaseHelper.GAME_NUM_COLUMN_PLAYER_4_POINTS));
			classement.put(players.get(3), c.getInt(DatabaseHelper.GAME_NUM_COLUMN_PLAYER_4_CLASSIFICATION));
		}
		if(players.size() > 4) {
			scores.put(players.get(4), c.getInt(DatabaseHelper.GAME_NUM_COLUMN_PLAYER_5_POINTS));
			classement.put(players.get(4), c.getInt(DatabaseHelper.GAME_NUM_COLUMN_PLAYER_5_CLASSIFICATION));
		}
		
		Game game = new Game();
		game.setId(c.getInt(DatabaseHelper.GAME_NUM_COLUMN_ID));
		game.setDate(new Date(c.getLong(DatabaseHelper.GAME_NUM_COLUMN_DATE)));
		game.getPlayers().addAll(players);
		game.getScore().putAll(scores);
		game.getClassification().putAll(classement);
		
		return game;
	}

//	/**
//	 * Requ�te count le nombre de partie d'un joueur
//	 * @param playerID
//	 * @return
//	 */
//	public Integer countNbGameWithPlayer(Integer playerID) {
//		String id = playerID.toString();
//		openReadableDatabase();
//		
//		StringBuffer selection = new StringBuffer();
//		selection.append(DatabaseHelper.GAME_COLUMN_PLAYER_1_ID);
//		selection.append(DatabaseHelper.EQUALS_VARIABLE);
//		selection.append(" OR ");
//		selection.append(DatabaseHelper.GAME_COLUMN_PLAYER_2_ID);
//		selection.append(DatabaseHelper.EQUALS_VARIABLE);
//		selection.append(" OR ");
//		selection.append(DatabaseHelper.GAME_COLUMN_PLAYER_3_ID);
//		selection.append(DatabaseHelper.EQUALS_VARIABLE);
//		selection.append(" OR ");
//		selection.append(DatabaseHelper.GAME_COLUMN_PLAYER_4_ID);
//		selection.append(DatabaseHelper.EQUALS_VARIABLE);
//		selection.append(" OR ");
//		selection.append(DatabaseHelper.GAME_COLUMN_PLAYER_5_ID);
//		selection.append(DatabaseHelper.EQUALS_VARIABLE);
//
//		String request = MessageFormat.format(DatabaseHelper.COUNT_REQUEST, DatabaseHelper.GAMES_TABLE_NAME, selection);
//
//		Cursor cursor = db.rawQuery(request, new String[]{id, id, id, id, id});
//		cursor.moveToFirst();
//		int nbRows = cursor.getInt(0);
//		cursor.close();
//		
//		close();
//		
//		return nbRows;
//	}
//
//	/**
//	 * Requete count du nombre de parties gagn�es par un joueur
//	 * @param playerID
//	 * @return
//	 */
//	public Integer countNbWinningGameForPlayer(Integer playerID) {
//		String id = playerID.toString();
//		openReadableDatabase();
//		
//		StringBuffer selection = new StringBuffer();
//		selection.append("(");
//		selection.append(DatabaseHelper.GAME_COLUMN_PLAYER_1_ID);
//		selection.append(DatabaseHelper.EQUALS_VARIABLE);
//		selection.append(" AND ");
//		selection.append(DatabaseHelper.GAME_COLUMN_PLAYER_1_CLASSIFICATION);
//		selection.append(" = 1 )");
//		selection.append(" OR ");
//		selection.append("(");
//		selection.append(DatabaseHelper.GAME_COLUMN_PLAYER_2_ID);
//		selection.append(DatabaseHelper.EQUALS_VARIABLE);
//		selection.append(" AND ");
//		selection.append(DatabaseHelper.GAME_COLUMN_PLAYER_2_CLASSIFICATION);
//		selection.append(" = 1 )");
//		selection.append(" OR ");
//		selection.append("(");
//		selection.append(DatabaseHelper.GAME_COLUMN_PLAYER_3_ID);
//		selection.append(DatabaseHelper.EQUALS_VARIABLE);
//		selection.append(" AND ");
//		selection.append(DatabaseHelper.GAME_COLUMN_PLAYER_3_CLASSIFICATION);
//		selection.append(" = 1 )");
//		selection.append(" OR ");
//		selection.append("(");
//		selection.append(DatabaseHelper.GAME_COLUMN_PLAYER_4_ID);
//		selection.append(DatabaseHelper.EQUALS_VARIABLE);
//		selection.append(" AND ");
//		selection.append(DatabaseHelper.GAME_COLUMN_PLAYER_4_CLASSIFICATION);
//		selection.append(" = 1 )");
//		selection.append(" OR ");
//		selection.append("(");
//		selection.append(DatabaseHelper.GAME_COLUMN_PLAYER_5_ID);
//		selection.append(DatabaseHelper.EQUALS_VARIABLE);
//		selection.append(" AND ");
//		selection.append(DatabaseHelper.GAME_COLUMN_PLAYER_5_CLASSIFICATION);
//		selection.append(" = 1 )");
//		String request = MessageFormat.format(DatabaseHelper.COUNT_REQUEST, DatabaseHelper.GAMES_TABLE_NAME, selection);
//
//		Cursor cursor = db.rawQuery(request, new String[]{id, id, id, id, id});
//		cursor.moveToFirst();
//		int nbRows = cursor.getInt(0);
//		cursor.close();
//		
//		close();
//		
//		return nbRows;
//	}

	/**
	 * Liste des places d'un joueur
	 * @param playerID
	 * @return
	 */
	public List<Game> getGamesWithPlayer(Integer playerID) {
		String id = String.valueOf(playerID);
		openReadableDatabase();
		
		StringBuffer selection = new StringBuffer();
		selection.append(DatabaseHelper.GAME_COLUMN_PLAYER_1_ID);
		selection.append(DatabaseHelper.EQUALS_VARIABLE);
		selection.append(" OR ");
		selection.append(DatabaseHelper.GAME_COLUMN_PLAYER_2_ID);
		selection.append(DatabaseHelper.EQUALS_VARIABLE);
		selection.append(" OR ");
		selection.append(DatabaseHelper.GAME_COLUMN_PLAYER_3_ID);
		selection.append(DatabaseHelper.EQUALS_VARIABLE);
		selection.append(" OR ");
		selection.append(DatabaseHelper.GAME_COLUMN_PLAYER_4_ID);
		selection.append(DatabaseHelper.EQUALS_VARIABLE);
		selection.append(" OR ");
		selection.append(DatabaseHelper.GAME_COLUMN_PLAYER_5_ID);
		selection.append(DatabaseHelper.EQUALS_VARIABLE);
		
		Cursor cursor = db.query(DatabaseHelper.GAMES_TABLE_NAME,
								new String[] { 	
									DatabaseHelper.GAME_COLUMN_ID,
									DatabaseHelper.GAME_COLUMN_DATE,
									DatabaseHelper.GAME_COLUMN_PLAYER_1_ID,
									DatabaseHelper.GAME_COLUMN_PLAYER_2_ID,
									DatabaseHelper.GAME_COLUMN_PLAYER_3_ID,
									DatabaseHelper.GAME_COLUMN_PLAYER_4_ID,
									DatabaseHelper.GAME_COLUMN_PLAYER_5_ID,
									DatabaseHelper.GAME_COLUMN_PLAYER_1_POINTS,
									DatabaseHelper.GAME_COLUMN_PLAYER_2_POINTS,
									DatabaseHelper.GAME_COLUMN_PLAYER_3_POINTS,
									DatabaseHelper.GAME_COLUMN_PLAYER_4_POINTS,
									DatabaseHelper.GAME_COLUMN_PLAYER_5_POINTS,
									DatabaseHelper.GAME_COLUMN_PLAYER_1_CLASSIFICATION,
									DatabaseHelper.GAME_COLUMN_PLAYER_2_CLASSIFICATION,
									DatabaseHelper.GAME_COLUMN_PLAYER_3_CLASSIFICATION,
									DatabaseHelper.GAME_COLUMN_PLAYER_4_CLASSIFICATION,
									DatabaseHelper.GAME_COLUMN_PLAYER_5_CLASSIFICATION
								}, 
								selection.toString(), 
								new String[]{id, id, id, id, id}, 
								null, null, 
								DatabaseHelper.GAME_COLUMN_ID);

		List<Game> gameList = convertCursorListToBeanList(cursor);
		close();
		return gameList;
	}
	
}
