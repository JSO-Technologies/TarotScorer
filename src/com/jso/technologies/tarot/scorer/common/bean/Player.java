package com.jso.technologies.tarot.scorer.common.bean;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import com.jso.technologies.tarot.scorer.Utils.ImageUtils;

/**
 * Bean Player
 * @author Jimmy
 *
 */
public class Player implements Parcelable {
	private Integer id;
	private String pseudo;
	private byte[] image;
	private Bitmap bitmap;
	private boolean enabled;

	public Player(Parcel source) {
		readFromParcel(source);
	}

	public Player(Integer id, String pseudo, byte[] image, boolean enabled) {
		this.id = id;
		this.pseudo = pseudo;
		this.image = image;
		this.enabled = enabled;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getPseudo() {
		return pseudo;
	}
	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public byte[] getImage() {
		if(image == null){
			image = ImageUtils.getByteArray(bitmap);
		}
		return image;
	}
	public void setImage(byte[] image) {
		this.image = image;
		this.bitmap = null;
	}
	public Bitmap getBitmap() {
		if(bitmap == null){
			bitmap = ImageUtils.getBitmap(image);
		}
		return bitmap;
	}
	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
		this.image = null;
	}

	@Override
	public String toString() {
		return pseudo;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(id);
		dest.writeString(pseudo);
		dest.writeInt(getImage().length);
		dest.writeByteArray(getImage());
		dest.writeInt(enabled ? 1 : 0);
	}

	public void readFromParcel(Parcel source){
		id = source.readInt();
		pseudo = source.readString();
		image = new byte[source.readInt()] ;
		source.readByteArray(image);
		enabled = source.readInt() == 1 ? true : false;
	}

	public static final Parcelable.Creator<Player> CREATOR =
			new Parcelable.Creator<Player>() {

		@Override
		public Player createFromParcel(Parcel source) {
			return new Player(source);
		}

		@Override
		public Player[] newArray(int size) {
			return new Player[size];
		}

	};
	
	@Override
	public boolean equals(Object o) {
		if(o == null) {
			return false;
		}
		if(o == this) {
			return true;
		}
		if(!(o instanceof Player)) {
			return false;
		}
		
		Player toCompare = (Player)o;
		if(id != null && id.equals(toCompare.getId())) {
			return true;
		}
		else {
			return id == null && toCompare.getId() == null && pseudo.equals(toCompare.getPseudo());
		}
	};

}
