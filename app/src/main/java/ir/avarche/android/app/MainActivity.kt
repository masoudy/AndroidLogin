package ir.avarche.android.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ir.avarche.android.app.infrastructure.di.AppModule
import ir.avarche.android.app.infrastructure.di.DaggerRepos
import ir.avarche.android.app.infrastructure.di.Repos
import ir.avarche.android.test.R


class MainActivity : AppCompatActivity()
{
    companion object{
        lateinit var injector:Repos
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        injector = DaggerRepos.builder().appModule(AppModule( application)).build()
    }
}