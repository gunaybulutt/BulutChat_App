package com.gunay.bulutchat

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.gunay.bulutchat.databinding.FragmentUserRegisterBinding


class User_register : Fragment() {



    private lateinit var binding : FragmentUserRegisterBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserRegisterBinding.inflate(inflater, container, false)
        val view = binding.root
        return view


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth

        binding.register.setOnClickListener {
            val email = binding.EmailRegisterText.text.toString()
            val password = binding.passwordRegisterText.text.toString()
            val password2 = binding.passwordRegisterText2.text.toString()

            try {
                if (email.equals("") || password.equals("") || password2.equals("")){
                    Toast.makeText(requireContext(),"Information entry error", Toast.LENGTH_LONG).show()
                }

                if ((password != password2)){
                    Toast.makeText(requireContext(),"error entering password", Toast.LENGTH_LONG).show()
                }else{
                    auth.createUserWithEmailAndPassword(email,password).addOnSuccessListener {
                        val action = User_registerDirections.actionUserRegisterToUserChat()
                        Navigation.findNavController(view).navigate(action)

                    }.addOnFailureListener {
                        Toast.makeText(requireContext(),it.localizedMessage, Toast.LENGTH_LONG).show()
                    }
                }
            }catch (e: Exception){
                e.printStackTrace()
            }


        }

        binding.back.setOnClickListener {
            val action = User_registerDirections.actionUserRegisterToUserPage()
            Navigation.findNavController(view).navigate(action)
        }

    }

}

