package com.jso.technologies.tarot.scorer.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jso.technologies.tarot.scorer.common.bean.Player;
import com.jso.technologies.tarot.scorer.common.bean.Prise;
import com.jso.technologies.tarot.scorer.common.enumeration.ChelemEnum;
import com.jso.technologies.tarot.scorer.common.enumeration.PetiteAuBoutEnum;
import com.jso.technologies.tarot.scorer.common.enumeration.PoigneeEnum;
import com.jso.technologies.tarot.scorer.common.enumeration.PriseEnum;

public class TarotRules {

	/**
	 * M�thode calculant si le contrat est r�ussi
	 * 
	 * @param nbOudlers
	 * @param points
	 * @return
	 */
	public static boolean contractSucceed(Integer nbOudlers, Integer points) {
		return points >= getContractPointsToDo(nbOudlers);
	}

	/**
	 * M�thode retournant les points � r�aliser dans le contrat
	 * 
	 * @param nbOudlers
	 * @return
	 */
	public static Integer getContractPointsToDo(Integer nbOudlers){
		Integer pointLimite;

		switch (nbOudlers) {
		case 0:
			pointLimite = Constantes.NO_OUDLER_POINTS;
			break;

		case 1:
			pointLimite = Constantes.ONE_OUDLER_POINTS;
			break;

		case 2:
			pointLimite = Constantes.TWO_OUDLERS_POINTS;
			break;

		case 3:
			pointLimite = Constantes.THREE_OUDLERS_POINTS;
			break;

		default:
			pointLimite = Constantes.TOTAL_POINTS;
			break;
		}

		return pointLimite;
	}

	/**
	 * M�thode renvoyant le coefficient multiplicateur suivant le contrat
	 * 
	 * @param priseEnum
	 * @return
	 */
	public static Integer getMultiplyingFactor(PriseEnum priseEnum){
		Integer coef;

		switch (priseEnum) {
		case PETITE:
			coef = Constantes.MULTIPLYING_FACTOR_PETITE;
			break;

		case GARDE:
			coef = Constantes.MULTIPLYING_FACTOR_GARDE;
			break;

		case GARDE_SANS:
			coef = Constantes.MULTIPLYING_FACTOR_GARDE_SANS;
			break;

		case GARDE_CONTRE:
			coef = Constantes.MULTIPLYING_FACTOR_GARDE_CONTRE;
			break;

		default:
			coef = 1;
			break;
		}

		return coef;
	}

	/**
	 * M�thode renvoyant le bonus de points en fonction de la poign�e
	 * 
	 * @param poigneeEnum
	 * @return
	 */
	public static Integer getPoigneeBonus(PoigneeEnum poigneeEnum){
		Integer bonus;

		switch (poigneeEnum) {
		case NON:
			bonus = Constantes.NO_POIGNEE_BONUS;
			break;

		case SIMPLE:
			bonus = Constantes.SIMPLE_POIGNEE_BONUS;
			break;

		case DOUBLE:
			bonus = Constantes.DOUBLE_POIGNEE_BONUS;
			break;

		case TRIPLE:
			bonus = Constantes.TRIPLE_POIGNEE_BONUS;
			break;

		default:
			bonus = 0;
			break;
		}

		return bonus;
	}

	/**
	 * M�thode calculant le bonus de petite au bout
	 * 
	 * @param petiteAuBout
	 * @param priseEnum
	 * @return
	 */
	public static Integer getPetiteAuBoutBonus(PetiteAuBoutEnum petiteAuBout, PriseEnum priseEnum) {
		Integer baseBonus;

		switch (petiteAuBout) {
		case NON:
			baseBonus = Constantes.NO_PETITE_AU_BOUT_BONUS;
			break;

		case OUI:
			baseBonus = Constantes.PETITE_AU_BOUT_BONUS;
			break;

		case PERDU:
			baseBonus = Constantes.LOST_PETITE_AU_BOUT_BONUS;
			break;

		default:
			baseBonus = 0;
			break;
		}

		return baseBonus * getMultiplyingFactor(priseEnum);
	}


	/**
	 * M�thode renvoyant le bonus de chelem
	 * 
	 * @param chelemEnum
	 * @param contratSucceed 
	 * @return
	 */
	public static Integer getChelemBonus(ChelemEnum chelemEnum, boolean contratSucceed) {
		switch (chelemEnum) {
		case NON:
			return Constantes.NO_CHELEM_BONUS;

		case NOT_ANNOUNCED:
			return Constantes.NOT_ANNOUNCED_CHELEM_BONUS;

		case ANNOUNCED:
			if(contratSucceed) {
				return Constantes.ANNOUNCED_CHELEM_BONUS;
			}
			else {
				return Constantes.LOST_ANNOUNCED_CHELEM_BONUS;
			}

		case LOST:
			return Constantes.LOST_ANNOUNCED_CHELEM_BONUS;

		default:
			return 0;
		}
	}

	/**
	 * M�thode calculant les points pour chaque joueur pour une prise
	 * 
	 * @param currentPrise
	 * @param players
	 * @return
	 */
	public static Map<Player, Integer> getPriseScorePoints(Prise currentPrise, List<Player> players) {
		//calcul des points de base
		Integer points = Math.abs(currentPrise.getPoints() - getContractPointsToDo(currentPrise.getNbOudlers()));
		points += 25;
		points *= getMultiplyingFactor(currentPrise.getPrise());

		//prime poignee
		points += getPoigneeBonus(currentPrise.getPoignee());

		//positif ou n�gatif
		boolean contratSucceed = contractSucceed(currentPrise.getNbOudlers(), currentPrise.getPoints());
		if(!contratSucceed) {
			points = -points;
		}
		
		//prime chelem
		points += getChelemBonus(currentPrise.getChelem(), contratSucceed);

		//prime petite au bout
		points += getPetiteAuBoutBonus(currentPrise.getPetiteAuBout(), currentPrise.getPrise());

		//calcul final pour chaque joueur
		Map<Player, Integer> currentPriseScore = new HashMap<Player, Integer>();
		for(Player player : players) {

			if(player.equals(currentPrise.getPreneur()) && player.equals(currentPrise.getAppel())) {
				currentPriseScore.put(player, 4 * points);
			}
			else if(player.equals(currentPrise.getPreneur())) {
				if(players.size() == 4) {
					currentPriseScore.put(player, 3 * points);
				}
				else {
					currentPriseScore.put(player, 2 * points);
				}
			}
			else if(player.equals(currentPrise.getAppel())) {
				currentPriseScore.put(player, points);
			}
			else {
				currentPriseScore.put(player, -points);
			}

		}

		return currentPriseScore;
	}

}
