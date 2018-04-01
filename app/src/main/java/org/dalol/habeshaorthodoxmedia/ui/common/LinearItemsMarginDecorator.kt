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
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * @author Filippo Engidashet [filippo.eng@gmail.com]
 * @version 1.0.0
 * @since Friday, 23/03/2018 at 11:32.
 */

class LinearItemsMarginDecorator : RecyclerView.ItemDecoration {

    private var mLeftSpaceSize: Int = 0
    private var mRightSpaceSize: Int = 0
    private var mTopSpaceSize: Int = 0
    private var mBottomSpaceSize: Int = 0

    constructor(spaceSize: Int) : this(spaceSize, spaceSize, spaceSize, spaceSize)

    constructor(leftSpaceSize: Int, rightSpaceSize: Int, topSpaceSize: Int, bottomSpaceSize: Int) {
        mLeftSpaceSize = leftSpaceSize
        mRightSpaceSize = rightSpaceSize
        mTopSpaceSize = topSpaceSize
        mBottomSpaceSize = bottomSpaceSize
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State?) {
        super.getItemOffsets(outRect, view, parent, state)
        val position = parent.getChildAdapterPosition(view)

        if (position < 1) {
            outRect.top = mTopSpaceSize
        } else {
            outRect.top = 0
        }
        outRect.left = mLeftSpaceSize
        outRect.right = mRightSpaceSize
        outRect.bottom = mBottomSpaceSize
    }
}