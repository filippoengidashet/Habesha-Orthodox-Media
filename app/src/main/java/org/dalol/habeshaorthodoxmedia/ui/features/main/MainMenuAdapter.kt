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

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.item_main_menu_layout.view.*
import org.dalol.habeshaorthodoxmedia.R
import org.dalol.habeshaorthodoxmedia.data.MainMenuItem
import org.dalol.habeshaorthodoxmedia.utilities.ImageLoadingUtils

/**
 * @author Filippo Engidashet [filippo.eng@gmail.com]
 * @version 1.0.0
 * @since Wednesday, 21/03/2018 at 17:43.
 */

class MainMenuAdapter constructor(
        private val layoutInflater: LayoutInflater,
        private val dashboardMenuItems: List<MainMenuItem>,
        private val dashboardMenuItemClickListener: OnDashboardMenuItemClickListener
        ) : RecyclerView.Adapter<MainMenuAdapter.DashboardMenuViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardMenuViewHolder {
        return DashboardMenuViewHolder(layoutInflater.inflate(R.layout.item_main_menu_layout, parent, false))
    }

    override fun onBindViewHolder(holder: DashboardMenuViewHolder, position: Int) {
        holder.bindMenuItem(dashboardMenuItems[position])
    }
    override fun getItemCount(): Int {
        return dashboardMenuItems.size
    }

    inner class DashboardMenuViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        private val labelTxt : TextView = itemView.textView
        private val menuContainer : View = itemView.menu_container
        private val appCompatImageView : ImageView = itemView.appCompatImageView

        init {
            itemView.setOnClickListener(this)
        }

        fun bindMenuItem(dashboardMenuItem: MainMenuItem) {
            labelTxt.text = dashboardMenuItem.menuLabel
            //menuContainer.setBackgroundColor(dashboardMenuItem.menuBgColor.toInt())
            ImageLoadingUtils.loadImage(itemView.context, dashboardMenuItem.menuIcon, R.drawable.loading_large, 0, appCompatImageView)
        }

        override fun onClick(v: View?) {
            dashboardMenuItemClickListener.onItemClick(dashboardMenuItems[adapterPosition])
        }
    }

    interface OnDashboardMenuItemClickListener {

        fun onItemClick(item: MainMenuItem)
    }
}