package com.example.baseklibrary.utils

import android.content.Context
import java.io.IOException
import java.security.KeyManagementException
import java.security.KeyStore
import java.security.KeyStoreException
import java.security.NoSuchAlgorithmException
import java.security.cert.CertificateException
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import javax.net.ssl.*

/**
 * Created by wangqiang on 2019/5/20.
 */
object HttpsUtils {

    private fun getWrappedTrustManagers(trustManagers: Array<TrustManager>): Array<TrustManager> {

        val originalTrustManager = trustManagers[0] as X509TrustManager

        return arrayOf(

            object : X509TrustManager {

                override fun getAcceptedIssuers(): Array<X509Certificate> {

                    return originalTrustManager.acceptedIssuers

                }

                override fun checkClientTrusted(certs: Array<X509Certificate>, authType: String) {

                    try {

                        originalTrustManager.checkClientTrusted(certs, authType)

                    } catch (e: CertificateException) {

                        e.printStackTrace()

                    }

                }

                override fun checkServerTrusted(certs: Array<X509Certificate>, authType: String) {

                    try {

                        originalTrustManager.checkServerTrusted(certs, authType)

                    } catch (e: CertificateException) {

                        e.printStackTrace()

                    }

                }

            })

    }

    fun getSSLSocketFactory_Certificate(
        context: Context,
        keyStoreType: String?,
        keystoreResId: Int
    ): SSLSocketFactory? {
        var keyStoreType = keyStoreType

        try {
            val cf = CertificateFactory.getInstance("X.509")

            val caInput = context.resources.openRawResource(keystoreResId)

            val ca = cf.generateCertificate(caInput)

            caInput.close()

            if (keyStoreType == null || keyStoreType.length == 0) {

                keyStoreType = KeyStore.getDefaultType()

            }

            val keyStore = KeyStore.getInstance(keyStoreType)

            keyStore.load(null, null)

            keyStore.setCertificateEntry("ca", ca)

            val tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm()

            val tmf = TrustManagerFactory.getInstance(tmfAlgorithm)

            tmf.init(keyStore)

            val wrappedTrustManagers = getWrappedTrustManagers(tmf.trustManagers)

            val sslContext = SSLContext.getInstance("TLS")

            sslContext.init(null, wrappedTrustManagers, null)

            return sslContext.socketFactory
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: CertificateException) {
            e.printStackTrace()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        } catch (e: KeyStoreException) {
            e.printStackTrace()
        } catch (e: KeyManagementException) {
            e.printStackTrace()
        }

        return null
    }
}
