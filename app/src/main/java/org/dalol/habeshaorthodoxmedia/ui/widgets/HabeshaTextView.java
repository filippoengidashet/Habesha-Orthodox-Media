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

package org.dalol.habeshaorthodoxmedia.ui.widgets;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * @author Filippo Engidashet [filippo.eng@gmail.com]
 * @version 1.0.0
 * @since Wednesday, 21/03/2018 at 18:44.
 */

public class HabeshaTextView extends AppCompatTextView {

    public HabeshaTextView(Context context) {
        super(context);
        initialize(context);
    }

    public HabeshaTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    public HabeshaTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context);
    }

    private void initialize(Context context) {
        setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/jiret.ttf"));
    }
}
