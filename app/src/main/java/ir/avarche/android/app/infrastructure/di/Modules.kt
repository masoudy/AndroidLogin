package ir.avarche.android.app.infrastructure.di

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey
import ir.avarche.android.app.infrastructure.ServerGateway
import ir.avarche.android.app.infrastructure.database.DatabaseGateway
import ir.avarche.android.app.infrastructure.httpGateways.LoginHttpGateway
import ir.avarche.android.app.infrastructure.repos.LoginRepo
import ir.avarche.android.app.infrastructure.repos.LoginRepository
import ir.avarche.android.app.loginPage.*
import ir.avarche.android.app.splashPage.SplashPage
import javax.inject.Provider


@Component(modules = [VMs::class])
interface Repos
{
    fun loginRepository(): LoginRepository
    fun inject(page:LoginMobilePage)
    fun inject(page:SplashPage)
    fun inject(page:LoginVerificationPage)
}




@Module(includes = [Bindings::class,AppModule::class])
object VMs
{
    @IntoMap
    @Provides
    @JvmStatic
    @StringKey("LoginViewModel")
    fun provideLoginViewModel(repo: LoginRepository) = LoginViewModel(repo)


    @Provides
    @JvmStatic
    fun provideDatabaseGateway(application: Application) = DatabaseGateway.initialize(application)

    @Provides
    @JvmStatic
    fun provideLoginRepository(databaseGateway: DatabaseGateway) =
        LoginRepo(
            ServerGateway.getGatewayImplementation(LoginHttpGateway::class.java),
            databaseGateway.userDao()
        )

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
class AppModule(var mApplication: Application) {
    @Provides
    fun providesApplication(): Application {
        return mApplication
    }

}


@Module
interface Bindings
{
    @Binds
    fun bind1(repo: LoginRepo): LoginRepository

    @Binds
    fun bind2(f:ViewModelFactory):ViewModelProvider.Factory

    @Binds
    fun bind3(repo:LoginViewModel):ViewModel
}