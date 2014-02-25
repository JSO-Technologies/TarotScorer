package com.jso.technologies.tarot.scorer.db.cache;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.app.Activity;
import android.util.SparseArray;

import com.jso.technologies.tarot.scorer.common.bean.Prise;
import com.jso.technologies.tarot.scorer.db.PriseDatabaseRepository;

/**
 * Cache des prises. Stockage sous forme de clé/valeur <GameId, <PriseIs, Prise>>
 * @author Jimmy
 *
 */
public class PriseRepositoryCache {
	private static final Comparator<Prise> BY_ID = new ByIdComparator();
	private static SparseArray<SparseArray<Prise>> cache = new SparseArray<SparseArray<Prise>>();

	public static void save(Prise prise, Activity activity) {
		new PriseDatabaseRepository(activity).save(prise);
		SparseArray<Prise> prisesForCurrentGame = cache.get(prise.getGameId());
		if(prisesForCurrentGame != null) {
			prisesForCurrentGame.put(prise.getId(), prise);
		}
	}

	public static void update(Prise prise, Activity activity) {
		new PriseDatabaseRepository(activity).update(prise);
		SparseArray<Prise> prisesForCurrentGame = cache.get(prise.getGameId());
		if(prisesForCurrentGame != null) {
			prisesForCurrentGame.put(prise.getId(), prise);
		}
	}

	public static void delete(Integer priseId, Activity activity) {
		new PriseDatabaseRepository(activity).delete(priseId);
		for(int i = 0; i < cache.size(); ++i) {
			cache.valueAt(i).remove(priseId);
		}
	}
	
	public static void deleteAllByGame(Integer gameId, Activity activity) {
		new PriseDatabaseRepository(activity).deleteAllByGame(gameId);
		cache.remove(gameId);
	}

	public static List<Prise> getAllByGame(Integer gameId, Activity activity) {
		List<Prise> prises;
		SparseArray<Prise> prisesForCurrentGame = cache.get(gameId);
		if(prisesForCurrentGame != null) {
			prises = new ArrayList<Prise>();
			for(int i = 0; i < prisesForCurrentGame.size(); ++i) {
				prises.add(prisesForCurrentGame.valueAt(i));
			}
			Collections.sort(prises, BY_ID);
			return prises;
		}
		
		prises = new PriseDatabaseRepository(activity).getAllByGame(gameId);
		SparseArray<Prise> prisesById = new SparseArray<Prise>();
		for(Prise prise : prises) {
			prise.fillPlayers(activity);
			prisesById.put(prise.getId(), prise);
		}
		cache.put(gameId, prisesById);
		return prises;
	}
	
	private static class ByIdComparator implements Comparator<Prise> {

		@Override
		public int compare(Prise arg0, Prise arg1) {
			return arg0.getId().compareTo(arg1.getId());
		}
	}
}
