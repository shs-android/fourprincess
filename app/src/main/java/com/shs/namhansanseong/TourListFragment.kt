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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shs.namhansanseong.databinding.ItemPlaceListBinding
import kotlin.properties.Delegates

/**
 * A simple [Fragment] subclass.
 *
 */
class TourListFragment : Fragment() {

    private val places: List<Place> = listOf(
        Place(
            id = 1,
            name = "산성로터리",
            description = "출발 지역",
            image = R.drawable.one,
            lat = 37.478313,
            lng = 127.184057
        ),
        Place(
            id = 2,
            name = "북문",
            description = "북문은 전승문(全勝門)이라 부른다.\n" +
                    "남한산성 북쪽 해발 365m 지점에 있다 이 문을 통해 \n" +
                    "나라에 세금으로 바치는 곡식을 운반하였다.",
            image = R.drawable.two,
            lat = 37.481738,
            lng = 127.184763
        ),
        Place(
            id = 3,
            name = "서문",
            description = "서문은 우익문(右翼門)이라 부른다.\n" +
                    "1637년 1월 30일 인조가 세자와 함께 청나라 진영으로 들어가 항복할 때 이 문을 통과했다고 한다.\n" +
                    "서쪽 경사면이 가파르다 그리하여 물자를 옮기기에는 힘이 들었다.\n" +
                    "하지만 서울 광나루와 송파나루 방면에서 산성으로 진입하는 가장 빠른 길이었다\n",
            image = R.drawable.three,
            lat = 37.484931,
            lng = 127.175803
        ),
        Place(
            id = 4,
            name = "수어장대",
            description = "외부 정면에 ‘수어장대(守禦將臺)’라고 쓴 현판이,\n" +
                    "안쪽에는 ‘무망루(無忘樓)’라고 쓴 현판이 걸려 있다.\n" +
                    "1624년(인조 2) 남한산성을 축조할 때 지은 4개의 장대 중 유일하게 남아 있는 중요한 건물이다.",
            image = R.drawable.four,
            lat = 37.479815,
            lng = 127.176524
        ),
        Place(
            id = 5,
            name = "영춘정",
            description = "영춘정(迎春亭)은 ‘봄을 맞이하는 정자’라는 뜻이다.\n" +
                    "왼쪽으로 성남시를, 오른쪽으로 송파구를 포함한 서울시 일대를 한 눈에 조망할 수 있는 전망이 좋은 곳이다.",
            image = R.drawable.five,
            lat = 37.468615,
            lng = 127.168306
        ),
        Place(
            id = 6,
            name = "남문",
            description = "남문은 지화문(至和門)이라 부른다.\n" +
                    "남한산성 서남쪽 해발 370m 지점에 있다. \n" +
                    "남한산성의 사대문 중 가장 크고 웅장하며 병자호란으로 인조가 산성으로 피신할 때 이 문을 통과하였다.",
            image = R.drawable.six,
            lat = 37.473156,
            lng = 127.181141
        )
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<com.shs.namhansanseong.databinding.FragmentTourListBinding>(
            inflater, R.layout.fragment_tour_list, container, false
        )

        binding.testButton.setOnClickListener {
            clickOpenCamera()
        }

        binding.recyclerView.adapter = PlaceAdapter().apply {
            list = places
            listener = {
                findNavController().navigate(TourListFragmentDirections.actionTourListFragmentToTourDetailFragment(it))
            }
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        return binding.root
    }


    fun clickOpenCamera() {
        findNavController().navigate(R.id.action_tourListFragment_to_stampCameraFragment)
    }

    private class PlaceAdapter : RecyclerView.Adapter<PlaceViewHolder>() {
        var list: List<Place> by Delegates.observable(arrayListOf()) { _, old, new ->
            if (old != new) {
                notifyDataSetChanged()
            }
        }

        var listener: ((Place) -> Unit)? = null

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewHolder {
            return ItemPlaceListBinding.inflate(LayoutInflater.from(parent.context), parent, false).let {
                PlaceViewHolder(it)
            }
        }

        override fun getItemCount(): Int {
            return list.size
        }

        override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {
            list.getOrNull(position)?.let { place ->
                listener?.let { listener ->
                    holder.itemView.setOnClickListener {
                        listener.invoke(place)
                    }
                }

                holder.bind(place)
            }
        }

    }

    private class PlaceViewHolder(val binding: ItemPlaceListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(place: Place) {
            binding.title = place.name
            binding.image.setImageResource(place.image)
        }
    }

}
