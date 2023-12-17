package com.misbah.quickcart.notifications
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.misbah.quickcart.core.data.model.Product
import com.misbah.quickcart.core.data.storage.QuickCartDao
import com.misbah.quickcart.ui.utils.Constants
import com.nytimes.utils.AppLog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author: Mohammad Misbah
 * @since:  17-Dec-2023
 * @sample: Technology Assessment for Sr. Android Role
 * Email Id: mohammadmisbahazmi@gmail.com
 * GitHub: https://github.com/misbahazmi
 * Expertise: Android||Java/Kotlin||Flutter
 */
open class TaskDataRepository @Inject constructor(private val quickCartDao : QuickCartDao){
    var remainingTasks: LiveData<List<Product>>? = null
    fun deleteAllCompletedTasks(){
        //quickCartDao.deleteCompletedTasks()
    }

    fun getRemainingTaskList() :  LiveData<List<Product>> {
        getTasksRemainingTask()
        return  remainingTasks!!
    }

    fun getTasksRemainingTask() = CoroutineScope(Dispatchers.IO).launch {
        val currentTime = System.currentTimeMillis()
        val futureTime = System.currentTimeMillis() + Constants.TASK_REMINDER_TIME_INTERVAL
        //remainingTasks  =  quickCartDao.getTasksRemainingTask(true,currentTime,futureTime).asLiveData()
        AppLog.debugD("SIZE:::::: ${remainingTasks?.value?.size}")
    }

}