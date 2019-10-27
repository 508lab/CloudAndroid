package com.example.cloud.adapter

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.LayoutRes
import androidx.annotation.Nullable
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.cloud.R
import com.example.cloud.bean.HomeBean
import kotlinx.android.synthetic.main.home_fragment.*


class HomeAdapter(layoutRes: Int, data: List<HomeBean.DataBean.DBean>) :
    BaseQuickAdapter<HomeBean.DataBean.DBean, BaseViewHolder>(layoutRes, data) {

    override fun convert(helper: BaseViewHolder?, item: HomeBean.DataBean.DBean?) {
        helper?.setText(R.id.text_item, item?.name)
        helper?.addOnClickListener(R.id.text_item)
    }


}