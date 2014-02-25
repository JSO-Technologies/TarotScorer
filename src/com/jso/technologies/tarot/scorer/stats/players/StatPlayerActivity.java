package com.jso.technologies.tarot.scorer.stats.players;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.jso.technologies.tarot.scorer.R;
import com.jso.technologies.tarot.scorer.Utils.Constantes;
import com.jso.technologies.tarot.scorer.Utils.DialogHandler;
import com.jso.technologies.tarot.scorer.Utils.StatistiqueCalculator;
import com.jso.technologies.tarot.scorer.api.TextProgressBar;
import com.jso.technologies.tarot.scorer.common.bean.Player;
import com.jso.technologies.tarot.scorer.common.enumeration.PriseEnum;
import com.jso.technologies.tarot.scorer.db.cache.PlayerRepositoryCache;
import com.jso.technologies.tarot.scorer.db.cache.StatistiqueCalculatorCache;

/**
 * Activity : statistiques d'un joueur
 * @author Jimmy
 *
 */
public class StatPlayerActivity extends Activity {
	private static NumberFormat nf;
	private CategorySeries mSeries;
	private DefaultRenderer mRenderer;
	private GraphicalView priseTypePieChartView;
	private Player player;
	private boolean playerHasChanged;

	static {
		nf = new DecimalFormat();
		nf.setMinimumFractionDigits(0);
		nf.setMaximumFractionDigits(2);
		nf.setMinimumIntegerDigits(1);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//recuperation de l'id du joueur
		Intent passedIntent = getIntent();
		Integer playerID = (Integer) passedIntent.getExtras().get(Constantes.SELECTED_PLAYER_ID);

		//recuperation du joueur
		player = PlayerRepositoryCache.getById(playerID, this);
		
		executeDisplayUpdates();

	}
	
	public void executeDisplayUpdates() {
		setContentView(R.layout.activity_stat_player);

		//bouton edit joueur
		findViewById(R.id.modifyPlayerButton).setOnClickListener(new StartModifyPlayerActivityListener(this, player));

		//bouton edit joueur
		findViewById(R.id.resetPlayerButton).setOnClickListener(new ResetPlayerActivityListener(this, player));
				
		//bouton suppr joueur
		findViewById(R.id.deletePlayerButton).setOnClickListener(new DeletePlayerListener(this, player));

		//mise en place du pseudo et de l'image
		updatePlayersDetails(player);

		//mise en place des infos statistiques de la page
		updateDisplayedStats();
	}

	public void updateDisplayedStats() {
		//Init du calculateur
		StatistiqueCalculator calculator = StatistiqueCalculatorCache.getCalculator(this, player);

		//nombre de parties
		Integer nbGamePlayed = calculator.getNbGamePlayed();
		((TextView)findViewById(R.id.statPlayerNbGame)).setText(nbGamePlayed.toString());

		//nombre de parties gagnees
		((TextView)findViewById(R.id.statPlayerNbWinningGame)).setText(createNumberAndPercentageText(calculator.getNbWinningGame(), calculator.getNbWinningGamePercentage()));

		//place moyenne aux scores finals
		((TextView)findViewById(R.id.statPlayerAveragePlace)).setText(nf.format(calculator.getAveragePlace()));

		//nombre de prise
		((TextView)findViewById(R.id.statPlayerNbPrise)).setText(calculator.getNbPrise().toString());

		//pie chart prises
		mSeries = new CategorySeries("");
		mRenderer = new DefaultRenderer();
		if(calculator.getNbPrise() > 0) {
			mRenderer.setChartTitleTextSize(20);
			mRenderer.setLabelsTextSize(15);
			mRenderer.setLegendTextSize(15);
			mRenderer.setMargins(new int[] { 20, 30, 15, 0 });
			mRenderer.setZoomButtonsVisible(false);
			mRenderer.setStartAngle(90);
			mRenderer.setShowLegend(false);
			mRenderer.setInScroll(false);
			mRenderer.setAntialiasing(true);

			SimpleSeriesRenderer renderer = new SimpleSeriesRenderer();
			renderer.setColor(Constantes.PETITE_CHART_COLOR);
			renderer.setDisplayChartValues(true);
			mRenderer.addSeriesRenderer(renderer);
			renderer = new SimpleSeriesRenderer();
			renderer.setColor(Constantes.GARDE_CHART_COLOR);
			renderer.setDisplayChartValues(true);
			mRenderer.addSeriesRenderer(renderer);
			renderer = new SimpleSeriesRenderer();
			renderer.setColor(Constantes.GARDE_SANS_CHART_COLOR);
			renderer.setDisplayChartValues(true);
			mRenderer.addSeriesRenderer(renderer);
			renderer = new SimpleSeriesRenderer();
			renderer.setColor(Constantes.GARDE_CONTRE_CHART_COLOR);
			renderer.setDisplayChartValues(true);
			mRenderer.addSeriesRenderer(renderer);

			mSeries.add(PriseEnum.PETITE.getName() + createPercentageText(calculator.getNbPetitePercentage()), calculator.getNbPetite());
			mSeries.add(PriseEnum.GARDE.getName() + createPercentageText(calculator.getNbGardePercentage()), calculator.getNbGarde());
			mSeries.add(PriseEnum.GARDE_SANS.getName() + createPercentageText(calculator.getNbGardeSansPercentage()), calculator.getNbGardeSans());
			mSeries.add(PriseEnum.GARDE_CONTRE.getName() + createPercentageText(calculator.getNbGardeContrePercentage()), calculator.getNbGardeContre());
		}

		//nombre de petite
		String wonText = getString(R.string.won_games);
		String loseText = getString(R.string.lost_games);
		TextProgressBar petiteBar = (TextProgressBar) findViewById(R.id.statPlayerPriseBar);
		petiteBar.setTexts(	calculator.getWonPriseCount().get(PriseEnum.PETITE) + wonText, 
				PriseEnum.PETITE.getName(), 
				calculator.getLostPriseCount().get(PriseEnum.PETITE) + loseText);
		petiteBar.setMax(calculator.getNbPetite());
		petiteBar.setProgress(calculator.getWonPriseCount().get(PriseEnum.PETITE));

		//nombre de garde
		TextProgressBar gardeBar = (TextProgressBar) findViewById(R.id.statPlayerGardeBar);
		gardeBar.setTexts(	calculator.getWonPriseCount().get(PriseEnum.GARDE) + wonText, 
				PriseEnum.GARDE.getName(), 
				calculator.getLostPriseCount().get(PriseEnum.GARDE) + loseText);
		gardeBar.setMax(calculator.getNbGarde());
		gardeBar.setProgress(calculator.getWonPriseCount().get(PriseEnum.GARDE));

		//nombre de garde sans
		TextProgressBar gardeSansBar = (TextProgressBar) findViewById(R.id.statPlayerGardeSansBar);
		gardeSansBar.setTexts(	calculator.getWonPriseCount().get(PriseEnum.GARDE_SANS) + wonText, 
				PriseEnum.GARDE_SANS.getName(), 
				calculator.getLostPriseCount().get(PriseEnum.GARDE_SANS) + loseText);
		gardeSansBar.setMax(calculator.getNbGardeSans());
		gardeSansBar.setProgress(calculator.getWonPriseCount().get(PriseEnum.GARDE_SANS));

		//nombre de garde contre
		TextProgressBar gardeContreBar = (TextProgressBar) findViewById(R.id.statPlayerGardeContreBar);
		gardeContreBar.setTexts(	calculator.getWonPriseCount().get(PriseEnum.GARDE_CONTRE) + wonText, 
				PriseEnum.GARDE_CONTRE.getName(), 
				calculator.getLostPriseCount().get(PriseEnum.GARDE_CONTRE) + loseText);
		gardeContreBar.setMax(calculator.getNbGardeContre());
		gardeContreBar.setProgress(calculator.getWonPriseCount().get(PriseEnum.GARDE_CONTRE));

		//nombre moyen de prise par partie
		((TextView)findViewById(R.id.statPlayerAveragePrise)).setText(nf.format(calculator.getAveragePriseByGame()));

		//type de prise le plus gagne
		PriseEnum theMostWonPriseType = calculator.getTheMostWonPriseType();
		((TextView)findViewById(R.id.statPlayerMaxWinningPrise)).setText(theMostWonPriseType == null ? "-" : theMostWonPriseType.getName());

		//Faite de combien en moyenne
		((TextView)findViewById(R.id.statPlayerAverageWinningPoints)).setText(nf.format(calculator.getAveragePointDoneOverContract()));

		//Faite de combien au max
		((TextView)findViewById(R.id.statPlayerMaxWinningPoints)).setText(calculator.getMaxPointDoneOverContract().toString());

		//type de prise le plus perdu
		PriseEnum theMostLostPriseType = calculator.getTheMostLostPriseType();
		((TextView)findViewById(R.id.statPlayerMaxLoosingPrise)).setText(theMostLostPriseType == null ? "-" : theMostLostPriseType.getName());

		//Perdue de combien en moyenne
		((TextView)findViewById(R.id.statPlayerAverageLoosingPoints)).setText(nf.format(calculator.getAveragePointDoneUnderContract()));

		//Faite de combien au max
		((TextView)findViewById(R.id.statPlayerMaxLoosingPoints)).setText(calculator.getMaxPointDoneUnderContract().toString());
	}

	/**
	 * Formatage : valeur (pouventage %) 
	 * @param value
	 * @param percentage
	 * @return
	 */
	private String createNumberAndPercentageText(Integer value, Double percentage){
		StringBuffer buffer = new StringBuffer()
		.append(value)
		.append(createPercentageText(percentage));

		return buffer.toString();
	}

	/**
	 * Formatage : (pouventage %) 
	 * @param value
	 * @param percentage
	 * @return
	 */
	private String createPercentageText(Double percentage){
		StringBuffer buffer = new StringBuffer()
		.append(" (")
		.append(nf.format(percentage))
		.append(" %)");

		return buffer.toString();
	}

	@Override
	protected void onResume() {
		super.onResume();
		refreshChart();
	}

	public void refreshChart() {
		if(mSeries.getItemCount() > 0) {
			if(priseTypePieChartView == null) {
				priseTypePieChartView = ChartFactory.getPieChartView(this, mSeries, mRenderer);
				LinearLayout priseTypePieChartLayout = (LinearLayout) findViewById(R.id.statPlayerPriseTypePieChart);
				priseTypePieChartLayout.addView(priseTypePieChartView, 0, new LayoutParams(600, 200));

			} else {
				priseTypePieChartView.repaint();
			}
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			Player modifiedPlayer = (Player) data.getParcelableExtra(Constantes.MODIFIED_PLAYER);
			updatePlayersDetails(modifiedPlayer);

			player.setPseudo(modifiedPlayer.getPseudo());
			player.setBitmap(modifiedPlayer.getBitmap());
			
			playerHasChanged = true;
		}
		DialogHandler.dismissWaitingDialog(this);
	}

	private void updatePlayersDetails(Player player) {
		((ImageView) findViewById(R.id.statPlayerPhoto)).setImageBitmap(player.getBitmap());
		((TextView)findViewById(R.id.statPlayerPseudo)).setText(player.getPseudo());
	}

	@Override
	public void onBackPressed() {
		Intent intent = new Intent();
		intent.putExtra(Constantes.REFRESH, playerHasChanged);
		setResult(Activity.RESULT_OK, intent);
		finish();
	}
}
