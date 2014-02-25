package com.jso.technologies.tarot.scorer.stats.classement;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup.LayoutParams;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.jso.technologies.tarot.scorer.R;
import com.jso.technologies.tarot.scorer.Utils.Constantes;
import com.jso.technologies.tarot.scorer.Utils.StatistiqueCalculator;
import com.jso.technologies.tarot.scorer.common.bean.Option;
import com.jso.technologies.tarot.scorer.common.bean.Player;
import com.jso.technologies.tarot.scorer.db.OptionDatabaseRepository;
import com.jso.technologies.tarot.scorer.db.cache.PlayerRepositoryCache;
import com.jso.technologies.tarot.scorer.db.cache.StatistiqueCalculatorCache;

public class ClassementActivity extends Activity {
	private NumberFormat nf;
	private List<Player> players;
	private List<Player> sortedPlayers;
	private Map<Player, StatistiqueCalculator> calculators;
	private Option playersInClassification;
	private OptionDatabaseRepository optionsRepo;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stat_classement);
		
		nf = new DecimalFormat();
		nf.setMaximumFractionDigits(2);
		nf.setMinimumFractionDigits(0);
		
		//recuperation des joueurs et des calculateurs
		players = PlayerRepositoryCache.getAll(this);
		calculators = new HashMap<Player, StatistiqueCalculator>();
		for(Player player : players) {
			calculators.put(player, StatistiqueCalculatorCache.getCalculator(this, player));
		}
		
		//recuperation des joueurs a afficher
		optionsRepo = new OptionDatabaseRepository(this);
		playersInClassification = optionsRepo.getById(Constantes.PLAYERS_IN_CLASSIFICATION_ID);
		
		//filtres des joueurs
		findViewById(R.id.priseScoreShorcut).setOnClickListener(new ClassementPlayerFilterListener(this));
		
		//classement
		sortedPlayers = new ArrayList<Player>();
		sortedPlayers.addAll(players);
		Collections.sort(sortedPlayers, new PlayerClassementSorter(calculators));
		
		updateClassificationTable();
	}

	/**
	 * Mise à jour des lignes dans le tableau
	 */
	public void updateClassificationTable() {
		//id des joueurs à afficher
		List<Integer> idList = playersInClassification.getPlayerIdList();
		
		//reset du tableau
		TableLayout tableLayout = (TableLayout)findViewById(R.id.classement_table_layout);
		tableLayout.removeAllViews();
		
		//ajout des lignes
		int i = 1;
		for(Player player : sortedPlayers) {
			if(idList == null || idList.contains(player.getId())) {
				tableLayout.addView(createTableRow(player, i, calculators.get(player)));
				i++;
			}
		}
	}
	
	/**
	 * Creation d'une ligne
	 * @param score
	 * @return
	 */
	private TableRow createTableRow(Player player, Integer place, StatistiqueCalculator calculator) {
		TableRow newRow = new TableRow(this);
		newRow.setWeightSum(32);
		if(place % 2 == 0) {
			newRow.setBackgroundColor(0x22ffffff);
		}

		TextView playerTextView = new TextView(this);
		playerTextView.setText(player.getPseudo());
		playerTextView.setTextAppearance(getApplicationContext(), R.style.classement_normal_text);
		LayoutParams playerParam = new TableRow.LayoutParams(0, LayoutParams.WRAP_CONTENT, 12f);
		playerTextView.setLayoutParams(playerParam);
		playerTextView.setGravity(Gravity.LEFT);
		newRow.addView(playerTextView);
		
		TextView placeTextView = new TextView(this);
		placeTextView.setText(place.toString());
		placeTextView.setTextAppearance(getApplicationContext(), R.style.classement_normal_text);
		LayoutParams placeParam = new TableRow.LayoutParams(0, LayoutParams.WRAP_CONTENT, 3f);
		placeTextView.setLayoutParams(placeParam);
		placeTextView.setGravity(Gravity.LEFT);
		newRow.addView(placeTextView);
		
		TextView averagePlaceTextView = new TextView(this);
		averagePlaceTextView.setText(nf.format(calculator.getAveragePlace()));
		averagePlaceTextView.setTextAppearance(getApplicationContext(), R.style.classement_normal_text);
		LayoutParams averagePlaceParam = new TableRow.LayoutParams(0, LayoutParams.WRAP_CONTENT, 5f);
		averagePlaceTextView.setLayoutParams(averagePlaceParam);
		averagePlaceTextView.setGravity(Gravity.LEFT);
		newRow.addView(averagePlaceTextView);
		
		TextView wonGamesTextView = new TextView(this);
		wonGamesTextView.setText(String.valueOf(calculator.getNbWinningGame()));
		wonGamesTextView.setTextAppearance(getApplicationContext(), R.style.classement_normal_text);
		LayoutParams wonGamesParam = new TableRow.LayoutParams(0, LayoutParams.WRAP_CONTENT, 3f);
		wonGamesTextView.setLayoutParams(wonGamesParam);
		wonGamesTextView.setGravity(Gravity.CENTER_HORIZONTAL);
		newRow.addView(wonGamesTextView);
		
		TextView lostGamesTextView = new TextView(this);
		lostGamesTextView.setText(String.valueOf(calculator.getNbGamePlayed() - calculator.getNbWinningGame()));
		lostGamesTextView.setTextAppearance(getApplicationContext(), R.style.classement_normal_text);
		LayoutParams lostGamesParam = new TableRow.LayoutParams(0, LayoutParams.WRAP_CONTENT, 3f);
		lostGamesTextView.setLayoutParams(lostGamesParam);
		lostGamesTextView.setGravity(Gravity.CENTER_HORIZONTAL);
		newRow.addView(lostGamesTextView);
		
		TextView wonPrisesTextView = new TextView(this);
		wonPrisesTextView.setText(String.valueOf(calculator.getNbWonPrise()));
		wonPrisesTextView.setTextAppearance(getApplicationContext(), R.style.classement_normal_text);
		LayoutParams wonPrisesParam = new TableRow.LayoutParams(0, LayoutParams.WRAP_CONTENT, 3f);
		wonPrisesTextView.setLayoutParams(wonPrisesParam);
		wonPrisesTextView.setGravity(Gravity.CENTER_HORIZONTAL);
		newRow.addView(wonPrisesTextView);
		
		TextView lostPrisesTextView = new TextView(this);
		lostPrisesTextView.setText(String.valueOf(calculator.getNbLostPrise()));
		lostPrisesTextView.setTextAppearance(getApplicationContext(), R.style.classement_normal_text);
		LayoutParams lostPrisesParam = new TableRow.LayoutParams(0, LayoutParams.WRAP_CONTENT, 3f);
		lostPrisesTextView.setLayoutParams(lostPrisesParam);
		lostPrisesTextView.setGravity(Gravity.CENTER_HORIZONTAL);
		newRow.addView(lostPrisesTextView);
		
		return newRow;
	}

	public Option getPlayersInClassification() {
		return playersInClassification;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public OptionDatabaseRepository getOptionsRepo() {
		return optionsRepo;
	}
	
}
