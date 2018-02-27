package id.co.digitalindo.streamingradio

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.media.MediaPlayer
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.ProgressBar

import java.io.IOException


class MainActivity : AppCompatActivity(), View.OnClickListener {


    private val url_radio = "http://usa8-vn.mixstream.net:8138"
    private var progressBar1: ProgressBar? = null

    private var textViewRadioUrl: TextView? = null
    private var buttonPlay: Button? = null

    private var buttonStop: Button? = null

    private var player: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        initializeUIElements()
        initializeMediaPlayer()
    }

    private fun initializeUIElements() {


        progressBar1?.max = 100
        progressBar1?.visibility = View.INVISIBLE
        progressBar1?.isIndeterminate = true
        buttonPlay?.setOnClickListener(this)
        

        
        buttonStop?.isEnabled = false
        buttonStop?.setOnClickListener(this)

        textViewRadioUrl?.text = "Radio url : " + url_radio
    }

    override fun onClick(v: View?) {

        if (v === buttonPlay) {
            startPlaying()
        } else if (v === buttonStop) {
            stopPlaying()
      }
    }

//    fun onClick(v: View) {
//        if (v === buttonPlay) {
//            startPlaying()
//        } else if (v === buttonStop) {
//            stopPlaying()
//        }
//    }

    private fun startPlaying() {
        buttonStop?.setEnabled(true)
        buttonPlay?.setEnabled(false)

        progressBar1?.setVisibility(View.VISIBLE)

        player?.prepareAsync()

        player?.setOnPreparedListener(MediaPlayer.OnPreparedListener { player?.start() })

    }

    private fun stopPlaying() {
        if (player?.isPlaying()!!) {
            player?.stop()
            player?.release()
            initializeMediaPlayer()
        }

        buttonPlay?.setEnabled(true)
        buttonStop?.setEnabled(false)
        progressBar1?.setIndeterminate(true)
        progressBar1?.setVisibility(View.INVISIBLE)

    }

    private fun initializeMediaPlayer() {
        player = MediaPlayer()
        try {
            player?.setDataSource(url_radio)
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        player?.setOnBufferingUpdateListener(MediaPlayer.OnBufferingUpdateListener { mp, percent ->
            progressBar1?.setIndeterminate(false)
            progressBar1?.setSecondaryProgress(100)
            Log.i("Buffering", "" + percent)
        })
    }

    override fun onPause() {
        super.onPause()
        if (player?.isPlaying()!!) {
            //  player.stop();
        }
    }
}
