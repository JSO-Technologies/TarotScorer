package com.jso.technologies.tarot.scorer.detailled.score;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TableRow;

public class DetailledScoreRowClickListener implements OnClickListener {
	private DetailledScoreActivity activity;
	private TableRow row;
	
	public DetailledScoreRowClickListener(DetailledScoreActivity detailledScoreActivity, TableRow newRow) {
		this.activity = detailledScoreActivity;
		this.row = newRow;
	}

	@Override
	public void onClick(View v) {
		for(TableRow currentRow : activity.getRows()) {
			if(currentRow.equals(row)) {
				currentRow.setBackgroundColor(activity.getResources().getColor(android.R.color.darker_gray));
			}
			else if(currentRow.getBackground() != null) {
				currentRow.setBackgroundColor(activity.getResources().getColor(android.R.color.transparent));
			}
		}
		activity.setSelectedRow(row);
		activity.openOptionsMenu();
	}

}
