package com.bongpal.aeonsendtrunorder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bongpal.aeonsendtrunorder.databinding.ActivityMainBinding

val extraCode = "playerCount"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val tag = "Main_Activity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)
        turnOredrStart()
    }

    private fun turnOredrStart() {
        val intent = Intent(this, TurnOrderActivity::class.java)

        binding.btnTwoPlayers.setOnClickListener {
            Log.i(tag, "btn two players click")
            intent.putExtra(extraCode, 2)
            startActivity(intent)
        }
        binding.btnThreePlayers.setOnClickListener {
            Log.i(tag, "btn three players click")
            intent.putExtra(extraCode, 3)
            startActivity(intent)
        }
        binding.btnFourPlayers.setOnClickListener {
            Log.i(tag, "btn four players click")
            intent.putExtra(extraCode, 4)
            startActivity(intent)
        }
    }

}
