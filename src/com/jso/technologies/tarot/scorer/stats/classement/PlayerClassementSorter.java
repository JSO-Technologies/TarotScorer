package com.jso.technologies.tarot.scorer.stats.classement;

import java.util.Comparator;
import java.util.Map;

import com.jso.technologies.tarot.scorer.Utils.StatistiqueCalculator;
import com.jso.technologies.tarot.scorer.common.bean.Player;

public class PlayerClassementSorter implements Comparator<Player> {

	private final Map<Player, StatistiqueCalculator> calculators;

	public PlayerClassementSorter(Map<Player, StatistiqueCalculator> calculators) {
		this.calculators = calculators;
	}

	@Override
	public int compare(Player arg0, Player arg1) {
		StatistiqueCalculator calculator1 = calculators.get(arg0);
		StatistiqueCalculator calculator2 = calculators.get(arg1);
		
		Double averagePlace1 = calculator1.getAveragePlace();
		Double averagePlace2 = calculator2.getAveragePlace();
		if(!averagePlace1.equals(averagePlace2)) {
			return averagePlace1.compareTo(averagePlace2);
		}
		
		Integer nbWonGame1 = calculator1.getNbWinningGame();
		Integer nbWonGame2 = calculator2.getNbWinningGame();
		if(!nbWonGame1.equals(nbWonGame2)) {
			return nbWonGame2.compareTo(nbWonGame1);
		}
		
		Integer nbWonPrise1 = calculator1.getNbWonPrise();
		Integer nbWonPrise2 = calculator2.getNbWonPrise();
		if(!nbWonPrise1.equals(nbWonPrise2)) {
			return nbWonPrise2.compareTo(nbWonPrise1);
		}
		
		Integer nbLostPrise1 = calculator1.getNbLostPrise();
		Integer nbLostPrise2 = calculator2.getNbLostPrise();
		return nbLostPrise1.compareTo(nbLostPrise2);
	}

}
