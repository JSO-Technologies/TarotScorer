package com.jso.technologies.tarot.scorer.game;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.ViewGroup.LayoutParams;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.jso.technologies.tarot.scorer.R;
import com.jso.technologies.tarot.scorer.common.bean.Game;
import com.jso.technologies.tarot.scorer.db.cache.GameRepositoryCache;

/**
 * Activity : Liste des parties
 * @author Jimmy
 *
 */
public class GamesListActivity extends Activity {

	private TableLayout tableLayout;
	private TextView textViewMessage;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_items_list);

		//recuperation des views
		tableLayout = (TableLayout) findViewById(R.id.listTableLayout);
		textViewMessage = (TextView) findViewById(R.id.message);

		refreshList();
	}

	/**
	 * Rafraichissement de la liste des joueurs
	 */
	private void refreshList() {
		
		List<Game> allGamesInDB = GameRepositoryCache.getAll(this);
		
		//vidage de la liste
		tableLayout.removeAllViews();

		//affichage de la liste des jeux
		if(allGamesInDB.isEmpty()) {
			textViewMessage.setText(getResources().getString(R.string.no_game));
		}
		else {
			textViewMessage.setText(getResources().getString(R.string.launch_game));
			for(Game game : allGamesInDB) {
				tableLayout.addView(createRow(game));
			}
		}
	}

	/**
	 * Creation d'une ligne
	 * @param game
	 * @return
	 */
	private TableRow createRow(Game game) {
		TableRow newRow = new TableRow(this);
		newRow.setBackgroundResource(R.drawable.items_list_row);
		TableRow.LayoutParams rowParams = new TableRow.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		newRow.setLayoutParams(rowParams);
		newRow.setOnClickListener(new StartExistingGameListener(this, game.getId()));

		DateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
		
		TextView dateTextView = new TextView(this);
		dateTextView.setText(df.format(game.getDate()));
		dateTextView.setTextAppearance(getApplicationContext(), R.style.item_list_small_text);
		TableRow.LayoutParams dateParam = new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
		dateParam.setMargins(20, 0, 0, 0);
		dateTextView.setLayoutParams(dateParam);
		dateTextView.setGravity(Gravity.CENTER_VERTICAL);
		
		TextView scoreTextView = new TextView(this);
		scoreTextView.setText(Html.fromHtml(game.getHtmlScores()));
		scoreTextView.setTextAppearance(getApplicationContext(), R.style.item_list_small_text);
		TableRow.LayoutParams param = new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
		param.setMargins(20, 0, 0, 0);
		scoreTextView.setLayoutParams(param);
		scoreTextView.setGravity(Gravity.CENTER_VERTICAL);

		newRow.addView(dateTextView);
		newRow.addView(scoreTextView);
		return newRow;
	}

}
