package com.jso.technologies.tarot.scorer.Utils;

import android.graphics.Color;




public class Constantes {

	public static final String CREATED_PLAYER = "CREATED_PLAYER";
	public static final String MODIFIED_PLAYER = "MODIFIED_PLAYER"; 
	public static final String SELECTED_PLAYERS = "SELECTED_PLAYERS";
	public static final String SELECTED_GAME = "SELECTED_GAME";
	public static final String GAME = "GAME";
	public static final String CAN_DELETE = "CAN_DELETE";
	public static final String DELETED_PRISE = "DELETED_PRISE";
	public static final String SELECTED_PLAYER_ID = "SELECTED_PLAYER_ID";
	public static final String REFRESH = "REFRESH";

	public static final int TOTAL_POINTS = 91;

	//request code
	public static final int DETAILLED_SCORE = 0;

	//prise enum
	public static final int PRISE_ID = 0;
	public static final int GARDE_ID = 1;
	public static final int GARDE_SANS_ID = 2;
	public static final int GARDE_CONTRE_ID = 3;

	//petite au bout enum
	public static final int PETITE_BOUT_NO_ID = 0;
	public static final int PETITE_BOUT_YES_ID = 1;
	public static final int PETITE_BOUT_LOST_ID = 2;

	//poignee enum
	public static final int POIGNEE_NO_ID = 0;
	public static final int POIGNEE_SIMPLE_ID = 1;
	public static final int POIGNEE_DOUBLE_ID = 2;
	public static final int POIGNEE_TRIPLE_ID = 3;

	//chelem enum
	public static final int CHELEM_NO_ID = 0;
	public static final int CHELEM_NOT_ANNOUNCED_ID = 1;
	public static final int CHELEM_ANNOUNCED_ID = 2;
	public static final int CHELEM_LOST_ID = 3;

	//prise elements
	public static final int PRENEUR_ELEMENT = 0;
	public static final int CALL_ELEMENT = 1;
	public static final int PRISE_ELEMENT = 2;
	public static final int POINTS_ELEMENT = 3;
	public static final int OUDLERS_ELEMENT = 4;
	public static final int PETITE_AU_BOUT_ELEMENT = 5;
	public static final int POIGNEE_ELEMENT = 6;
	public static final int CHELEM_ELEMENT = 7;
	public static final int MISERE_ELEMENT = 8;

	//points suivant contrat
	public static final int NO_OUDLER_POINTS = 56;
	public static final int ONE_OUDLER_POINTS = 51;
	public static final int TWO_OUDLERS_POINTS = 41;
	public static final int THREE_OUDLERS_POINTS = 36;

	//coefficient multiplicateur 
	public static final int MULTIPLYING_FACTOR_PETITE = 1;
	public static final int MULTIPLYING_FACTOR_GARDE = 2;
	public static final int MULTIPLYING_FACTOR_GARDE_SANS = 4;
	public static final int MULTIPLYING_FACTOR_GARDE_CONTRE = 6;

	//bonus poignees
	public static final int NO_POIGNEE_BONUS = 0;
	public static final int SIMPLE_POIGNEE_BONUS = 20;
	public static final int DOUBLE_POIGNEE_BONUS = 30;
	public static final int TRIPLE_POIGNEE_BONUS = 40;

	//bonus petite au bout
	public static final int NO_PETITE_AU_BOUT_BONUS = 0;
	public static final int PETITE_AU_BOUT_BONUS = 10;
	public static final int LOST_PETITE_AU_BOUT_BONUS = -10;
	
	//bonus misere
	public static final int MISERE_BONUS = 10;

	//bonus chelem
	public static final int NO_CHELEM_BONUS = 0;
	public static final int NOT_ANNOUNCED_CHELEM_BONUS = 200;
	public static final int ANNOUNCED_CHELEM_BONUS = 400;
	public static final int LOST_ANNOUNCED_CHELEM_BONUS = -200;

	//player stat pie chart color
	public static final int PETITE_CHART_COLOR = 0xff00eaff;
	public static final int GARDE_CHART_COLOR = 0xff009aff;
	public static final int GARDE_SANS_CHART_COLOR = 0xff00cb00;
	public static final int GARDE_CONTRE_CHART_COLOR = 0xff009400;
	public static final int[] PRISE_TYPE_CHART_COLOR = new int[] {	
		Constantes.PETITE_CHART_COLOR, 
		Constantes.GARDE_CHART_COLOR, 
		Constantes.GARDE_SANS_CHART_COLOR, 
		Constantes.GARDE_CONTRE_CHART_COLOR};

	//Analyse pie chart color
	public static final int PLAYER_1_CHART_COLOR = Color.RED;
	public static final int PLAYER_2_CHART_COLOR = Color.GREEN;
	public static final int PLAYER_3_CHART_COLOR = Color.BLUE;
	public static final int PLAYER_4_CHART_COLOR = Color.YELLOW;
	public static final int PLAYER_5_CHART_COLOR = Color.MAGENTA;
	public static final int[] ANALYSE_CHART_COLOR = new int[] {	
		Constantes.PLAYER_1_CHART_COLOR, 
		Constantes.PLAYER_2_CHART_COLOR, 
		Constantes.PLAYER_3_CHART_COLOR, 
		Constantes.PLAYER_4_CHART_COLOR,
		Constantes.PLAYER_5_CHART_COLOR};

	//ids des options en base
	public static final int PLAYERS_IN_CLASSIFICATION_ID = 0;
}
