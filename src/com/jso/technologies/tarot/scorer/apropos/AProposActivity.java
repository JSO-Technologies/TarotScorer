package com.jso.technologies.tarot.scorer.apropos;

import android.app.Activity;
import android.os.Bundle;

import com.jso.technologies.tarot.scorer.R;

/**
 * Activity : Page a propos
 * @author Jimmy
 *
 */
public class AProposActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_a_propos);
		
		findViewById(R.id.aProposMail).setOnClickListener(new MailClientListener(this));
	}	
}
