package com.jso.technologies.tarot.scorer.Utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

public class ImageHelper {
	public static Intent getCroptIntent(Uri picUri){
		//call the standard crop action intent (the user device may not support it)
    	Intent cropIntent = new Intent("com.android.camera.action.CROP"); 
    	//indicate image type and Uri
    	cropIntent.setDataAndType(picUri, "image/*");
    	//set crop properties
    	cropIntent.putExtra("crop", "true");
    	//indicate aspect of desired crop
    	cropIntent.putExtra("aspectX", 1);
    	cropIntent.putExtra("aspectY", 1);
    	//indicate output X and Y
    	cropIntent.putExtra("outputX", 256);
    	cropIntent.putExtra("outputY", 256);
    	//retrieve data on return
    	cropIntent.putExtra("return-data", true);
    	
    	return cropIntent;
	}

	public static Uri getNextTmpUri() {
		File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "TarotScorer");

	    // Create the storage directory if it does not exist
	    if (! mediaStorageDir.exists()){
	        if (! mediaStorageDir.mkdirs()){
	            Log.d("TarotScorer", "failed to create directory");
	            return null;
	        }
	    }

	    // Create a media file name
	    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.FRANCE).format(new Date());
	    File mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_"+ timeStamp + ".jpg");

	    return Uri.fromFile(mediaFile);
	}
}
