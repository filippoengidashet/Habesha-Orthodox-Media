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

package org.dalol.habeshaorthodoxmedia.ui.common

import android.graphics.Rect
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * @author Filippo Engidashet [filippo.eng@gmail.com]
 * @version 1.0.0
 * @since Wednesday, 21/03/2018 at 17:31.
 */

class GridItemsMarginDecorator(private val mSpaceSize: Int, private val mSpanCount: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State?) {
        super.getItemOffsets(outRect, view, parent, state)
        val position = parent.getChildAdapterPosition(view)

        if (position < mSpanCount) {
            outRect.top = mSpaceSize
        } else {
            outRect.top = 0
        }

        val params = view.layoutParams as GridLayoutManager.LayoutParams
        val column = params.spanIndex

        outRect.left = mSpaceSize - column * mSpaceSize / mSpanCount
        outRect.right = (column + 1) * mSpaceSize / mSpanCount
        outRect.bottom = mSpaceSize
    }
}
