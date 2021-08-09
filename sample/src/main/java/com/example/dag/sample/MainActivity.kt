package com.example.dag.sample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.example.dag.sample.task.FirstTask
import com.example.dag.sample.task.FourthTask
import com.example.dag.sample.task.SecondTask
import com.example.dag.sample.task.ThirdTask
import com.tool.dag.task.StartTaskManager
import com.tool.dag.task.TaskCreator

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var tvStart = findViewById<TextView>(R.id.tvStart)
        tvStart.setOnClickListener {
            var taskCreator = TaskCreator.Builder().addTask(FirstTask())
                .addTask(ThirdTask()).after(FirstTask::class.java,SecondTask::class.java,FourthTask::class.java)
                .addTask(SecondTask()).after(FirstTask::class.java)
                .addTask(FourthTask()).after(FirstTask::class.java)
                .build()
            StartTaskManager().addContextAndTask(taskCreator).addCompleteListener {
                Log.d("StartTask:","complete")
            }.start()
            Log.d("StartTask:","click complete")
        }
    }
}