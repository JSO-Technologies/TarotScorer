package com.jso.technologies.tarot.scorer.db;

import com.jso.technologies.tarot.scorer.Utils.Constantes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Classe de gestion de la base de donnees
 * @author Jimmy
 *
 */
public class DatabaseHelper extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 3;
	private static final String BASE_NAME = "tarot_scorer.db";

	public static final String EQUALS_VARIABLE = "=?";
	public static final String COUNT_REQUEST = "SELECT COUNT(*) FROM {0} WHERE {1}";

	// Table 'players' 
	public static final String PLAYERS_TABLE_NAME = "players";
	public static final String PLAYER_COLUMN_ID = "ID";
	public static final int PLAYER_NUM_COLUMN_ID = 0;
	public static final String PLAYER_COLUMN_PSEUDO = "PSEUDO";
	public static final int PLAYER_NUM_COLUMN_PSEUDO = 1;
	public static final String PLAYER_COLUMN_IMAGE = "IMAGE";
	public static final int PLAYER_NUM_COLUMN_IMAGE = 2;
	public static final String PLAYER_COLUMN_ENABLED = "ENABLED";
	public static final int PLAYER_NUM_COLUMN_ENABLED = 3;

	// Table 'Prise' 
	public static final String PRISES_TABLE_NAME = "prises";
	public static final String PRISE_COLUMN_ID = "ID";
	public static final int PRISE_NUM_COLUMN_ID = 0;
	public static final String PRISE_COLUMN_GAME_ID = "GAME_ID";
	public static final int PRISE_NUM_COLUMN_GAME_ID = 1;
	public static final String PRISE_COLUMN_PRENEUR_ID = "PRENEUR_ID";
	public static final int PRISE_NUM_COLUMN_PRENEUR_ID = 2;
	public static final String PRISE_COLUMN_APPEL_ID = "APPEL_ID";
	public static final int PRISE_NUM_COLUMN_APPEL_ID = 3;
	public static final String PRISE_COLUMN_PRISE = "PRISE";
	public static final int PRISE_NUM_COLUMN_PRISE = 4;
	public static final String PRISE_COLUMN_POINTS = "POINTS";
	public static final int PRISE_NUM_COLUMN_POINTS = 5;
	public static final String PRISE_COLUMN_NB_OUDLERS = "NB_OUDLERS";
	public static final int PRISE_NUM_COLUMN_NB_OUDLERS = 6;
	public static final String PRISE_COLUMN_PETITE_AU_BOUT = "PETITE_AU_BOUT";
	public static final int PRISE_NUM_COLUMN_PETITE_AU_BOUT = 7;
	public static final String PRISE_COLUMN_POIGNEE = "POIGNEE";
	public static final int PRISE_NUM_COLUMN_POIGNEE = 8;
	public static final String PRISE_COLUMN_CHELEM = "CHELEM";
	public static final int PRISE_NUM_COLUMN_CHELEM = 9;
	
	// Table 'Prise' 
	public static final String GAMES_TABLE_NAME = "games";
	public static final String GAME_COLUMN_ID = "ID";
	public static final int GAME_NUM_COLUMN_ID = 0;
	public static final String GAME_COLUMN_DATE = "DATE";
	public static final int GAME_NUM_COLUMN_DATE = 1;
	public static final String GAME_COLUMN_PLAYER_1_ID = "PLAYER_1_ID";
	public static final int GAME_NUM_COLUMN_PLAYER_1_ID = 2;
	public static final String GAME_COLUMN_PLAYER_2_ID = "PLAYER_2_ID";
	public static final int GAME_NUM_COLUMN_PLAYER_2_ID = 3;
	public static final String GAME_COLUMN_PLAYER_3_ID = "PLAYER_3_ID";
	public static final int GAME_NUM_COLUMN_PLAYER_3_ID = 4;
	public static final String GAME_COLUMN_PLAYER_4_ID = "PLAYER_4_ID";
	public static final int GAME_NUM_COLUMN_PLAYER_4_ID = 5;
	public static final String GAME_COLUMN_PLAYER_5_ID = "PLAYER_5_ID";
	public static final int GAME_NUM_COLUMN_PLAYER_5_ID = 6;
	public static final String GAME_COLUMN_PLAYER_1_POINTS = "PLAYER_1_POINTS";
	public static final int GAME_NUM_COLUMN_PLAYER_1_POINTS = 7;
	public static final String GAME_COLUMN_PLAYER_2_POINTS = "PLAYER_2_POINTS";
	public static final int GAME_NUM_COLUMN_PLAYER_2_POINTS = 8;
	public static final String GAME_COLUMN_PLAYER_3_POINTS = "PLAYER_3_POINTS";
	public static final int GAME_NUM_COLUMN_PLAYER_3_POINTS = 9;
	public static final String GAME_COLUMN_PLAYER_4_POINTS = "PLAYER_4_POINTS";
	public static final int GAME_NUM_COLUMN_PLAYER_4_POINTS = 10;
	public static final String GAME_COLUMN_PLAYER_5_POINTS = "PLAYER_5_POINTS";
	public static final int GAME_NUM_COLUMN_PLAYER_5_POINTS = 11;
	public static final String GAME_COLUMN_PLAYER_1_CLASSIFICATION = "PLAYER_1_CLASSIFICATION";
	public static final int GAME_NUM_COLUMN_PLAYER_1_CLASSIFICATION = 12;
	public static final String GAME_COLUMN_PLAYER_2_CLASSIFICATION = "PLAYER_2_CLASSIFICATION";
	public static final int GAME_NUM_COLUMN_PLAYER_2_CLASSIFICATION = 13;
	public static final String GAME_COLUMN_PLAYER_3_CLASSIFICATION = "PLAYER_3_CLASSIFICATION";
	public static final int GAME_NUM_COLUMN_PLAYER_3_CLASSIFICATION = 14;
	public static final String GAME_COLUMN_PLAYER_4_CLASSIFICATION = "PLAYER_4_CLASSIFICATION";
	public static final int GAME_NUM_COLUMN_PLAYER_4_CLASSIFICATION = 15;
	public static final String GAME_COLUMN_PLAYER_5_CLASSIFICATION = "PLAYER_5_CLASSIFICATION";
	public static final int GAME_NUM_COLUMN_PLAYER_5_CLASSIFICATION = 16;
	
	//Table 'Options'
	public static final String OPTIONS_TABLE_NAME = "options";
	public static final String OPTION_COLUMN_ID = "ID";
	public static final int OPTION_NUM_COLUMN_ID = 0;
	public static final String OPTION_COLUMN_VALUE = "VALUE";
	public static final int OPTION_NUM_COLUMN_VALUE = 1;

	private static final String CREATION_PLAYERS_TABLE_REQUEST = 
			"CREATE TABLE " + PLAYERS_TABLE_NAME + " (" + 
					PLAYER_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + 
					PLAYER_COLUMN_PSEUDO + " VARCHAR(30) NOT NULL, " + 
					PLAYER_COLUMN_IMAGE + " BLOB, " +
					PLAYER_COLUMN_ENABLED + " TINYINT DEFAULT '1');";

	private static final String CREATION_PRISES_TABLE_REQUEST = 
			"CREATE TABLE " + PRISES_TABLE_NAME + " (" + 
					PRISE_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + 
					PRISE_COLUMN_GAME_ID + " INTEGER NOT NULL, " +
					PRISE_COLUMN_PRENEUR_ID + " INTEGER NOT NULL, " +
					PRISE_COLUMN_APPEL_ID + " INTEGER NULLABLE, " +
					PRISE_COLUMN_PRISE + " INTEGER NOT NULL, " +
					PRISE_COLUMN_POINTS + " INTEGER NOT NULL, " +
					PRISE_COLUMN_NB_OUDLERS + " INTEGER NOT NULL, " +
					PRISE_COLUMN_PETITE_AU_BOUT + " INTEGER NOT NULL, " +
					PRISE_COLUMN_POIGNEE + " INTEGER NOT NULL, " +
					PRISE_COLUMN_CHELEM + " INTEGER NOT NULL);";
	
	private static final String CREATION_GAMES_TABLE_REQUEST = 
			"CREATE TABLE " + GAMES_TABLE_NAME + " (" + 
					GAME_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + 
					GAME_COLUMN_DATE + " INTEGER NOT NULL, " +
					GAME_COLUMN_PLAYER_1_ID + " INTEGER NOT NULL, " +
					GAME_COLUMN_PLAYER_2_ID + " INTEGER NOT NULL, " +
					GAME_COLUMN_PLAYER_3_ID + " INTEGER NOT NULL, " +
					GAME_COLUMN_PLAYER_4_ID + " INTEGER NULLABLE, " +
					GAME_COLUMN_PLAYER_5_ID + " INTEGER NULLABLE, " +
					GAME_COLUMN_PLAYER_1_POINTS + " INTEGER NOT NULL, " +
					GAME_COLUMN_PLAYER_2_POINTS + " INTEGER NOT NULL, " +
					GAME_COLUMN_PLAYER_3_POINTS + " INTEGER NOT NULL, " +
					GAME_COLUMN_PLAYER_4_POINTS + " INTEGER NULLABLE, " +
					GAME_COLUMN_PLAYER_5_POINTS + " INTEGER NULLABLE, " +
					GAME_COLUMN_PLAYER_1_CLASSIFICATION + " INTEGER NOT NULL, " +
					GAME_COLUMN_PLAYER_2_CLASSIFICATION + " INTEGER NOT NULL, " +
					GAME_COLUMN_PLAYER_3_CLASSIFICATION + " INTEGER NOT NULL, " +
					GAME_COLUMN_PLAYER_4_CLASSIFICATION + " INTEGER NULLABLE, " +
					GAME_COLUMN_PLAYER_5_CLASSIFICATION + " INTEGER NULLABLE);";
	
	private static final String CREATE_OPTIONS_TABLE_REQUEST = 
			"CREATE TABLE " + OPTIONS_TABLE_NAME + " (" +
					OPTION_COLUMN_ID + " INTEGER PRIMARY KEY NOT NULL, " + 
					OPTION_COLUMN_VALUE + " TEXT NULLABLE);";
	
	private static final String INSERT_PLAYERS_IN_CLASSIFICATION_OPTION = 
			"INSERT INTO " + OPTIONS_TABLE_NAME + " VALUES(" +
					Constantes.PLAYERS_IN_CLASSIFICATION_ID + ", null);";
	
	private static final String ALTER_PLAYERS_ADD_ENABLED = 
			"ALTER TABLE " + PLAYERS_TABLE_NAME + " ADD " + PLAYER_COLUMN_ENABLED + " TINYINT DEFAULT '1'";


	public DatabaseHelper(Context context, CursorFactory factory) {
		super(context, BASE_NAME, factory, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		//version 1
		db.execSQL(CREATION_PLAYERS_TABLE_REQUEST);
		db.execSQL(CREATION_PRISES_TABLE_REQUEST);
		db.execSQL(CREATION_GAMES_TABLE_REQUEST);
		
		//version 2
		db.execSQL(CREATE_OPTIONS_TABLE_REQUEST);
		db.execSQL(INSERT_PLAYERS_IN_CLASSIFICATION_OPTION);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		switch (oldVersion) {
		case 1:
			switch (newVersion) {
			case 2:
				db.execSQL(CREATE_OPTIONS_TABLE_REQUEST);
				db.execSQL(INSERT_PLAYERS_IN_CLASSIFICATION_OPTION);
				break;
			case 3:
				db.execSQL(CREATE_OPTIONS_TABLE_REQUEST);
				db.execSQL(INSERT_PLAYERS_IN_CLASSIFICATION_OPTION);
				db.execSQL(ALTER_PLAYERS_ADD_ENABLED);
				break;

			default:
				break;
			}
			break;
		case 2:
			switch (newVersion) {
			case 3:
				db.execSQL(ALTER_PLAYERS_ADD_ENABLED);
				break;
			default:
				break;
			}
			break;

		default:
			break;
		}
	}

}
