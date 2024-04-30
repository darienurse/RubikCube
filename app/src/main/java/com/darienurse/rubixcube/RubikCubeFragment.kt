package com.darienurse.rubixcube

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.GridLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.darienurse.rubixcube.databinding.FragmentRubikCubeBinding
import com.darienurse.rubixcube.model.RubikCube
import com.darienurse.rubixcube.viewmodel.RubikCubeViewModel
import com.darienurse.rubixcube.viewmodel.RubikCubeViewModelFactory
import kotlin.math.min
import kotlin.properties.Delegates

class RubikCubeFragment : Fragment() {

    private lateinit var viewModel: RubikCubeViewModel
    private var _binding: FragmentRubikCubeBinding? = null
    private lateinit var rowNumberEditText: EditText
    private lateinit var columnNumberEditText: EditText
    private lateinit var turnRowLeftButton: Button
    private lateinit var turnRowRightButton: Button
    private lateinit var turnColUpButton: Button
    private lateinit var turnColDownButton: Button
    private lateinit var rubikGridLayout: GridLayout
    private var size by Delegates.notNull<Int>()

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        size = arguments?.getInt("inputNumber")!!
        viewModel =
            ViewModelProvider(this, RubikCubeViewModelFactory(size))[RubikCubeViewModel::class.java]
        _binding = FragmentRubikCubeBinding.inflate(inflater, container, false)
        turnColUpButton = binding.turnColUpButton
        turnColDownButton = binding.turnColDownButton
        columnNumberEditText = binding.columnNumberEditText
        turnRowLeftButton = binding.turnRowLeftButton
        turnRowRightButton = binding.turnRowRightButton
        rowNumberEditText = binding.rowNumberEditText

        val view = binding.root
        val screenWidth = resources.displayMetrics.widthPixels
        val screenHeight = resources.displayMetrics.heightPixels
        val gridSize = min(screenWidth, screenHeight)
        val marginSize = gridSize / 10

        rubikGridLayout = GridLayout(requireContext()).apply {
            id = View.generateViewId()
            layoutParams = GridLayout.LayoutParams().apply {
                width = gridSize
                height = gridSize
                leftMargin = marginSize
                topMargin = marginSize
                rightMargin = marginSize
                bottomMargin = marginSize
            }
            columnCount = size
            rowCount = size
            useDefaultMargins = true
        }

        val constraintLayout = view.findViewById<ConstraintLayout>(R.id.constraint_layout)
        constraintLayout.removeView(view.findViewById(R.id.placeholder_frame_layout))
        constraintLayout.addView(rubikGridLayout)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.yawLeftButton.setOnClickListener {
            viewModel.changeToFace(RubikCube.FACE.LEFT)
        }

        binding.yawRightButton.setOnClickListener {
            viewModel.changeToFace(RubikCube.FACE.RIGHT)
        }

        binding.pitchUpButton.setOnClickListener {
            viewModel.changeToFace(RubikCube.FACE.TOP)
        }

        binding.pitchDownButton.setOnClickListener {
            viewModel.changeToFace(RubikCube.FACE.BOTTOM)
        }

        viewModel.rubikCube.observe(viewLifecycleOwner) { rubikCube ->
            updateRubikCubeUI(rubikCube)
        }

        turnColUpButton.setOnClickListener {
            val col = columnNumberEditText.text.toString().toIntOrNull()
            if (col != null && col in (0 until size)) {
                viewModel.turnColUp(col)
            }
        }

        turnColDownButton.setOnClickListener {
            val col = columnNumberEditText.text.toString().toIntOrNull()
            if (col != null && col in (0 until size)) {
                viewModel.turnColDown(col)
            }
        }

        turnRowLeftButton.setOnClickListener {
            val row = rowNumberEditText.text.toString().toIntOrNull()
            if (row != null && row in 0 until size) {
                viewModel.turnRowToLeft(row)
            }
        }

        turnRowRightButton.setOnClickListener {
            val row = rowNumberEditText.text.toString().toIntOrNull()
            if (row != null && row in 0 until size) {
                viewModel.turnRowToRight(row)
            }
        }

        viewModel.rubikCube.observe(viewLifecycleOwner) { rubikCube ->
            updateRubikCubeUI(rubikCube)
        }

        rowNumberEditText.addTextChangedListener {
            val row = rowNumberEditText.text.toString().toIntOrNull()
            val isEnabled = row != null && row in 0 until size
            turnRowLeftButton.isEnabled = isEnabled
            turnRowRightButton.isEnabled = isEnabled
        }

        columnNumberEditText.addTextChangedListener {
            val col = columnNumberEditText.text.toString().toIntOrNull()
            val isEnabled =  col != null && col in (0 until size)
            turnColUpButton.isEnabled = isEnabled
            turnColDownButton.isEnabled = isEnabled
        }
    }

    private fun updateRubikCubeUI(rubikCube: RubikCube) {
        rubikGridLayout.removeAllViews()
        for (i in 0 until size) {
            for (j in 0 until size) {
                val cell = View(requireContext())
                val params = GridLayout.LayoutParams()
                params.width = 10
                params.height = 10
                params.rowSpec = GridLayout.spec(i, 1f)
                params.columnSpec = GridLayout.spec(j, 1f)
                cell.layoutParams = params
                cell.setBackgroundColor(getColorForValue(rubikCube.main.getRow(i)[j]))
                rubikGridLayout.addView(cell)
            }
        }
    }

    private fun getColorForValue(value: Int): Int {
        return when (value) {
            1 -> Color.RED
            2 -> Color.BLUE
            3 -> Color.rgb(255, 165, 0) // Orange
            4 -> Color.GREEN
            5 -> Color.GRAY
            6 -> Color.YELLOW
            else -> Color.BLACK
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}