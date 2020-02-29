package ir.avarche.android.app.splashPage

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import ir.avarche.android.app.MainActivity
import ir.avarche.android.app.infrastructure.repos.LoginRepo
import ir.avarche.android.app.infrastructure.util.tellNavControllerToNavigate
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

        MainActivity.injector.inject(this)
        val binding = SplashBinding.inflate(inflater, container, false)
        val view = binding.root

        ObjectAnimator.ofFloat(view, "alpha", 1f, 0f,1f).apply {
            duration = 2000
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    loginRepo.isThereAnyLoggedInUser.observe(viewLifecycleOwner, Observer {
                        if(it.not())
                            view.tellNavControllerToNavigate(R.id.action_splashPage_to_loginMobilePage2)
                        else
                            view.tellNavControllerToNavigate(R.id.action_splashPage_to_mainMenuPage)
                    })
                }
            })
        }.start()


        return view
    }
}