package com.jso.technologies.tarot.scorer.detailled.score;

import java.util.List;

import android.view.View;
import android.widget.TableRow;

import com.jso.technologies.tarot.scorer.common.bean.Game;
import com.jso.technologies.tarot.scorer.common.iface.ITarotElementaryAction;

/**
 * Action �l�mentaire : suppression de la ligne s�lectionn�e en sauvant les infos supprim�e pour pouvoir rollbacker
 * @author Jimmy
 *
 */
public class DeleteRowAction implements ITarotElementaryAction {

	private DetailledScoreActivity activity;

	public DeleteRowAction(DetailledScoreActivity detailledScoreActivity) {
		this.activity = detailledScoreActivity;
	}

	@Override
	public void execute() {
		//recuperation des donn�es
		TableRow selectedRow = activity.getSelectedRow();
		List<TableRow> rows = activity.getRows();
		Game game = activity.getGame();
		
		//suppression de la ligne
		activity.getDetailledScoreTableLayout().removeView(selectedRow);
		int index = rows.indexOf(selectedRow);
		rows.remove(selectedRow);
		activity.setSelectedRow(null);

		//ajout de l'index dans la liste � passer � priseActivity
		activity.getDeletedRows().add(index);
		activity.getDeletedTableRows().add(selectedRow);
		activity.getDeletedPrises().add(game.getPrises().get(index));

		//suppression du Game
		game.removePrise(index);

		//mise � jour du score total
		activity.updateTotals();

		//affichage du bouton cancel
		activity.getCancelButton().setVisibility(View.VISIBLE);
	}

}
