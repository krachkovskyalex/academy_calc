package com.krachkovsky.mycalculator.ui

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.krachkovsky.mycalculator.databinding.CalcFragmentBinding
import com.krachkovsky.mycalculator.logic.Calculator
import com.krachkovsky.mycalculator.logic.InputParser
import com.krachkovsky.mycalculator.logic.command.AdditionCommand
import com.krachkovsky.mycalculator.logic.command.DivideCommand
import com.krachkovsky.mycalculator.logic.command.MultiplyCommand
import com.krachkovsky.mycalculator.logic.command.SubtractCommand
import kotlin.Exception

class CalcFragment : Fragment() {

    private val inputParser = InputParser()
    private val calculator = setupBasicCalcCommands(Calculator())

    private var _binding: CalcFragmentBinding? = null
    private val binding
        get() = requireNotNull(_binding) {
            "View was destroyed"
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return CalcFragmentBinding.inflate(inflater, container, false)
            .also { binding ->
                _binding = binding
            }
            .root
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            b0.setOnClickListener { replaceInputIfZeroOrSet("0") }
            b1.setOnClickListener { replaceInputIfZeroOrSet("1") }
            b2.setOnClickListener { replaceInputIfZeroOrSet("2") }
            b3.setOnClickListener { replaceInputIfZeroOrSet("3") }
            b4.setOnClickListener { replaceInputIfZeroOrSet("4") }
            b5.setOnClickListener { replaceInputIfZeroOrSet("5") }
            b6.setOnClickListener { replaceInputIfZeroOrSet("6") }
            b7.setOnClickListener { replaceInputIfZeroOrSet("7") }
            b8.setOnClickListener { replaceInputIfZeroOrSet("8") }
            b9.setOnClickListener { replaceInputIfZeroOrSet("9") }
            bSub.setOnClickListener { tvDisplay.append("-") }
            bAc.setOnClickListener {
                tvDisplay.text = ""
                inputParser.clearInput()
            }
            bAdd.setOnClickListener { tvDisplay.append("+") }
            bOpenBracket.setOnClickListener { replaceInputIfZeroOrSet("(") }
            bCloseBracket.setOnClickListener { tvDisplay.append(")") }
            bDiv.setOnClickListener { tvDisplay.append("/") }
            bMult.setOnClickListener { tvDisplay.append("*") }
            bDot.setOnClickListener { tvDisplay.append(".") }
            bBack.setOnClickListener { tvDisplay.text = tvDisplay.text.dropLast(1) }
            bEquals.setOnClickListener {
                if (tvDisplay.text.isNotEmpty()) {
                    try {
                        val userInput = tvDisplay.text.toString()
                        val parser = inputParser.convert(userInput)
                        val result = calculator.calculate(parser).toString()
                        inputParser.clearInput()
                        tvDisplay.text = result

                    } catch (e: Exception) {
                        Snackbar.make(
                            view,
                            "Check your input. Something wrong",
                            Snackbar.LENGTH_SHORT
                        ).setAction("Clear") {
                            tvDisplay.text = ""
                        }
                            .show()
                    }
                }
            }
        }
    }

    private fun setupBasicCalcCommands(calculator: Calculator): Calculator {
        calculator.addCommand("+", AdditionCommand())
        calculator.addCommand("/", DivideCommand())
        calculator.addCommand("-", SubtractCommand())
        calculator.addCommand("*", MultiplyCommand())
        return calculator
    }

    private fun replaceInputIfZeroOrSet(value: String) {
        with(binding) {
            if (tvDisplay.text.equals("0")) {
                tvDisplay.text = ""
                tvDisplay.append(value)
            } else {
                tvDisplay.append(value)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}