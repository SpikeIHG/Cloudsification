package com.ihg.cloudsification.fragments

import android.app.AlertDialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ihg.cloudsification.R
import com.ihg.cloudsification.adapter.CareerManager
import com.ihg.cloudsification.adapter.LifeManager
import com.ihg.cloudsification.adapter.SharedViewModel
import org.eazegraph.lib.charts.PieChart
import org.eazegraph.lib.charts.ValueLineChart
import org.eazegraph.lib.models.PieModel
import org.eazegraph.lib.models.ValueLinePoint
import org.eazegraph.lib.models.ValueLineSeries
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class LifeFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var lifeManager: LifeManager
    private lateinit var sharedViewModel: SharedViewModel
    private  lateinit var  careerManager: CareerManager
    private  lateinit var mPieChart: PieChart
    private lateinit var cloud_count : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_life, container, false)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifeManager = LifeManager(requireContext())
        careerManager = CareerManager(requireContext())
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        cloud_count = view.findViewById(R.id.count_cloud)
        cloud_count.text = "你一共记录了"+ careerManager.getAllAtlasNum() +"朵云，种类分布如下"
        val overall_time = view.findViewById<TextView>(R.id.overall)
        val days = lifeManager.getDaydiff(LocalDate.now())
        if(days.second == -1L)
            overall_time.text = "今天是拾云的第(记不清了)天（其实是遇到bug了）"
        else
            overall_time.text = "今天是拾云的第"+days.second+"天"
        sharedViewModel.geneCountLiveData.observe(viewLifecycleOwner, Observer { dcount ->
            // 更新 TextView
            Log.d("SHA","Do the share change")
            overall_time.text = "今天是拾云的第"+dcount+"天"
        })

         mPieChart = view.findViewById<PieChart>(R.id.piechart)
        val color = arrayOf(
            "#BDB5D7",
            "#A0CDCC",
            "#F4BAAF",
            "#84BB9F",
            "#FAEEC7",
            "#CC5F5A",
            "#B3CAD8"
        )
        careerManager.getAllGeneNumArray() .forEachIndexed { index,value ->
            mPieChart.addPieSlice(PieModel(value.first, value.second.toFloat(), Color.parseColor(color[index % 7])))
        }


        val mCubicValueLineChart = view.findViewById(R.id.cubiclinechart) as ValueLineChart

        val series = ValueLineSeries()
       //
 //       series.color = -0xa9480f
        series.color =  R.color.okcomputer

        val month_count = view.findViewById<TextView>(R.id.record_month_count)

        val confirm_btn = view.findViewById<Button>(R.id.confirmMonth)
        val month_now = LocalDate.now()
        val owm = month_now.toString()
        val myseries = mutableListOf<ValueLinePoint>()


        val share_btn = view.findViewById<ImageButton>(R.id.share)
        share_btn.setOnClickListener {
            val clipboard = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("Copied Link", "https://spikeihg.github.io/2024/09/02/%E4%BA%91%E5%90%96/")
            clipboard.setPrimaryClip(clip)

            // 显示提示信息
            Toast.makeText(context, "分享链接已复制到剪切板  ^ω^", Toast.LENGTH_SHORT).show()


        }

        for(index in 11 downTo 0)
        {
            val months = month_now.minusMonths(index.toLong())
            myseries.add(ValueLinePoint(months.format(DateTimeFormatter.ofPattern("yyyy-MM")), lifeManager.Monthdiff(months).toFloat()))
        }

        val lastword = view.findViewById<ImageButton>(R.id.imageButton)
        lastword.setOnClickListener {
            val dialogView: View = layoutInflater.inflate(R.layout.dialog_last_word, null)
            val dialog = AlertDialog.Builder(context)
                .setView(dialogView)  // 设置自定义布局
                .create()



            dialog.show()


        }



        confirm_btn.setOnClickListener {
            val cc = month_count.text.toString()
            Log.d("SS", month_count.text.toString())
            if (cc == "") {
                lifeManager.putCountMonth(LocalDate.now(), 0L)
                myseries[11]=ValueLinePoint(owm.format(DateTimeFormatter.ofPattern("yyyy-MM")), 0f)
                series.series = myseries
                mCubicValueLineChart.clearChart()
                mCubicValueLineChart.addSeries(series)
                mCubicValueLineChart.startAnimation()


            } else {
                lifeManager.putCountMonth(LocalDate.now(), cc.toLong())
                myseries[11]=ValueLinePoint(owm.format(DateTimeFormatter.ofPattern("yyyy-MM")), cc.toFloat())
                series.series = myseries
                mCubicValueLineChart.clearChart()
                mCubicValueLineChart.addSeries(series)
                mCubicValueLineChart.startAnimation()

            }
        }
        series.series = myseries
        mCubicValueLineChart.addSeries(series)

       /* series.addPoint(ValueLinePoint("Jan", 2.4f))
        series.addPoint(ValueLinePoint("Feb", 60f))
        series.addPoint(ValueLinePoint("Mar", .4f))
        series.addPoint(ValueLinePoint("Apr", 1.2f))
        series.addPoint(ValueLinePoint("Mai", 2.6f))
        series.addPoint(ValueLinePoint("Jun", 1.0f))
        series.addPoint(ValueLinePoint("Jul", 3.5f))
        series.addPoint(ValueLinePoint("Aug", 2.4f))
        series.addPoint(ValueLinePoint("Sep", 2.4f))
        series.addPoint(ValueLinePoint("Oct", 3.4f))
        series.addPoint(ValueLinePoint("Nov", .4f))
        series.addPoint(ValueLinePoint("Dec", 1.3f))*/

        mCubicValueLineChart.startAnimation()






    }

    override fun onStart() {
        super.onStart()
        Log.d("RR","this is the time to st")

    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onResume() {
        super.onResume()
        val time_now = lifeManager.getDaydiff(LocalDate.now())
        if(time_now.first)
            sharedViewModel.setTimeDiff(time_now.second.toString())

        val color = arrayOf(
            "#BDB5D7",
            "#A0CDCC",
            "#F4BAAF",
            "#84BB9F",
            "#FAEEC7",
            "#CC5F5A",
            "#B3CAD8"
        )
        mPieChart.clearChart()
        careerManager.getAllGeneNumArray() .forEachIndexed { index,value ->
            mPieChart.addPieSlice(PieModel(value.first, value.second.toFloat(), Color.parseColor(color[index % 7])))
        }

        Log.d("RR","this is the time to resume")

        cloud_count.text = "你一共记录了"+ careerManager.getAllAtlasNum() +"朵云，种类分布如下"

    }

    companion object {

        @JvmStatic
        fun newInstance() =
            LifeFragment().apply {

            }
    }
}
