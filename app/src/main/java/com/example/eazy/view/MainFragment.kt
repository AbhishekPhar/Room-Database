package com.example.eazy.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.eazy.R
import com.example.eazy.databinding.MainFragmentBinding
import com.example.eazy.util.ApplicationUtil
import java.io.IOException
import java.io.OutputStreamWriter


/**
 * A simple [Fragment] subclass.
 * Use the [MainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MainFragment : Fragment() {
    private var views: View? = null
    private var binding: MainFragmentBinding? = null



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<ViewDataBinding>(inflater,
            R.layout.main_fragment, container, false) as MainFragmentBinding?
        views = binding!!.root

        binding!!.userDetail.setOnClickListener(View.OnClickListener {
            ApplicationUtil.openClass(UserDetails::class.java, ApplicationUtil.homeactivity!!.supportFragmentManager,null)
        })
        binding!!.kycDetail.setOnClickListener(View.OnClickListener {
            ApplicationUtil.openClass(UserKYCDetails::class.java, ApplicationUtil.homeactivity!!.supportFragmentManager , null )
        })
        binding!!.profileDetail.setOnClickListener(View.OnClickListener {
            ApplicationUtil.openClass(UserCompleteProfile::class.java, ApplicationUtil.homeactivity!!.supportFragmentManager , null )
        })
        return views
    }

    private fun writeToFile(data: String, context: Context) {
        try {
            val outputStreamWriter = OutputStreamWriter(context.openFileOutput("config.txt", Context.MODE_PRIVATE))
            outputStreamWriter.write(data)
            outputStreamWriter.close()
        } catch (e: IOException) {
            Log.e("Exception", "File write failed: " + e.toString())
        }
    }


    override fun onResume() {
        super.onResume()
        Log.d("AbhiPhar","----OnResume()")
    }
}