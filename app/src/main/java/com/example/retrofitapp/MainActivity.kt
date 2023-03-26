package com.example.retrofitapp

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.retrofitapp.databinding.ActivityMainBinding
import com.example.retrofitapp.databinding.PostingDialogBinding
import kotlinx.coroutines.launch
import retrofit2.http.Body
import kotlin.math.log

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var PostAdapter:PostAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        PostAdapter = PostAdapter()
        binding.RV.layoutManager = LinearLayoutManager(this)
        binding.RV.adapter = PostAdapter
        getAllPosts()
        binding.FABAddPost.setOnClickListener {
            val dialog = Dialog(this)
            val Binding = PostingDialogBinding.inflate(LayoutInflater.from(this))
            dialog.setContentView(Binding.root)
            Binding.btnCancel.setOnClickListener {
                dialog.dismiss()
            }
            Binding.btnPost.setOnClickListener {
                if (Binding.etPostTitle.text.isNotEmpty()&&Binding.etPostBody.text.isNotEmpty()) {
                    val title = Binding.etPostTitle.toString()
                    val body = Binding.etPostBody.toString()
                    val ID =Binding.etPostID.text.toString().toInt()
                    makePost(title,body,ID)
                    Toast.makeText(this, "Post done successfully", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                }else
                    Toast.makeText(this, "Title and body can not be empty", Toast.LENGTH_SHORT).show()
            }
            dialog.show()
        }
    }
    private fun getAllPosts(){
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                val response=RetrofitInstance.retrofit.gatAllPosts()

                if(response.isSuccessful&&response.body()!=null) {
                    PostAdapter.differ.submitList(response.body())
                    Log.d("response", "successfully called ${response.body()}")
                }else
                    Log.d("response","error in ${response.code()}")
            }
            }
    }
    private fun makePost(title:String,body: String,Id:Int){
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                val post=PostItem(body,0,title,Id)
                val response=RetrofitInstance.retrofit.posting(post)

                if(response.isSuccessful&&response.body()!=null)
                    Log.d("post response ","OurPost: ${post.body}")
                else
                    Log.d("post error ","Error: ${response.code()}")

            }
        }
    }
}