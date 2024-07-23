package com.herdialfachri.rukaloumkm.ui.profile.tutorial

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.herdialfachri.rukaloumkm.R
import com.herdialfachri.rukaloumkm.databinding.FragmentCaraPakaiBinding
import com.herdialfachri.rukaloumkm.databinding.FragmentHakBinding

class CaraPakaiFragment : Fragment() {

    private var _binding: FragmentCaraPakaiBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCaraPakaiBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val bottomNav = activity?.findViewById<BottomNavigationView>(R.id.nav_view)
        bottomNav?.visibility = View.GONE
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbarCara.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
        binding.howToRegister.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_caraPakaiFragment_to_caraDaftarFragment)
        )
        binding.howToLogin.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_caraPakaiFragment_to_caraLoginFragment)
        )
        binding.howToBuy.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_caraPakaiFragment_to_caraBeliFragment)
        )
        binding.howToReset.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_caraPakaiFragment_to_resetFragment)
        )
        binding.howToTambah.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_caraPakaiFragment_to_tambahFragment)
        )
        binding.howToHubungi.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_caraPakaiFragment_to_hubungiFragment)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}