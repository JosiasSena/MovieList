package com.josiassena.movielist.movie_info.receiver

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.database.Cursor
import com.josiassena.movielist.app.App
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.longToast
import javax.inject.Inject


class PosterDownloadBroadcastReceiver : BroadcastReceiver(), AnkoLogger {

    @Inject
    lateinit var downloadManager: DownloadManager

    override fun onReceive(context: Context, intent: Intent) {
        App.component.inject(this)

        when (intent.action) {
            DownloadManager.ACTION_DOWNLOAD_COMPLETE -> {

                val downloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0)
                val query = DownloadManager.Query().apply { setFilterById(downloadId) }
                val cursor = downloadManager.query(query)

                if (cursor.moveToFirst()) {
                    // Column for download  status
                    val columnStatusIndex = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)
                    val status = cursor.getInt(columnStatusIndex)

                    // Column for reason code if the download failed or paused
                    val columnReasonIndex = cursor.getColumnIndex(DownloadManager.COLUMN_REASON)
                    val reason = cursor.getInt(columnReasonIndex)

                    when (status) {
                        DownloadManager.STATUS_RUNNING -> {
                        }
                        DownloadManager.STATUS_PAUSED -> {
                            context.longToast("Download failed: $reason")
                        }
                        DownloadManager.STATUS_PENDING -> {
                        }
                        DownloadManager.STATUS_SUCCESSFUL -> {
                            handleSuccessfulDownload(cursor, context)
                        }
                        DownloadManager.STATUS_FAILED -> {
                            context.longToast("Download failed: $reason")
                        }
                    }
                }
            }
            DownloadManager.ACTION_NOTIFICATION_CLICKED -> {
                context.startActivity(Intent(DownloadManager.ACTION_VIEW_DOWNLOADS))
            }
        }
    }

    private fun handleSuccessfulDownload(cursor: Cursor, context: Context) {
        context.longToast("Download completed successfully.")

        val uriString = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI))
        info("Download uri: $uriString")
    }
}
