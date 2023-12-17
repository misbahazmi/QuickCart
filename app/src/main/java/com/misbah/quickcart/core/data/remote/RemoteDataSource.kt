package com.misbah.quickcart.core.data.remote

import com.misbah.quickcart.core.base.BaseDataSource
import javax.inject.Inject

/**
 * @author: Mohammad Misbah
 * @since: 26-Sep-2023
 * @sample: Technology Assessment for Sr. Android Role
 * Email Id: mohammadmisbahazmi@gmail.com
 * GitHub: https://github.com/misbahazmi
 * Expertise: Android||Java/Kotlin||Flutter
 */
class RemoteDataSource @Inject constructor(private val apiService: APIService) : BaseDataSource(){
    //suspend method to access data from API services
}