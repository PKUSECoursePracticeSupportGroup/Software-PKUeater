package com.example.psycho.ui.dashboard

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.psycho.R
import com.example.psycho.data.Data
import com.example.psycho.databinding.FragmentDashboardBinding
import com.example.psycho.ui.log.LogActivity
import com.example.psycho.ui.log.SelectFoodActivity
import java.util.*

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val userData = Data
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("Q","1")
        when(requestCode){
            100->if (resultCode==Activity.RESULT_OK){
                flash()
            }
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val chooseBreakfast: ImageView = binding.includeDailyShower.imageBgBreakfastBorder
        val chooseLunch: ImageView = binding.includeDailyShower.imageBgLunchBorder
        val chooseDinner: ImageView = binding.includeDailyShower.imageBgDinnerBorder
        val chooseDessert: ImageView = binding.includeDailyShower.imageBgDessertBorder

        chooseBreakfast.setOnClickListener {
            //val act: FragmentActivity? = getActivity()
            // Russian
            val intent: Intent = Intent(context!!, SelectFoodActivity::class.java)
            intent.putExtra("meal",1)
            startActivityForResult(intent,100)
        }
        chooseLunch.setOnClickListener{
            //val act: FragmentActivity? = getActivity()
            // Russian
            val intent: Intent = Intent(context!!, SelectFoodActivity::class.java)
            intent.putExtra("meal",2)
            startActivityForResult(intent,100)
        }
        chooseDinner.setOnClickListener{
            //val act: FragmentActivity? = getActivity()
            // Russian
            val intent: Intent = Intent(context!!, SelectFoodActivity::class.java)
            intent.putExtra("meal",3)
            startActivityForResult(intent,100)
        }
        chooseDessert.setOnClickListener{

            // Russian
            val intent: Intent = Intent(context!!, SelectFoodActivity::class.java)
            intent.putExtra("meal",4)
            startActivityForResult(intent,100)
        }
        flash()
        val button1=binding.includeDailyShower.button6
        val button2=binding.includeDailyShower.button7
        val button3=binding.includeDailyShower.button8
        val button4=binding.includeDailyShower.button9

        button1.setOnClickListener {
            userData.deleteLog(1)
            flash()
        }
        button2.setOnClickListener {
            userData.deleteLog(2)
            flash()
        }
        button3.setOnClickListener {
            userData.deleteLog(3)
            flash()
        }
        button4.setOnClickListener {
            userData.deleteLog(4)
            flash()
        }

        return binding.root

    }
    fun flash()
    {
        val textTodayBreakfast: TextView = binding.includeDailyShower.textBreakfastToday
        val textTodayLunch: TextView = binding.includeDailyShower.textLunchToday
        val textTodayDinner: TextView = binding.includeDailyShower.textDinnerToday
        val textTodayDessert: TextView = binding.includeDailyShower.textDessertToday
        val todayCalorie: TextView = binding.textTodayKal

        /*
        var breakfast:List<String> = userData.getDietLog(1)
        var lunch:List<String> = userData.getDietLog(2)
        var dinner:List<String> = userData.getDietLog(3)
        var snack:List<String> = userData.getDietLog(4)
        */
        var breakfast:List<String> = userData.getLogFromServer(1)
        var lunch:List<String> = userData.getLogFromServer(2)
        var dinner:List<String> = userData.getLogFromServer(3)
        var snack:List<String> = userData.getLogFromServer(4)

        if(breakfast.size > 0){
            textTodayBreakfast.setText(breakfast.toString())
        }
        else
        {
            textTodayBreakfast.setText("记录完整三餐，查看全天饮食评价")
        }

        if(lunch.size > 0){
            textTodayLunch.setText(lunch.toString())
        }
        else
        {
            textTodayLunch.setText("记录完整三餐，查看全天饮食评价")
        }
        if(dinner.size > 0){
            textTodayDinner.setText(dinner.toString())
        }
        else
        {
            textTodayDinner.setText("记录完整三餐，查看全天饮食评价")
        }

        if(snack.size > 0){
            textTodayDessert.setText(snack.toString())
        }
        else
        {
            Log.d("Flush","In")
            textTodayDessert.setText("记录完整三餐，查看全天饮食评价")
        }
        val Todaycal:Int = userData.getCalorieFromServer()
        todayCalorie.setText(Todaycal.toString()+"Kal")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}