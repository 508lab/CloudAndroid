package com.example.cloud.fragment


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cloud.R
import com.example.cloud.adapter.HomeAdapter
import com.example.cloud.adapter.HomeFileAdapter
import com.example.cloud.bean.HomeBean
import com.example.cloud.network.HttpClient
import com.example.cloud.network.ResponseResult
import com.example.cloud.page.ShowFileActivity
import com.example.cloud.util.Constant
import com.example.cloud.util.JsonUtil
import com.kongzue.dialog.v3.WaitDialog

import kotlinx.android.synthetic.main.home_fragment.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe


class HomeFragment : Fragment() {


    private var sb = ""

    private val mData = ArrayList<HomeBean.DataBean.DBean>()

    private val homeAdapter = HomeAdapter(R.layout.item_home, mData)

    private val mFileData = ArrayList<HomeBean.DataBean.FBean>()

    private val homeFileAdapter = HomeFileAdapter(R.layout.item_home, mFileData)


    private val map = HashMap<String, String>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        EventBus.getDefault().register(this)

        initData()
        initList()
    }

    @Subscribe
    fun event(msg: String) {
        when (msg) {
            "Back" -> {
                var path = sb.split("/");
                path = listOf(path.subList(0, path.size - 1).joinToString("/"))
                sb = path[0]
                val map = HashMap<String, String>();
                map.put("q", sb)
                requestFile(map, mData, homeAdapter, mFileData, homeFileAdapter)

            }
        }
    }

    private fun initList() {
        homeAdapter.setOnItemChildClickListener { adapter, view, position ->
            when (view.id) {
                R.id.text_item -> {
                    WaitDialog.show(activity as AppCompatActivity, "请稍候...");
                    sb += "/" + mData.get(position).name;
                    map.put("q", sb)
                    requestFile(map, mData, homeAdapter, mFileData, homeFileAdapter)
                }
            }

        }

        homeFileAdapter.setOnItemChildClickListener { adapter, view, position ->
            when (view.id) {
                R.id.text_item -> {
                    val intent = Intent(context,ShowFileActivity::class.java)
                    intent.putExtra("filePath", Constant.PATH_URL+sb+mFileData.get(position).name)
                    startActivity(intent)
                }
            }
        }
    }


    private fun initData() {
        rv_home.layoutManager = LinearLayoutManager(context)
        rv_home.adapter = homeAdapter
        rv_home_file.layoutManager = LinearLayoutManager(context)
        rv_home_file.adapter = homeFileAdapter
        requestFile(map, mData, homeAdapter, mFileData, homeFileAdapter)
    }

    private fun requestFile(
        map: HashMap<String, String>,
        mData: ArrayList<HomeBean.DataBean.DBean>,
        homeAdapter: HomeAdapter,
        mFileData: ArrayList<HomeBean.DataBean.FBean>,
        homeFileAdapter: HomeFileAdapter
    ) {
        mData.clear()
        mFileData.clear()
        HttpClient().getPath(map, object : ResponseResult {
            override fun onSuccess(data: String?) {
                WaitDialog.dismiss()

                val homeBean = JsonUtil.parse(data, HomeBean::class.java)
                if (homeBean.status == 200) {
                    mData.addAll(homeBean.data.d)
                    homeAdapter.setNewData(mData)

                    mFileData.addAll(homeBean.data.f)
                    homeFileAdapter.setNewData(mFileData)
                }
            }

            override fun onError(error: String?) {
                Toast.makeText(context, "sever error:" + error, Toast.LENGTH_SHORT).show()

                WaitDialog.dismiss()

            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

}