package com.jso.technologies.tarot.scorer.home;

import static com.jso.technologies.tarot.scorer.Utils.Constantes.BACKUP_PATH;
import static com.jso.technologies.tarot.scorer.Utils.Constantes.CURRENT_DB_PATH;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.concurrent.Executors;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.jso.technologies.tarot.scorer.R;
import com.jso.technologies.tarot.scorer.Utils.DialogHandler;

public class StartBackupListener implements OnClickListener {

	private final HomePageActivity activity;

	public StartBackupListener(HomePageActivity activity) {
		this.activity = activity;
	}

	@Override
	public void onClick(View v) {
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		builder.setMessage(activity.getString(R.string.backup_confirmation))
		.setCancelable(false)
		.setPositiveButton(activity.getString(R.string.yes), new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				performBackup();
			}
		})
		.setNegativeButton(activity.getString(R.string.no), new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
			}
		});
		AlertDialog alert = builder.create();
		alert.show();
	}
	
	private void performBackup() {
		DialogHandler.showWaitingDialog(activity);

		Executors.newFixedThreadPool(1).execute(new Runnable() {

			@Override
			public void run() {
				File sd = Environment.getExternalStorageDirectory();
				File data = Environment.getDataDirectory();

				if (sd.canWrite()) {
					FileInputStream currentDBStream = null;
					FileOutputStream backupDBStream = null;
					FileChannel src = null;
					FileChannel dst = null;
					try{
						String currentDBPath = CURRENT_DB_PATH;
						String backupDBPath = BACKUP_PATH;
						File currentDB = new File(data, currentDBPath);
						File backupDB = new File(sd, backupDBPath);

						currentDBStream = new FileInputStream(currentDB);
						backupDBStream = new FileOutputStream(backupDB);
						src = currentDBStream.getChannel();
						dst = backupDBStream.getChannel();
						dst.transferFrom(src, 0, src.size());

						activity.runOnUiThread(new Runnable() {
							public void run() {
								Toast.makeText(activity.getBaseContext(), activity.getString(R.string.backup_success), Toast.LENGTH_LONG).show();
							}
						});
					}
					catch(final Exception e) {
						activity.runOnUiThread(new Runnable() {
							public void run() {
								Toast.makeText(activity.getBaseContext(), e.toString(), Toast.LENGTH_LONG).show();
							}
						});
					}
					finally {
						closeStream(src);
						closeStream(dst);
						closeStream(currentDBStream);
						closeStream(backupDBStream);
					}
				}
				else {
					Toast.makeText(activity.getBaseContext(), activity.getString(R.string.backup_sd_error), Toast.LENGTH_LONG).show();
				}

				DialogHandler.dismissWaitingDialog(activity);
			}

			private void closeStream(FileOutputStream backupDBStream) {
				if(backupDBStream != null) {
					try {
						backupDBStream.close();
					} catch (Exception e) {}
				}
			}

			private void closeStream(FileInputStream stream) {
				if(stream != null) {
					try {
						stream.close();
					} catch (Exception e) {}
				}
			}

			private void closeStream(FileChannel src) {
				if(src != null) {
					try {
						src.close();
					} catch(Exception e) {}
				}
			}
		});
	}

}
