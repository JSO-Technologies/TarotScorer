package com.jso.technologies.tarot.scorer.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import android.app.Activity;

import com.jso.technologies.tarot.scorer.common.bean.Game;
import com.jso.technologies.tarot.scorer.common.bean.Player;
import com.jso.technologies.tarot.scorer.common.bean.Prise;
import com.jso.technologies.tarot.scorer.common.enumeration.PriseEnum;
import com.jso.technologies.tarot.scorer.db.cache.GameRepositoryCache;

public class StatistiqueCalculator {
	private List<Game> games;
	private Player player;

	private Integer nbGamePlayed;
	private Integer nbWinningGame;
	private Double nbWinningGamePercentage;
	private Double averagePlace;
	private Integer nbPrise;
	private Integer nbPetite;
	private Integer nbGarde;
	private Integer nbGardeSans;
	private Integer nbGardeContre;
	private Double nbPetitePercentage;
	private Double nbGardePercentage;
	private Double nbGardeSansPercentage;
	private Double nbGardeContrePercentage;
	private Double averagePriseByGame;
	private PriseEnum theMostWonPriseType;
	private Integer maxPointDoneOverContract;
	private Double averagePointDoneOverContract;
	private PriseEnum theMostLostPriseType;
	private Integer maxPointDoneUnderContract;
	private Double averagePointDoneUnderContract;
	private Integer nbWonPrise;
	private Integer nbLostPrise;
	private Map<PriseEnum, Integer> wonPriseCount;
	private Map<PriseEnum, Integer> lostPriseCount;
	
	public StatistiqueCalculator(List<Game> games, Player player) {
		this.games = games;
		this.player = player;
		compute();
	}

	public StatistiqueCalculator(Activity activity, Player player) {

		this.games = GameRepositoryCache.getGamesWithPlayer(player.getId(), activity);
		for(Game game : games) {
			game.fillPrisesIfNeeded(activity);
		}
		this.player = player;
		compute();
	}

	/**
	 * Retourne le joueur correspondant au joueur en cours
	 * @param playerList
	 * @return
	 */
	private Player getGamePlayer(List<Player> playerList) {
		for(Player p : playerList) {
			if(p.getId().equals(player.getId())) {
				return p;
			}
		}

		return null;
	}

	protected void compute() {
		nbGamePlayed = games.size();
		
		wonPriseCount = new HashMap<PriseEnum, Integer>();
		wonPriseCount.put(PriseEnum.PETITE, 0);
		wonPriseCount.put(PriseEnum.GARDE, 0);
		wonPriseCount.put(PriseEnum.GARDE_SANS, 0);
		wonPriseCount.put(PriseEnum.GARDE_CONTRE, 0);
		
		lostPriseCount = new HashMap<PriseEnum, Integer>();
		lostPriseCount.put(PriseEnum.PETITE, 0);
		lostPriseCount.put(PriseEnum.GARDE, 0);
		lostPriseCount.put(PriseEnum.GARDE_SANS, 0);
		lostPriseCount.put(PriseEnum.GARDE_CONTRE, 0);

		int sumAveragePlace = 0;
		nbPrise = 0;
		nbWinningGame = 0;
		nbPetite = 0;
		nbGarde = 0;
		nbGardeSans = 0;
		nbGardeContre = 0;
		maxPointDoneOverContract = 0;
		maxPointDoneUnderContract = 0;
		int sumPointDoneOverContract = 0;
		int sumPointDoneUnderContract = 0;
		nbWonPrise = 0;
		nbLostPrise = 0;
		for(Game game : games) {
			Player playerInGame = getGamePlayer(game.getPlayers());

			Integer classification = game.getClassification().get(playerInGame);

			sumAveragePlace += classification;

			if(classification == 1) {
				nbWinningGame ++;
			}

			for(Prise prise : game.getPrises()) {
				if(prise.getPreneur().equals(playerInGame)) {
					nbPrise ++;
					PriseEnum priseType = prise.getPrise();
					switch (priseType) {
					case PETITE:
						nbPetite ++;
						break;
						
					case GARDE:
						nbGarde ++;
						break;
						
					case GARDE_SANS:
						nbGardeSans ++;
						break;
						
					case GARDE_CONTRE:
						nbGardeContre ++;
						break;

					default:
						break;
					}
					
					if(prise.isSuccess()) {
						nbWonPrise ++;
						Integer point = prise.getPoints() - TarotRules.getContractPointsToDo(prise.getNbOudlers());
						sumPointDoneOverContract += point;
						if(point > maxPointDoneOverContract) {
							maxPointDoneOverContract = point;
						}
						wonPriseCount.put(priseType, wonPriseCount.get(priseType) + 1);
					}
					else {
						nbLostPrise ++;
						Integer point = TarotRules.getContractPointsToDo(prise.getNbOudlers()) - prise.getPoints();
						sumPointDoneUnderContract += point;
						if(point > maxPointDoneUnderContract) {
							maxPointDoneUnderContract = point;
						}
						lostPriseCount.put(priseType, lostPriseCount.get(priseType) + 1);
					}
				}
			}

		}

		nbWinningGamePercentage = nbGamePlayed == 0 ? 0.0 : nbWinningGame * 100.0 / nbGamePlayed;
		averagePlace = sumAveragePlace > 0 ? Double.valueOf(sumAveragePlace) / games.size() : 5.0;
		nbPetitePercentage = nbPetite == 0 ? 0.0 : nbPetite * 100.0 / nbPrise;
		nbGardePercentage = nbGarde == 0 ? 0.0 : nbGarde  * 100.0 / nbPrise;
		nbGardeSansPercentage = nbGardeSans == 0 ? 0.0 : nbGardeSans * 100.0 / nbPrise;
		nbGardeContrePercentage = nbGardeContre == 0 ? 0.0 : nbGardeContre  * 100.0 / nbPrise;
		averagePriseByGame = nbGamePlayed == 0 ? 0.0 : Double.valueOf(nbPrise) / nbGamePlayed;
		
		int nbWon = -1;
		for(Entry<PriseEnum, Integer> entry : wonPriseCount.entrySet()) {
			if(entry.getValue() > 0 && entry.getValue() > nbWon) {
				theMostWonPriseType = entry.getKey();
				nbWon = entry.getValue();
			}
		}
		
		int nbLost = -1;
		for(Entry<PriseEnum, Integer> entry : lostPriseCount.entrySet()) {
			if(entry.getValue() > 0 && entry.getValue() > nbLost) {
				theMostLostPriseType = entry.getKey();
				nbLost = entry.getValue();
			}
		}
		
		averagePointDoneOverContract = nbWonPrise == 0 ? 0.0 : Double.valueOf(sumPointDoneOverContract) / nbWonPrise;
		averagePointDoneUnderContract = nbLostPrise == 0 ? 0.0 : Double.valueOf(sumPointDoneUnderContract) / nbLostPrise;
	}

	/**
	 * Nombre de partie jou�e
	 * @param activity
	 * @param playerID
	 * @return
	 */
	public Integer getNbGamePlayed() {
		return nbGamePlayed;
	}

	/**
	 * Nombre de jeux gagn�s
	 * @param activity
	 * @param playerID
	 * @param nbGamePlayed
	 * @return
	 */
	public Integer getNbWinningGame() {
		return nbWinningGame;
	}
	
	/**
	 * Pourcentage de jeux gagn�s
	 * @return
	 */
	public Double getNbWinningGamePercentage() {
		return nbWinningGamePercentage;
	}

	/**
	 * Place moyenne aux scores totals
	 * @param statPlayerActivity
	 * @param playerID
	 * @return
	 */
	public Double getAveragePlace() {
		return averagePlace;
	}

	/**
	 * Nombre de prises r�alis�es
	 * @return
	 */
	public Integer getNbPrise() {
		return nbPrise;
	}

	/**
	 * Nombre de petite prise
	 * @return
	 */
	public Integer getNbPetite() {
		return nbPetite;
	}

	/**
	 * Nombre de garde prise
	 * @return
	 */
	public Integer getNbGarde() {
		return nbGarde;
	}

	/**
	 * Nombre de garde sans prise
	 * @return
	 */
	public Integer getNbGardeSans() {
		return nbGardeSans;
	}

	/**
	 * Nombre de garde contre prise
	 * @return
	 */
	public Integer getNbGardeContre() {
		return nbGardeContre;
	}

	/**
	 * Pourcentage de petite prise
	 * @return
	 */
	public Double getNbPetitePercentage() {
		return nbPetitePercentage;
	}

	/**
	 * Pourcentage de garde prise
	 * @return
	 */
	public Double getNbGardePercentage() {
		return nbGardePercentage;
	}

	/**
	 * Nombre de garde sans prise
	 * @return
	 */
	public Double getNbGardeSansPercentage() {
		return nbGardeSansPercentage;
	}

	/**
	 * Nombre de garde contre prise
	 * @return
	 */
	public Double getNbGardeContrePercentage() {
		return nbGardeContrePercentage;
	}

	/**
	 * Moyenne de prise par partie
	 * @return
	 */
	public Double getAveragePriseByGame() {
		return averagePriseByGame;
	}

	/**
	 * Type de prise le plus gagn�e
	 * @return
	 */
	public PriseEnum getTheMostWonPriseType() {
		return theMostWonPriseType;
	}

	/**
	 * Type de prise le plus perdue 
	 * @return
	 */
	public PriseEnum getTheMostLostPriseType() {
		return theMostLostPriseType;
	}

	/**
	 * Prises faites de combien au max
	 * @return
	 */
	public Integer getMaxPointDoneOverContract() {
		return maxPointDoneOverContract;
	}

	/**
	 * Prises faites de combien en moyenne
	 * @return
	 */
	public Double getAveragePointDoneOverContract() {
		return averagePointDoneOverContract;
	}

	/**
	 * Prises perdues de combien au max
	 * @return
	 */
	public Integer getMaxPointDoneUnderContract() {
		return maxPointDoneUnderContract;
	}

	/**
	 * Prises perdues de combien en moyenne 
	 * @return
	 */
	public Double getAveragePointDoneUnderContract() {
		return averagePointDoneUnderContract;
	}

	/**
	 * Nombre de prises reussies
	 * @return
	 */
	public Integer getNbWonPrise() {
		return nbWonPrise;
	}

	/**
	 * Nombre de prises perdues
	 * @return
	 */
	public Integer getNbLostPrise() {
		return nbLostPrise;
	}

	public Map<PriseEnum, Integer> getWonPriseCount() {
		return wonPriseCount;
	}

	public Map<PriseEnum, Integer> getLostPriseCount() {
		return lostPriseCount;
	}
	
}
