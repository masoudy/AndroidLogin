package ir.avarche.android.app.splashPage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import ir.avarche.android.app.di.DaggerRepos
import ir.avarche.android.app.di.ViewModelFactory
import ir.avarche.android.app.loginPage.LoginRepo
import ir.avarche.android.app.util.alert
import ir.avarche.android.app.util.tellNavControllerToNavigate
import ir.avarche.android.test.R
import ir.avarche.android.test.databinding.SplashBinding
import javax.inject.Inject

class SplashPage : Fragment() {

    @Inject
    lateinit var loginRepo: LoginRepo

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        DaggerRepos.create().inject(this)
        val binding = SplashBinding.inflate(inflater, container, false)
        val view = binding.root

        loginRepo.loggedInUser.observe(viewLifecycleOwner, Observer {
            if(it == null)
                view.tellNavControllerToNavigate(R.id.action_splashPage_to_loginMobilePage2)
            else
                view.tellNavControllerToNavigate(R.id.action_splashPage_to_mainMenuPage)
        })

        return view
    }
}