package com.krachkovsky.mycalculator.logic.command

import android.icu.math.BigDecimal
import android.os.Build
import androidx.annotation.RequiresApi

class DivideCommand : CalcCommand() {
    @RequiresApi(Build.VERSION_CODES.N)
    override fun invoke(a: String, b: String): BigDecimal {
        if (BigDecimal(a).divide(BigDecimal(b), 10, BigDecimal.ROUND_HALF_UP) > BigDecimal(4.9e-324)
            && BigDecimal(a).divide(BigDecimal(b), 10, BigDecimal.ROUND_HALF_UP) < BigDecimal(1.7e+308)) {
            return BigDecimal(a.toDouble() / b.toDouble())
        } else {
            return BigDecimal(a).divide(BigDecimal(b), 10, BigDecimal.ROUND_HALF_UP)
        }
    }
}