package com.example.taplayout_usingtabitem

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * A simple [Fragment] subclass.
 */
class Tab2Fragment : Fragment() {

    fun newInstant() : Tab2Fragment
    {
        val args = Bundle()
        val frag = Tab2Fragment()
        frag.arguments = args
        return frag
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tab2, container, false)
    }

}
