package com.jso.technologies.tarot.scorer.db.cache;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.app.Activity;
import android.util.SparseArray;

import com.jso.technologies.tarot.scorer.common.bean.Player;
import com.jso.technologies.tarot.scorer.db.DatabaseHelper;
import com.jso.technologies.tarot.scorer.db.PlayerDatabaseRepository;

public class PlayerRepositoryCache {
	private static final Comparator<Player> BY_PSEUDO = new ByPseudoComparator();
	private static boolean hasAllPlayer;
	private static boolean hasAllInativePlayer;
	private static SparseArray<Player> activePlayerCache = new SparseArray<Player>();
	private static SparseArray<Player> inactivePlayerCache = new SparseArray<Player>();
	
	private PlayerRepositoryCache(){}
	
	public static List<Player> getAll(Activity activity) {
		if(hasAllPlayer) {
			List<Player> playerList = new ArrayList<Player>();
			for(int i = 0; i < activePlayerCache.size(); ++i) {
				playerList.add(activePlayerCache.valueAt(i));
			}
			Collections.sort(playerList, BY_PSEUDO);
			return playerList;
		}
		
		List<Player> playerList = new PlayerDatabaseRepository(activity).getAll();
		for(Player player : playerList) {
			activePlayerCache.put(player.getId(), player);
		}
		hasAllPlayer = true;
		return playerList;
	}
	
	public static List<Player> getAllInactive(Activity activity) {
		if(hasAllInativePlayer) {
			List<Player> playerList = new ArrayList<Player>();
			for(int i = 0; i < inactivePlayerCache.size(); ++i) {
				playerList.add(inactivePlayerCache.valueAt(i));
			}
			Collections.sort(playerList, BY_PSEUDO);
			return playerList;
		}
		
		List<Player> playerList = new PlayerDatabaseRepository(activity).getAllInactive();
		for(Player player : playerList) {
			inactivePlayerCache.put(player.getId(), player);
		}
		hasAllInativePlayer = true;
		return playerList;
	}
	
	public static Player getById(Integer id, Activity activity) {
		Player player = activePlayerCache.get(id);
		if(player != null) {
			return player;
		}
		
		player = inactivePlayerCache.get(id);
		if(player != null) {
			return player;
		}
		
		PlayerDatabaseRepository playerRepo = new PlayerDatabaseRepository(activity);
		player = playerRepo.getById(id);
		if(player != null) {
			if(player.isEnabled()) {
				activePlayerCache.put(player.getId(), player);
			}
			else {
				inactivePlayerCache.put(player.getId(), player);
			}
		}
		
		return player;
	}
	
	public static List<Player> getByIdList(List<Integer> idList, Activity activity) {
		List<Player> players = new ArrayList<Player>(idList.size());
		for(Integer id : idList) {
			Player player = activePlayerCache.get(id);
			if(player != null) {
				players.add(player);
				continue;
			}
			
			player = inactivePlayerCache.get(id);
			if(player != null) {
				players.add(player);
				continue;
			}
			
			PlayerDatabaseRepository playerRepo = new PlayerDatabaseRepository(activity);
			player = playerRepo.getById(id);
			if(id != null) {
				if(player.isEnabled()) {
					activePlayerCache.put(player.getId(), player);
				}
				else {
					inactivePlayerCache.put(player.getId(), player);
				}
				players.add(player);
				continue;
			}
		}		
		return players;
	}

	public static void save(Player player, Activity activity) {
		PlayerDatabaseRepository playerRepo = new PlayerDatabaseRepository(activity);
		playerRepo.save(player);
		activePlayerCache.put(player.getId(), player);
	}

	public static void update(Player player, Activity activity) {
		PlayerDatabaseRepository playerRepo = new PlayerDatabaseRepository(activity);
		playerRepo.update(player);
		if(player.isEnabled()) {
			inactivePlayerCache.remove(player.getId());
			activePlayerCache.put(player.getId(), player);
		}
		else {
			activePlayerCache.remove(player.getId());
			inactivePlayerCache.put(player.getId(), player);
		}
		GameRepositoryCache.invalidateOrUpdateGamesWithPlayer(player);
	}

	public static void delete(Integer id, Activity activity) {
		PlayerDatabaseRepository playerRepo = new PlayerDatabaseRepository(activity);
		playerRepo.delete(id);
		inactivePlayerCache.remove(id);
		activePlayerCache.remove(id);
	}
	
	public static boolean exists(String[] columns, String[] values, Activity activity) {
		return new PlayerDatabaseRepository(activity).exists(DatabaseHelper.PLAYERS_TABLE_NAME, columns, values);
	}
	
	private static class ByPseudoComparator implements Comparator<Player> {

		@Override
		public int compare(Player arg0, Player arg1) {
			return arg0.getPseudo().compareTo(arg1.getPseudo());
		}

	}

}
