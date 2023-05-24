package com.example.week7

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.appcompat.app.AppCompatActivity
import com.example.week7.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        var handler = Handler(Looper.getMainLooper())
        var player: MediaPlayer = MediaPlayer.create(this, R.raw.music)
        var pause_position = 0

        // Seekbar
        binding.seekbar.max = player.duration
        binding.seekbar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                // 사용자가 Seekbar 움직이면 음악 재생 위치도 변경
                if (fromUser) player.seekTo(progress)
                val m = progress / 1000 / 60
                val s = progress / 1000 % 60
                val presenttime = String.format("%02d:%02d", m, s)
                binding.presentsecond.text = presenttime
            }
            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })

        // 재생 스레드
        class PlayThread : Thread() {
            override fun run() {
                if(player == null) player = MediaPlayer.create(this@MainActivity, R.raw.music)
                player.seekTo(pause_position)
                player.start()
                binding.tvStatus.text = "현재 상태: 재생 중"
                while (player.isPlaying) {
                    Thread.sleep(1000)
                    handler.post {
                        binding.presentsecond.text = player.currentPosition.toString()
                        binding.seekbar.progress = player.currentPosition
                    }
                }
            }
        }

        // 일시정지 스레드
        class PauseThread : Thread() {
            override fun run() {
                player.pause()
                pause_position = player.currentPosition
                binding.tvStatus.text = "현재 상태: 일시 정지"
            }
        }

        // 정지 스레드
        class StopThread : Thread() {
            override fun run() {
                player.stop()
                player.prepare()
                pause_position = 0
                player.seekTo(pause_position)
                handler.post {
                    binding.presentsecond.text = "00:00"
                    binding.seekbar.progress = player.currentPosition
                    binding.tvStatus.text = "현재 상태: 정지"
                }
            }
        }

        binding.btnStart.setOnClickListener {
            PlayThread().start()
        }

        binding.btnPause.setOnClickListener {
            PauseThread().start()
        }

        binding.btnStop.setOnClickListener {
            StopThread().start()
        }





    }
}