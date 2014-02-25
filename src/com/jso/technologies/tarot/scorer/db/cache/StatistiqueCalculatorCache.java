package com.jso.technologies.tarot.scorer.db.cache;

import android.app.Activity;
import android.util.SparseArray;

import com.jso.technologies.tarot.scorer.Utils.StatistiqueCalculator;
import com.jso.technologies.tarot.scorer.common.bean.Player;

public class StatistiqueCalculatorCache {
	private static SparseArray<StatistiqueCalculator> cache = new SparseArray<StatistiqueCalculator>();
	
	private StatistiqueCalculatorCache(){}
	
	public static StatistiqueCalculator getCalculator(Activity activity, Player player) {
		StatistiqueCalculator calculator = cache.get(player.getId());
		if(calculator == null) {
			calculator = new StatistiqueCalculator(activity, player);
			cache.put(player.getId(), calculator);
		}
		return calculator;
	}
	
	public static void invalidate(Player player) {
		cache.remove(player.getId());
	}
}
