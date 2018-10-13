@file:JvmName("ExtensionsUtils")

package io.github.anugrahrochmat.footballmatchschedule.utils

import android.content.res.Resources

/**
 *  Created by Anugrah Rochmat on 11/10/18
 */
fun dp2px(dp: Int): Float = dp * Resources.getSystem().displayMetrics.density
