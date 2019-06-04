package com.ityzp.something.utils

import android.content.Context
import android.graphics.Bitmap
import com.example.baseklibrary.utils.ToastUtil
import com.ityzp.something.SomeThingApp
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.WXAPIFactory

/**
 * Created by wangqiang on 2019/6/4.
 */
object WxShareUtils {
    private var api: IWXAPI? = null
    private val bitmap: Bitmap? = null

    fun shareWeb(context: Context, webUrl: String, title: String, content: String, bitmap: Bitmap, friend: Boolean?) {
        api = WXAPIFactory.createWXAPI(context, SomeThingApp.APP_ID)
        api!!.registerApp(SomeThingApp.APP_ID)
        if (!api!!.isWXAppInstalled) {
            ToastUtil.show(context, "您还没有安装微信")
            return
        }
        val webpageObject = WXWebpageObject()
        webpageObject.webpageUrl = webUrl
        val msg = WXMediaMessage(webpageObject)
        msg.title = title
        msg.description = content
        val thumbBmp = Bitmap.createScaledBitmap(bitmap, 150, 150, true)
        msg.thumbData = WxShareUtil.bmpToByteArray(thumbBmp, true)
        /* bitmap.recycle();
        msg.setThumbImage(bitmap);*/

        val req = SendMessageToWX.Req()
        req.transaction = "sharenews"
        req.message = msg
        req.scene = if (friend!!) SendMessageToWX.Req.WXSceneTimeline else SendMessageToWX.Req.WXSceneSession
        api!!.sendReq(req)
    }
}