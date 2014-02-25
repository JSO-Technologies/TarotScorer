package com.jso.technologies.tarot.scorer.player.config;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;

import com.jso.technologies.tarot.scorer.R;
import com.jso.technologies.tarot.scorer.Utils.Constantes;
import com.jso.technologies.tarot.scorer.Utils.DialogHandler;
import com.jso.technologies.tarot.scorer.Utils.ImageUtils;
import com.jso.technologies.tarot.scorer.common.bean.Player;
import com.jso.technologies.tarot.scorer.db.cache.PlayerRepositoryCache;

/**
 * Activity : configuration des joueurs pour une nouvelle partie
 * @author Jimmy
 *
 */
public class PlayersConfigActivity extends Activity {
	private SeekBar nbPlayerSeekBar;
	private List<Spinner> playerSpinners;
	private List<ImageView> playerPhotos;
	private List<LinearLayout> playerGroups;
	private Button validationButton;
	
	private ArrayAdapter<Player> arrayAdapter;
	
	private Player newPlayer;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_players_config);

		//recuperation des view
		nbPlayerSeekBar = (SeekBar) findViewById(R.id.nbPlayerSeekBar);
		
		validationButton = (Button) findViewById(R.id.validPlayerConfigButton);
		
		playerSpinners = new ArrayList<Spinner>();
		playerSpinners.add((Spinner) findViewById(R.id.player1));
		playerSpinners.add((Spinner) findViewById(R.id.player2));
		playerSpinners.add((Spinner) findViewById(R.id.player3));
		playerSpinners.add((Spinner) findViewById(R.id.player4));
		playerSpinners.add((Spinner) findViewById(R.id.player5));
		
		playerGroups = new ArrayList<LinearLayout>();
		playerGroups.add((LinearLayout) findViewById(R.id.players1Group));
		playerGroups.add((LinearLayout) findViewById(R.id.players2Group));
		playerGroups.add((LinearLayout) findViewById(R.id.players3Group));
		playerGroups.add((LinearLayout) findViewById(R.id.players4Group));
		playerGroups.add((LinearLayout) findViewById(R.id.players5Group));
		
		playerPhotos = new ArrayList<ImageView>();
		playerPhotos.add((ImageView) findViewById(R.id.photo1));
		playerPhotos.add((ImageView) findViewById(R.id.photo2));
		playerPhotos.add((ImageView) findViewById(R.id.photo3));
		playerPhotos.add((ImageView) findViewById(R.id.photo4));
		playerPhotos.add((ImageView) findViewById(R.id.photo5));

		//listeners
		nbPlayerSeekBar.setOnSeekBarChangeListener(new PlayerNumberListener(this));
		validationButton.setOnClickListener(new ValidationPlayerConfigListener(this));
		for(ImageView photo : playerPhotos){
			photo.setOnClickListener(new PlayerPhotoListener(this));
		}
		for(Spinner spinner : playerSpinners){
			spinner.setOnItemSelectedListener(new PlayerSelectionListener(this));
		}

		//recuperation des joueurs en base
		List<Player> allPlayersInDB = PlayerRepositoryCache.getAll(this);

		//creation du 1er element de combobox
		Drawable no_photo = getResources().getDrawable(R.drawable.no_photo_create);
		newPlayer = new Player(null, getString(R.string.newPlayer), ImageUtils.getByteArray(no_photo), true);
		allPlayersInDB.add(0, newPlayer);
		
		//remplissage des combobox
		arrayAdapter = new ArrayAdapter<Player>(this, R.layout.custom_spinner, allPlayersInDB);
		for(Spinner spinner : playerSpinners){
			spinner.setAdapter(arrayAdapter);
		}

	}
	
	/**
	 * Action a la fin de l'activite :
	 * * Creation d'un joueur 
	 */
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// le requestCode correspond a l'index du joueur
		if (resultCode == RESULT_OK) {
			Player player = (Player) data.getParcelableExtra(Constantes.CREATED_PLAYER);
			
			if(player != null){
				//ajout du joueur dans les listes
			    arrayAdapter.add(player);
			    
			    //selection dans la combobox
			    Spinner s = playerSpinners.get(requestCode);
				s.setSelection(arrayAdapter.getPosition(player));
				
				//affichage de la photo apres selection
				displayPhoto(requestCode, player);
			}
		}
	}
	
	/**
	 * Affichage de la photo
	 */
	public void displayPhoto(int nthPlayer, Player player){
		ImageView imageView = playerPhotos.get(nthPlayer);
		
		imageView.setImageBitmap(player.getBitmap());
	}
	
	
	@Override
	protected void onDestroy() {
		DialogHandler.dismissWaitingDialog(this);
		super.onDestroy();
	}

	public SeekBar getNbPlayerSeekBar() {
		return nbPlayerSeekBar;
	}

	public List<Spinner> getPlayers() {
		return playerSpinners;
	}

	public List<LinearLayout> getPlayerGroups() {
		return playerGroups;
	}

	public List<Spinner> getPlayerSpinners() {
		return playerSpinners;
	}

	public List<ImageView> getPlayerPhotos() {
		return playerPhotos;
	}

	public Player getNewPlayer() {
		return newPlayer;
	}

}
