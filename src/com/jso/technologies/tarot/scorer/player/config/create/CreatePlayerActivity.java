package com.jso.technologies.tarot.scorer.player.config.create;

import android.os.Bundle;

import com.jso.technologies.tarot.scorer.player.common.AbstractPlayerOperationActivity;
import com.jso.technologies.tarot.scorer.player.common.CancelCreatePlayerListener;
import com.jso.technologies.tarot.scorer.player.common.GetPhotoListener;

public class CreatePlayerActivity extends AbstractPlayerOperationActivity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//listener
		imageView.setOnClickListener(new GetPhotoListener(this));
		okButton.setOnClickListener(new CreatePlayerListener(this));
		cancelButton.setOnClickListener(new CancelCreatePlayerListener(this));

	}

}
