package com.tool.dag.task

import com.tool.dag.task.exception.StartTaskException
import com.tool.dag.task.sort.DAGSort
import com.tool.dag.task.sort.SortResult

/**
 * Created by wxk on 2021/7/14.
 */
class TaskCreator(sortResult: SortResult) {
    var taskList:List<StartTask>
    var childMap:Map<Class<out StartTask>,ArrayList<StartTask>>
    init {
      taskList = sortResult.taskList
      childMap = sortResult.childMap
    }

    class Builder{
        private var mTask : StartTask?=null
        private var taskList = ArrayList<StartTask>()
        private var checkSet = hashSetOf<Class<out StartTask>>()
        fun <T:StartTask>addTask(task:T):Builder{
            if (!checkSet.add(task::class.java)){
                throw StartTaskException("cant add the same name task")
            }
            mTask = task
            if (mTask != null){
                mTask?.let {
                    taskList.add(it)
                }
            }
            return this
        }

        fun  after(taskClass: Class<out StartTask>):Builder{
            if (mTask == null){
                throw NullPointerException("must call add before after")
            }
            mTask?.addPreTask(taskClass)
            return this
        }

        fun after(vararg taskClassArray: Class<out StartTask>):Builder{
            if (mTask == null){
                throw NullPointerException("must call add before after")
            }
            if (taskClassArray.isNotEmpty()){
                taskClassArray.iterator().forEach {
                    mTask?.addPreTask(it)
                }
            }
            return this
        }

        fun build():TaskCreator{
            checkSet.clear()
            val result = DAGSort.sort(taskList)
            return TaskCreator(result)
        }
    }
}