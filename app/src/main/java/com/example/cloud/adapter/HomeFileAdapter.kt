package com.example.cloud.adapter

import android.view.View
import android.widget.TextView
import androidx.annotation.LayoutRes
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.cloud.R
import com.example.cloud.bean.HomeBean

class HomeFileAdapter(layoutRes: Int,data: List<HomeBean.DataBean.FBean>) : BaseQuickAdapter<HomeBean.DataBean.FBean,BaseViewHolder>(layoutRes,data) {
    override fun convert(helper: BaseViewHolder?, item: HomeBean.DataBean.FBean?) {
        val text = helper?.getView<TextView>(R.id.text_item)
        text?.setCompoundDrawablesRelativeWithIntrinsicBounds(R.mipmap.file, 0, 0,0)
        text?.text = item?.name

        helper?.addOnClickListener(R.id.text_item)
    }
}