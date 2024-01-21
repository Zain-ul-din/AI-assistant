package com.randoms.ai_assistant.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class BindAbleActivity<T>: AppCompatActivity() {
    protected var binding: T? = null;

    abstract fun init(): T;

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        super.onCreate(savedInstanceState);
        binding = init()
        start()
    }

    open fun start() {}
}
