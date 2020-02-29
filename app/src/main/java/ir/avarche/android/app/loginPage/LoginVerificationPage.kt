package ir.avarche.android.app.loginPage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import ir.avarche.android.app.MainActivity
import ir.avarche.android.app.infrastructure.di.ViewModelFactory
import ir.avarche.android.app.infrastructure.util.alert
import ir.avarche.android.app.infrastructure.util.tellNavControllerToNavigate
import ir.avarche.android.test.R
import ir.avarche.android.test.databinding.LoginVerificationPageBinding
import javax.inject.Inject

class LoginVerificationPage : Fragment() {

    @Inject
    lateinit var viewModelFactory:ViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        MainActivity.injector.inject(this)
        val viewModel = ViewModelProvider(activity as AppCompatActivity,viewModelFactory).get(LoginViewModel::class.java)

        val binding = LoginVerificationPageBinding.inflate(inflater,container,false)
        val view = binding.root
        binding.viewmodel = viewModel


        viewModel.wrongCodeWarns.handleIfHasNotBeenHandled(viewLifecycleOwner){
                alert(getString(R.string.warning_verification_code_should_be_correct))
        }


        viewModel.isLoggedIn.observe(viewLifecycleOwner, Observer{
            if(it)
                view.tellNavControllerToNavigate(R.id.action_loginVerificationPage_to_mainMenuPage)
        })


        return view
    }
}