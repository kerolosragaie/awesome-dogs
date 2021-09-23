package com.kerollosragaie.awesomedogs

import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import com.bumptech.glide.Glide
import com.kerollosragaie.awesomedogs.api.ApiRetriever
import com.kerollosragaie.awesomedogs.api.DogsData
import com.kerollosragaie.awesomedogs.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        //To animate background between gradients:
        backgroundAnimation()
        //To make api request:
        makeApiRequest()

        //now give animation to floating action button:
        binding.btFloatingAction.setOnClickListener {
            binding.btFloatingAction.animate().apply {
                rotationBy(360f)
                duration = 1000
            }.start()

            makeApiRequest()
            binding.ivRandomDog.visibility = View.GONE
        }
    }

    /**
     * To make background animation between
     * the three gradients in drawable
     * */
    private fun backgroundAnimation() {
        val animationDrawable: AnimationDrawable =
            binding.relativeLayout.background as AnimationDrawable
        animationDrawable.apply {
            setEnterFadeDuration(1000)
            setExitFadeDuration(3000)
            start()
        }
    }

    /**
     * To make an api request using Retrofit
     * get images only less than 4 MB
     * */
    private fun makeApiRequest() {
        val apiCall: Call<DogsData> = ApiRetriever().getRandDog()

        apiCall.enqueue(object : Callback<DogsData> {
            override fun onResponse(call: Call<DogsData>, response: Response<DogsData>) {
                try {
                    Log.d("Main", "Size ${response.body()?.fileSizeBytes}")
                    //stop downloading images that are more than 4 MB
                    //400_000 for 4 MB
                    if (response.body()!!.fileSizeBytes < 400_000) {
                        Glide.with(applicationContext).load(response.body()?.url)
                            .into(binding.ivRandomDog)
                        binding.ivRandomDog.visibility = View.VISIBLE
                    } else {
                        //will do in recursive till get an image size<4MB
                        makeApiRequest()
                    }
                } catch (e: Exception) {
                    //Log.e which is used for exceptions
                    Log.e("Main", "Error: ${e.message}")
                }
            }
            override fun onFailure(call: Call<DogsData>, t: Throwable) {
            }

        })

    }

}