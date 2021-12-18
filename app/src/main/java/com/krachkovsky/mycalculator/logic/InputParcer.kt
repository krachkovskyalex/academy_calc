package com.krachkovsky.mycalculator.logic

class InputParcer {
    private val stack = mutableListOf<String>()
    private val output = mutableListOf<String>()
    private val allOperators = arrayOf("/", "*", "-", "+")
    private val lowPriorityOperators = arrayOf("-", "+")
    private val highPriorityOperators = arrayOf("/", "*")

    fun convert(expression: String): Array<String> {

        val originalStringComponents = this.convert2StringComponents(expression)
        for (component in originalStringComponents) {

            if (component == "(") {
                stack.add(component)
            } else if (component == ")") {
                while (stack.isNotEmpty()) {
                    val last = stack.removeAt(stack.lastIndex)
                    if (last != "(") {
                        output.add(last)
                        continue
                    }
                    break
                }
            } else if (component.isAnyOperator() && stack.isEmpty()
                || component.isHighLevelOperator() && stack[stack.lastIndex].isLowLevelOperator()
                || component.isAnyOperator() && stack[stack.lastIndex] == "("
            ) {
                stack.add(component)
            } else if (stack.isNotEmpty() && pushOutOperator(component, stack[stack.lastIndex])) {
                while (pushOutOperator(component, stack[stack.lastIndex])) {
                    output.add(stack[stack.lastIndex])
                    stack.removeAt(stack.lastIndex)
                    if (stack.isEmpty()) {
                        break
                    }
                }
                stack.add(component)
            } else {
                output.add(component)
            }
        }

        if (stack.isNotEmpty()) {
            while (stack.isNotEmpty()) {
                val element = stack.removeAt(stack.lastIndex)
                if (element == "(" || element == ")") {
                    throw Exception("Syntax error in expression: $expression  at '$element'")
                }
                output.add(element)
            }
        }

        return output.toTypedArray()
    }

    private fun String.isAnyOperator(): Boolean {
        return when (this) {
            "/", "*", "-", "+" -> true
            else -> false
        }
    }

    private fun String.isLowLevelOperator(): Boolean {
        return when (this) {
            "-", "+" -> true
            else -> false
        }
    }

    private fun String.isHighLevelOperator(): Boolean {
        return when (this) {
            "/", "*" -> true
            else -> false
        }
    }

    private fun pushOutOperator(component: String, topInStack: String): Boolean {
        return component.isHighLevelOperator() && topInStack.isHighLevelOperator()
                || component.isLowLevelOperator() && topInStack.isLowLevelOperator()
                || component.isLowLevelOperator() && topInStack.isHighLevelOperator()
    }

    private fun convert2StringComponents(expression: String): Array<String> {
        val result = mutableListOf<String>()
        var prevIndex = 0
        for (index in expression.indices) {
            when (expression[index]) {
                '+', '-', '*', '/', '(', ')' -> {
                    if (expression.substring(prevIndex, index).trim().isNotEmpty())
                        result.add(expression.substring(prevIndex, index))
                    result.add(expression[index].toString())
                    prevIndex = index + 1
                }
            }
        }
        if (prevIndex != expression.length)
            result.add(expression.substring(prevIndex, expression.length))

        return result.toTypedArray()
    }
}