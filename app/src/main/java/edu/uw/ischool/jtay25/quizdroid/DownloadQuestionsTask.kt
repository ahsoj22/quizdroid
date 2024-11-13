package edu.uw.ischool.jtay25.quizdroid

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.AsyncTask
import android.provider.Settings
import android.widget.Toast
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

class DownloadQuestionsTask(private val context: Context) : AsyncTask<String, Void, Boolean>() {

    override fun onPreExecute() {
        super.onPreExecute()

        if (!isConnected(context)) {
            Toast.makeText(context, "No Internet connection. Please try again later.", Toast.LENGTH_LONG).show()
            cancel(true)
        }
    }

    override fun doInBackground(vararg params: String?): Boolean {
        val urlString = params[0] ?: return false
        var connection: HttpURLConnection? = null
        var inputStream: InputStream? = null
        var outputStream: FileOutputStream? = null

        try {
            val url = URL(urlString)
            connection = url.openConnection() as HttpURLConnection
            connection.connectTimeout = 5000
            connection.readTimeout = 5000
            connection.connect()

            if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                inputStream = connection.inputStream
                val outputFile = File(context.filesDir, "questions.json")
                outputStream = FileOutputStream(outputFile)

                val buffer = ByteArray(1024)
                var bytesRead: Int
                while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                    outputStream.write(buffer, 0, bytesRead)
                }

                return true
            } else {
                return false
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        } finally {
            inputStream?.close()
            outputStream?.close()
            connection?.disconnect()
        }
    }

    override fun onPostExecute(result: Boolean) {
        super.onPostExecute(result)
        if (result) {
            Toast.makeText(context, "Questions downloaded successfully!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Failed to download questions. Please try again later.", Toast.LENGTH_LONG).show()
        }
    }

    private fun isConnected(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork

        if (Settings.Global.getInt(context.contentResolver, Settings.Global.AIRPLANE_MODE_ON, 0) != 0) {
            // If airplane mode is on, show a message and prompt to turn it off
            Toast.makeText(context, "You are in Airplane Mode. Please disable Airplane Mode to continue.", Toast.LENGTH_LONG).show()
            context.startActivity(Intent(Settings.ACTION_AIRPLANE_MODE_SETTINGS))
            return false
        }

        val activeNetwork = network?.let { connectivityManager.getNetworkCapabilities(it) }
        return activeNetwork?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) ?: false
    }
}
