package com.example.warmupexample.warmup

import org.springframework.aop.support.AopUtils
import org.springframework.context.ApplicationContext

// Spring 컨텍스트에서 어노테이션이 붙은 클래스/메서드 탐색
class WarmupScanner(private val applicationContext: ApplicationContext) {
    data class WarmupTarget(val bean: Any, val methodName: String, val method: () -> Unit)

    fun scan(): List<WarmupTarget> {
        val warmups = mutableListOf<WarmupTarget>()
        val beanNames = applicationContext.beanDefinitionNames

        for (beanName in beanNames) {
            val bean = applicationContext.getBean(beanName)
            val targetClass = AopUtils.getTargetClass(bean)

            // 클래스에 @Warmup이 붙어있으면 모든 public method 추가
            if (targetClass.isAnnotationPresent(Warmup::class.java)) {
                for (method in targetClass.methods) {
                    if (method.declaringClass != Any::class.java && method.parameterCount == 0) { // 파라미터 없는것만 filtering
                        warmups.add(WarmupTarget(bean, method.name) {
                            method.invoke(bean)
                        })
                    }
                }
            }

            // 메서드에 @Warmup이 붙은 경우
            for (method in targetClass.declaredMethods) {
                val annotation = method.getAnnotation(Warmup::class.java)
                if (annotation != null && method.parameterCount == 0) { // 파라미터 없는것만 filtering
                    method.isAccessible = true
                    warmups.add(WarmupTarget(bean, method.name) {
                        method.invoke(bean)
                    })
                }

                if (annotation != null) { // 파라미터 있는것만 filtering
                    val args = WarmupMetaRegistry.getArgs(annotation.metaKey)
                    if (args != null) {
                        method.isAccessible = true
                        warmups.add(WarmupTarget(bean, method.name) {
                            method.invoke(bean, *args)
                        })
                    } else {
                        println("No args found for metaKey: ${annotation.metaKey}")
                    }
                }
            }
        }
        return warmups
    }
}