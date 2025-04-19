package com.example.warmupexample.warmup

@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class Warmup(val metaKey: String = "")
