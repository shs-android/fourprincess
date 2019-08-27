package com.shs.namhansanseong


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.shs.namhansanseong.databinding.FragmentTourDetailBinding

/**
 * A simple [Fragment] subclass.
 *
 */
class TourDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentTourDetailBinding>(
            inflater, R.layout.fragment_tour_detail, container, false)

        return binding.root
    }


}