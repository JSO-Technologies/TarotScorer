package com.jso.technologies.tarot.scorer.db.cache;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.app.Activity;
import android.util.SparseArray;

import com.jso.technologies.tarot.scorer.common.bean.Game;
import com.jso.technologies.tarot.scorer.common.bean.Player;
import com.jso.technologies.tarot.scorer.db.GameDatabaseRepository;

public class GameRepositoryCache {
	private static final Comparator<Game> BY_DATE_DESC = new ByDateDescComparator();
	private static SparseArray<Game> cache = new SparseArray<Game>();
	private static boolean hasAllGames;

	public static List<Game> getAll(Activity activity) {
		List<Game> allGames;
		if(hasAllGames) {
			allGames = new ArrayList<Game>();
			for(int i = 0; i < cache.size(); i++){
			    allGames.add(cache.valueAt(i));
			}
			Collections.sort(allGames, BY_DATE_DESC);
			return allGames;
		}
		else {
			allGames = new GameDatabaseRepository(activity).getAll();
			for(Game game : allGames) {
				game.fillPlayersIfNeeded(activity);
				cache.put(game.getId(), game);
			}
			hasAllGames = true;
		}
		return allGames;
	}

	public static Game getById(int id, Activity activity) {
		Game game = cache.get(id);
		if(game == null) {
			game = new GameDatabaseRepository(activity).getById(id);
			game.fillPlayersIfNeeded(activity);
			cache.put(game.getId(), game);
		}
		return game;
	}

	public static void save(Game game, Activity activity) {
		new GameDatabaseRepository(activity).save(game);
		cache.put(game.getId(), game);
	}

	public static void update(Game game, Activity activity) {
		new GameDatabaseRepository(activity).update(game);
		cache.put(game.getId(), game);
	}

	public static void delete(int id, Activity activity) {
		new GameDatabaseRepository(activity).delete(id);
		cache.remove(id);
	}
	
	public static List<Game> getGamesWithPlayer(Integer playerID, Activity activity) {
		return new GameDatabaseRepository(activity).getGamesWithPlayer(playerID);
	}
	
	/**
	 * Si on a toutes les parties ene cache alors on update le joueur modifié
	 * Si on a pas tout, on invalide les parties qui ont le joueur
	 * @param player
	 */
	public static void invalidateOrUpdateGamesWithPlayer(Player player) {
		List<Integer> gamesKeyToRemove = new ArrayList<Integer>();
		for(int i = 0; i < cache.size(); ++i) {
			Game game = cache.valueAt(i);
			if(game.getPlayers().contains(player)) {
				if(hasAllGames) {
					game.updatePlayer(player);
				}
				else {
					gamesKeyToRemove.add(cache.keyAt(i));
				}
			}
		}
		for(int key : gamesKeyToRemove) {
			cache.remove(key);
		}
	}

	/**
	 * Supression de toutes les parties et prise où le joueur a participé.
	 * Invalidation des stats des joueurs de toutes les parties supprimées
	 * @param player
	 */
	public static void deleteAllGamesWithPlayer(Player player, Activity activity) {
		GameDatabaseRepository gameRepo = new GameDatabaseRepository(activity);
		List<Game> gamesToDelete = gameRepo.getGamesWithPlayer(player.getId());
		for(Game game : gamesToDelete) {
			for(Player playerInGame : game.getPlayers()) {
				StatistiqueCalculatorCache.invalidate(playerInGame);
			}
			
			Integer gameId = game.getId();
			PriseRepositoryCache.deleteAllByGame(gameId, activity);
			gameRepo.delete(gameId);
			cache.remove(gameId);
		}
	}
	
	private static class ByDateDescComparator implements Comparator<Game> {

		@Override
		public int compare(Game arg0, Game arg1) {
			return arg1.getDate().compareTo(arg0.getDate());
		}
	}
}
