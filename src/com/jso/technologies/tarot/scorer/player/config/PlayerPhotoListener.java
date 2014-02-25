package com.jso.technologies.tarot.scorer.player.config;

import com.jso.technologies.tarot.scorer.player.config.create.CreatePlayerActivity;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * Listener du seekBar de la page de configuration des joueurs pour une nouvelle partie
 * @author Jimmy
 *
 */
public class PlayerPhotoListener implements OnClickListener {
	private PlayersConfigActivity activity;

	public PlayerPhotoListener(PlayersConfigActivity playersConfigActivity) {
		this.activity = playersConfigActivity;
	}
	
	@Override
	public void onClick(View v) {
		int index = activity.getPlayerPhotos().indexOf(v);
		
		Intent myIntent = new Intent(activity.getApplicationContext(), CreatePlayerActivity.class);
        activity.startActivityForResult(myIntent, index);
	}

}
