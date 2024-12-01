package com.herdialfachri.rukaloumkm.ui.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.herdialfachri.rukaloumkm.R
import com.herdialfachri.rukaloumkm.data.DataClass
import com.herdialfachri.rukaloumkm.data.MyAdapter

class HomeFragment : Fragment() {

    private lateinit var fab: FloatingActionButton
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MyAdapter
    private lateinit var searchView: SearchView
    private lateinit var tvSelengkapnya: TextView
    private lateinit var rightText: TextView  // Tambahkan ini
    private val viewModel: HomeViewModel by viewModels()
    private var dataList = mutableListOf<DataClass>()
    private var originalList = mutableListOf<DataClass>()

    // Fungsi onCreateView dipanggil untuk menginflate layout Fragment
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    // Fungsi onViewCreated dipanggil setelah View dibuat
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerView)
        fab = view.findViewById(R.id.fab)
        searchView = view.findViewById(R.id.search)
        tvSelengkapnya = view.findViewById(R.id.tvMore)
        rightText = view.findViewById(R.id.rightText)  // Tambahkan ini

        // Mengatur layout manager untuk RecyclerView
        val gridLayoutManager = GridLayoutManager(requireContext(), 2)
        recyclerView.layoutManager = gridLayoutManager

        setupRecyclerView()
        setupViewModel()

        // Memantau status autentikasi pengguna
        val auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        updateFabVisibility(currentUser)

        // Menambahkan listener untuk perubahan status autentikasi
        auth.addAuthStateListener { firebaseAuth ->
            updateFabVisibility(firebaseAuth.currentUser)
        }

        // Mengatur aksi klik untuk TextView tvSelengkapnya
        tvSelengkapnya.setOnClickListener {
            val url = "https://www.pemdespalasarigirang.id/#posts"
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }

        // Mengatur aksi klik untuk FloatingActionButton fab
        fab.setOnClickListener {
            val intent = Intent(requireContext(), UploadActivity::class.java)
            startActivity(intent)
        }

        // Mengatur listener untuk SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchList(newText)
                return true
            }
        })
    }

    // Fungsi untuk mengupdate visibilitas FloatingActionButton berdasarkan status pengguna
    private fun updateFabVisibility(user: FirebaseUser?) {
        fab.visibility = if (user != null) View.VISIBLE else View.GONE
    }

    // Fungsi onResume dipanggil saat Fragment menjadi aktif kembali
    override fun onResume() {
        super.onResume()
        viewModel.refreshData() // Memastikan data diperbarui saat fragment aktif kembali
    }

    // Fungsi untuk mengatur RecyclerView dan adapter
    private fun setupRecyclerView() {
        adapter = MyAdapter(requireContext(), dataList)
        recyclerView.adapter = adapter
    }

    // Fungsi untuk mengatur ViewModel dan mengamati perubahan data
    private fun setupViewModel() {
        viewModel.products.observe(viewLifecycleOwner) { products ->
            dataList.clear()
            dataList.addAll(products)
            originalList.clear()
            originalList.addAll(products)
            adapter.notifyDataSetChanged()
            rightText.text = "${products.size} Makanan"  // Tambahkan ini
        }
    }

    // Fungsi untuk mencari data berdasarkan teks yang diinput di SearchView
    private fun searchList(text: String?) {
        val searchList = mutableListOf<DataClass>()
        text?.let {
            for (dataClass in originalList) {
                if (dataClass.dataTitle?.toLowerCase()?.contains(it.toLowerCase()) == true) {
                    searchList.add(dataClass)
                }
            }
        }
        dataList.clear()
        dataList.addAll(searchList)
        adapter.notifyDataSetChanged()
    }

    // Fungsi onDestroyView dipanggil saat View dari Fragment dihancurkan
    override fun onDestroyView() {
        super.onDestroyView()
        // Memastikan listener dihapus saat Fragment dihancurkan
        FirebaseAuth.getInstance().removeAuthStateListener { /* listener */ }
    }
}
