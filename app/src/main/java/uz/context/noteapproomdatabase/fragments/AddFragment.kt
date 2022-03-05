package uz.context.noteapproomdatabase.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import uz.context.noteapproomdatabase.R
import uz.context.noteapproomdatabase.data.User
import uz.context.noteapproomdatabase.viewmodel.UserViewModel

class AddFragment : Fragment() {

    private lateinit var mUserViewModel: UserViewModel
    private lateinit var addFirstName: EditText
    private lateinit var addLastName: EditText
    private lateinit var addAge: EditText
    private lateinit var addBtn: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add, container, false)
        initViews(view)
        return view
    }
    private fun initViews(view: View) {
        mUserViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        addFirstName = view.findViewById(R.id.addFirstName_et)
        addLastName = view.findViewById(R.id.addLastName_et)
        addAge = view.findViewById(R.id.addAge_et)
        addBtn = view.findViewById(R.id.addBtn)
        addBtn.setOnClickListener {
            insertDataToDatabase()
        }
    }
    private fun insertDataToDatabase() {
        val firstName = addFirstName.text.toString().trim()
        val lastName = addLastName.text.toString().trim()
        val age = addAge.text

        if (inputCheck(firstName,lastName,age)) {
            val user = User(0,firstName,lastName,Integer.parseInt(age.toString()))
            mUserViewModel.addUser(user)
            toast("Successfully added")
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        } else {
            toast("Please enter some data!")
        }
    }
    private fun inputCheck(firstName: String, lastName: String, age: Editable): Boolean {
        return !(TextUtils.isEmpty(firstName) || TextUtils.isEmpty(lastName) || age.isEmpty())
    }

    private fun toast(msg: String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }
}