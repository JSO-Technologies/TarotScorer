package com.jso.technologies.tarot.scorer.common.bean;

import java.util.Comparator;
import java.util.Map;

public class PlayerClassificationComparator implements Comparator<Player> {

	private Map<Player, Integer> score;

	public PlayerClassificationComparator(Map<Player, Integer> score) {
		this.score = score;
	}

	@Override
	public int compare(Player arg0, Player arg1) {
		return score.get(arg1).compareTo(score.get(arg0));
	}
	
}
