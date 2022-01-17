package com.krachkovsky.mycalculator.logic.command

import android.icu.math.BigDecimal
import android.os.Build
import androidx.annotation.RequiresApi

class AdditionCommand : CalcCommand() {
    @RequiresApi(Build.VERSION_CODES.N)
    override fun invoke(a: String, b: String): BigDecimal {
            return BigDecimal(a).add(BigDecimal(b))
    }
}