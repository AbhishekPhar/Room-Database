package com.example.eazy.view

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.example.eazy.R
import com.example.eazy.data.AppDatabase
import com.example.eazy.data.AppExecutors
import com.example.eazy.databinding.FragmentUserCompleteProfileBinding
import com.example.eazy.model.User
import com.example.eazy.model.relations.UserAndKyc
import com.example.eazy.util.ApplicationUtil
import java.io.IOException
import java.io.OutputStreamWriter

class UserCompleteProfile : Fragment() {
    private var views: View? = null
    private var binding: FragmentUserCompleteProfileBinding? = null
    private var user: User? = null
    private var persons: UserAndKyc? = null
    private var mDb: AppDatabase? = null
    private lateinit var setDataHandler: Handler


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<ViewDataBinding>(inflater,
                R.layout.fragment_user_complete_profile, container, false) as FragmentUserCompleteProfileBinding?
        views = binding!!.root

        binding!!.download.setOnClickListener(View.OnClickListener {
            context?.let { writeToFile(persons.toString(), it) }

        })

        retrieveTasks()

        return views
    }

    private fun writeToFile(data: String, context: Context) {
        try {
            val outputStreamWriter = OutputStreamWriter(context.openFileOutput("abhi.txt", Context.MODE_PRIVATE))
            outputStreamWriter.write(data)
            outputStreamWriter.close()
        } catch (e: IOException) {
            Log.e("Exception", "File write failed: " + e.toString())
        }
    }


    private fun retrieveTasks() {
        mDb = AppDatabase.getInstance(ApplicationUtil.homeactivity)

        Thread(Runnable {
            try {
                AppExecutors.getInstance().diskIO().execute(Runnable {
                    persons = mDb!!.userDetailDao()?.getUserAndKycDetail(1001)
                    if(persons!= null)
                    {
                        setDataHandler.sendEmptyMessage(1);
                    }
                })
            }catch (e:Exception)
            {
                print(e.message)
                setDataHandler.sendEmptyMessage(0);
            }

        }).start()

        setDataHandler = Handler(Handler.Callback { message ->
            if (message.what == 1) {
                val homeWorksListDTO = persons
                binding!!.setDatamodel(homeWorksListDTO)
            } else {
                Toast.makeText(activity, "There is no User", Toast.LENGTH_LONG).show()
            }
            false
        })
    }

    override fun onResume() {
        super.onResume()
        Log.d("AbhiPhar", "----OnResume()")
    }
}