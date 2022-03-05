package uz.context.noteapproomdatabase.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import uz.context.noteapproomdatabase.R
import uz.context.noteapproomdatabase.data.User
import uz.context.noteapproomdatabase.viewmodel.UserViewModel

class UpdateFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()
    private lateinit var mUserViewModel: UserViewModel
    private lateinit var updateFirstName: EditText
    private lateinit var updateLastName: EditText
    private lateinit var updateAge: EditText
    private lateinit var updateBtn: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_update, container, false)
        initViews(view)
        return view

    }

    private fun initViews(view: View) {
        mUserViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        updateFirstName = view.findViewById(R.id.editUpdateFirstName)
        updateLastName = view.findViewById(R.id.editUpdateLastName)
        updateAge = view.findViewById(R.id.editUpdateAge)
        updateBtn = view.findViewById(R.id.btnUpdate)

        updateFirstName.setText(args.currentUser.firstName)
        updateLastName.setText(args.currentUser.lastName)
        updateAge.setText(args.currentUser.age.toString())

        updateBtn.setOnClickListener {
            updateItem()
        }
        setHasOptionsMenu(true)
    }

    private fun updateItem() {
        val firstName = updateFirstName.text.toString()
        val lastName = updateLastName.text.toString()
        val age = Integer.parseInt(updateAge.text.toString())

        if (inputCheck(firstName, lastName, updateAge.text)) {
            val updatedUser = User(args.currentUser.id, firstName, lastName, age)
            mUserViewModel.updateUser(updatedUser)
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
            toast("Successfully added")
        } else {
            toast("Please enter some data!")
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_delete) {
            deleteUser()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteUser() {
        val builder = AlertDialog.Builder(requireContext())
        builder.apply {
            setPositiveButton("Yes") { _, _ ->
                mUserViewModel.deleteUser(args.currentUser)
                findNavController().navigate(R.id.action_updateFragment_to_listFragment)
                toast("Successfully removed: ${args.currentUser.firstName}")
            }
            setNegativeButton("No") { _, _ -> }
            setTitle("Delete ${args.currentUser.firstName}?")
            setMessage("Are you sure you want to delete ${args.currentUser.firstName}?")
            create()
            show()
        }
    }

    private fun inputCheck(firstName: String, lastName: String, age: Editable): Boolean {
        return !(TextUtils.isEmpty(firstName) || TextUtils.isEmpty(lastName) || age.isEmpty())
    }

    private fun toast(msg: String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }
}