package com.jso.technologies.tarot.scorer.common.bean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.os.Parcel;
import android.os.Parcelable;

import com.jso.technologies.tarot.scorer.Utils.TarotRules;
import com.jso.technologies.tarot.scorer.db.cache.PlayerRepositoryCache;
import com.jso.technologies.tarot.scorer.db.cache.PriseRepositoryCache;

public class Game implements Parcelable {
	private Integer id;
	private Date date;
	private List<Player> players;
	private List<Prise> prises;
	private Map<Player, Integer> score;
	private Map<Prise, Map<Player, Integer>> scoreByPrise;
	private Map<Player, Integer> classification;

	public Game() {
	}
	
	public Game(Parcel source) {
		readFromParcel(source);
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Map<Player, Integer> getClassification() {
		if(classification == null) {
			classification = new HashMap<Player, Integer>();
		}
		return classification;
	}

	public List<Player> getPlayers() {
		if(players == null) {
			players = new ArrayList<Player>();
		}
		return players;
	}

	public List<Prise> getPrises() {
		if(prises == null) {
			prises = new ArrayList<Prise>();
		}
		return prises;
	}

	public Map<Player, Integer> getScore() {
		if(score == null) {
			score = new HashMap<Player, Integer>();
			for(Player player : players) {
				score.put(player, 0);
			}
		}
		return score;
	}

	public Map<Prise, Map<Player, Integer>> getScoreByPrise() {
		if(scoreByPrise == null) {
			scoreByPrise = new HashMap<Prise, Map<Player,Integer>>();
		}
		return scoreByPrise;
	}

	/**
	 * Ajout d'une prise dans la partie avec calcul des points
	 * @param currentPrise
	 */
	public void addPrise(Prise currentPrise) {
		addPrise(currentPrise, getPrises().size());
	}
	
	/**
	 * Ajout a une place particuliere
	 * @param prise
	 * @param index
	 */
	public void addPrise(Prise currentPrise, int index) {
		
		Map<Player, Integer> newPrisePoints = TarotRules.getPriseScorePoints(currentPrise, players);
		getPrises().add(index, currentPrise);
		for(Player player : newPrisePoints.keySet()) {
			getScore().put(player, getScore().get(player) + newPrisePoints.get(player));
		}
		getScoreByPrise().put(currentPrise, newPrisePoints);
	}
	
	/**
	 * Suppression d'une prise et mise a jour du total
	 * @param index
	 */
	public void removePrise(int index) {
		Prise prise = getPrises().get(index);
		Map<Player, Integer> scoreToRemove = getScoreByPrise().get(prise);
		
		for(Player player : getPlayers()) { 
			Integer newScore = getScore().get(player) - scoreToRemove.get(player);
			getScore().put(player, newScore);
		}
		
		getScoreByPrise().remove(prise);
		getPrises().remove(prise);
	}
	
	/**
	 * Tri les joueurs suivant les scores
	 */
	public void sortPlayers() {
		classification = new HashMap<Player, Integer>();
		List<Player> playersTmp = new ArrayList<Player>();
		playersTmp.addAll(getPlayers());
		Collections.sort(playersTmp, new PlayerClassificationComparator(score));
		
		for(int i = 0; i < playersTmp.size(); ++i) {
			Player player = playersTmp.get(i);
			Player lastPlayer = null;
			if(i > 0){
				lastPlayer = playersTmp.get(i - 1);
			}

			if(lastPlayer != null && score.get(lastPlayer).equals(score.get(player))) {
				classification.put(player, classification.get(lastPlayer));
			}
			else {
				classification.put(player, i + 1);
			}
			
		}
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(id != null ? 1 : 0);
		if(id != null) {
			dest.writeInt(id);
		}
		
		dest.writeInt(getPlayers().size());
		for(Player player : getPlayers()) {
			dest.writeInt(player.getId());
		}
		
		dest.writeInt(getPrises().size());
		for(Prise prise : getPrises()) {
			prise.writeToParcel(dest, flags);
		}
		
		for(Player player : getPlayers()) {
			dest.writeInt(getScore().get(player));
		}
		
		for(Prise prise : getPrises()) {
			Map<Player, Integer> currentScore = getScoreByPrise().get(prise);
			for(Player player : getPlayers()) {
				dest.writeInt(currentScore.get(player));
			}
		}
	}
	
	public void readFromParcel(Parcel source){
		if(source.readInt() == 1) {
			id = source.readInt();
		}
		
		int nbPlayers = source.readInt();
		for(int i = 0; i < nbPlayers; ++i) {
			getPlayers().add(new Player(source.readInt(), null, null, true));
		}
		
		int nbPrise = source.readInt();
		for(int i = 0; i < nbPrise; ++i) {
			getPrises().add(new Prise(source));
		}
		for(Prise prise : getPrises()) {
			for(Player player : getPlayers()) {
				if(player.getId().equals(prise.getPreneur().getId())) {
					prise.setPreneur(player);
				}
				if(prise.getAppel() != null && player.getId().equals(prise.getAppel().getId())) {
					prise.setAppel(player);
				}
				
			}

			List<Player> miseres = new ArrayList<Player>();
			for(Player misereux : prise.getMiseres()) {
				for(Player player : getPlayers()) {					
					if(player.getId().equals(misereux.getId())) {
						miseres.add(player);
						break;
					}
				}
			}
			prise.getMiseres().clear();
			prise.getMiseres().addAll(miseres);
		}
		
		for(Player player : getPlayers()) {
			getScore().put(player, source.readInt());
		}
		
		for(Prise prise : getPrises()) {
			Map<Player, Integer> currentScore = new HashMap<Player, Integer>();
			for(Player player : getPlayers()) {
				currentScore.put(player, source.readInt());
			}
			
			getScoreByPrise().put(prise, currentScore);
		}
	}

	public static final Parcelable.Creator<Game> CREATOR =
			new Parcelable.Creator<Game>() {

		@Override
		public Game createFromParcel(Parcel source) {
			return new Game(source);
		}

		@Override
		public Game[] newArray(int size) {
			return new Game[size];
		}

	};

	public void fillPlayersIfNeeded(Activity activity) {
		for(Player player : getPlayers()) {
			if(player.getPseudo() == null) {
				Player playerFromDB = PlayerRepositoryCache.getById(player.getId(), activity);
				player.setPseudo(playerFromDB.getPseudo());
				player.setBitmap(playerFromDB.getBitmap());
				player.setEnabled(playerFromDB.isEnabled());
			}
		}
	}

	/**
	 * Lors d'un update dr joueur on met à jour le joueur dans toutes les parties en cache
	 * @param player
	 */
	public void updatePlayer(Player player) {
		for(Player currentPlayer : getPlayers()) {
			if(currentPlayer.getId().equals(player.getId())) {
				currentPlayer.setPseudo(player.getPseudo());
				currentPlayer.setBitmap(player.getBitmap());
				currentPlayer.setEnabled(player.isEnabled());
			}
		}
	}

	/**
	 * Formattage HTML joueur : score 
	 * @return
	 */
	public String getHtmlScores() {
		StringBuilder builder = new StringBuilder("<br/>");
		for(Player player : getPlayers()) {
			addScoreToBuilder(builder, player);
		}
		return builder.toString();
	}
	
	private void addScoreToBuilder(StringBuilder builder, Player player) {
		builder.append("<b>");
		builder.append(player.getPseudo());
		builder.append("</b> : ");
		builder.append(getScore().get(player));
		builder.append("<br/>");
	}

	public void fillPrisesIfNeeded(Activity activity) {
		if(getPrises().isEmpty()) {
			score = null;
			getScoreByPrise().clear();
			for(Prise prise : PriseRepositoryCache.getAllByGame(id, activity)) {
				addPrise(prise);
			}
		}
	}

}
