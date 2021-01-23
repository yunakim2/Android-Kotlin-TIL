package com.example.recyclerview_ex.recyclerview_multi


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.recyclerview_ex.HorizontalItemDecorator
import com.example.recyclerview_ex.R
import com.example.recyclerview_ex.VerticalItemDecorator
import kotlinx.android.synthetic.main.activity_main.*


class MultiviewActivity : AppCompatActivity() {
    lateinit var multiAdapter: MultiviewAdpater
    val datas = mutableListOf<MultiData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_multiview)
        initRecycler()
    }
    private fun initRecycler() {
        multiAdapter = MultiviewAdpater(this)
        rv_profile.adapter = multiAdapter
        rv_profile.addItemDecoration(VerticalItemDecorator(10))
        rv_profile.addItemDecoration(HorizontalItemDecorator(10))


        datas.apply {
            add(MultiData(image = R.drawable.profile1, name = "mary", age = 24, multi_type3))
            add(MultiData(image = R.drawable.profile3, name = "jenny", age = 26, multi_type2))
            add(MultiData(image = R.drawable.profile2, name = "jhon", age = 27, multi_type1))
            add(MultiData(image = R.drawable.profile5, name = "ruby", age = 21, multi_type2))
            add(MultiData(image = R.drawable.profile4, name = "yuna", age = 23, multi_type3))

            multiAdapter.datas = datas
            multiAdapter.notifyDataSetChanged()

        }

    }
}