package com.bongpal.aeonsendtrunorder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.bongpal.aeonsendtrunorder.databinding.ActivityTrunOrderBinding

class TurnOrderActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTrunOrderBinding
    private var players = ""
    private var veiledDummy = arrayListOf<String>()
    private var dumpDummy = arrayListOf<String>()
    private val dumpDummyImage by lazy {
        arrayListOf(
            binding.ivFirstDumpCard,
            binding.ivSecondDumpCard,
            binding.ivThirdDumpCard,
            binding.ivFourthDumpCard,
            binding.ivFifthDumpCard,
            binding.ivSixthDumpCard,
        )
    }

    private val tag = "Turn_Activity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTrunOrderBinding.inflate(layoutInflater)
        players = intent.getIntExtra(extraCode, 0).toString()

        val view = binding.root

        setContentView(view)
        turnOredrSetting()
        drawCardClick()
        dumpDummyClick()

        binding.ivNowCard.setOnClickListener {
            Log.i(tag, "턴오더 더미 : ${veiledDummy}")
            Log.i(tag, "폐기 더미 : ${dumpDummy}")
        }


    }

    private fun turnOredrSetting() {
        when (players) {
            "2" -> veiledDummy += arrayListOf("1","1","2","2","N","N")
            "3" -> veiledDummy += arrayListOf("1","2","3","W","N","N")
            "4" -> veiledDummy += arrayListOf("1","2","3","4","N","N")
        }
        veiledDummy.shuffle()
    }

    private fun drawCardClick() {
        binding.btnDrawCard.setOnClickListener {
            if (veiledDummy.isEmpty()) {
                reset()
            } else {
                drawOne()
            }

            turnOrderDummyImageSynchro()
            dumpDummyImageSynchro()
        }
    }

    private fun dumpDummyClick() {
        dumpDummyImage.forEach {
            it.setOnClickListener {
                var index = dumpDummyImage.indexOf(it)

                Log.i(tag, "${dumpDummy[index]}")
                veiledDummy += dumpDummy[index]
                veiledDummy.shuffle()
                dumpDummy.removeAt(index)
                binding.ivNowCard.visibility = View.INVISIBLE
                turnOrderDummyImageSynchro()
                dumpDummyImageSynchro()
            }
        }
    }

    private fun turnOrderDummyImageSynchro() {
        when (veiledDummy.dummyCount()) {
            6 -> binding.btnDrawCard.setImageResource(R.drawable.dummy_six)
            5 -> binding.btnDrawCard.setImageResource(R.drawable.dummy_five)
            4 -> binding.btnDrawCard.setImageResource(R.drawable.dummy_four)
            3 -> binding.btnDrawCard.setImageResource(R.drawable.dummy_three)
            2 -> binding.btnDrawCard.setImageResource(R.drawable.dummy_two)
            1 -> binding.btnDrawCard.setImageResource(R.drawable.card_back)
            0 -> binding.btnDrawCard.setImageResource(R.drawable.dummy_empty)
        }
    }

    private fun dumpDummyImageSynchro(){
        for (i in 0 .. dumpDummyImage.lastIndex) {
            if (i < dumpDummy.size) {
                dumpDummyImage[i].visibility = View.VISIBLE
                dumpDummyImage[i].imageChange(dumpDummy[i])
            } else {
                dumpDummyImage[i].visibility = View.INVISIBLE
            }

        }
    }

    private fun ImageView.imageChange(v: String){
        this.visibility = View.VISIBLE
        when (v) {
            "1" -> this.setImageResource(R.drawable.one)
            "2" -> this.setImageResource(R.drawable.two)
            "3" -> this.setImageResource(R.drawable.three)
            "4" -> this.setImageResource(R.drawable.four)
            "W" -> this.setImageResource(R.drawable.wild)
            "N" -> this.setImageResource(R.drawable.nemesis)
        }


    }

    private fun ArrayList<String>.dummyCount(): Int {
        var count = 0

        this.forEach{ if (it != "0") count++ }
        return count
    }

    private fun reset() {
        binding.ivNowCard.visibility = View.INVISIBLE
        dumpDummyImage.forEach { it.visibility = View.INVISIBLE }
        dumpDummy.clear()
        veiledDummy.clear()
        turnOredrSetting()
    }

    private fun drawOne() {
        binding.ivNowCard.imageChange(veiledDummy.first())
        dumpDummy += veiledDummy.first()
        veiledDummy.remove(veiledDummy.first())
    }

}