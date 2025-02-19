package com.example.psycho.ui.setting

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.*
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.psycho.R
import com.example.psycho.data.Data
import com.example.psycho.databinding.FragmentSettingBinding
import com.example.psycho.resource.AcceptableAdapter
import com.example.psycho.resource.AvoidanceAdapter
import com.example.psycho.ui.login.LoginActivity
import com.example.psycho.ui.setting.usage.CountActivity
import java.util.*


class SettingFragment : Fragment() {
    private var _data = Data
    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!

    // Russian
    private var avoidanceList: List<String>? = null
    private var acceptableList: List<String>? = null
    private var recyclerViewAcceptable: RecyclerView? = null
    private var acceptableAdapter: AcceptableAdapter? = null
    private var recyclerViewAvoidance: RecyclerView? = null
    private var avoidanceAdapter: AvoidanceAdapter? = null
    private var buttonCount:ImageButton?=null
    private var textWeight: TextView?=null
    private var textHeight: TextView? = null
    private var buttonLogout:Button?=null
    private var buttonLog:Button? = null
    private var buttonVisibleW:ImageButton? = null
    private var textVisToolW:TextView? = null
    private var buttonVisibleH:ImageButton? = null
    private var textVisToolH:TextView? = null
    fun  flash()
    {
        if (_data.getTimerFlag()) {
            if (_data.getAvoidanceChange()) {
            }
            if (_data.getModifyFlag()) {
                if (_data.getWeightVisible()) {
                    textWeight!!.text =
                        _data.getTrueWeight(context!!).toFloat().toString().format("%.1f")
                    textVisToolW!!.text = "visible"
                    buttonVisibleW!!.setImageDrawable(resources.getDrawable(R.drawable.icon_visible_on))
                } else {
                    textVisToolW!!.text = "invisible"
                    buttonVisibleW!!.setImageDrawable(resources.getDrawable(R.drawable.icon_visible_off))
                    textWeight!!.text = "***"
                }
                if (_data.getHeightVisible()) {
                    textVisToolH!!.text = "visible"
                    buttonVisibleH!!.setImageDrawable(resources.getDrawable(R.drawable.icon_visible_on))
                    textHeight!!.text = _data.getTrueHeight(context!!).toString()
                } else {
                    textVisToolH!!.text = "invisible"
                    buttonVisibleH!!.setImageDrawable(resources.getDrawable(R.drawable.icon_visible_off))
                    textHeight!!.text = "***"
                }
                Log.d("Flush", "Flush")
            }
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 111 && resultCode == Activity.RESULT_OK) {
           flash()
        }
    }
    // fix try
    // 尝试1：把整个集成，每次点击都是完全刷新
    fun bindAvoidanceAdapter(context: Context) {
        avoidanceList = _data.getAvoidanceType(context)
        avoidanceAdapter = AvoidanceAdapter(dataList = avoidanceList)
        recyclerViewAvoidance?.adapter = avoidanceAdapter

        acceptableList = _data.getAcceptable(context)
        acceptableAdapter = AcceptableAdapter(dataList = acceptableList)
        recyclerViewAcceptable?.adapter = acceptableAdapter

        avoidanceAdapter?.mOnRecyclerViewItemClick = object :
            AvoidanceAdapter.OnRecyclerViewItemClick<String> {
            override fun onItemClick(view: View?, t: String?, position: Int) {
                when (view?.id) {
                    R.id.content_avoidance -> {
                        t?.let {
                            _data.deleteAvoidance(context,t)
                        }
                        bindAvoidanceAdapter(context)
                    }
                }
            }
        }
        acceptableAdapter?.mOnRecyclerViewItemClick = object :
            AcceptableAdapter.OnRecyclerViewItemClick<String> {
            override fun onItemClick(view: View?, t: String?, position: Int) {
                when (view?.id) {
                    R.id.content_acceptable -> {
                        t?.let {
                            _data.addAvoidance(context,t)
                        }
                        bindAvoidanceAdapter(context)
                    }
                }
            }
        }
    }

    /**
     * 重启应用
     * @param context
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val settingViewModel =
            ViewModelProvider(this).get(SettingViewModel::class.java)


        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val textView: TextView = binding.textSetting
        settingViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        val imageView:ImageView = binding.imageTopBg
        //val head:ImageButton=binding.imageButton
        buttonCount = binding.buttonSetting
        textWeight = binding.textWeight
        textHeight = binding.textHeight
        buttonLogout=binding.logout
        buttonLog= binding.buttonLog
        buttonVisibleW = binding.buttonEyeWeight
        textVisToolW = binding.textViewToolWeightVis
        buttonVisibleH= binding.buttonEyeHeight
        textVisToolH= binding.textViewToolHeightVis




        Log.d("Flush", "Flush")

        // Roulette Avoidance
        // 把后面的List换成获取忌口清单的函数，要求返回值为List<String>
        // Roulette Acceptable
        // 把后面的List换成获取忌口清单的函数，要求返回值为List<String>
        recyclerViewAvoidance = binding.recyclerAvoidance
        recyclerViewAvoidance?.layoutManager =
            StaggeredGridLayoutManager(3,
                StaggeredGridLayoutManager.HORIZONTAL)
        recyclerViewAcceptable = binding.recyclerAcceptable
        recyclerViewAcceptable?.layoutManager =
            StaggeredGridLayoutManager(3,
                StaggeredGridLayoutManager.HORIZONTAL)
        bindAvoidanceAdapter(context!!)

        buttonLogout!!.setOnClickListener {
            _data.setHeightInvisible()
            _data.setWeightInvisible()
            _data.deleteUser(context!!)
            val act : FragmentActivity? =getActivity()
            val intentL:Intent = Intent(act,LoginActivity::class.java)
            startActivity(intentL)
            activity?.finish()
        }

        buttonCount!!.setOnClickListener(View.OnClickListener() {
            //val act : FragmentActivity? =getActivity()
            val intent:Intent = Intent(context!!,CountActivity::class.java)
            println("跳转到身高体重调整界面")
            startActivityForResult(intent, 111)
        })

        buttonVisibleW!!.setOnClickListener(View.OnClickListener() {
            if(textVisToolW!!.text=="visible") {
                _data.setWeightInvisible()
            }
            else if(textVisToolW!!.text=="invisible") {
                _data.setWeightVisible()
            }
            flash()
        })

        buttonVisibleH!!.setOnClickListener(View.OnClickListener() {
            if(textVisToolH!!.text=="visible") {
                _data.setHeightInvisible()
            }
            else if(textVisToolH!!.text=="invisible") {
                _data.setHeightVisible()
            }
            flash()
        })

        flash()
        /*
        buttonLog.setOnClickListener {
            val act : FragmentActivity? =getActivity()
            Log.d("日志","点击成功")
            val intent:Intent = Intent(act, LogActivity::class.java)
            act?.startActivity(intent)

        }
         */

        return root
    }
    /*
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activityCount:ImageButton = requireActivity().findViewById(R.id.button_setting)
        activityCount.setOnClickListener(View.OnClickListener {
            val intent = Intent(requireActivity(),CountActivity::class.java)
        })
    }
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}