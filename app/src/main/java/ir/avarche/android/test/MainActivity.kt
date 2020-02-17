package ir.avarche.android.test

import android.app.Activity
import android.os.Bundle
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.main_activity.*


class MainActivity : Activity()
{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

    }
}