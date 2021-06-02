package com.wynnlab.util

import com.wynnlab.mobs.BaseMob


fun combineOr(ais: Array<out BaseMob.AI>): Int {
    var res = 0
    for (ai in ais)
        res = res or ai.flags
    return res
}