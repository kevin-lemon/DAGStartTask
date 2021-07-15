# DAGStartTask
### 利用有向无环图实现启动初始化任务的有序异步加载框架
#### 使用示例：
```
//以application的onCreate中初始化任务为例
class app : Application() {
    override fun onCreate() {
        super.onCreate()
        var taskCreator = TaskCreator.Builder().addTask(FirstTask())
            .addTask(ThirdTask()).after(FirstTask::class.java,SecondTask::class.java, FourthTask::class.java)
            .addTask(SecondTask()).after(FirstTask::class.java)
            .addTask(FourthTask()).after(FirstTask::class.java)
            .build()
        StartTaskManager.addContextAndTask(taskCreator).addCompleteListener {
            Log.d("StartTask:","complete")
        }.start()
        Log.d("StartTask:","Application onCreate complete")
    }
}
```
