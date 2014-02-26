package com.jso.technologies.tarot.scorer.common.bean;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Parcel;
import android.os.Parcelable;

import com.jso.technologies.tarot.scorer.Utils.TarotRules;
import com.jso.technologies.tarot.scorer.common.enumeration.ChelemEnum;
import com.jso.technologies.tarot.scorer.common.enumeration.PetiteAuBoutEnum;
import com.jso.technologies.tarot.scorer.common.enumeration.PoigneeEnum;
import com.jso.technologies.tarot.scorer.common.enumeration.PriseEnum;
import com.jso.technologies.tarot.scorer.db.cache.PlayerRepositoryCache;

public class Prise implements Parcelable {
	private Integer id;
	private Integer gameId;
	private Player preneur;
	private Player appel;
	private PriseEnum prise;
	private Integer points;
	private Integer nbOudlers;
	private PetiteAuBoutEnum petiteAuBout;
	private PoigneeEnum poignee;
	private ChelemEnum chelem;
	private List<Player> miseres;
	
	public Prise() {
		prise = PriseEnum.PETITE;
		points = 0;
		nbOudlers = 0;
		petiteAuBout = PetiteAuBoutEnum.NON;
		poignee = PoigneeEnum.NON;
		chelem = ChelemEnum.NON;
		miseres = new ArrayList<Player>(1);
	}
	
	public Prise(Builder builder) {
		id = builder.getId();
		gameId = builder.getGameId();
		preneur = builder.getPreneur();
		appel = builder.getAppel();
		prise = builder.getPrise();
		points = builder.getPoints();
		nbOudlers = builder.getNbOudlers();
		petiteAuBout = builder.getPetiteAuBout();
		poignee = builder.getPoignee();
		chelem = builder.getChelem();
		miseres = builder.getMiseres();
	}

	public Prise(Parcel source) {
		readFromParcel(source);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setGameId(Integer gameId) {
		this.gameId = gameId;
	}

	public Integer getGameId() {
		return gameId;
	}

	public Player getPreneur() {
		return preneur;
	}
	
	public void setPreneur(Player preneur) {
		this.preneur = preneur;
	}
	
	public Player getAppel() {
		return appel;
	}
	
	public void setAppel(Player appel) {
		this.appel = appel;
	}
	
	public PriseEnum getPrise() {
		return prise;
	}
	
	public void setPrise(PriseEnum prise) {
		this.prise = prise;
	}
	
	public Integer getPoints() {
		return points;
	}
	
	public void setPoints(Integer points) {
		this.points = points;
	}
	
	public Integer getNbOudlers() {
		return nbOudlers;
	}
	
	public void setNbOudlers(Integer nbOudlers) {
		this.nbOudlers = nbOudlers;
	}
	
	public PetiteAuBoutEnum getPetiteAuBout() {
		return petiteAuBout;
	}
	
	public void setPetiteAuBout(PetiteAuBoutEnum petiteAuBout) {
		this.petiteAuBout = petiteAuBout;
	}
	
	public PoigneeEnum getPoignee() {
		return poignee;
	}
	
	public void setPoignee(PoigneeEnum poignee) {
		this.poignee = poignee;
	}
	
	public ChelemEnum getChelem() {
		return chelem;
	}
	
	public void setChelem(ChelemEnum chelem) {
		this.chelem = chelem;
	}
	
	public List<Player> getMiseres() {
		return miseres;
	}
	
	public void addMiseres(Player p) {
		miseres.add(p);
	}
	
	public void removeMiseres(Player p) {
		miseres.remove(p);
	}
	
	public boolean isSuccess() {
		return TarotRules.contractSucceed(nbOudlers, points);
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
		dest.writeInt(gameId != null ? 1 : 0);
		if(gameId != null) {
			dest.writeInt(gameId);
		}
		dest.writeInt(preneur.getId());
		dest.writeInt(appel != null ? 1 : 0);
		if(appel != null) {
			dest.writeInt(appel.getId());
		}
		dest.writeInt(prise.getId());
		dest.writeInt(points);
		dest.writeInt(nbOudlers);
		dest.writeInt(petiteAuBout.getId());
		dest.writeInt(poignee.getId());
		dest.writeInt(chelem.getId());
		dest.writeInt(miseres.size());
		for(Player p : miseres) {
			dest.writeInt(p.getId());
		}
	}
	
	public void readFromParcel(Parcel source){
		if(source.readInt() == 1) {
			id = source.readInt();
		}
		if(source.readInt() == 1) {
			gameId = source.readInt();
		}
		preneur = new Player(source.readInt(), null, null, true);
		if(source.readInt() == 1) {
			appel = new Player(source.readInt(), null, null, true);
		}
		prise = PriseEnum.fromValue(source.readInt());
		points = source.readInt();
		nbOudlers = source.readInt();
		petiteAuBout = PetiteAuBoutEnum.fromValue(source.readInt());
		poignee = PoigneeEnum.fromValue(source.readInt());
		chelem = ChelemEnum.fromValue(source.readInt());
		int misereSize = source.readInt();
		miseres = new ArrayList<Player>(misereSize);
		for(int i = 0; i < misereSize; ++i) {
			miseres.add(new Player(source.readInt(), null, null, true));
		}
	}

	public static final Parcelable.Creator<Prise> CREATOR =
			new Parcelable.Creator<Prise>() {

		@Override
		public Prise createFromParcel(Parcel source) {
			return new Prise(source);
		}

		@Override
		public Prise[] newArray(int size) {
			return new Prise[size];
		}

	};
	
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer("- PRISE -");				buffer.append("\n");
		buffer.append("Id : ");				buffer.append(id);				buffer.append("\n");
		buffer.append("GameId : ");			buffer.append(gameId);			buffer.append("\n");
		buffer.append("Preneur : ");		buffer.append(preneur);			buffer.append("\n");
		buffer.append("Appel : ");			buffer.append(appel);			buffer.append("\n");
		buffer.append("Prise : ");			buffer.append(prise);			buffer.append("\n");
		buffer.append("Points : ");			buffer.append(points);			buffer.append("\n");
		buffer.append("Bouts : ");			buffer.append(nbOudlers);		buffer.append("\n");
		buffer.append("Petite au bout : ");	buffer.append(petiteAuBout);	buffer.append("\n");
		buffer.append("Poignee : ");		buffer.append(poignee);			buffer.append("\n");
		buffer.append("Chelem : ");			buffer.append(chelem);			buffer.append("\n");
		buffer.append("Misères : ");		buffer.append(miseres);			buffer.append("\n");
		
		return buffer.toString();
	}
	
	public static Builder builder() {
		Builder builder = new Builder();
		builder.withPrise(PriseEnum.PETITE);
		builder.withPoints(0);
		builder.withNbOudlers(0);
		builder.withPetiteAuBout(PetiteAuBoutEnum.NON);
		builder.withPoignee(PoigneeEnum.NON);
		builder.withChelem(ChelemEnum.NON);

		return builder;
	}
	
	public static class Builder {
		private Integer id;
		private Integer gameId;
		private Player preneur;
		private Player appel;
		private PriseEnum prise;
		private Integer points;
		private Integer nbOudlers;
		private PetiteAuBoutEnum petiteAuBout;
		private PoigneeEnum poignee;
		private ChelemEnum chelem;
		private List<Player> miseres = new ArrayList<Player>(2);
		
		public Builder withId(Integer id) {
			this.id = id;
			return this;
		}
		
		public Builder withGameId(Integer gameId) {
			this.gameId = gameId;
			return this;
		}
		
		public Builder withPreneur(Player preneur) {
			this.preneur = preneur;
			return this;
		}
		
		public Builder withAppel(Player appel) {
			this.appel = appel;
			return this;
		}
		
		public Builder withPrise(PriseEnum prise) {
			this.prise = prise;
			return this;
		}
		
		public Builder withPoints(Integer points) {
			this.points = points;
			return this;
		}
		
		public Builder withNbOudlers(Integer nbOudlers) {
			this.nbOudlers = nbOudlers;
			return this;
		}
		
		public Builder withPetiteAuBout(PetiteAuBoutEnum petiteAuBout) {
			this.petiteAuBout = petiteAuBout;
			return this;
		}
		
		public Builder withPoignee(PoigneeEnum poignee) {
			this.poignee = poignee;
			return this;
		}
		
		public Builder withChelem(ChelemEnum chelem) {
			this.chelem = chelem;
			return this;
		}
		
		public Builder addMisere(Player player) {
			this.miseres.add(player);
			return this;
		}

		public Builder addAllMisere(List<Player> players) {
			if(players != null) {
				this.miseres.addAll(players);
			}
			return this;
		}
		
		public Prise build() {
			return new Prise(this);
		}

		public Integer getId() {
			return id;
		}

		public Integer getGameId() {
			return gameId;
		}

		public Player getPreneur() {
			return preneur;
		}

		public Player getAppel() {
			return appel;
		}

		public PriseEnum getPrise() {
			return prise;
		}

		public Integer getPoints() {
			return points;
		}

		public Integer getNbOudlers() {
			return nbOudlers;
		}

		public PetiteAuBoutEnum getPetiteAuBout() {
			return petiteAuBout;
		}

		public PoigneeEnum getPoignee() {
			return poignee;
		}

		public ChelemEnum getChelem() {
			return chelem;
		}
		
		public List<Player> getMiseres() {
			return miseres;
		}

	}

	public void fillPlayers(Activity activity) {
		if(preneur.getPseudo() == null) {
			preneur = PlayerRepositoryCache.getById(preneur.getId(), activity);
		}
		if(appel != null && appel.getPseudo() == null) {
			appel = PlayerRepositoryCache.getById(appel.getId(), activity);
		}
		
		List<Player> filledMiseres = new ArrayList<Player>(miseres.size());
		for(Player p : miseres) {
			filledMiseres.add(PlayerRepositoryCache.getById(p.getId(), activity));
		}
		miseres = filledMiseres;
	}
}
