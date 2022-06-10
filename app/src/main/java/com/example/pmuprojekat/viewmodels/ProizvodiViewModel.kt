package com.example.pmuprojekat.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pmuprojekat.data.Category
import com.example.pmuprojekat.data.Proizvod

class ProizvodiViewModel: ViewModel() {
    val lstProizovdi= MutableLiveData<List<Proizvod>>()
    val proizvod= MutableLiveData<Proizvod>()
}