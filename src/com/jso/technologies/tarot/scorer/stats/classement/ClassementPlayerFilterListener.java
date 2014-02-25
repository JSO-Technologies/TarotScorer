package com.jso.technologies.tarot.scorer.stats.classement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import android.app.Dialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TableLayout;

import com.jso.technologies.tarot.scorer.R;
import com.jso.technologies.tarot.scorer.common.bean.Option;
import com.jso.technologies.tarot.scorer.common.bean.Player;

public class ClassementPlayerFilterListener implements OnClickListener {

	private ClassementActivity activity;

	public ClassementPlayerFilterListener(ClassementActivity activity) {
		this.activity = activity;
	}

	@Override
	public void onClick(View arg0) {
		//option en bdd
		final Option option = activity.getPlayersInClassification();
		List<Integer> idList = option.getPlayerIdList();
		boolean allPlayers = idList == null;
		
		//map des checkbox
		final Map<Player, CheckBox> checkboxList = new HashMap<Player, CheckBox>();
		
		//creation du dialog
		final Dialog dialog = new Dialog(activity);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.classement_players_filter);
		
		//all/none
		final CheckBox allNoneCheckbox = (CheckBox) dialog.findViewById(R.id.classementPlayersFilterAllNone);
		if(allPlayers) {
			allNoneCheckbox.setChecked(true);
		}
		allNoneCheckbox.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				boolean isChecked = ((CheckBox)v).isChecked();
				for(CheckBox checkbox : checkboxList.values()) {
					checkbox.setChecked(isChecked);
				}
			}
		});

		//insertion des donnees
		TableLayout table = (TableLayout) dialog.findViewById(R.id.classementPlayersFilterTableLayout);
		for(Player player : activity.getPlayers()) {
			MarginLayoutParams layoutParams = new MarginLayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			layoutParams.setMargins(20, 0, 0, 0);
			
			CheckBox checkBox = new CheckBox(activity);
			checkBox.setText(player.getPseudo());
			checkBox.setLayoutParams(layoutParams);
			checkBox.setChecked(allPlayers || idList.contains(player.getId()));
			
			checkBox.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					boolean isChecked = ((CheckBox)v).isChecked();
					if(allNoneCheckbox.isChecked() && !isChecked) {
						allNoneCheckbox.setChecked(false);
					}
					else if(!allNoneCheckbox.isChecked() && isChecked) {
						boolean allChecked = true;
						for(CheckBox checkbox : checkboxList.values()) {
							if(!checkbox.isChecked()) {
								allChecked = false;
								break;
							}
						}
						if(allChecked) {
							allNoneCheckbox.setChecked(true);
						}
					}
				}
			});
			
			checkboxList.put(player, checkBox);
			table.addView(checkBox);
		}
		
		//action fermeture du dialog
		((Button)dialog.findViewById(R.id.classementPlayersFilterOkButton)).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						StringBuffer playersOption = null;
						if(!allNoneCheckbox.isChecked()) {
							playersOption = new StringBuffer();
							for(Entry<Player, CheckBox> entry : checkboxList.entrySet()) {
								if(entry.getValue().isChecked()) {
									if(playersOption.length() > 0) {
										playersOption.append(Option.SEPARATOR);
									}
									playersOption.append(entry.getKey().getId());
								}
							}
						}
						option.setValue(playersOption == null ? null : playersOption.toString());
						activity.getOptionsRepo().update(option);
						activity.updateClassificationTable();
						dialog.dismiss();
					}
				});

		//affichage
		dialog.show();
	}

}
