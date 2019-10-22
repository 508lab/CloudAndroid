package com.example.cloud.page

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.cloud.MainActivity
import com.example.cloud.R
import com.example.cloud.network.HttpClient
import com.example.cloud.util.PreferencesUtil
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private fun initView() {
        val that = this;
        login.setOnClickListener {
            var url = urlText.text.toString();
            var pwd = pwdText.text.toString();
            val hashMap = HashMap<String, String>();
            hashMap.put("password", pwd);
            HttpClient().httpPost("$url/login", hashMap) { data ->
                run {
                    var res = JSONObject(data.toString());
                    var status = res.get("status");
                    if (status.toString().toInt() === 200) {
                        var token = res.get("data").toString();
//                        startActivity(Intent(that, MainActivity::class.java));
                    }
                }
            };
        };
    }

}
