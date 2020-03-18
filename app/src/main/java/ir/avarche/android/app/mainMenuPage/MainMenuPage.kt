package ir.avarche.android.app.mainMenuPage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import ir.avarche.android.test.R
import ir.avarche.android.test.databinding.MainMenuBinding

class MainMenuPage : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val binding = MainMenuBinding.inflate(inflater, container, false)

        binding.toolbar.navigationIcon = ContextCompat.getDrawable(context!!,R.drawable.home)
        binding.toolbar.setNavigationOnClickListener {

         if(binding.drawerLayout.isDrawerOpen(GravityCompat.START))
                binding.drawerLayout.closeDrawer(GravityCompat.START)
            else
                binding.drawerLayout.openDrawer(GravityCompat.START)
        }

        NavigationUI.setupWithNavController(binding.navigation,findNavController())


        return binding.root
    }
}