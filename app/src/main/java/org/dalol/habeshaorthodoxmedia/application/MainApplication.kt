package org.dalol.habeshaorthodoxmedia.application

import android.app.Application
import com.google.android.gms.ads.MobileAds
import org.dalol.habeshaorthodoxmedia.R

/**
 * @author Filippo Engidashet [filippo.eng@gmail.com]
 * @version 1.0.0
 * @since Wednesday, 21/03/2018 at 16:20.
 */

class MainApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        MobileAds.initialize(this, getString(R.string.admob_app_id));
    }

    override fun onTerminate() {
        super.onTerminate()
    }
}
