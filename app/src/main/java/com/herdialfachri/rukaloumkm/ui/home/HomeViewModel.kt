package com.herdialfachri.rukaloumkm.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.*
import com.herdialfachri.rukaloumkm.data.DataClass

class HomeViewModel : ViewModel() {

    // LiveData untuk menyimpan daftar produk
    private val _products = MutableLiveData<List<DataClass>>()
    val products: LiveData<List<DataClass>> get() = _products

    // Referensi ke database Firebase untuk node "Product"
    private val databaseReference: DatabaseReference = FirebaseDatabase.getInstance().getReference("Product")
    private val eventListener: ValueEventListener

    init {
        // EventListener untuk mendengarkan perubahan data pada node "Product"
        eventListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val productList = mutableListOf<DataClass>()
                for (itemSnapshot in snapshot.children) {
                    val dataClass = itemSnapshot.getValue(DataClass::class.java)
                    dataClass?.key = itemSnapshot.key
                    dataClass?.let { productList.add(it) }
                }
                _products.value = productList
            }

            override fun onCancelled(error: DatabaseError) {
                // Tangani kesalahan
            }
        }
        // Menambahkan event listener ke referensi database
        databaseReference.addValueEventListener(eventListener)
    }

    // Fungsi untuk menyegarkan data dengan mengambil data sekali dari database
    fun refreshData() {
        databaseReference.addListenerForSingleValueEvent(eventListener)
    }

    // Membersihkan event listener ketika ViewModel dihapus
    override fun onCleared() {
        super.onCleared()
        databaseReference.removeEventListener(eventListener)
    }
}
