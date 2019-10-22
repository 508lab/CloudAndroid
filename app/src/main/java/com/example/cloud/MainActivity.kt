package com.example.cloud

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.cloud.fragment.HomeFragment
import com.example.cloud.fragment.PersonalFragment
import com.example.cloud.page.LoginActivity
import com.example.cloud.util.PreferencesUtil
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val fragmentList = ArrayList<androidx.fragment.app.Fragment>();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        startActivity(Intent(this, LoginActivity::class.java));
        initListener();
    }

    private fun initListener() {
        homeBtn.setOnClickListener {
            supportFragmentManager.inTransaction {
                replaceFragment(HomeFragment())
            }
            setBarBtn(homeBtn, meBtn);
        }
        meBtn.setOnClickListener {
            supportFragmentManager.inTransaction {
                replaceFragment(PersonalFragment())
            }
            setBarBtn(meBtn, homeBtn);
        }
    }

    private fun initView() {
        supportFragmentManager.inTransaction {
            replaceFragment(HomeFragment())
        }

    }

    private fun setBarBtn(btnc: Button, btn: Button) {
        btnc.setTextColor(Color.parseColor("#337ab7"));
        btn.setTextColor(Color.BLACK);
    }

    inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> Unit) {
        val fragmentTransaction = beginTransaction()
        fragmentTransaction.func()
        fragmentTransaction.commit()
    }

    fun FragmentActivity.replaceFragment(fragment: Fragment) {
        supportFragmentManager.inTransaction { replace(R.id.framePage, fragment) }
    }
}