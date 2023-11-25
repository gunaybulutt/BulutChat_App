package com.gunay.bulutchat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.gunay.bulutchat.databinding.FragmentUserPageBinding
import com.gunay.bulutchat.databinding.FragmentUserRegisterBinding


class User_page : Fragment() {

    lateinit var binding: FragmentUserPageBinding
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentUserPageBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth

        val currentUser = auth.currentUser
        if (currentUser != null){
            val action = User_pageDirections.actionUserPageToUserChat()
            Navigation.findNavController(view).navigate(action)
        }



        binding.login.setOnClickListener {
            val email = binding.EmailText.text.toString()
            val password = binding.passwordText.text.toString()

            if (email.equals("") || password.equals("") ){
                Toast.makeText(requireContext(),"Information entry error", Toast.LENGTH_LONG).show()
            }else{
                auth.signInWithEmailAndPassword(email,password).addOnSuccessListener {
                    val action = User_pageDirections.actionUserPageToUserChat()
                    Navigation.findNavController(view).navigate(action)
                }.addOnFailureListener {
                    Toast.makeText(requireContext(),it.localizedMessage, Toast.LENGTH_LONG).show()
                }
            }

        }


        binding.userPageRegister.setOnClickListener {
            val action = User_pageDirections.actionUserPageToUserRegister()
            Navigation.findNavController(it).navigate(action)
        }
    }


}