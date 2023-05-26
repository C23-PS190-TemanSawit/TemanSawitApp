package com.example.temansawit.di


import com.example.temansawit.data.Repository

object Injection {
    fun provideRepository(): Repository {
        return Repository.getInstance()
    }


}