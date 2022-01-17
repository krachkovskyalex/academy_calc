package com.krachkovsky.mycalculator.logic

import android.os.Build
import androidx.annotation.RequiresApi
import android.icu.math.BigDecimal
import com.krachkovsky.mycalculator.logic.command.CalcCommand

class Calculator {

    private val availableCommands = mutableMapOf<String, CalcCommand>()

    @RequiresApi(Build.VERSION_CODES.N)
    fun calculate(inputData: Array<String>): BigDecimal {
        val executableExpression = inputData.toMutableList()
        var indexOfNextOperator = getFirstOperatorIndex(executableExpression)

        var finalResult = BigDecimal(0)

        while (indexOfNextOperator > 1) {
            val first = executableExpression[indexOfNextOperator - 2]
            val second = executableExpression[indexOfNextOperator - 1]
            val command = getCommand(executableExpression[indexOfNextOperator])
            val result = command(first, second)

            executableExpression[indexOfNextOperator - 2] = result.toString()

            executableExpression.removeAt(indexOfNextOperator - 1)
            executableExpression.removeAt(indexOfNextOperator - 1)

            indexOfNextOperator = getFirstOperatorIndex(executableExpression)

            finalResult = result
        }

        return finalResult
    }

    fun addCommand(operator: String, command: CalcCommand) {
        availableCommands[operator] = command
    }

    private fun getFirstOperatorIndex(expression: MutableList<String>): Int {
        return expression.indexOfFirst { t -> getAllKnownCommands().contains(t) }
    }

    private fun getAllKnownCommands(): List<String> {
        return availableCommands.keys.toList()
    }

    private fun getCommand(commandChar: String): CalcCommand {
        availableCommands[commandChar]?.let {
            return it
        }
        throw Exception("Unknown operator!!!")
    }
}