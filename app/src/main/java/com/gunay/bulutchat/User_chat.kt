package com.gunay.bulutchat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.LinearLayout
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.gunay.bulutchat.adapter.RecyclerAdapter
import com.gunay.bulutchat.databinding.FragmentUserChatBinding
import com.gunay.bulutchat.model.Post


class User_chat : Fragment() {

    private lateinit var binding : FragmentUserChatBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var recyclerAdapter: RecyclerAdapter
    lateinit var postArrayList : ArrayList<Post>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserChatBinding.inflate(inflater, container, false)
        val view = binding.root
        setHasOptionsMenu(true)

        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth
        firestore = Firebase.firestore
        postArrayList = ArrayList<Post>()
        getData()
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerAdapter = RecyclerAdapter(postArrayList)
        binding.recyclerView.adapter = recyclerAdapter



        binding.sendButton.setOnClickListener {
            val postMap = hashMapOf<String,Any>()

            if (auth.currentUser != null){
                postMap.put("userEmail",auth.currentUser!!.email!!)
                postMap.put("message",binding.editText.text.toString())
                postMap.put("date",Timestamp.now())

                firestore.collection("Posts").add(postMap).addOnSuccessListener {

                    binding.editText.setText("")

                }.addOnFailureListener {
                    Toast.makeText(requireContext(),it.localizedMessage, Toast.LENGTH_LONG).show()
                }
            }
        }

    }

    fun getData(){
        firestore.collection("Posts").orderBy("date", Query.Direction.ASCENDING).addSnapshotListener { value, error ->
            if (error!=null){
                Toast.makeText(requireContext(),error.localizedMessage, Toast.LENGTH_LONG).show()
            }else{
                if (value != null){
                    if (!value.isEmpty){
                        val documents = value.documents
                        postArrayList.clear()

                        for (document in documents){

                            val message = document.get("message") as String
                            val userEmail = document.get("userEmail") as String


                            val post = Post(userEmail,message)
                            postArrayList.add(post)

                        }

                        recyclerAdapter.notifyDataSetChanged()
                        binding.recyclerView.scrollToPosition(postArrayList.size - 1)
                    }
                }
            }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.chat_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.back){
            auth.signOut()
            val action = User_chatDirections.actionUserChatToUserPage()
            view?.let {
                Navigation.findNavController(it).navigate(action)



            }
        }

        return super.onOptionsItemSelected(item)

    }


    override fun onDestroy() {
        super.onDestroy()
    }

}