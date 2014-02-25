package com.jso.technologies.tarot.scorer.prise.graphics;

import java.util.HashMap;
import java.util.Map;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jso.technologies.tarot.scorer.R;
import com.jso.technologies.tarot.scorer.Utils.Constantes;
import com.jso.technologies.tarot.scorer.common.bean.Game;
import com.jso.technologies.tarot.scorer.common.bean.Player;
import com.jso.technologies.tarot.scorer.common.bean.Prise;
import com.jso.technologies.tarot.scorer.common.enumeration.PriseEnum;

public class PriseGraphicsPageAdapter extends PagerAdapter {

	private Game game;
	private GameGraphicsActivity activity;

	public PriseGraphicsPageAdapter(GameGraphicsActivity activity, Game game) {
		this.activity = activity;
		this.game = game;
	}

	@Override
	public int getCount() {
		return 3;
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		LayoutInflater inflater = (LayoutInflater) container.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.activity_game_graphics_pane, null);

		switch (position) {
		case 0:
			instantiateScoreEvolution(view);
			break;
		case 1:
			instantiatePriseRepartitionByPlayer(view);
			break;
		case 2:
			instantiatePriseRepartitionByType(view);
			break;
		}

		((ViewPager) container).addView(view, 0);
		return view;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		((ViewPager) container).removeView((View) object);
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == ((View) arg1);
	}

	@Override
	public Parcelable saveState() {
		return null;
	}

	private void instantiatePriseRepartitionByPlayer(View view) {
		Map<Player, Integer> repartition = new HashMap<Player, Integer>();
		for(Player player : game.getPlayers()) {
			repartition.put(player, 0);
		}
		for(Prise prise : game.getPrises()) {
			Player preneur = prise.getPreneur();
			Integer nb = repartition.get(preneur);
			repartition.put(preneur, nb + 1);
		}

		//titre
		setTitleInView(view, R.string.prises_by_player);

		//chart renderer
		DefaultRenderer mRenderer  = getPieChartRenderer(game.getPlayers().size(), Constantes.ANALYSE_CHART_COLOR);

		//chart series
		CategorySeries mSeries = new CategorySeries("");
		for(Player player : game.getPlayers()) {
			Integer value = repartition.get(player);
			mSeries.add(player.getPseudo() + " (" + value + ")", value);
		}

		//chartView
		addPieChartInView(view, mSeries, mRenderer);
	}


	private void instantiatePriseRepartitionByType(View view) {
		Map<PriseEnum, Integer> repartition = new HashMap<PriseEnum, Integer>();
		for(PriseEnum type : PriseEnum.values()) {
			repartition.put(type, 0);
		}
		for(Prise prise : game.getPrises()) {
			PriseEnum type = prise.getPrise();
			Integer nb = repartition.get(type);
			repartition.put(type, nb + 1);
		}

		//titre
		setTitleInView(view, R.string.prises_by_type);

		//chart renderer
		DefaultRenderer mRenderer  = getPieChartRenderer(PriseEnum.values().length, Constantes.PRISE_TYPE_CHART_COLOR);

		//chart series
		CategorySeries mSeries = new CategorySeries("");
		for(PriseEnum type : PriseEnum.values()) {
			Integer value = repartition.get(type);
			mSeries.add(type.getName() + " (" + value + ")", value);
		}

		//chartView
		addPieChartInView(view, mSeries, mRenderer);
	}

	private void instantiateScoreEvolution(View view) {
		//titre
		setTitleInView(view, R.string.scores_evolution);

		XYMultipleSeriesDataset mDataset = new XYMultipleSeriesDataset();
		XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
		mRenderer.setAntialiasing(true);
		mRenderer.setAxisTitleTextSize(20);
		mRenderer.setChartTitleTextSize(20);
		mRenderer.setClickEnabled(false);
		mRenderer.setInScroll(true);
		mRenderer.setLabelsTextSize(25);
		mRenderer.setLegendTextSize(25);
		mRenderer.setPanEnabled(false, false);
		mRenderer.setPointSize(5);
		mRenderer.setShowLegend(true);
		mRenderer.setZoomButtonsVisible(false);
		mRenderer.setZoomEnabled(false, false);
		
		int minY = 0;
		int maxY = 0;
		
		for(int i = 0; i < game.getPlayers().size(); ++i) {
			Player player = game.getPlayers().get(i);

			XYSeriesRenderer renderer = new XYSeriesRenderer();
			renderer.setPointStyle(PointStyle.CIRCLE);
			renderer.setFillPoints(true);
			renderer.setDisplayChartValues(true);
			renderer.setChartValuesTextSize(15);
			renderer.setColor(Constantes.ANALYSE_CHART_COLOR[i]);
			mRenderer.addSeriesRenderer(renderer);

			XYSeries series = new XYSeries(player.getPseudo());
			series.add(0, 0);

			int currentScore = 0;
			int x = 1;
			for(Prise prise : game.getPrises()) {
				currentScore += game.getScoreByPrise().get(prise).get(player);
				series.add(x++, currentScore);
				
				if(currentScore > maxY) {
					maxY = currentScore;
				}
				else if(currentScore < minY) {
					minY = currentScore;
				}
			}
			mDataset.addSeries(series);
		}
		
		mRenderer.setXAxisMax(game.getPrises().size() + 1);
		mRenderer.setXLabels(game.getPrises().size());
		mRenderer.setYAxisMin(minY - 50);
		mRenderer.setYAxisMax(maxY + 50);

		//chartView
		addLineChartInView(view, mDataset, mRenderer);
	}

	private DefaultRenderer getPieChartRenderer(int size, int[] colors) {
		DefaultRenderer mRenderer = new DefaultRenderer();
		mRenderer.setAntialiasing(true);
		mRenderer.setChartTitleTextSize(20);
		mRenderer.setClickEnabled(false);
		mRenderer.setInScroll(true);
		mRenderer.setLabelsTextSize(25);
		mRenderer.setLegendTextSize(25);
		mRenderer.setPanEnabled(false);
		mRenderer.setShowLegend(true);
		mRenderer.setStartAngle(90);
		mRenderer.setZoomButtonsVisible(false);

		for(int i = 0; i < size; ++i) {
			SimpleSeriesRenderer renderer = new SimpleSeriesRenderer();
			renderer.setColor(colors[i]);
			renderer.setDisplayChartValues(true);
			mRenderer.addSeriesRenderer(renderer);
		}

		return mRenderer;
	}

	private void setTitleInView(View view, int id) {
		TextView titleView = (TextView) view.findViewById(R.id.gameGraphicsPaneTitle);
		titleView.setText(activity.getText(id));		
	}

	private void addPieChartInView(View view, CategorySeries mSeries, DefaultRenderer mRenderer) {
		GraphicalView chartView = ChartFactory.getPieChartView(activity, mSeries, mRenderer);
		RelativeLayout chartLayout = (RelativeLayout) view.findViewById(R.id.gameGraphicsChartLayout);
		chartLayout.addView(chartView, 0, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));		
	}

	private void addLineChartInView(View view, XYMultipleSeriesDataset mDataset, XYMultipleSeriesRenderer mRenderer) {
		GraphicalView chartView = ChartFactory.getLineChartView(activity, mDataset, mRenderer);
		RelativeLayout chartLayout = (RelativeLayout) view.findViewById(R.id.gameGraphicsChartLayout);
		LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		layoutParams.setMargins(10, 10, 10, 10);
		chartLayout.addView(chartView, 0, layoutParams);
	}
}

