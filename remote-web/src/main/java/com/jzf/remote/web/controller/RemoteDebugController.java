package com.jzf.remote.web.controller;

import com.jzf.remote.core.JavaFileExecuter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jiazhifeng
 * @date 2021/11/24 14:10
 */
@RestController
@RequestMapping("/remote")
public class RemoteDebugController {

    /**
     * 简介：编写一段调试代码,上传到服务器后会直接运行，并把执行结果返回给调用方。
     * 1.调试代码可以使用到当前服务的上下文.
     * 2.调试代码可以调用到服务所有的类和依赖的jar包
     * 3.不侵入原有程序，即无须改动原程序的任何代码。
     * 4.不改变原有服务端程序的部署，不依赖任何第三方类库。
     * 5.不占用内存，每次执行execute()方法都会生成一个新的类加载器实例，因此同一个类可以实现重复加载,并在下次垃圾回收时卸载.
     * <p>
     * 使用场景:
     * 1.比如我写了一个service的方法,想在服务器上测试一下功能是否正常,可以在调试代码里根据Spring的上下文获取这个service的bean对象，然后调用方法。
     * 2.监控服务的内存占用,线程堆栈信息等等.
     * 3.可以把这段调式代码想象成打包部署时就已经存在项目中一样.
     *
     * @param className  待编译的Java文件名称
     * @param sourceCode 待编译的Java源码
     * @return java文件执行结果
     */
    @PostMapping("/executeJavaSourceCode")
    public String executeJavaSourceCode(String className, String sourceCode) throws Exception {
        return JavaFileExecuter.execute(className, sourceCode);
    }
}
