package com.krachkovsky.mycalculator.logic

class InputParser {
    private val stack = mutableListOf<String>()
    private val output = mutableListOf<String>()
    private val allOperators = arrayOf("/", "*", "-", "+")

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
            } else if (prioritization(component)) {
                stack.add(component)
            } else if (stack.isNotEmpty() && pushOperatorToOutput(
                    component,
                    stack[stack.lastIndex]
                )
            ) {
                while (pushOperatorToOutput(component, stack[stack.lastIndex])) {
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
                    //                   throw Exception()
                }
                output.add(element)
            }
        }

        return output.toTypedArray()
    }

    fun clearInput() {
        stack.clear()
        output.clear()
    }

    private fun prioritization(component: String): Boolean {
        return isAnyOperator(component) && stack.isEmpty()
                || component.isHighLevelOperator() && stack[stack.lastIndex].isLowLevelOperator()
                || isAnyOperator(component) && stack[stack.lastIndex] == "("
    }

    private fun isAnyOperator(component: String): Boolean {
        return allOperators.contains(component)
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

    private fun pushOperatorToOutput(component: String, topInStack: String): Boolean {
        return component.isHighLevelOperator() && topInStack.isHighLevelOperator()
                || component.isLowLevelOperator() && topInStack.isLowLevelOperator()
                || component.isLowLevelOperator() && topInStack.isHighLevelOperator()
    }

    private fun convert2StringComponents(expression: String): Array<String> {
        val result = mutableListOf<String>()
        var prevIndex = 0
        for (index in expression.indices) {
            if (index == 0 && expression[index] == '-') {
                continue
            } else {
                when (expression[index]) {
                    '+', '-', '*', '/', '(', ')' -> {
                        if (expression.substring(prevIndex, index).trim().isNotEmpty()) {
                            result.add(expression.substring(prevIndex, index))
                        }
                        result.add(expression[index].toString())
                        prevIndex = index + 1
                    }
                }
            }
        }
        if (prevIndex != expression.length)
            result.add(expression.substring(prevIndex, expression.length))
        result.forEachIndexed { index, s ->
            if (index < result.size - 2) {
                if (allOperators.contains(s) && allOperators.contains(result[index + 1])) {
                    result.clear()
                    result.add("error")
                }
            }
        }
        return result.toTypedArray()
    }
}