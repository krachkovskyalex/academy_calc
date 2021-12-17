package com.krachkovsky.mycalculator.logic

class InputParcer {

    fun convert(expression: String): Array<String> {
        val stack = mutableListOf<String>()
        val output = mutableListOf<String>()

        val originalStringComponents = this.convert2StringComponents(expression)
        for (component in originalStringComponents) {

            var isBracketOpen = false

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
            } else if ((component == "/" || component == "*") && stack.isNotEmpty() && stack[stack.lastIndex] != "/" && stack[stack.lastIndex] != "*") {
                stack.add(component)
            } else if ((component == "/" || component == "*") && stack.isNotEmpty() && (stack[stack.lastIndex] == "/" || stack[stack.lastIndex] == "*")) {
                output.add(stack[stack.lastIndex])
                stack.removeAt(stack.lastIndex)
                stack.add(component)
            } else if ((component == "-" || component == "+") && stack.isNotEmpty() && stack[stack.lastIndex] != "/" && stack[stack.lastIndex] != "*") {
                stack.add(component)
            } else if ((component == "-" || component == "+") && stack.isNotEmpty() && (stack[stack.lastIndex] == "/" || stack[stack.lastIndex] == "*")) {
                while (stack.isNotEmpty()) {
                    val last1 = stack.removeAt(stack.lastIndex)
                    output.add(last1)
                }
                stack.add(component)
            } else {
                output.add(component)
            }
        }

        // While there's operators on the stack, pop them to the queue
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

    /**
     * Convert expression to array. This is needed to transform the expression to RPN
     * @param expression The String expression
     *
     * @return Array of Strings, which contains the operators and the variables/numbers
     */

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