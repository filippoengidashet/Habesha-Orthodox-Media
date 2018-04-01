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

package org.dalol.habeshaorthodoxmedia.ui.features.video

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_holy_videos.*
import org.dalol.habeshaorthodoxmedia.R
import org.dalol.habeshaorthodoxmedia.ui.base.BaseActivity
import org.dalol.habeshaorthodoxmedia.ui.common.LinearItemsMarginDecorator

class HolyVideosActivity : BaseActivity() {

    override fun getContentView(): Int {
        return R.layout.activity_holy_videos
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        title = "Holy - Videos"
        showHome()

        showToast("Hello")

        holyVideosList.setHasFixedSize(true)
        holyVideosList.layoutManager = LinearLayoutManager(this)
        holyVideosList.addItemDecoration(LinearItemsMarginDecorator(resources.getDimensionPixelOffset(R.dimen.item_book_gap)))
        //holyBookList.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        holyVideosList.adapter = object : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
                return object : RecyclerView.ViewHolder(layoutInflater.inflate(R.layout.item_holy_book_item_layout, parent, false)) {}
            }

            override fun getItemCount(): Int {
                return 30
            }

            override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

            }
        }
    }
}
