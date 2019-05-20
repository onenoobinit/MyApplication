package com.example.baseklibrary.utils

import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import android.widget.Toast

/**
 * Created by wangqiang on 2019/5/20.
 */
class ExitActivityUtil : AppCompatActivity() {
    private var exitTime: Long = 0

    //重写 onKeyDown方法
    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_DOWN) {
            //两秒之内按返回键就会退出
            if (System.currentTimeMillis() - exitTime > 2000) {
                Toast.makeText(applicationContext, "再按一次退出程序", Toast.LENGTH_SHORT).show()
                exitTime = System.currentTimeMillis()
            } else {
                finish()
                System.exit(0)
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}