package com.jso.technologies.tarot.scorer.Utils;

import java.io.ByteArrayOutputStream;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

public class ImageUtils {
	public static byte[] getByteArray(Bitmap bitmap) {
		if(bitmap == null){
			return null;
		}
		
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		bitmap.compress(CompressFormat.PNG, 0, bos);
		return bos.toByteArray();
	}

	public static Bitmap getBitmap(byte[] bitmap) {
		if(bitmap == null){
			return null;
		}
		
		return BitmapFactory.decodeByteArray(bitmap , 0, bitmap.length);
	}
	
	public static byte[] getByteArray(Drawable drawable){
		if(drawable == null){
			return null;
		}
		
		Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
		return stream.toByteArray();
	}

	public static Bitmap getBitmap(ImageView imageView) {
		if(imageView == null){
			return null;
		}
		
		imageView.setDrawingCacheEnabled(true);
		imageView.buildDrawingCache(true);
		
		Bitmap bitmap = imageView.getDrawingCache();
		bitmap = bitmap.copy(bitmap.getConfig(), false);
		
		imageView.setDrawingCacheEnabled(false);
		
		return bitmap;
	}

}
