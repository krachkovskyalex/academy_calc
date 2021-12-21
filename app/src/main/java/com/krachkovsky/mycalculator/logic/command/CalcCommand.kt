package com.krachkovsky.mycalculator.logic.command

import android.icu.math.BigDecimal

abstract class CalcCommand {
    abstract operator fun invoke(a: String, b: String): BigDecimal
}