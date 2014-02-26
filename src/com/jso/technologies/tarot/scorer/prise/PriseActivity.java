package com.jso.technologies.tarot.scorer.prise;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.jso.technologies.tarot.scorer.R;
import com.jso.technologies.tarot.scorer.Utils.Constantes;
import com.jso.technologies.tarot.scorer.Utils.DialogHandler;
import com.jso.technologies.tarot.scorer.Utils.MessageUtils;
import com.jso.technologies.tarot.scorer.common.bean.Game;
import com.jso.technologies.tarot.scorer.common.bean.Player;
import com.jso.technologies.tarot.scorer.common.bean.Prise;
import com.jso.technologies.tarot.scorer.common.enumeration.ChelemEnum;
import com.jso.technologies.tarot.scorer.common.enumeration.PetiteAuBoutEnum;
import com.jso.technologies.tarot.scorer.common.enumeration.PoigneeEnum;
import com.jso.technologies.tarot.scorer.common.enumeration.PriseEnum;
import com.jso.technologies.tarot.scorer.db.cache.GameRepositoryCache;
import com.jso.technologies.tarot.scorer.db.cache.PlayerRepositoryCache;
import com.jso.technologies.tarot.scorer.detailled.score.ShowDetailledScoreListener;
import com.jso.technologies.tarot.scorer.player.config.PlayersConfigActivity;

public class PriseActivity extends Activity {
	private SeekBar pointsSeekBar;
	private SeekBar oudlersSeekBar;

	private TextView selectedPointsTextView;
	private List<ImageView> playerPhotos;
	private List<TextView> playerPseudos;
	private List<TextView> scoresTextView;

	private List<RelativeLayout> preneursRadioLayout;
	private List<RadioButton> preneurRadios;
	private List<RadioButton> appelRadios;
	private List<CheckBox> misereCheckBoxes;
	private List<RadioButton> petiteAuBoutRadios;
	private List<RadioButton> poigneeRadios;
	private List<RadioButton> chelemRadios;

	private Spinner priseSpinner;

	private List<Player> players;

	private RelativeLayout optionLayout;

	private AnimationSet optionInAnimation;
	private AnimationSet optionOutAnimation;

	private Game game;
	private Prise currentPrise;
	private Class<PlayersConfigActivity> nextActivity;
	
	private DatabaseGameAction databaseGameAction;

	@SuppressWarnings("unchecked")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_prise);

		//helper de manipulation en database
		databaseGameAction = new DatabaseGameAction(this);
		
		Intent passedIntent = getIntent();
		//Nouveau jeu : recuperation des joueurs selectionnes
		List<Integer> playersIdFromIntent = (List<Integer>) passedIntent.getExtras().get(Constantes.SELECTED_PLAYERS);
		if(playersIdFromIntent != null && !playersIdFromIntent.isEmpty()) {
			//recuperation des joueurs en base
			players = PlayerRepositoryCache.getByIdList(playersIdFromIntent, this);
			//init jeux
			game = new Game();
			game.getPlayers().addAll(players);			
		}
		else {
			//Reprise d'un jeu existant : recuperation de l'id du jeu a charger
			Integer gameID = passedIntent.getExtras().getInt(Constantes.SELECTED_GAME);
			game = GameRepositoryCache.getById(gameID, this);
			game.fillPlayersIfNeeded(this);
			game.fillPrisesIfNeeded(this);
			
			players = game.getPlayers();
		}

		currentPrise = new Prise();

		//recuperation des view
		playerPhotos = new ArrayList<ImageView>();
		playerPhotos.add((ImageView) findViewById(R.id.prisePlayer1PhotoImageView));
		playerPhotos.add((ImageView) findViewById(R.id.prisePlayer2PhotoImageView));
		playerPhotos.add((ImageView) findViewById(R.id.prisePlayer3PhotoImageView));
		playerPhotos.add((ImageView) findViewById(R.id.prisePlayer4PhotoImageView));
		playerPhotos.add((ImageView) findViewById(R.id.prisePlayer5PhotoImageView));

		playerPseudos = new ArrayList<TextView>();
		playerPseudos.add((TextView) findViewById(R.id.prisePlayer1PseudoTextView));
		playerPseudos.add((TextView) findViewById(R.id.prisePlayer2PseudoTextView));
		playerPseudos.add((TextView) findViewById(R.id.prisePlayer3PseudoTextView));
		playerPseudos.add((TextView) findViewById(R.id.prisePlayer4PseudoTextView));
		playerPseudos.add((TextView) findViewById(R.id.prisePlayer5PseudoTextView));

		scoresTextView = new ArrayList<TextView>();
		scoresTextView.add((TextView) findViewById(R.id.prisePlayer1ScoreTextView));
		scoresTextView.add((TextView) findViewById(R.id.prisePlayer2ScoreTextView));
		scoresTextView.add((TextView) findViewById(R.id.prisePlayer3ScoreTextView));
		scoresTextView.add((TextView) findViewById(R.id.prisePlayer4ScoreTextView));
		scoresTextView.add((TextView) findViewById(R.id.prisePlayer5ScoreTextView));

		preneurRadios = new ArrayList<RadioButton>();
		preneurRadios.add((RadioButton) findViewById(R.id.prisePreneur1RadioButton));
		preneurRadios.add((RadioButton) findViewById(R.id.prisePreneur2RadioButton));
		preneurRadios.add((RadioButton) findViewById(R.id.prisePreneur3RadioButton));
		preneurRadios.add((RadioButton) findViewById(R.id.prisePreneur4RadioButton));
		preneurRadios.add((RadioButton) findViewById(R.id.prisePreneur5RadioButton));

		List<RelativeLayout> preneurRadiosLayout = new ArrayList<RelativeLayout>();
		preneurRadiosLayout.add((RelativeLayout) findViewById(R.id.prisePreneur1RadioLayout));
		preneurRadiosLayout.add((RelativeLayout) findViewById(R.id.prisePreneur2RadioLayout));
		preneurRadiosLayout.add((RelativeLayout) findViewById(R.id.prisePreneur3RadioLayout));
		preneurRadiosLayout.add((RelativeLayout) findViewById(R.id.prisePreneur4RadioLayout));
		preneurRadiosLayout.add((RelativeLayout) findViewById(R.id.prisePreneur5RadioLayout));

		preneursRadioLayout = new ArrayList<RelativeLayout>();
		preneursRadioLayout.add((RelativeLayout) findViewById(R.id.prisePreneur1RadioLayout));
		preneursRadioLayout.add((RelativeLayout) findViewById(R.id.prisePreneur2RadioLayout));
		preneursRadioLayout.add((RelativeLayout) findViewById(R.id.prisePreneur3RadioLayout));
		preneursRadioLayout.add((RelativeLayout) findViewById(R.id.prisePreneur4RadioLayout));
		preneursRadioLayout.add((RelativeLayout) findViewById(R.id.prisePreneur5RadioLayout));

		List<RelativeLayout> appelRadiosLayout = new ArrayList<RelativeLayout>();
		appelRadiosLayout.add((RelativeLayout) findViewById(R.id.priseAppel1RadioLayout));
		appelRadiosLayout.add((RelativeLayout) findViewById(R.id.priseAppel2RadioLayout));
		appelRadiosLayout.add((RelativeLayout) findViewById(R.id.priseAppel3RadioLayout));
		appelRadiosLayout.add((RelativeLayout) findViewById(R.id.priseAppel4RadioLayout));
		appelRadiosLayout.add((RelativeLayout) findViewById(R.id.priseAppel5RadioLayout));
		
		appelRadios = new ArrayList<RadioButton>();
		appelRadios.add((RadioButton) findViewById(R.id.priseAppel1RadioButton));
		appelRadios.add((RadioButton) findViewById(R.id.priseAppel2RadioButton));
		appelRadios.add((RadioButton) findViewById(R.id.priseAppel3RadioButton));
		appelRadios.add((RadioButton) findViewById(R.id.priseAppel4RadioButton));
		appelRadios.add((RadioButton) findViewById(R.id.priseAppel5RadioButton));
		
		misereCheckBoxes = new ArrayList<CheckBox>();
		misereCheckBoxes.add((CheckBox) findViewById(R.id.priseMisere1Checkbox));
		misereCheckBoxes.add((CheckBox) findViewById(R.id.priseMisere2Checkbox));
		misereCheckBoxes.add((CheckBox) findViewById(R.id.priseMisere3Checkbox));
		misereCheckBoxes.add((CheckBox) findViewById(R.id.priseMisere4Checkbox));
		misereCheckBoxes.add((CheckBox) findViewById(R.id.priseMisere5Checkbox));

		petiteAuBoutRadios = new ArrayList<RadioButton>();
		petiteAuBoutRadios.add((RadioButton) findViewById(R.id.prisePasPetiteAuBoutRadio));
		petiteAuBoutRadios.add((RadioButton) findViewById(R.id.prisePetiteAuBoutRadio));
		petiteAuBoutRadios.add((RadioButton) findViewById(R.id.prisePerduPetiteAuBoutRadio));

		poigneeRadios = new ArrayList<RadioButton>();
		poigneeRadios.add((RadioButton) findViewById(R.id.prisePasPoigneeRadio));
		poigneeRadios.add((RadioButton) findViewById(R.id.priseSimplePoigneeRadio));
		poigneeRadios.add((RadioButton) findViewById(R.id.priseDoublePoigneeRadio));
		poigneeRadios.add((RadioButton) findViewById(R.id.priseTriplePoigneeRadio));

		chelemRadios = new ArrayList<RadioButton>();
		chelemRadios.add((RadioButton) findViewById(R.id.priseNoChelemRadio));
		chelemRadios.add((RadioButton) findViewById(R.id.priseNotAnnouncedChelemRadio));
		chelemRadios.add((RadioButton) findViewById(R.id.priseAnnoucedChelemRadio));
		chelemRadios.add((RadioButton) findViewById(R.id.priseLostChelemRadio));

		oudlersSeekBar = (SeekBar) findViewById(R.id.priseOudlersSeekBar);
		pointsSeekBar = (SeekBar) findViewById(R.id.prisePointsSeekBar);
		selectedPointsTextView = (TextView) findViewById(R.id.priseSelectedPointsTextView);

		priseSpinner = (Spinner) findViewById(R.id.priseSpinner);
		optionLayout = (RelativeLayout) findViewById(R.id.priseOptionLayout);

		//--------------------------------------------------------------------------------
		// Shortcut
		//--------------------------------------------------------------------------------
		findViewById(R.id.priseScoreShorcut).setOnClickListener(new ShowDetailledScoreListener(this, game));
		findViewById(R.id.priseAnalyseShorcut).setOnClickListener(new ShowGraphicsListener(this));

		//--------------------------------------------------------------------------------
		// Joueurs (photos, pseudos) - Preneur - Appel au roi 
		//--------------------------------------------------------------------------------
		//mise en place des joueurs/pseudos et radios preneur/appel
		for(int i = 0; i < 5; ++i) {
			if(i < players.size()) {
				Player player = players.get(i);

				//photos
				ImageView photo = playerPhotos.get(i);
				photo.setImageBitmap(player.getBitmap());

				//pseudos
				TextView pseudo = playerPseudos.get(i);
				pseudo.setText(player.getPseudo());

				ViewGroup parent = (ViewGroup) photo.getParent();
				parent.setVisibility(View.VISIBLE);

				//preneur radio
				preneursRadioLayout.get(i).setVisibility(View.VISIBLE);
				preneurRadios.get(i).setOnCheckedChangeListener(new PriseRadioSelectionListener<Player>(this, Constantes.PRENEUR_ELEMENT, preneurRadios, i, player));
				preneurRadiosLayout.get(i).setOnClickListener(new PerformClickListener(preneurRadios.get(i)));

				//appel radio
				appelRadios.get(i).setOnCheckedChangeListener(new PriseRadioSelectionListener<Player>(this, Constantes.CALL_ELEMENT, appelRadios, i, player));
				appelRadiosLayout.get(i).setOnClickListener(new PerformClickListener(appelRadios.get(i)));
				
				//miseres
				misereCheckBoxes.get(i).setVisibility(View.VISIBLE);
				misereCheckBoxes.get(i).setOnCheckedChangeListener(new PriseCheckboxSelectionListener<Player>(this, Constantes.MISERE_ELEMENT, player));
			}
		}

		//mise en page suivant nombre de joueurs
		LinearLayout playerLayout = (LinearLayout) findViewById(R.id.prisePlayersLayout);
		playerLayout.setWeightSum(players.size());

		RadioGroup preneurRadioGroup = (RadioGroup) findViewById(R.id.prisePreneursRadioGroup);
		preneurRadioGroup.setWeightSum(players.size());

		//listener sur les scores
		for(TextView scoreView : scoresTextView) {
			scoreView.setOnClickListener(new ShowDetailledScoreListener(this, game));
		}
		updateScores();

		//zone d'appel au roi
		if(players.size() == 5) {
			RelativeLayout appels = (RelativeLayout) findViewById(R.id.priseAppelLayout);
			appels.setVisibility(View.VISIBLE);
		}

		//--------------------------------------------------------------------------------
		// Prise Contrat
		//--------------------------------------------------------------------------------
		//remplissage de la combobox de prise
		PriseEnum[] priseValues = new PriseEnum[4];
		priseValues[0] = PriseEnum.PETITE; 
		priseValues[1] = PriseEnum.GARDE;
		priseValues[2] = PriseEnum.GARDE_SANS;
		priseValues[3] = PriseEnum.GARDE_CONTRE;
		ArrayAdapter<PriseEnum> arrayAdapter = new ArrayAdapter<PriseEnum>(this, R.layout.custom_spinner, priseValues );
		priseSpinner.setAdapter(arrayAdapter);
		priseSpinner.setOnItemSelectedListener(new PriseSpinnerSelectionListener<PriseEnum>(this, Constantes.PRISE_ELEMENT));

		//--------------------------------------------------------------------------------
		// SeekBar (Points et Oudlers)
		//--------------------------------------------------------------------------------
		pointsSeekBar.setOnSeekBarChangeListener(new PriseSeekBarSelectionListener(this, Constantes.POINTS_ELEMENT, true));
		oudlersSeekBar.setOnSeekBarChangeListener(new PriseSeekBarSelectionListener(this, Constantes.OUDLERS_ELEMENT, false));

		//--------------------------------------------------------------------------------
		// Options
		//--------------------------------------------------------------------------------
		//Animation
		optionInAnimation = (AnimationSet) AnimationUtils.loadAnimation(this, R.anim.options_appear_animation);
		optionOutAnimation = (AnimationSet) AnimationUtils.loadAnimation(this, R.anim.options_disappear_animation);
		findViewById(R.id.priseOptionTextView).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if(optionLayout.getVisibility() == View.GONE){
					optionLayout.startAnimation(optionInAnimation);
					optionLayout.setVisibility(View.VISIBLE);
				}
				else{
					optionLayout.startAnimation(optionOutAnimation);
					optionLayout.setVisibility(View.GONE);
				}
			}
		});

		//petite au bout radio
		for (int i = 0; i < petiteAuBoutRadios.size(); ++i) {
			petiteAuBoutRadios.get(i).setOnCheckedChangeListener(new PriseRadioSelectionListener<PetiteAuBoutEnum>(this, Constantes.PETITE_AU_BOUT_ELEMENT, petiteAuBoutRadios, i, PetiteAuBoutEnum.fromValue(i)));
		}

		//poignees radio
		for (int i = 0; i < poigneeRadios.size(); ++i) {
			poigneeRadios.get(i).setOnCheckedChangeListener(new PriseRadioSelectionListener<PoigneeEnum>(this, Constantes.POIGNEE_ELEMENT, poigneeRadios, i, PoigneeEnum.fromValue(i)));
		}

		//chelem radio
		for (int i = 0; i < chelemRadios.size(); ++i) {
			chelemRadios.get(i).setOnCheckedChangeListener(new PriseRadioSelectionListener<ChelemEnum>(this, Constantes.CHELEM_ELEMENT, chelemRadios, i, ChelemEnum.fromValue(i)));
		}

		//--------------------------------------------------------------------------------
		// Boutons
		//--------------------------------------------------------------------------------
		//bouton de validation
		findViewById(R.id.priseValidGameButton).setOnClickListener(new PriseValidationListener(this));
		findViewById(R.id.priseFinishGameButton).setOnClickListener(new PriseFinishListener(this));

		//boutons + et -
		findViewById(R.id.prisePointsMinusTextView).setOnClickListener(new PriseAdjustementPointListener(this, PriseAdjustementPointListener.MINUS));
		findViewById(R.id.prisePointsPlusTextView).setOnClickListener(new PriseAdjustementPointListener(this, PriseAdjustementPointListener.PLUS));
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		DialogHandler.dismissWaitingDialog(this);
		if (resultCode == RESULT_OK) {
			List<Integer> priseIndex = (List<Integer>) data.getExtras().get(Constantes.DELETED_PRISE);
			databaseGameAction.deletePrises(game, priseIndex);
				
			updateScores();
		}
	}

	/**
	 * Mise a jour des scores a l'ecran
	 */
	public void updateScores() {
		for(int i = 0; i < getPlayers().size(); ++i) {
			getScoresTextView().get(i).setText(getGame().getScore().get(getPlayers().get(i)).toString());
		}
	}

	/**
	 * Arret de l'activite
	 * @param displayToastMessage
	 */
	public void finishGameActivity(boolean displayToastMessage) {
		if(displayToastMessage) {
			MessageUtils.showToast(this, getString(R.string.gameFinished));
		}

		if(nextActivity != null) {
			Intent i = new Intent();
			i.setClass(this, nextActivity);
			startActivity(i);
		}

		finish();
	}

	@Override
	protected void onDestroy() {
		DialogHandler.dismissWaitingDialog(this);
		super.onDestroy();
	}

	public void resetValues() {
		//current prise
		currentPrise = new Prise();

		//Joueur : preneur et appel
		for(int i = 0; i < players.size(); ++i){
			preneurRadios.get(i).setChecked(false);
			appelRadios.get(i).setChecked(false);
			misereCheckBoxes.get(i).setChecked(false);
		}

		//prise : contrat
		priseSpinner.setSelection(0);

		//seekbars : points and oudlers
		pointsSeekBar.setProgress(0);
		oudlersSeekBar.setProgress(0);

		//options : layout
		optionLayout.setVisibility(View.GONE);

		petiteAuBoutRadios.get(0).setChecked(true);
		poigneeRadios.get(0).setChecked(true);
		chelemRadios.get(0).setChecked(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_prise, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_nouveau:
			nextActivity = PlayersConfigActivity.class;
			findViewById(R.id.priseFinishGameButton).performClick();
			return true;
		case R.id.menu_fin:
			nextActivity = null;
			findViewById(R.id.priseFinishGameButton).performClick();
			return true;
		case R.id.menu_score:
			scoresTextView.get(0).performClick();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onBackPressed() {
		if(game.getPrises().isEmpty()) {
			finishGameActivity(false);
		}
		else {
			nextActivity = null;
			findViewById(R.id.priseFinishGameButton).performClick();
		}
	}

	public SeekBar getPointsSeekBar() {
		return pointsSeekBar;
	}

	public TextView getSelectedPointsTextView() {
		return selectedPointsTextView;
	}

	public List<RadioButton> getPreneurRadios() {
		return preneurRadios;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public List<RadioButton> getAppelRadios() {
		return appelRadios;
	}

	public Spinner getPriseSpinner() {
		return priseSpinner;
	}

	public SeekBar getOudlersSeekBar() {
		return oudlersSeekBar;
	}

	public Game getGame() {
		return game;
	}

	public Prise getCurrentPrise() {
		return currentPrise;
	}

	public List<TextView> getScoresTextView() {
		return scoresTextView;
	}

	public List<ImageView> getPlayerPhotos() {
		return playerPhotos;
	}

	public DatabaseGameAction getDatabaseGameAction() {
		return databaseGameAction;
	}
	
	public List<CheckBox> getMisereCheckBoxes() {
		return misereCheckBoxes;
	}
}
