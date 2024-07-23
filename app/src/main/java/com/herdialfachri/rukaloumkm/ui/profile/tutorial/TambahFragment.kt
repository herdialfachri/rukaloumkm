package com.herdialfachri.rukaloumkm.ui.profile.tutorial

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.herdialfachri.rukaloumkm.R
import com.herdialfachri.rukaloumkm.databinding.FragmentCaraBeliBinding
import com.herdialfachri.rukaloumkm.databinding.FragmentCaraLoginBinding
import com.herdialfachri.rukaloumkm.databinding.FragmentResetBinding
import com.herdialfachri.rukaloumkm.databinding.FragmentTambahBinding

class TambahFragment : Fragment() {

    private var _binding: FragmentTambahBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTambahBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val bottomNav = activity?.findViewById<BottomNavigationView>(R.id.nav_view)
        bottomNav?.visibility = View.GONE
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbarTambah.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}