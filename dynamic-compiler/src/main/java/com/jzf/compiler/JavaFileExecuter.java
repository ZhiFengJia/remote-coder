package com.jzf.compiler;

import java.lang.reflect.Method;

/**
 * class执行工具
 *
 * @author jiazhifeng
 * @date 2021/11/24 12:42
 */
public class JavaFileExecuter {

    /**
     * 执行外部传过来的代表一个Java类的Byte数组<br>
     * 将输入类的byte数组中代表java.lang.System的CONSTANT_Utf8_info常量修改为劫持后的HackSystem类
     * 执行方法为该类的static main(String[] args)方法，输出结果为该类向System.out/err输出的信息
     *
     * @param className
     * @param sourceCode
     * @return 执行结果
     */
    public static String execute(String className, String sourceCode) throws Exception {
        DynamicCompiler compiler = new DynamicCompiler();
        Class<?> clazz = compiler.compileAndLoad(className, sourceCode);
        compiler.closeFileManager();

        JZFSystem system = null;
        try {
            Method method = clazz.getMethod("main", String[].class);
            system = (JZFSystem) method.invoke(null, (Object[]) new String[] { null });
            return system.getBufferString();
        } catch (Throwable e) {
            if (system == null) {
                system = new JZFSystem();
            }
            e.printStackTrace(system.out);
        }
        return system.getBufferString();
    }
}