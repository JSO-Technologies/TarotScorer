package com.jso.technologies.tarot.scorer.player.config.modify;

import android.content.Intent;
import android.os.Bundle;

import com.jso.technologies.tarot.scorer.Utils.Constantes;
import com.jso.technologies.tarot.scorer.common.bean.Player;
import com.jso.technologies.tarot.scorer.player.common.AbstractPlayerOperationActivity;
import com.jso.technologies.tarot.scorer.player.common.CancelCreatePlayerListener;
import com.jso.technologies.tarot.scorer.player.common.GetPhotoListener;

public class ModifyPlayerActivity extends AbstractPlayerOperationActivity {
	private Player currentPlayer;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//listener
		imageView.setOnClickListener(new GetPhotoListener(this));
		okButton.setOnClickListener(new ModifyPlayerListener(this));
		cancelButton.setOnClickListener(new CancelCreatePlayerListener(this));
		
		//recuperation du joueur a modifier
		Intent passedIntent = getIntent();
		currentPlayer = (Player) passedIntent.getExtras().get(Constantes.SELECTED_PLAYERS);
		
		//initialisation des champs
		pseudo.setText(currentPlayer.getPseudo());
		imageView.setImageBitmap(currentPlayer.getBitmap());
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}

}
