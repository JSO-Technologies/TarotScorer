package com.jso.technologies.tarot.scorer.detailled.score;

import java.util.List;

import android.view.View;
import android.widget.TableRow;

import com.jso.technologies.tarot.scorer.common.bean.Game;
import com.jso.technologies.tarot.scorer.common.bean.Prise;
import com.jso.technologies.tarot.scorer.common.iface.ITarotElementaryAction;

/**
 * Action �l�mentaire : annuler la derni�re suppression de ligne
 * @author Jimmy
 *
 */
public class CancelLastDeletionAction implements ITarotElementaryAction {

	private DetailledScoreActivity activity;

	public CancelLastDeletionAction(DetailledScoreActivity activity) {
		this.activity = activity;
	}

	@Override
	public void execute() {
		//recuperation des donnees
		List<Integer> deletedRows = activity.getDeletedRows();
		List<TableRow> deletedTableRow = activity.getDeletedTableRows();
		List<Prise> deletedPrises = activity.getDeletedPrises();
		Game game = activity.getGame();
		
		//recuperation de la derniere ligne supprimee
		int index = deletedRows.get(deletedRows.size() - 1);
		TableRow row = deletedTableRow.get(deletedTableRow.size() - 1);
		Prise prise = deletedPrises.get(deletedPrises.size() - 1);
		
		//injection de la prise dans le jeu 
		game.addPrise(prise, index);
		
		//ajout du row dans la view
		activity.getDetailledScoreTableLayout().addView(row, index + 2);
		activity.getRows().add(index, row);
		
		//mise a jour du score total
		activity.updateTotals();
		
		//suppression de la ligne dans la liste des lignes supprimees
		deletedRows.remove(deletedRows.size() - 1);
		deletedTableRow.remove(deletedTableRow.size() - 1);
		deletedPrises.remove(deletedPrises.size() - 1);
		
		//non affichage du bouton cancel s'il n'y a plus de ligne supprimee
		if(deletedRows.isEmpty()) {
			activity.getCancelButton().setVisibility(View.GONE);
		}
	}

}
