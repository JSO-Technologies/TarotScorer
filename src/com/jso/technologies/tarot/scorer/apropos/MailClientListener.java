package com.jso.technologies.tarot.scorer.apropos;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

public class MailClientListener implements OnClickListener {

	private AProposActivity activity;
	private static final String MAIL = "jso.technologies@gmail.com";
	private static final String SUBJECT = "[Tarot Scorer] Retour utilisateur";

	public MailClientListener(AProposActivity activity) {
		this.activity = activity;
	}

	@Override
	public void onClick(View arg0) {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");
        i.putExtra(Intent.EXTRA_EMAIL, new String[]{MAIL});
        i.putExtra(Intent.EXTRA_SUBJECT, SUBJECT);
        i.putExtra(Intent.EXTRA_TEXT, "");
        activity.startActivity(i);
	}

}
