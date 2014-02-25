package com.jso.technologies.tarot.scorer.player.common;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.jso.technologies.tarot.scorer.R;
import com.jso.technologies.tarot.scorer.Utils.DialogHandler;
import com.jso.technologies.tarot.scorer.Utils.ImageHelper;

public abstract class AbstractPlayerOperationActivity extends Activity {
	//keep track of camera capture intent
	protected final int CAMERA_CAPTURE = 1;
	//keep track of cropping intent
	protected final int PIC_CROP = 2;
	//captured picture uri
	protected Uri picUri;
	
	protected Uri tmpUri;
	
	protected ImageView imageView;
	protected EditText pseudo;
	protected Button okButton;
	protected Button cancelButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_create_player);

		//recuperation des views
		imageView = (ImageView) findViewById(R.id.addPhoto);
		pseudo = (EditText) findViewById(R.id.addPseudo);
		okButton = (Button) findViewById(R.id.okAddPlayerButton);
		cancelButton = (Button) findViewById(R.id.cancelAddPlayerButton);
	}
	
	/**
	 * Helper method to carry out crop operation
	 */
	protected void performCrop(){
		//take care of exceptions
		try {
			//start the activity - we handle returning in onActivityResult
			startActivityForResult(ImageHelper.getCroptIntent(picUri), PIC_CROP);  
		}
		//respond to users whose devices do not support the crop action
		catch(ActivityNotFoundException anfe){
			//display an error message
			String errorMessage = getText(R.string.crop_not_supported).toString();
			Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
			toast.show();
		}
	}
	
	/**
	 * Action a la fin des activites
	 */
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			//user is returning from capturing an image using the camera
			if(requestCode == CAMERA_CAPTURE){
				//get the Uri for the captured image
				if(data == null) {
					picUri = tmpUri;
				}
				else{
					picUri = data.getData();
				}
				//carry out the crop operation
				performCrop();
			}
			//user is returning from cropping the image
			else if(requestCode == PIC_CROP){
				//get the returned data
				Bundle extras = data.getExtras();
				//get the cropped bitmap
				Bitmap thePic = extras.getParcelable("data");
				//display the returned cropped image
				imageView.setImageBitmap(thePic);
			}
		}
	}
	
	@Override
	protected void onDestroy() {
		DialogHandler.dismissWaitingDialog(this);
		super.onDestroy();
	}

	public Uri getNextTmpUri() {
		tmpUri = ImageHelper.getNextTmpUri();
		return tmpUri;
	}

	public Uri getTmpUri() {
		return tmpUri;
	}
	
	public ImageView getImageView() {
		return imageView;
	}

	public EditText getPseudo() {
		return pseudo;
	}
}
