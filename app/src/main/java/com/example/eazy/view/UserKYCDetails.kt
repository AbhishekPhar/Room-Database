package com.example.eazy.view

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.eazy.R
import com.example.eazy.data.AppDatabase
import com.example.eazy.data.AppExecutors
import com.example.eazy.databinding.FragmentkycdetailBinding
import com.example.eazy.model.User
import com.example.eazy.model.UserKycDetail
import com.example.eazy.util.ApplicationUtil

class UserKYCDetails : Fragment() {
    private var views: View? = null
    private var binding: FragmentkycdetailBinding? = null
    var intent: Intent? = null
    private var mDb: AppDatabase? = null
    private lateinit var setDataHandler: Handler
    private var persons: UserKycDetail? = null



    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<ViewDataBinding>(inflater,
            R.layout.fragmentkycdetail, container, false) as FragmentkycdetailBinding?
        views = binding!!.root
        mDb = AppDatabase.getInstance(ApplicationUtil.homeactivity)

        Thread(Runnable {
            try {
                AppExecutors.getInstance().diskIO().execute(Runnable {
                    persons = mDb!!.userKYCDao().loadPersonById(1001)
                    setDataHandler.sendEmptyMessage(1)
                })
            }catch (e:Exception){
                setDataHandler.sendEmptyMessage(7)
            }
        }).start()


        binding!!.button.setOnClickListener(View.OnClickListener {
            val user = ApplicationUtil.encrypt(binding!!.gstinNo.getText().toString())?.let { it1 ->
                ApplicationUtil.encrypt(binding!!.panNo.getText().toString())?.let { it2 ->
                    ApplicationUtil.encrypt(binding!!.aadhaarNo.getText().toString())?.let { it3 ->
                        ApplicationUtil.encrypt(binding!!.drivingLicence.getText().toString())?.let { it4 ->
                            ApplicationUtil.encrypt(binding!!.voterId.getText().toString())?.let { it5 ->
                                ApplicationUtil.encrypt(binding!!.upiId.getText().toString())?.let { it6 ->
                                    UserKycDetail(
                                            1001,
                                            it1,
                                            it2,
                                            it3,
                                            it4,
                                            it5,
                                            it6
                                    )
                                }
                            }
                        }
                    }
                }
            }

            var th = Thread(Runnable {
                try {
                    AppExecutors.getInstance().diskIO().execute(Runnable {

                        val persons: List<UserKycDetail> = mDb!!.userKYCDao().loadAllPersons()

                        if (persons.size == 0) {
                            mDb!!.userKYCDao().insertPerson(user)

                        } else {
                            mDb!!.userKYCDao().updatePerson(user)
                        }
                        setDataHandler.sendEmptyMessage(2)
                    })
                } catch (e: Exception) {
                    print(e.printStackTrace())
                }
            }).start()

        })

        setUpHandler()
        return views
    }

    private fun setUpHandler() {
        setDataHandler = Handler(Handler.Callback { message ->
            if (message.what == 1) {
                binding!!.setDatamodel(persons)
            }
            else if (message.what == 2) {
                Toast.makeText(activity, "KYC Detail Saved", Toast.LENGTH_LONG).show()
            }
            else {
                Toast.makeText(activity, "There is no User", Toast.LENGTH_LONG).show()
            }
            false
        })
    }

    override fun onResume() {
        super.onResume()
        Log.d("AbhiPhar","----OnResume()")
    }
}