package com.krachkovsky.mycalculator.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.krachkovsky.mycalculator.databinding.CalcFragmentBinding
import com.krachkovsky.mycalculator.logic.InputParser
import java.lang.Exception

class CalcFragment : Fragment() {

    val inputParcer = InputParser()
    var list = arrayOf<String>()

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            b0.setOnClickListener { tvWorkings.append("0") }
            b1.setOnClickListener { tvWorkings.append("1") }
            b2.setOnClickListener { tvWorkings.append("2") }
            b3.setOnClickListener { tvWorkings.append("3") }
            b4.setOnClickListener { tvWorkings.append("4") }
            b5.setOnClickListener { tvWorkings.append("5") }
            b6.setOnClickListener { tvWorkings.append("6") }
            b7.setOnClickListener { tvWorkings.append("7") }
            b8.setOnClickListener { tvWorkings.append("8") }
            b9.setOnClickListener { tvWorkings.append("9") }
            bAc.setOnClickListener { tvWorkings.text = "" }
            bAdd.setOnClickListener { tvWorkings.append("+") }
            bOpenBracket.setOnClickListener { tvWorkings.append("(") }
            bCloseBracket.setOnClickListener { tvWorkings.append(")") }
            bDiv.setOnClickListener { tvWorkings.append("/") }
            bSub.setOnClickListener { tvWorkings.append("-") }
            bMult.setOnClickListener { tvWorkings.append("*") }
            bDot.setOnClickListener { tvWorkings.append(".") }
            bBack.setOnClickListener { tvWorkings.text = tvWorkings.text.dropLast(1) }
            bEquals.setOnClickListener {
                try {
                    list = inputParcer.convert(tvWorkings.text.toString())
                    Log.e("Fragment input", list.joinToString())
                } catch (e: Exception) {
                    Snackbar.make(view, "Check your input. Something wrong", Snackbar.LENGTH_SHORT).show()
                }
            }

        }
    }
    

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}