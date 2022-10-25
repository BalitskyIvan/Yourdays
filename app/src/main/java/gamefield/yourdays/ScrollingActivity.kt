package gamefield.yourdays

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import gamefield.yourdays.databinding.ActivityScrollingBinding

class ScrollingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScrollingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityScrollingBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val emotionView = binding.emotionView
        emotionView.layoutParams
        val height = emotionView.layoutParams.height
        val width = emotionView.layoutParams.width

        binding.scrollingPeriod.root.setOnScrollChangeListener { p0, p1, p2, p3, p4 ->
            if (p2 < 600) {
                emotionView.layoutParams.height = height - (p2/2)
                emotionView.layoutParams.width = width - (p2/2)

                emotionView.requestLayout()
            }
        }
    }
}