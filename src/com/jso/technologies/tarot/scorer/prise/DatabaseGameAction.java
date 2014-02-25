package com.jso.technologies.tarot.scorer.prise;

import java.util.Date;
import java.util.List;

import android.app.Activity;

import com.jso.technologies.tarot.scorer.common.bean.Game;
import com.jso.technologies.tarot.scorer.common.bean.Prise;
import com.jso.technologies.tarot.scorer.db.cache.GameRepositoryCache;
import com.jso.technologies.tarot.scorer.db.cache.PriseRepositoryCache;

public class DatabaseGameAction {
	private Activity activity;

	public DatabaseGameAction(PriseActivity activity) {
		this.activity = activity;
	}

	public void save(Game game) {
		if(! game.getPrises().isEmpty() && game.getId() == null) {
			game.sortPlayers();
			GameRepositoryCache.save(game, activity);
		}
		else if(! game.getPrises().isEmpty()) {
			game.setDate(new Date());
			game.sortPlayers();
			GameRepositoryCache.update(game, activity);
		}
		
		for(Prise prise : game.getPrises()) {
			if(prise.getId() == null) {
				prise.setGameId(game.getId());
				PriseRepositoryCache.save(prise, activity);
			}
		}
	}

	public void deletePrises(Game game, List<Integer> priseIndex) {
		for(Integer index : priseIndex) {
			Prise prise = game.getPrises().get(index);
			if(prise != null && prise.getId() != null) {
				PriseRepositoryCache.delete(prise.getId(), activity);
			}
			game.removePrise(index);
		}
		
		if(game.getId() != null && !game.getPrises().isEmpty()) {
			game.setDate(new Date());
			game.sortPlayers();
			GameRepositoryCache.update(game, activity);
		}
		else if(game.getId() != null) {
			GameRepositoryCache.delete(game.getId(), activity);
			game.setId(null);
		}
	}

}
