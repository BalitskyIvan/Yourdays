package gamefield.yourdays.presentation.screen.main_screen.utils

import android.widget.SeekBar

class EmotionSeekBarListener(
    private val onChangedAction: (Int) -> Unit
) : SeekBar.OnSeekBarChangeListener {

    override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
        onChangedAction.invoke(p1)
    }

    override fun onStartTrackingTouch(p0: SeekBar?) {}

    override fun onStopTrackingTouch(p0: SeekBar?) {}
}
