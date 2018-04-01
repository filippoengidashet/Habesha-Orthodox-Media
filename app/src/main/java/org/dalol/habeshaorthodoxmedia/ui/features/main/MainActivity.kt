/*
 * Copyright (c) 2018 Filippo Engidashet.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.dalol.habeshaorthodoxmedia.ui.features.main

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import org.dalol.habeshaorthodoxmedia.R
import org.dalol.habeshaorthodoxmedia.data.MainMenuItem
import org.dalol.habeshaorthodoxmedia.ui.base.BaseActivity
import org.dalol.habeshaorthodoxmedia.ui.common.GridItemsMarginDecorator
import org.dalol.habeshaorthodoxmedia.ui.presentation.MainView
import android.view.MenuItem
import android.view.View
import android.os.Looper
import android.os.Message
import android.view.Gravity
import android.widget.Toast
import org.dalol.habeshaorthodoxmedia.ui.features.blog.HolyBlogActivity
import org.dalol.habeshaorthodoxmedia.ui.features.books.HolyBooksListActivity
import org.dalol.habeshaorthodoxmedia.ui.features.churches.ChurchesLocationActivity
import org.dalol.habeshaorthodoxmedia.ui.features.mezmur.MezmurLyricsListActivity
import org.dalol.habeshaorthodoxmedia.ui.features.pictures.HolyPicturesActivity
import org.dalol.habeshaorthodoxmedia.ui.features.video.HolyVideosActivity
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest


class MainActivity : BaseActivity() {

    //lateinit var mView: MainView

    override fun getContentView(): Int {
        return R.layout.activity_main
    }

    private lateinit var mDrawerToggle: ActionBarDrawerToggle

    override fun onResume() {
        super.onResume()
        adView.loadAd(AdRequest.Builder().build())
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adView.adListener = object : AdListener() {
            override fun onAdLoaded() {
                super.onAdLoaded()
                val visibility = adView.getVisibility()
                if (visibility != View.VISIBLE) adView.setVisibility(View.VISIBLE)
            }

            override fun onAdFailedToLoad(p0: Int) {
                super.onAdFailedToLoad(p0)
                showToast("Failed to load ad -> $p0")
            }

            //            @Override
            //            public void onAdFailedToLoad(int i) {
            //                super.onAdFailedToLoad(i);
            //                Log.d("AdLoading", "Failed to load ad -> " + i);
            //            }
        }


//        val displayMetrics = resources.displayMetrics
//
//        val screenWidth = displayMetrics.widthPixels / displayMetrics.density
//
//        if (screenWidth <= 360) {
//            //navigationView.layoutParams.width = displayMetrics.widthPixels - Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 56f, displayMetrics))
//        }

        navigationView.setNavigationItemSelectedListener({ item ->
            when (item.itemId) {
                R.id.nav_share -> handler.sendEmptyMessageDelayed(0, 150L)
                R.id.nav_rate_app -> handler.sendEmptyMessageDelayed(1, 150L)
                R.id.nav_about -> handler.sendEmptyMessageDelayed(2, 150L)
            }
            navigationDrawer.closeDrawer(Gravity.START); true
        })

        showHome()

        mDrawerToggle = object : ActionBarDrawerToggle(this, navigationDrawer, R.string.openDrawer, R.string.closeDrawer) {
            override fun onDrawerClosed(drawerView: View) {
                super.onDrawerClosed(drawerView)
            }

            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)
            }
        }
        mDrawerToggle.isDrawerIndicatorEnabled = true
        navigationDrawer.addDrawerListener(mDrawerToggle)
        mDrawerToggle.syncState()

        val bgArray: Array<Long> = arrayOf(0xFFc6e7f7, 0xFFcddeff, 0xFFe8dcfe, 0xFFffdee7, 0xFFc6f7e7, 0xFFfcefc5)

        val mainMenuItems = mutableListOf<MainMenuItem>()


        mainMenuItems.add(MainMenuItem(0, R.drawable.ic_lyrics, "የመዝሙር ግጥም", 0))
        mainMenuItems.add(MainMenuItem(1, R.drawable.ic_bible, "የጸሎት መጻሕፍት", 0))
        mainMenuItems.add(MainMenuItem(2, R.drawable.ic_picture, "መንፈሳዊ ምስል", 0))
        mainMenuItems.add(MainMenuItem(3, R.drawable.ic_video, "መንፈሳዊ ቪዲዮ", 0))
        mainMenuItems.add(MainMenuItem(4, R.drawable.ic_rss, "መንፈሳዊ ጦማር", 0))
        mainMenuItems.add(MainMenuItem(5, R.drawable.ic_church, "ቤተክርስቲያናት በውጭ ሀገር", 0))

//        val random = Random()
//
//        for (i in 1..150) {
//            mainMenuItems.add(MainMenuItem(0, "ፊሊፖ ${(i + 1)}", bgArray[random.nextInt(bgArray.size)]))
//        }

        //mView = DefaultMainView()

        recyclerView.addItemDecoration(GridItemsMarginDecorator(resources.getDimensionPixelOffset(R.dimen.item_main_menu_gap), 2))
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.adapter = MainMenuAdapter(layoutInflater, mainMenuItems, object : MainMenuAdapter.OnDashboardMenuItemClickListener {
            override fun onItemClick(item: MainMenuItem) {
                when (item.menuId) {
                    0 -> start<MezmurLyricsListActivity>()
                    1 -> start<HolyBooksListActivity>()
                    2 -> start<HolyPicturesActivity>()
                    3 -> start<HolyVideosActivity>()
                    4 -> start<HolyBlogActivity>()
                    5 -> start<ChurchesLocationActivity>()
                    else -> {
                        showToast("Undefined yet")
                    }
                }

            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item)
    }


    private val handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
//            when (msg.what) {
//                0 -> "Share"
//                1 -> "Rate App"
//                2 -> "About"
//                3 -> "Setting"
//            }

            Toast.makeText(applicationContext, "${msg.what}", Toast.LENGTH_SHORT).show()

            //val mainActivity = this@MainActivity
        }
    }
}
