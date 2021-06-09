package com.example.eazy.util

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.eazy.R
import java.lang.Byte.decode
import java.lang.Integer.decode
import java.nio.charset.StandardCharsets
import java.security.spec.KeySpec
import java.util.*
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec
import java.util.Base64;

class ApplicationUtil {

    companion object {
        var homeactivity: AppCompatActivity? = null

        fun openClass(
                fragmentClass: Class<*>,
                fragmentManager: FragmentManager,
                bundle: Bundle?
        ) {
            try {
                val fragment = fragmentClass.newInstance() as Fragment
                fragment.arguments = bundle
                fragmentManager.beginTransaction().replace(R.id.content_frame, fragment)
                    .addToBackStack("BACKSTACK").commit()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        private const val SECRET_KEY = "my_super_secret_key_ho_ho_ho"
        private const val SALT = "ssshhhhhhhhhhh!!!!"

        @RequiresApi(Build.VERSION_CODES.O)
        fun encrypt(strToEncrypt: String): String? {
            try {
                val iv = byteArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
                val ivspec = IvParameterSpec(iv)
                val factory: SecretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
                val spec: KeySpec = PBEKeySpec(SECRET_KEY.toCharArray(), SALT.toByteArray(), 65536, 256)
                val tmp: SecretKey = factory.generateSecret(spec)
                val secretKey = SecretKeySpec(tmp.getEncoded(), "AES")
                val cipher: Cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
                cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivspec)
                return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.toByteArray(StandardCharsets.UTF_8)))
            } catch (e: java.lang.Exception) {
                println("Error while encrypting: $e")
            }
            return null
        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun decrypt(strToDecrypt: String?): String? {
            try {
                val iv = byteArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
                val ivspec = IvParameterSpec(iv)
                val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
                val spec: KeySpec = PBEKeySpec(SECRET_KEY.toCharArray(), SALT.toByteArray(), 65536, 256)
                val tmp = factory.generateSecret(spec)
                val secretKey = SecretKeySpec(tmp.encoded, "AES")
                val cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING")
                cipher.init(Cipher.DECRYPT_MODE, secretKey, ivspec)
                return String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)))
            } catch (e: java.lang.Exception) {
                println("Error while decrypting: $e")
            }
            return null
        }


    }
}