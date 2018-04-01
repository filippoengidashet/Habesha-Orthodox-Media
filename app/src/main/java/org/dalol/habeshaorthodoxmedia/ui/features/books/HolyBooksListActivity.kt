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

package org.dalol.habeshaorthodoxmedia.ui.features.books

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.formats.*
import kotlinx.android.synthetic.main.activity_holy_books_list.*
import org.dalol.habeshaorthodoxmedia.R
import org.dalol.habeshaorthodoxmedia.data.model.DefaultListItem
import org.dalol.habeshaorthodoxmedia.data.model.ListItem
import org.dalol.habeshaorthodoxmedia.ui.base.BaseActivity
import org.dalol.habeshaorthodoxmedia.ui.common.LinearItemsMarginDecorator
import com.google.android.gms.ads.InterstitialAd






class HolyBooksListActivity : BaseActivity() {

    private lateinit var mInterstitialAd: InterstitialAd

    lateinit var adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>
    private val items = mutableListOf<ListItem<Int>>()

    private val MENU_ITEM_VIEW_TYPE = 0

    // The native app install ad view type.
    private val NATIVE_APP_INSTALL_AD_VIEW_TYPE = 1

    // The native content ad view type.
    private val NATIVE_CONTENT_AD_VIEW_TYPE = 2

    override fun getContentView(): Int {
        return R.layout.activity_holy_books_list
    }

    private fun showAd() {
        if (mInterstitialAd.isLoaded) {
            mInterstitialAd.show()
        }
    }


    override fun onResume() {
        super.onResume()
        showAd()
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        title = "Holy - Books"

        items.add(DefaultListItem<Int>(MENU_ITEM_VIEW_TYPE, "Model"))
        items.add(DefaultListItem<Int>(MENU_ITEM_VIEW_TYPE, "Model"))
        items.add(DefaultListItem<Int>(MENU_ITEM_VIEW_TYPE, "Model"))
//        items.add(DefaultListItem<Int>(MENU_ITEM_VIEW_TYPE, "Model"))
//        items.add(DefaultListItem<Int>(MENU_ITEM_VIEW_TYPE, "Model"))
//        items.add(DefaultListItem<Int>(MENU_ITEM_VIEW_TYPE, "Model"))
//        items.add(DefaultListItem<Int>(MENU_ITEM_VIEW_TYPE, "Model"))


        mInterstitialAd = InterstitialAd(this)
        mInterstitialAd.adUnitId = getString(R.string.admob_interstitial_ad_unit_id)
        mInterstitialAd.loadAd(AdRequest.Builder().build())
        mInterstitialAd.adListener = object : AdListener() {

            override fun onAdClosed() {
                super.onAdClosed()
                mInterstitialAd.loadAd(AdRequest.Builder().build())
            }
        }

        val adLoader = AdLoader.Builder(this, getString(R.string.admob_app_id))
                .forAppInstallAd { ad : NativeAppInstallAd ->
                    // Show the app install ad.
                    items.add(DefaultListItem<Int>(NATIVE_APP_INSTALL_AD_VIEW_TYPE, ad))
                    adapter.notifyDataSetChanged()
                }
                .forContentAd { ad : NativeContentAd ->
                    items.add(DefaultListItem<Int>(NATIVE_CONTENT_AD_VIEW_TYPE, ad))
                    adapter.notifyDataSetChanged()
                    // Show the content ad.
                }
                .withAdListener(object : AdListener() {
                    override fun onAdFailedToLoad(errorCode: Int) {
                        // Handle the failure by logging, altering the UI, and so on.
                        showToast("Failed to load ad -> $errorCode")
                    }
                })
                .withNativeAdOptions(NativeAdOptions.Builder()
                        // Methods in the NativeAdOptions.Builder class can be
                        // used here to specify individual options settings.
                        .build())
                .build()

        adLoader.loadAd(AdRequest.Builder().build())

        showHome()

        showToast("Hello")

        holyBookList.setHasFixedSize(true)
        holyBookList.layoutManager = LinearLayoutManager(this)
        holyBookList.addItemDecoration(LinearItemsMarginDecorator(resources.getDimensionPixelOffset(R.dimen.item_book_gap)))
        //holyBookList.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        adapter = object : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


            override fun getItemViewType(position: Int): Int {
                return items[position].getType()
            }

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
                return when (viewType) {
                    NATIVE_APP_INSTALL_AD_VIEW_TYPE -> {
                        NativeAppInstallAdViewHolder(layoutInflater.inflate(R.layout.ad_app_install, parent, false))
                    }
                    NATIVE_CONTENT_AD_VIEW_TYPE -> {
                        NativeContentAdViewHolder(layoutInflater.inflate(R.layout.ad_content, parent, false))
                    }
                    else -> {
                        BookItemViewHolder(layoutInflater.inflate(R.layout.item_holy_book_item_layout, parent, false))
                    }
                }
            }

            override fun getItemCount(): Int {
                return items.size
            }

            override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
                val viewType = items[position].getType()

                when (viewType) {
                    NATIVE_APP_INSTALL_AD_VIEW_TYPE -> {
                        val appInstallAd = items[position] as NativeAppInstallAd
                        populateAppInstallAdView(appInstallAd, holder.itemView as NativeAppInstallAdView);
                    }
                    NATIVE_CONTENT_AD_VIEW_TYPE -> {
                        val contentAd = items[position] as NativeContentAd
                        populateContentAdView(contentAd, holder.itemView as NativeContentAdView);
                    }
                    else -> {
                        val menuItemHolder =  holder as BookItemViewHolder;
//                        MenuItem menuItem = (MenuItem) mRecyclerViewItems.get(position);
//
//                        // Get the menu item image resource ID.
//                        String imageName = menuItem.getImageName();
//                        int imageResID = mContext.getResources().getIdentifier(imageName, "drawable",
//                                mContext.getPackageName());
//
//                        // Add the menu item details to the menu item view.
//                        menuItemHolder.menuItemImage.setImageResource(imageResID);
//                        menuItemHolder.menuItemName.setText(menuItem.getName());
//                        menuItemHolder.menuItemPrice.setText(menuItem.getPrice());
//                        menuItemHolder.menuItemCategory.setText(menuItem.getCategory());
//                        menuItemHolder.menuItemDescription.setText(menuItem.getDescription());
                    }
                }
            }

            private fun populateContentAdView(nativeContentAd: NativeContentAd,
                                              adView: NativeContentAdView) {
                // Some assets are guaranteed to be in every NativeContentAd.
                (adView.headlineView as TextView).text = nativeContentAd.headline
                (adView.bodyView as TextView).text = nativeContentAd.body
                (adView.callToActionView as TextView).text = nativeContentAd.callToAction
                (adView.advertiserView as TextView).text = nativeContentAd.advertiser

                val images = nativeContentAd.images

                if (images.size > 0) {
                    (adView.imageView as ImageView).setImageDrawable(images[0].drawable)
                }

                // Some aren't guaranteed, however, and should be checked.
                val logoImage = nativeContentAd.logo

                if (logoImage == null) {
                    adView.logoView.visibility = View.INVISIBLE
                } else {
                    (adView.logoView as ImageView).setImageDrawable(logoImage.drawable)
                    adView.logoView.visibility = View.VISIBLE
                }

                // Assign native ad object to the native view.
                adView.setNativeAd(nativeContentAd)
            }

            private fun populateAppInstallAdView(nativeAppInstallAd: NativeAppInstallAd,
                                                 adView: NativeAppInstallAdView) {

                // Some assets are guaranteed to be in every NativeAppInstallAd.
                (adView.iconView as ImageView).setImageDrawable(nativeAppInstallAd.icon
                        .drawable)
                (adView.headlineView as TextView).text = nativeAppInstallAd.headline
                (adView.bodyView as TextView).text = nativeAppInstallAd.body
                (adView.callToActionView as Button).setText(nativeAppInstallAd.callToAction)

                // These assets aren't guaranteed to be in every NativeAppInstallAd, so it's important to
                // check before trying to display them.
                if (nativeAppInstallAd.price == null) {
                    adView.priceView.visibility = View.INVISIBLE
                } else {
                    adView.priceView.visibility = View.VISIBLE
                    (adView.priceView as TextView).text = nativeAppInstallAd.price
                }

                if (nativeAppInstallAd.store == null) {
                    adView.storeView.visibility = View.INVISIBLE
                } else {
                    adView.storeView.visibility = View.VISIBLE
                    (adView.storeView as TextView).text = nativeAppInstallAd.store
                }

                if (nativeAppInstallAd.starRating == null) {
                    adView.starRatingView.visibility = View.INVISIBLE
                } else {
                    (adView.starRatingView as RatingBar).rating = nativeAppInstallAd.starRating!!.toFloat()
                    adView.starRatingView.visibility = View.VISIBLE
                }

                // Assign native ad object to the native view.
                adView.setNativeAd(nativeAppInstallAd)
            }
        }
        holyBookList.adapter = adapter
    }

    inner class NativeAppInstallAdViewHolder constructor(itemView: View): RecyclerView.ViewHolder(itemView) {

        init {
            val adView = itemView as NativeAppInstallAdView

            // Register the view used for each individual asset.
            // The MediaView will display a video asset if one is present in the ad, and the
            // first image asset otherwise.
            val mediaView = adView.findViewById<View>(R.id.appinstall_media) as MediaView
            adView.mediaView = mediaView
            adView.headlineView = adView.findViewById(R.id.appinstall_headline)
            adView.bodyView = adView.findViewById(R.id.appinstall_body)
            adView.callToActionView = adView.findViewById(R.id.appinstall_call_to_action)
            adView.iconView = adView.findViewById(R.id.appinstall_app_icon)
            adView.priceView = adView.findViewById(R.id.appinstall_price)
            adView.starRatingView = adView.findViewById(R.id.appinstall_stars)
            adView.storeView = adView.findViewById(R.id.appinstall_store)
        }
    }

    inner class NativeContentAdViewHolder constructor(itemView: View): RecyclerView.ViewHolder(itemView) {
        init {
            val adView = itemView as NativeContentAdView

            // Register the view used for each individual asset.
            adView.headlineView = adView.findViewById(R.id.contentad_headline)
            adView.imageView = adView.findViewById(R.id.contentad_image)
            adView.bodyView = adView.findViewById(R.id.contentad_body)
            adView.callToActionView = adView.findViewById(R.id.contentad_call_to_action)
            adView.logoView = adView.findViewById(R.id.contentad_logo)
            adView.advertiserView = adView.findViewById(R.id.contentad_advertiser)
        }
    }

    inner class BookItemViewHolder constructor(itemView: View): RecyclerView.ViewHolder(itemView) {

    }
}
