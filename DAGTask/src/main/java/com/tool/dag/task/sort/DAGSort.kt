package com.tool.dag.task.sort

import com.tool.dag.task.StartTask
import com.tool.dag.task.exception.StartTaskException
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by wxk on 2021/7/14.
 */
class DAGSort {
    companion object{
        fun sort(list: ArrayList<StartTask>): SortResult {
            var result = mutableListOf<StartTask>()
            var inQueue = LinkedList<StartTask>()
            var preCountMap = mutableMapOf<String, Int>()
            var taskChildMap = mutableMapOf<Class<out StartTask>,ArrayList<StartTask>>()
            list.forEach {
                if (it.getPreTaskSize() == 0) {
                    inQueue.offer(it)
                }else{
                    preCountMap[it.getTaskName()] = it.getPreTaskSize()
                }
            }


            list.forEach {task->
                task.getPreTask()?.forEach {
                    var taskList = taskChildMap[it]
                    if (taskList == null){
                        taskList = arrayListOf()
                    }
                    taskList.add(task)
                    taskChildMap[it] = taskList
                }
            }

            while (!inQueue.isEmpty()){
                val task = inQueue.pop()
                result.add(task)
                val taskClass = task::class.java
                taskChildMap[taskClass]?.forEach {
                    val key = it.getTaskName()
                    var count = preCountMap[key]?:0
                    count--
                    if (count == 0){
                        inQueue.offer(it)
                    }
                    preCountMap[key] = count
                }
            }

            if (result.size != list.size){
                throw StartTaskException("cant sort by DAG list size is ${list} result  is ${result}}")
            }
            return SortResult(result,taskChildMap)
        }

        fun getReadyTask(task:StartTask,map:Map <Class<out StartTask>, ArrayList<StartTask>>):List<StartTask>{
            var readyTask = mutableListOf<StartTask>()
            var tasks = map[task::class.java]
            tasks?.forEach {
                it.removePreTask(task::class.java)
                if (it.checkAllPreTaskFinish() && !it.isFinish()){
                    readyTask.add(it)
                }
            }
            return readyTask
        }
    }

}