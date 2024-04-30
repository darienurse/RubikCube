package com.darienurse.rubixcube.model

class RubikCube(val size: Int) {
    var main: RubikSide = RubikSide(size, 1)
    private var right: RubikSide = RubikSide(size, 2)
    private var back: RubikSide = RubikSide(size, 3)
    private var left: RubikSide = RubikSide(size, 4)
    private var top: RubikSide = RubikSide(size, 5)
    private var bottom: RubikSide = RubikSide(size, 6)

    fun turnRowToRight(row: Int) {
        val mainRow = main.getRow(row)
        main.setRow(row, left.getRow(row))
        left.setRow(row, back.getRow(row))
        back.setRow(row, right.getRow(row))
        right.setRow(row, mainRow)
        if (row == 0) {
            top.rotateCounterClockwise()
        } else if (row == size - 1) {
            bottom.rotateClockwise()
        }
    }

    fun turnRowToLeft(row: Int) {
        val mainRow = main.getRow(row)
        main.setRow(row, right.getRow(row))
        right.setRow(row, back.getRow(row))
        back.setRow(row, left.getRow(row))
        left.setRow(row, mainRow)
        if (row == 0) {
            top.rotateClockwise()
        } else if (row == size - 1) {
            bottom.rotateCounterClockwise()
        }
    }

    fun turnColUp(col: Int) {
        val mainCol = main.getCol(col)
        val reversedBack = back.cloneReversed()
        main.setCol(col, bottom.getCol(col))
        bottom.setCol(col, reversedBack.getCol(col))
        reversedBack.setCol(col, top.getCol(col))
        top.setCol(col, mainCol)
        back.setValues(reversedBack.cloneReversed().values)
        if (col == 0) {
            left.rotateCounterClockwise()
        } else if (col == size - 1) {
            right.rotateClockwise()
        }
    }

    fun turnColDown(col: Int) {
        val mainCol = main.getCol(col)
        val reversedBack = back.cloneReversed()
        main.setCol(col, top.getCol(col))
        top.setCol(col, reversedBack.getCol(col))
        reversedBack.setCol(col, bottom.getCol(col))
        bottom.setCol(col, mainCol)
        back.setValues(reversedBack.cloneReversed().values)
        if (col == 0) {
            left.rotateClockwise()
        } else if (col == size - 1) {
            right.rotateCounterClockwise()
        }
    }

    fun face(newFace: FACE) {
        val old = mapOf(
            FACE.MAIN to main.clone(),
            FACE.RIGHT to right.clone(),
            FACE.BACK to back.clone(),
            FACE.LEFT to left.clone(),
            FACE.TOP to top.clone(),
            FACE.BOTTOM to bottom.clone()
        )
        main = getFace(newFace)
        right = old[getRightFaceOf(newFace)]!!
        back = old[getBackFaceOf(newFace)]!!
        left = old[getLeftFaceOf(newFace)]!!
        top = old[getTopFaceOf(newFace)]!!
        bottom = old[getBottomFaceOf(newFace)]!!
    }

    private fun getFace(face: FACE): RubikSide {
        return when (face) {
            FACE.MAIN -> main
            FACE.RIGHT -> right
            FACE.BACK -> back
            FACE.LEFT -> left
            FACE.TOP -> top
            FACE.BOTTOM -> bottom
        }
    }

    private fun getBackFaceOf(face: FACE): FACE {
        return when (face) {
            FACE.MAIN -> FACE.BACK
            FACE.RIGHT -> FACE.LEFT
            FACE.BACK -> FACE.MAIN
            FACE.LEFT -> FACE.RIGHT
            FACE.TOP -> FACE.BOTTOM
            FACE.BOTTOM -> FACE.TOP
        }
    }

    private fun getRightFaceOf(face: FACE): FACE {
        return when (face) {
            FACE.MAIN -> FACE.RIGHT
            FACE.RIGHT -> FACE.BACK
            FACE.BACK -> FACE.LEFT
            FACE.LEFT -> FACE.MAIN
            FACE.TOP, FACE.BOTTOM -> FACE.RIGHT
        }
    }

    private fun getLeftFaceOf(face: FACE): FACE {
        return when (face) {
            FACE.MAIN -> FACE.LEFT
            FACE.RIGHT -> FACE.MAIN
            FACE.BACK -> FACE.RIGHT
            FACE.LEFT -> FACE.BACK
            FACE.TOP, FACE.BOTTOM -> FACE.LEFT
        }
    }

    private fun getTopFaceOf(face: FACE): FACE {
        return when (face) {
            FACE.TOP -> FACE.BACK
            FACE.BOTTOM -> FACE.MAIN
            FACE.MAIN, FACE.RIGHT, FACE.BACK, FACE.LEFT -> FACE.TOP
        }
    }

    private fun getBottomFaceOf(face: FACE): FACE {
        return when (face) {
            FACE.TOP -> FACE.MAIN
            FACE.BOTTOM -> FACE.BACK
            FACE.MAIN, FACE.RIGHT, FACE.BACK, FACE.LEFT -> FACE.BOTTOM
        }
    }

    enum class FACE {
        MAIN, RIGHT, BACK, LEFT, TOP, BOTTOM
    }
}
