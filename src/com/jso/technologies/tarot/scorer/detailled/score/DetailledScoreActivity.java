package com.jso.technologies.tarot.scorer.detailled.score;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.jso.technologies.tarot.scorer.FinishActivityListener;
import com.jso.technologies.tarot.scorer.R;
import com.jso.technologies.tarot.scorer.Utils.Constantes;
import com.jso.technologies.tarot.scorer.common.bean.Game;
import com.jso.technologies.tarot.scorer.common.bean.Player;
import com.jso.technologies.tarot.scorer.common.bean.Prise;
import com.jso.technologies.tarot.scorer.common.iface.ITarotActivity;
import com.jso.technologies.tarot.scorer.common.iface.ITarotElementaryAction;

public class DetailledScoreActivity extends Activity implements ITarotActivity {
	private Game game;
	private List<TableRow> rows;
	private boolean canDelete;
	private TableRow selectedRow;
	private TableLayout detailledScoreTableLayout;
	private TableRow totalRow;
	private ArrayList<Integer> deletedRows;
	private ArrayList<TableRow> deletedTableRows;
	private ArrayList<Prise> deletedPrises;
	private Button cancelButton;
	
	private ITarotElementaryAction deleteRowAction;
	private ITarotElementaryAction showPriseDetailAction;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detailled_score);

		//recuperation du jeu concerne
		Intent passedIntent = getIntent();
		game = (Game) passedIntent.getExtras().get(Constantes.GAME);
		canDelete = (Boolean) passedIntent.getExtras().get(Constantes.CAN_DELETE);
		
		//liste des lignes supprimees
		deletedRows = new ArrayList<Integer>();
		deletedTableRows = new ArrayList<TableRow>();
		deletedPrises = new ArrayList<Prise>();
		
		//actions des menus
		deleteRowAction = new DeleteRowAction(this);
		showPriseDetailAction = new ShowPriseDetailAction(this);

		//remplissage des joueurs (image et pseudo)
		game.fillPlayersIfNeeded(this);

		//listener sur le bouton retour
		findViewById(R.id.detailledScoreRetourButton).setOnClickListener(new FinishActivityListener(this));
		cancelButton = (Button) findViewById(R.id.detailledScoreAnnulerButton);
		cancelButton.setOnClickListener(new CancelLastActionDetailledScoreListener(this));

		//recuperation des tableaux de la view
		detailledScoreTableLayout = (TableLayout) findViewById(R.id.detailledScoreLayout);

		TableRow headerRow = (TableRow) findViewById(R.id.detailledScoreHeader);
		headerRow.setWeightSum(game.getPlayers().size());

		TableRow photosRow = (TableRow) findViewById(R.id.detailledScorePhotos);
		photosRow.setWeightSum(game.getPlayers().size());

		List<TextView> detailledScorePseudo = new ArrayList<TextView>();
		detailledScorePseudo.add((TextView) findViewById(R.id.detailledScorePlayer1Pseudo));
		detailledScorePseudo.add((TextView) findViewById(R.id.detailledScorePlayer2Pseudo));
		detailledScorePseudo.add((TextView) findViewById(R.id.detailledScorePlayer3Pseudo));
		detailledScorePseudo.add((TextView) findViewById(R.id.detailledScorePlayer4Pseudo));
		detailledScorePseudo.add((TextView) findViewById(R.id.detailledScorePlayer5Pseudo));

		List<ImageView> detailledScorePhotos = new ArrayList<ImageView>();
		detailledScorePhotos.add((ImageView) findViewById(R.id.detailledScorePlayer1PhotoImageView));
		detailledScorePhotos.add((ImageView) findViewById(R.id.detailledScorePlayer2PhotoImageView));
		detailledScorePhotos.add((ImageView) findViewById(R.id.detailledScorePlayer3PhotoImageView));
		detailledScorePhotos.add((ImageView) findViewById(R.id.detailledScorePlayer4PhotoImageView));
		detailledScorePhotos.add((ImageView) findViewById(R.id.detailledScorePlayer5PhotoImageView));

		//Ecriture des pseudos et photos
		for(int i = 0; i < detailledScorePseudo.size(); ++i) {
			if(i < game.getPlayers().size()) {
				Player player = game.getPlayers().get(i);
				detailledScorePseudo.get(i).setText(player.getPseudo());
				detailledScorePhotos.get(i).setImageBitmap(player.getBitmap());
			}
			else {
				detailledScorePseudo.get(i).setVisibility(View.GONE);
				detailledScorePhotos.get(i).setVisibility(View.GONE);
			}
		}

		//affichage de scores
		rows = new ArrayList<TableRow>();
		for(Prise prise : game.getPrises()) {
			TableRow newRow = createTableRow(game.getScoreByPrise().get(prise));
			newRow.setOnClickListener(new DetailledScoreRowClickListener(this, newRow));
			detailledScoreTableLayout.addView(newRow);
			rows.add(newRow);
		}

		//affichage ligne Total
		TableRow.LayoutParams tableRowParam = new TableRow.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, game.getPlayers().size());
		tableRowParam.span = game.getPlayers().size();
		tableRowParam.setMargins(0, 20, 0, 0);
		TextView totalTextView = new TextView(this);
		totalTextView.setText(R.string.total);
		totalTextView.setTextAppearance(getApplicationContext(), R.style.detailled_score_normal_text);
		totalTextView.setLayoutParams(tableRowParam);
		totalTextView.setGravity(Gravity.CENTER);

		TableRow newRow = new TableRow(this);
		newRow.addView(totalTextView);

		detailledScoreTableLayout.addView(
				newRow,
				new TableLayout.LayoutParams(
						LayoutParams.MATCH_PARENT,
						LayoutParams.WRAP_CONTENT));

		//affichage HR
		TableRow.LayoutParams hrTableRowParam = new TableRow.LayoutParams(LayoutParams.MATCH_PARENT, 2, game.getPlayers().size());
		hrTableRowParam.span = game.getPlayers().size();
		TextView hrTotalTextView = new TextView(this);
		hrTotalTextView.setText(R.string.total);
		hrTotalTextView.setTextAppearance(getApplicationContext(), R.style.detailled_score_normal_text);
		hrTotalTextView.setLayoutParams(hrTableRowParam);
		hrTotalTextView.setGravity(Gravity.CENTER_HORIZONTAL);
		hrTotalTextView.setBackgroundColor(Color.WHITE);

		TableRow hrNewRow = new TableRow(this);
		hrNewRow.addView(hrTotalTextView);

		detailledScoreTableLayout.addView(
				hrNewRow,
				new TableLayout.LayoutParams(
						LayoutParams.MATCH_PARENT,
						2));

		//affichage des scores totaux
		updateTotals();
	}

	/**
	 * Mise a jour de la ligne de total
	 */
	public void updateTotals() {
		if(totalRow != null) {
			detailledScoreTableLayout.removeView(totalRow);
		}
		totalRow = createTableRow(game.getScore());
		detailledScoreTableLayout.addView(totalRow, new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT,2));
	}

	/**
	 * Creation d'une ligne de score
	 * @param score
	 * @return
	 */
	private TableRow createTableRow(Map<Player, Integer> score) {
		TableRow newRow = new TableRow(this);
		for(Player player : game.getPlayers()) {
			TextView scoreTextView = new TextView(this);
			scoreTextView.setText(score.get(player).toString());
			scoreTextView.setTextAppearance(getApplicationContext(), R.style.detailled_score_normal_text);
			LayoutParams param = new TableRow.LayoutParams(LayoutParams.MATCH_PARENT, 100, 1f);
			scoreTextView.setLayoutParams(param);
			scoreTextView.setGravity(Gravity.CENTER);

			newRow.addView(scoreTextView);
		}
		return newRow;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_detailled_score, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_details:
			showPriseDetailAction.execute();
			return true;
		case R.id.menu_trash:
			deleteRowAction.execute();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/**
	 * Retour � la vue de prise
	 */
	@Override
	public void onBackPressed() {
		findViewById(R.id.detailledScoreRetourButton).performClick();
	}

	/**
	 * Ne pas afficher le menu lorsqu'on appui la touche menu
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_MENU) {
			if(selectedRow != null) {
				selectedRow.setBackgroundColor(getResources().getColor(android.R.color.transparent));
				selectedRow.refreshDrawableState();
				selectedRow = null;
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * Mise des lignes supprim�es dans le r�sultat � passer � la vue appelante
	 */
	@Override
	public void executeBeforeFinish() {
		Intent intent = new Intent();
		intent.putIntegerArrayListExtra(Constantes.DELETED_PRISE, deletedRows);
		setResult(Activity.RESULT_OK, intent);
	}
	
	
	public List<TableRow> getRows() {
		return rows;
	}

	public boolean isCanDelete() {
		return canDelete;
	}

	public TableRow getSelectedRow() {
		return selectedRow;
	}

	public void setSelectedRow(TableRow selectedRow) {
		this.selectedRow = selectedRow;
	}
	
	public ArrayList<Integer> getDeletedRows() {
		return deletedRows;
	}
	
	public ArrayList<TableRow> getDeletedTableRows() {
		return deletedTableRows;
	}

	public ArrayList<Prise> getDeletedPrises() {
		return deletedPrises;
	}

	public Button getCancelButton() {
		return cancelButton;
	}

	public Game getGame() {
		return game;
	}

	public TableLayout getDetailledScoreTableLayout() {
		return detailledScoreTableLayout;
	}
}
