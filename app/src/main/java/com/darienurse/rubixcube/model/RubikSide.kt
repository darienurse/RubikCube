package com.darienurse.rubixcube.model

class RubikSide(private val size: Int, private val value: Int) {
    val values: Array<IntArray> = Array(size) { IntArray(size) { value } }

    fun getRow(row: Int): IntArray {
        return values[row].clone()
    }

    fun getCol(col: Int): IntArray {
        return IntArray(size) { i -> values[i][col] }
    }

    fun setRow(row: Int, newValues: IntArray) {
        values[row] = newValues.clone()
    }

    fun setCol(col: Int, newValues: IntArray) {
        newValues.forEachIndexed { i, value ->
            values[i][col] = value
        }
    }

    fun rotateClockwise() {
        val newValues = Array(size) { IntArray(size) }
        for (i in 0 until size) {
            for (j in 0 until size) {
                newValues[j][size - 1 - i] = values[i][j]
            }
        }
        values.forEachIndexed { index, ints -> System.arraycopy(newValues[index], 0, ints, 0, size) }
    }

    fun rotateCounterClockwise() {
        val newValues = Array(size) { IntArray(size) }
        for (i in 0 until size) {
            for (j in 0 until size) {
                newValues[size - 1 - j][i] = values[i][j]
            }
        }
        values.forEachIndexed { index, ints -> System.arraycopy(newValues[index], 0, ints, 0, size) }
    }

    fun cloneReversed(): RubikSide {
        val interim = values.map { it.reversed().toIntArray() }.toTypedArray<IntArray>()
        val reversedValues = interim.reversed().toTypedArray<IntArray>()
        return RubikSide(size, value).apply { setValues(reversedValues) }
    }

    fun setValues(newValues: Array<IntArray>) {
        for (i in 0 until size) {
            values[i] = newValues[i].clone()
        }
    }

    fun clone(): RubikSide {
        val newValues = Array(size) { row -> values[row].copyOf() }
        val newRubikSide = RubikSide(size, 0)
        newRubikSide.setValues(newValues)
        return newRubikSide
    }
}