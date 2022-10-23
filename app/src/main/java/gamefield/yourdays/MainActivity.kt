package gamefield.yourdays

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import gamefield.yourdays.databinding.ActivityMainBinding
import gamefield.yourdays.ui.customviews.emotions.EmotionView
import gamefield.yourdays.ui.customviews.emotions.MinusEmotionView
import gamefield.yourdays.ui.customviews.emotions.PlusEmotionView
import gamefield.yourdays.ui.customviews.emotions.ZeroEmotionView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var currentEmotion: EmotionView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val minusEmotionView = MinusEmotionView(this)
        val plusEmotionView = PlusEmotionView(this)
        val zeroEmotionView = ZeroEmotionView(this)

        val emotionView = binding.emotionContainer

        currentEmotion = plusEmotionView

        emotionView.addView(currentEmotion)

        emotionView.setOnClickListener {
            emotionView.removeView(currentEmotion)
            currentEmotion = when (currentEmotion) {
                    is PlusEmotionView -> zeroEmotionView
                    is ZeroEmotionView -> minusEmotionView
                    is MinusEmotionView -> plusEmotionView
                    else -> plusEmotionView
                }
            emotionView.addView(currentEmotion)
        }

        binding.anxienty.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                currentEmotion?.anxiety = p1
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }

        })

        binding.joy.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                currentEmotion?.joy = p1
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }

        })

        binding.sadness.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                currentEmotion?.sadness = p1
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }

        })

        binding.calmness.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                currentEmotion?.calmness = p1
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }

        })
    }

}