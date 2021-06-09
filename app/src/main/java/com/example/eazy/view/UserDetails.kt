package com.example.eazy.view

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.example.eazy.R
import com.example.eazy.data.AppDatabase
import com.example.eazy.data.AppExecutors
import com.example.eazy.databinding.FragmentUserDetailsBinding
import com.example.eazy.model.User
import com.example.eazy.util.ApplicationUtil
import java.io.IOException
import java.io.OutputStreamWriter

class UserDetails : Fragment() {
    private var views: View? = null
    private var binding: FragmentUserDetailsBinding? = null
    private var user: User?=null
    private var countryString: String?=null
    private var stateString: String?=null
    private lateinit var setDataHandler: Handler
    private var mDb: AppDatabase? = null
    val country = arrayOf("Country1", "Country2",
            "Country3", "Country4")

    val state = arrayOf("State1", "State2",
            "State3", "State4")


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<ViewDataBinding>(inflater,
                R.layout.fragment_user_details, container, false) as FragmentUserDetailsBinding?
        views = binding!!.root
        mDb = AppDatabase.getInstance(ApplicationUtil.homeactivity)

        setUpHandler()
        setupUI()


        return views
    }

    private fun setupUI() {

        val aa: ArrayAdapter<Any?>? = context?.let { ArrayAdapter<Any?>(it, R.layout.support_simple_spinner_dropdown_item, country) }
        aa!!.setDropDownViewResource(android.R.layout.simple_list_item_1)
        binding!!.state.setAdapter(aa)

        val bb: ArrayAdapter<Any?>? = context?.let { ArrayAdapter<Any?>(it, R.layout.support_simple_spinner_dropdown_item, state) }
        aa.setDropDownViewResource(android.R.layout.simple_list_item_1)
        binding!!.country.setAdapter(bb)

        binding!!.country.setOnItemSelectedListener(object : OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View, position: Int, id: Long) {
                countryString = country.get(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })

        binding!!.state.setOnItemSelectedListener(object : OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View, position: Int, id: Long) {
                stateString = state.get(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })

        binding!!.button.setOnClickListener(View.OnClickListener {

            val user = stateString?.let { it1 ->
                countryString?.let { it2 ->
                    User(
                            1001,
                            binding!!.editName.getText().toString(),
                            binding!!.editEmail.getText().toString(),
                            binding!!.editNumber.getText().toString(),
                            binding!!.editDob.getText().toString(),
                            binding!!.editAddress.getText().toString(),
                            binding!!.editCity.getText().toString(),
                            it1,
                            it2,
                            binding!!.editPincode.getText().toString(),
                    )
                }
            }

            Thread(Runnable {
                try {
                    AppExecutors.getInstance().diskIO().execute(Runnable {
                        val persons: List<User> = mDb!!.userDetailDao().loadAllPersons()
                        if (persons.size == 0) {
                            mDb!!.userDetailDao().insertPerson(user)
                            setDataHandler.sendEmptyMessage(1);
                        } else {
                            mDb!!.userDetailDao().updatePerson(user)
                            setDataHandler.sendEmptyMessage(2);
                        }
                    })
                } catch (e: Exception) {
                    setDataHandler.sendEmptyMessage(3);
                    print(e.message)
                }
            }).start()
        })
    }


    private fun setUpHandler() {

        Thread(Runnable {
            AppExecutors.getInstance().diskIO().execute(Runnable {
                try {
                    user = mDb!!.userDetailDao().loadPersonById(1001)
                    setDataHandler.sendEmptyMessage(4);
                } catch (e: Exception) {
                    setDataHandler.sendEmptyMessage(5);
                    print(e.message)
                }
            })
        }).start()

        setDataHandler = Handler(Handler.Callback { message ->
            if (message.what == 1) {
                Toast.makeText(activity, "User Inserted", Toast.LENGTH_LONG).show()
            } else if (message.what == 2) {
                Toast.makeText(activity, "User Updated", Toast.LENGTH_LONG).show()
            } else if (message.what == 4) {

                for ((index, value) in state.withIndex()) {
                    if (user != null && user!!.state.equals(state.get(index))) {
                        binding!!.state.setSelection(index)
                    }
                }

                for ((index, value) in country.withIndex()) {
                    if (user != null && user!!.country.equals(country.get(index))) {
                        binding!!.country.setSelection(index)
                    }
                }

                binding!!.setDatamodel(user)
            } else {
                Toast.makeText(activity, "Something went wrong", Toast.LENGTH_LONG).show()
            }
            false
        })
    }


    override fun onResume() {
        super.onResume()
        Log.d("AbhiPhar", "----OnResume()")
    }
}