package com.shs.namhansanseong


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController

/**
 * A simple [Fragment] subclass.
 *
 */
class TourListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<com.shs.namhansanseong.databinding.FragmentTourListBinding>(
            inflater, R.layout.fragment_tour_list, container, false)

        binding.testButton.setOnClickListener {
            clickOpenCamera()
        }
        return binding.root
    }


    fun clickOpenCamera() {
        findNavController().navigate(R.id.action_tourListFragment_to_stampCameraFragment)
    }
}
