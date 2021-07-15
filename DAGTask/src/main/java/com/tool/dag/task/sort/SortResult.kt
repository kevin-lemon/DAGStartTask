package com.tool.dag.task.sort

import com.tool.dag.task.StartTask

/**
 * Created by wxk on 2021/7/15.
 */
data class SortResult (val taskList:List<StartTask>, var childMap:Map<Class<out StartTask>,ArrayList<StartTask>>)