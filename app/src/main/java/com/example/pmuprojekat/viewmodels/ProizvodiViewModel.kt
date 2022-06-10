package com.example.pmuprojekat.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pmuprojekat.data.Category
import com.example.pmuprojekat.data.Proizvod

class ProizvodiViewModel: ViewModel() {
    val lstProizovdi= MutableLiveData<List<Proizvod>>()
    val mapProizvodi = MutableLiveData<Map<Int, Int>>()
    val proizvod= MutableLiveData<Proizvod>()
}