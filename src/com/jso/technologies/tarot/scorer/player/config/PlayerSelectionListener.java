package com.jso.technologies.tarot.scorer.player.config;

import com.jso.technologies.tarot.scorer.common.bean.Player;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;

public class PlayerSelectionListener implements OnItemSelectedListener {
	private PlayersConfigActivity activity;

	public PlayerSelectionListener(PlayersConfigActivity playersConfigActivity) {
		activity = playersConfigActivity;
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		//r�cup�ration de la view � changer et du joueur s�lectionn�
		int indexToChange = activity.getPlayerSpinners().indexOf(parent);
		Player player = (Player)parent.getSelectedItem();

		//modification de la photo du joueur s�lectionn�
		activity.displayPhoto(indexToChange , player);

		//si le joueur est s�lectionn�e autre part, on change cet ancien emplacement par 'Nouveau'
		if(!player.equals(activity.getNewPlayer())){
			for(int i = 0; i < 5; ++i){
				if(indexToChange != i){
					Spinner spinner = activity.getPlayerSpinners().get(i);
					Player selectedPlayer = (Player) spinner .getSelectedItem();
					if(player.equals(selectedPlayer)){
						spinner.setSelection(0);
						activity.displayPhoto(i , activity.getNewPlayer());
						break;
					}
				}
			}
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {

	}

}
