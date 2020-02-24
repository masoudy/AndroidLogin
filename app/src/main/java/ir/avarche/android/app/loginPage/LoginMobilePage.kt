package ir.avarche.android.app.loginPage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import ir.avarche.android.app.util.alert
import ir.avarche.android.app.di.DaggerRepos
import ir.avarche.android.app.di.ViewModelFactory
import ir.avarche.android.test.R
import javax.inject.Inject

class LoginMobilePage : Fragment() {

    @Inject
    lateinit var viewModelFactory:ViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        DaggerRepos.create().inject(this)
        val viewModel = ViewModelProvider(activity as AppCompatActivity,viewModelFactory).get(LoginViewModel::class.java)
        val view = inflater.inflate(R.layout.login_mobile_page,container,false)
        val mobileField = view.findViewById<EditText>(R.id.mobileField)
        val askForVerificationCodeButton = view.findViewById<Button>(R.id.askForVerificationCodeButton)

        mobileField.doOnTextChanged { text, _, _, _ ->
            viewModel.mobile  = text?.toString() ?: ""
        }

        viewModel.invalidMobileWarns.handleIfHasNotBeenHandled(viewLifecycleOwner) {
                alert(
                    context!!,
                    "",
                    getString(R.string.warning_mobile_should_be_correct)
                )

        }

        viewModel.verificationCodeSent.handleIfHasNotBeenHandled(viewLifecycleOwner) {

            if(it)
                view.findNavController().navigate(R.id.action_loginMobilePage2_to_loginVerificationPage)
        }

        askForVerificationCodeButton.setOnClickListener {
            viewModel.login()
        }


        return view
    }
}