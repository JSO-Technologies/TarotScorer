package com.jso.technologies.tarot.scorer.player.config.create;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.jso.technologies.tarot.scorer.R;
import com.jso.technologies.tarot.scorer.Utils.Constantes;
import com.jso.technologies.tarot.scorer.Utils.DialogHandler;
import com.jso.technologies.tarot.scorer.Utils.ImageUtils;
import com.jso.technologies.tarot.scorer.common.bean.Player;
import com.jso.technologies.tarot.scorer.db.DatabaseHelper;
import com.jso.technologies.tarot.scorer.db.cache.PlayerRepositoryCache;
import com.jso.technologies.tarot.scorer.player.common.AbstractPlayerOperationActivity;

public class CreatePlayerListener implements OnClickListener {
	private AbstractPlayerOperationActivity activity;

	public CreatePlayerListener(AbstractPlayerOperationActivity playerActivity) {
		activity = playerActivity;
	}

	@Override
	public void onClick(View v) {
		String pseudo = activity.getPseudo().getText().toString().trim();

		//pseudo non correct
		if("".equals(pseudo)){
			String errorMessage = activity.getText(R.string.pseudo_empty).toString();
			Toast toast = Toast.makeText(activity.getApplicationContext(), errorMessage, Toast.LENGTH_SHORT);
			toast.show();
			return;
		}

		pseudo = Character.toUpperCase(pseudo.charAt(0)) + pseudo.substring(1);
		
		//pseudo existe deja
		if(PlayerRepositoryCache.exists(new String[]{DatabaseHelper.PLAYER_COLUMN_PSEUDO}, new String[]{pseudo}, activity)){
			String errorMessage = activity.getString(R.string.pseudo_exists);
			Toast toast = Toast.makeText(activity.getApplicationContext(), errorMessage, Toast.LENGTH_SHORT);
			toast.show();
			return;
		}
		
		//pseudo non autorise
		if(activity.getString(R.string.newPlayer).equals(pseudo)){
			String errorMessage = activity.getString(R.string.pseudo_not_authorised);
			Toast toast = Toast.makeText(activity.getApplicationContext(), errorMessage, Toast.LENGTH_SHORT);
			toast.show();
			return;
		}

		//affichage dialog d'attente
		DialogHandler.showWaitingDialog(activity);
		
		//recuperation de l'image affichee
		Bitmap bitmap = ImageUtils.getBitmap(activity.getImageView());

		//creation du joueur en base
		Player player = new Player(null, pseudo, ImageUtils.getByteArray(bitmap), true);
		PlayerRepositoryCache.save(player, activity);

		//transmission du joueur a l'activite appelante
		Intent intent = new Intent();
		intent.putExtra(Constantes.CREATED_PLAYER, player);
		activity.setResult(Activity.RESULT_OK, intent);
		activity.finish();
	}

}
