package com.jso.technologies.tarot.scorer.stats.players;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.jso.technologies.tarot.scorer.R;
import com.jso.technologies.tarot.scorer.Utils.Constantes;
import com.jso.technologies.tarot.scorer.Utils.DialogHandler;
import com.jso.technologies.tarot.scorer.common.bean.Player;
import com.jso.technologies.tarot.scorer.db.cache.PlayerRepositoryCache;

/**
 * Activity : Liste des parties
 * @author Jimmy
 *
 */
public class PlayersListActivity extends Activity {

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
		//recuperation des joueurs en base
		List<Player> allPlayersInDB = PlayerRepositoryCache.getAll(this);
		
		//vidage de la liste
		tableLayout.removeAllViews();

		//affichage de la liste des joueurs
		if(allPlayersInDB.isEmpty()) {
			textViewMessage.setText(getResources().getString(R.string.no_player));
		}
		else {
			textViewMessage.setText(getResources().getString(R.string.player_details));
			for(Player player : allPlayersInDB) {
				tableLayout.addView(createRow(player));
			}
		}
	}

	/**
	 * Creation d'une ligne
	 * @param player
	 * @return
	 */
	private TableRow createRow(Player player) {
		TableRow newRow = new TableRow(this);
		newRow.setBackgroundResource(R.drawable.items_list_row);
		TableRow.LayoutParams rowParams = new TableRow.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		newRow.setLayoutParams(rowParams);
		newRow.setOnClickListener(new StartPlayerDetailsListener(this, player.getId()));

		ImageView imageView = new ImageView(this);
		imageView.setAdjustViewBounds(true);
		imageView.setScaleType(ScaleType.CENTER_CROP);
		TableRow.LayoutParams imageParams = new TableRow.LayoutParams(100, 100);
		imageView.setLayoutParams(imageParams);
		imageView.setImageBitmap(player.getBitmap());

		TextView scoreTextView = new TextView(this);
		scoreTextView.setText(player.toString());
		scoreTextView.setTextAppearance(getApplicationContext(), R.style.item_list_normal_text);
		TableRow.LayoutParams param = new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
		param.setMargins(20, 0, 0, 0);
		scoreTextView.setLayoutParams(param);
		scoreTextView.setGravity(Gravity.CENTER_VERTICAL);

		newRow.addView(imageView);
		newRow.addView(scoreTextView);
		return newRow;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == RESULT_OK) {
			boolean hasToRefresh = data.getBooleanExtra(Constantes.REFRESH, false);
			if(hasToRefresh) {
				refreshList();
			}
		}
		DialogHandler.dismissWaitingDialog(this);
	}

}
