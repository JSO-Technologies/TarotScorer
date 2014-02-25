package com.jso.technologies.tarot.scorer.player.common;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.jso.technologies.tarot.scorer.R;

public class GetPhotoListener implements OnClickListener {
	private AbstractPlayerOperationActivity activity;

	public GetPhotoListener(AbstractPlayerOperationActivity playerActivity) {
		activity = playerActivity;
	}

	@Override
	public void onClick(View v) {
		try {
        	//use standard intent to capture an image
        	Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        	captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, activity.getNextTmpUri());
        	//we will handle the returned data in onActivityResult
            activity.startActivityForResult(captureIntent, activity.CAMERA_CAPTURE);
    	}
        catch(ActivityNotFoundException anfe){
    		//display an error message
    		String errorMessage = activity.getString(R.string.photo_not_supported);
    		Toast toast = Toast.makeText(activity.getApplicationContext(), errorMessage, Toast.LENGTH_SHORT);
    		toast.show();
    	}

	}

}
