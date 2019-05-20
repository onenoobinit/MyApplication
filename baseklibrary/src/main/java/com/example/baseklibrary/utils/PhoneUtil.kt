package com.example.baseklibrary.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import java.io.File

/**
 * Created by wangqiang on 2019/5/20.
 */
class PhoneUtil {
    companion object {
        private var lastClickTime: Long = 0

        /**
         * 调用系统发短信界面
         *
         * @param activity    Activity
         * @param phoneNumber 手机号码
         * @param smsContent  短信内容
         */
        fun sendMessage(activity: Context, phoneNumber: String?, smsContent: String) {
            if (phoneNumber == null || phoneNumber.length < 4) {
                return
            }
            val uri = Uri.parse("smsto:$phoneNumber")
            val it = Intent(Intent.ACTION_SENDTO, uri)
            it.putExtra("sms_body", smsContent)
            it.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            activity.startActivity(it)
        }


        /**
         * 判断是否为连击
         *
         * @return boolean
         */
        fun isFastDoubleClick(): Boolean {
            val time = System.currentTimeMillis()
            val timeD = time - lastClickTime
            if (0 < timeD && timeD < 500) {
                return true
            }
            lastClickTime = time
            return false
        }

        /**
         * 获取手机型号
         *
         * @param context 上下文
         * @return String
         */
        fun getMobileModel(context: Context): String {
            try {
                return Build.MODEL
            } catch (e: Exception) {
                return "未知"
            }

        }

        /**
         * 获取手机品牌
         *
         * @param context 上下文
         * @return String
         */
        fun getMobileBrand(context: Context): String {
            try {
                return Build.BRAND
            } catch (e: Exception) {
                return "未知"
            }

        }


        /**
         * 拍照打开照相机！
         *
         * @param requestcode 返回值
         * @param activity    上下文
         * @param fileName    生成的图片文件的路径
         */
        fun toTakePhoto(requestcode: Int, activity: Activity, fileName: String) {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            intent.putExtra("camerasensortype", 2)// 调用前置摄像头
            intent.putExtra("autofocus", true)// 自动对焦
            intent.putExtra("fullScreen", false)// 全屏
            intent.putExtra("showActionIcons", false)
            try {//创建一个当前任务id的文件然后里面存放任务的照片的和路径！这主文件的名字是用uuid到时候在用任务id去查路径！
                val file = File(fileName)
                if (!file.exists()) {//如果这个文件不存在就创建一个文件夹！
                    file.mkdirs()
                }
                val uri = Uri.fromFile(File(fileName))
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
                activity.startActivityForResult(intent, requestcode)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }


        /**
         * 打开相册
         *
         * @param requestcode 响应码
         * @param activity    上下文
         */
        fun toTakePicture(requestcode: Int, activity: Activity) {
            val intent = Intent(Intent.ACTION_PICK, null)
            intent.setDataAndType(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                "image/*"
            )
            activity.startActivityForResult(intent, requestcode)
        }
    }
}