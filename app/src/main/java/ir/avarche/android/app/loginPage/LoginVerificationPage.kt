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
import ir.avarche.android.app.di.DaggerRepos
import ir.avarche.android.app.di.ViewModelFactory
import ir.avarche.android.app.util.alert
import ir.avarche.android.test.R
import javax.inject.Inject

class LoginVerificationPage : Fragment() {

    @Inject
    lateinit var viewModelFactory:ViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        DaggerRepos.create().inject(this)
        val viewModel = ViewModelProvider(activity as AppCompatActivity,viewModelFactory).get(LoginViewModel::class.java)
        val view = inflater.inflate(R.layout.login_verification_page,container,false)
        val verificationField = view.findViewById<EditText>(R.id.verificationField)
        val confirmVerificationButton = view.findViewById<Button>(R.id.confirmVerificationCodeButton)

        verificationField.doOnTextChanged { text, _, _, _ ->
            viewModel.verificationCode  = text?.toString() ?: ""
        }

        viewModel.wrongCodeWarns.handleIfHasNotBeenHandled(viewLifecycleOwner){
                alert(
                    context!!,
                    "",
                    getString(R.string.warning_verification_code_should_be_correct)
                )
        }



        confirmVerificationButton.setOnClickListener {
            viewModel.verifyCode()
        }

        return view
    }
}