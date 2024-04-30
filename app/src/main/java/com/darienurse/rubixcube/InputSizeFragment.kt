package com.darienurse.rubixcube

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.darienurse.rubixcube.databinding.FragmentInputSizeBinding

class InputSizeFragment : Fragment() {

    private var _binding: FragmentInputSizeBinding? = null
    private lateinit var inputSizeEditText: EditText
    private lateinit var generateCubeButton: Button
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentInputSizeBinding.inflate(inflater, container, false)
        inputSizeEditText = binding.numberInputEditText
        generateCubeButton = binding.generateCubeButton
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        inputSizeEditText.addTextChangedListener {
            val numberString = inputSizeEditText.text.toString()
            val number = numberString.toIntOrNull()
            if (number != null && number in 2..19) {
                generateCubeButton.text = "Generate $number x $number Rubik Cube"
                generateCubeButton.isEnabled = true
            } else {
                generateCubeButton.text = "Generate Rubik Cube"
                generateCubeButton.isEnabled = false

            }
        }

        generateCubeButton.setOnClickListener {
            val bundle = bundleOf("inputNumber" to inputSizeEditText.text.toString().toInt())
            findNavController().navigate(
                R.id.action_InputSizeFragment_to_RubikCubeFragment,
                bundle
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}