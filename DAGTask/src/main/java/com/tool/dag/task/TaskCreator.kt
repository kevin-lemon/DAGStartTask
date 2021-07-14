package com.tool.dag.task

/**
 * Created by wxk on 2021/7/14.
 */
class TaskCreator {


    fun start() {

    }

    class Builder{


        fun <T:Task>addTask(task:T):Builder{

            return this
        }

        fun affter(taskName: String):Builder{

            return this
        }
    }
}