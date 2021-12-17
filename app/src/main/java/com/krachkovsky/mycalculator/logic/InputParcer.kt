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
            } else if ((component == "/" || component == "*" || component == "-" || component == "+") && stack.isEmpty()) {
                stack.add(component)
            } else if ((component == "/" || component == "*") && (stack[stack.lastIndex] == "-" || stack[stack.lastIndex] == "+")) {
                stack.add(component)
            } else if ((component == "/" || component == "*" || component == "-" || component == "+") && stack[stack.lastIndex] == "(") {
                stack.add(component)
            } else if ((component == "/" || component == "*") && (stack[stack.lastIndex] == "/" || stack[stack.lastIndex] == "*")
                || (component == "-" || component == "+") && (stack[stack.lastIndex] == "-" || stack[stack.lastIndex] == "+")
                || (component == "-" || component == "+") && (stack[stack.lastIndex] == "*" || stack[stack.lastIndex] == "/")
            ) {
                while ((component == "/" || component == "*") && (stack[stack.lastIndex] == "/" || stack[stack.lastIndex] == "*")
                    || (component == "-" || component == "+") && (stack[stack.lastIndex] == "-" || stack[stack.lastIndex] == "+")
                    || (component == "-" || component == "+") && (stack[stack.lastIndex] == "*" || stack[stack.lastIndex] == "/")
                ) {
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