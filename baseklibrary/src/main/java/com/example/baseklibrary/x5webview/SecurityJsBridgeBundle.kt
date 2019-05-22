package com.example.baseklibrary.x5webview

import android.content.Context

/**
 * Created by wangqiang on 2019/5/22.
 */
abstract class SecurityJsBridgeBundle @Throws(Exception::class)
constructor(JsBlockName: String?, methodName: String?) {

    private val mContext: Context? = null
    var jsBlockName: String? = null
        private set
    val methodName: String? = null


    abstract fun onCallMethod()

    init {
        if (methodName == null) {
            throw Exception("methodName can not be null!")
        }

        if (JsBlockName != null) {
            this.jsBlockName = JsBlockName
        } else {
            this.jsBlockName = DEFAULT_JS_BRIDGE
        }


    }


    private fun injectJsMsgPipecode(data: Map<String, Any>?) {
        if (data == null) {
            return
        }
        val injectCode = "javascript:(function JsAddJavascriptInterface_(){ " +
                "if (typeof(window.jsInterface)!='undefined') {" +
                "console.log('window.jsInterface_js_interface_name is exist!!');}   " +
                "else {" +
                data[BLOCK] + data[METHOD] +
                "window.jsBridge = {" +
                "onButtonClick:function(arg0) {" +
                "return prompt('MyApp:'+JSON.stringify({obj:'jsInterface',func:'onButtonClick',args:[arg0]}));" +
                "}," +

                "onImageClick:function(arg0,arg1,arg2) {" +
                "prompt('MyApp:'+JSON.stringify({obj:'jsInterface',func:'onImageClick',args:[arg0,arg1,arg2]}));" +
                "}," +
                "};" +
                "}" +
                "}" +
                ")()"
    }

    companion object {


        ///////////////////////////////////////////////////////////////////////////////
        //add js
        private val DEFAULT_JS_BRIDGE = "JsBridge"
        val METHOD = "method"
        val BLOCK = "block"
        val CALLBACK = "callback"

        val PROMPT_START_OFFSET = "local_js_bridge::"


        private val standardMethodSignature: String?
            get() = null
    }


}
