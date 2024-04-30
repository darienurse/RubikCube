package com.darienurse.rubixcube.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.darienurse.rubixcube.model.RubikCube

class RubikCubeViewModel(size: Int) : ViewModel() {

    private val _rubikCube: MutableLiveData<RubikCube> = MutableLiveData()
    val rubikCube: LiveData<RubikCube>
        get() = _rubikCube

    init {
        _rubikCube.value = RubikCube(size)
    }

    fun turnRowToRight(row: Int) {
        _rubikCube.value?.turnRowToRight(row)
        _rubikCube.value = _rubikCube.value
    }

    fun turnRowToLeft(row: Int) {
        _rubikCube.value?.turnRowToLeft(row)
        _rubikCube.value = _rubikCube.value
    }

    fun turnColUp(col: Int) {
        _rubikCube.value?.turnColUp(col)
        _rubikCube.value = _rubikCube.value
    }

    fun turnColDown(col: Int) {
        _rubikCube.value?.turnColDown(col)
        _rubikCube.value = _rubikCube.value
    }

    fun changeToFace(face: RubikCube.FACE) {
        _rubikCube.value?.face(face)
        _rubikCube.value = _rubikCube.value
    }
}