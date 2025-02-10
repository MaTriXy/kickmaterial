package com.byoutline.kickmaterial

import android.support.annotation.VisibleForTesting
import com.byoutline.androidstubserver.AndroidStubServer
import com.byoutline.kickmaterial.dagger.AppComponent
import com.byoutline.kickmaterial.dagger.AppModule
import com.byoutline.kickmaterial.dagger.DaggerAppComponent
import com.byoutline.mockserver.NetworkType
import com.byoutline.observablecachedfield.util.RetrofitHelper
import com.byoutline.secretsauce.SecretSauceSettings
import com.byoutline.secretsauce.di.ActivityInjectorApp
import com.byoutline.secretsauce.di.AppInjector
import com.byoutline.secretsauce.utils.showToast
import timber.log.Timber

/**
 * @author Pawel Karczewski <pawel.karczewski at byoutline.com> on 2015-01-03
 */
open class KickMaterialApp : ActivityInjectorApp() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        AndroidStubServer.start(this, NetworkType.UMTS)
        RetrofitHelper.setMsgDisplayer { msg -> this.showToast(msg, true) }

        initDagger()
    }

    @VisibleForTesting
    open fun initDagger() {
        setComponents(createGlobalComponent())
    }

    @VisibleForTesting
    open fun setComponents(appComponent: AppComponent) {
        component = appComponent
        component.inject(this)
        AppInjector.init(this)
        SecretSauceSettings.set(debug = BuildConfig.DEBUG,
                containerViewId = R.id.container,
                bindingViewModelId = BR.viewModel,
                viewModelFactoryProvider = { viewModelFactory })
    }

    private fun createGlobalComponent(): AppComponent {
        return DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
    }

    companion object {
        lateinit var component: AppComponent
            private set
    }
}
