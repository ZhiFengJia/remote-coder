package com.jzf.compiler;

import java.io.IOException;

import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;

/**
 * 输出字节码到JavaClassFile
 *
 * @author jiazhifeng
 * @date 2021/11/25 14:52
 */
public class ClassFileManager<M extends JavaFileManager> extends ForwardingJavaFileManager<M> {

    /**
     * 存储编译后的代码数据
     */
    private JavaFileObjectByteCode classJavaFileObject;

    protected ClassFileManager(M fileManager) {
        super(fileManager);
    }

    /**
     * 给编译器提供JavaClassObject，编译器会将编译结果写进去
     * 
     * @throws IOException
     */
    @Override
    public JavaFileObject getJavaFileForOutput(Location location, String className, JavaFileObject.Kind kind,
            FileObject sibling) throws IOException {
        return classJavaFileObject = new JavaFileObjectByteCode(className);
    }

    public byte[] getBytes() {
        return classJavaFileObject.getBytes();
    }

}