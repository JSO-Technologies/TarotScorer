package com.jso.technologies.tarot.scorer.home;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.jso.technologies.tarot.scorer.R;
import com.jso.technologies.tarot.scorer.Utils.DialogHandler;
import com.jso.technologies.tarot.scorer.common.enumeration.PriseEnum;

/**
 * Activity : Page d'accueil
 */
public class HomePageActivity extends Activity {

	private TextView newGame;
	private TextView playerList;
	private TextView classement;
	private TextView gameList;
	private TextView backup;
	private TextView restore;
	private TextView aPropos;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_page);
		
		//recuperation des boutons
		newGame = (TextView)findViewById(R.id.homeNewGame);
		playerList = (TextView)findViewById(R.id.homePlayerList);
		classement = (TextView)findViewById(R.id.homeClassement);
		gameList = (TextView)findViewById(R.id.homeGameList);
		backup = (TextView)findViewById(R.id.homeBackup);
		restore = (TextView)findViewById(R.id.homeRestore);
		aPropos = (TextView)findViewById(R.id.homeAPropos);
		
		//listener
		newGame.setOnClickListener(new StartNewGameListener(this));
		playerList.setOnClickListener(new StartPlayerListListener(this));
		classement.setOnClickListener(new StartClassementListener(this));
		gameList.setOnClickListener(new StartGameListListener(this));
		backup.setOnClickListener(new StartBackupListener(this));
		restore.setOnClickListener(new StartRestoreListener(this));
		aPropos.setOnClickListener(new StartAProposeListener(this));
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		DialogHandler.dismissWaitingDialog(this);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		PriseEnum.initEnumNames(this);
	}
	
	@Override
	public void onBackPressed() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(getString(R.string.closeApp))
		.setCancelable(false)
		.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				executeSuperBackPressed();
			}
		})
		.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
			}
		});
		AlertDialog alert = builder.create();
		alert.show();
	}
	
	private void executeSuperBackPressed(){
		super.onBackPressed();
	}
}
