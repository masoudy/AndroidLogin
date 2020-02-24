package ir.avarche.android.app.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.*
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey
import ir.avarche.android.app.ServerGateway
import ir.avarche.android.app.loginPage.*
import okhttp3.Interceptor
import retrofit2.Retrofit
import javax.inject.Provider


@Component(modules = [VMs::class])
interface Repos
{
    fun loginRepository():LoginRepository
    fun inject(page:LoginMobilePage)
    fun inject(page:LoginVerificationPage)
}




@Module(includes = [Bindings::class])
object VMs
{

    @IntoMap
    @Provides
    @JvmStatic
    @StringKey("LoginViewModel")
    fun provideLoginViewModel(repo:LoginRepository) = LoginViewModel(repo)



    @Provides
    @JvmStatic
    fun provideLoginRepository() = LoginRepo( ServerGateway.getGatewayImplementation(LoginHttpGateway::class.java))

    @Provides
    @JvmStatic
    fun provideViewModelFactory(map:Map<String,@JvmSuppressWildcards Provider< out ViewModel>>) =
        ViewModelFactory(map)

    @Provides
    @JvmStatic
    fun provideViewModelFactoryMap(vm1:Provider<LoginViewModel>):Map<String,@JvmSuppressWildcards Provider<out ViewModel>> =
        mapOf("LoginViewModel" to vm1 )


}

@Module
interface Bindings
{
    @Binds
    fun bind1(repo:LoginRepo):LoginRepository

    @Binds
    fun bind2(f:ViewModelFactory):ViewModelProvider.Factory

    @Binds
    fun bind3(repo:LoginViewModel):ViewModel
}