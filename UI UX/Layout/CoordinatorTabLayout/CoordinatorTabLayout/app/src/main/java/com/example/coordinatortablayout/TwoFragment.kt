package com.example.coordinatortablayout

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coordinatortablayout.RecyclerView.RecyclerData
import com.example.coordinatortablayout.RecyclerView.RecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_one.*
import kotlinx.android.synthetic.main.fragment_two.*

/**
 * A simple [Fragment] subclass.
 */
class TwoFragment : Fragment() {

    lateinit var rvAdapter: RecyclerViewAdapter
    val datas = mutableListOf<RecyclerData>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_two, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvAdapter  = RecyclerViewAdapter(view.context)
        rv_two.adapter = rvAdapter
        rv_two.layoutManager = LinearLayoutManager(view.context, RecyclerView.VERTICAL,false)

        loadDatas()
    }
    fun loadDatas()
    {
        datas.apply {
            add(RecyclerData(string= "text 1"))
            add(RecyclerData(string= "text 2"))
            add(RecyclerData(string= "text 3"))
            add(RecyclerData(string= "text 4"))
            add(RecyclerData(string= "text 5"))
            add(RecyclerData(string= "text 6"))
            add(RecyclerData(string= "text 7"))
            add(RecyclerData(string= "text 8"))
            add(RecyclerData(string= "text 9"))
            add(RecyclerData(string= "text 10"))
            add(RecyclerData(string= "text 11"))
            add(RecyclerData(string= "text 12"))
            add(RecyclerData(string= "text 13"))
            add(RecyclerData(string= "text 14"))
            add(RecyclerData(string= "text 15"))
            add(RecyclerData(string= "text 16"))
            add(RecyclerData(string= "text 17"))
            add(RecyclerData(string= "text 18"))
            add(RecyclerData(string= "text 19"))
            add(RecyclerData(string= "text 20"))
        }
        rvAdapter.datas = datas
        rvAdapter.notifyDataSetChanged()
    }

    fun newInstant() : TwoFragment
    {
        val args = Bundle()
        val frag = TwoFragment()
        frag.arguments = args
        return frag
    }

}
