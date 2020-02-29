package ir.avarche.android.app.mainMenuPage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ir.avarche.android.test.databinding.MainMenuBinding

class MainMenuPage : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = MainMenuBinding.inflate(inflater, container, false)
        return binding.root
    }
}