package com.shs.namhansanseong


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.navArgs
import com.shs.namhansanseong.databinding.FragmentTourDetailBinding
import java.util.*

/**
 * A simple [Fragment] subclass.
 *
 */
class TourDetailFragment : Fragment() {
    private val args: TourDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val place = args.place

        val binding = DataBindingUtil.inflate<FragmentTourDetailBinding>(
            inflater, R.layout.fragment_tour_detail, container, false).apply {
            title.text = place.name
            description.text = place.description
            image.setImageResource(place.image)

            mapButton.setOnClickListener {
                val uri = String.format(Locale.ENGLISH, "geo:%f,%f", place.lat, place.lng)

                showMap(Uri.parse(uri))
            }
        }



        return binding.root
    }

    fun showMap(geoLocation: Uri) {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = geoLocation
        }
        if (intent.resolveActivity(requireActivity().packageManager) != null) {
            startActivity(intent)
        }
    }


}
