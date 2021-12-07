package com.jzf.remote.core;

import javax.tools.SimpleJavaFileObject;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;

/**
 * 存储编译后的字节码
 *
 * @author jiazhifeng
 * @date 2021/11/25 14:52
 */
public class JavaFileObjectByteCode extends SimpleJavaFileObject {

    /**
     * Compiler编译后的byte数据会存在这个ByteArrayOutputStream对象中， 后面可以取出，加载到JVM中。
     */
    private final ByteArrayOutputStream byteArrayOutputStream;

    protected JavaFileObjectByteCode(String className) {
        super(URI.create("string:///" + className.replaceAll("\\.", "/") + Kind.CLASS.extension), Kind.CLASS);
        this.byteArrayOutputStream = new ByteArrayOutputStream();
    }

    /**
     * 覆盖父类SimpleJavaFileObject的方法。 该方法提供给编译器结果输出的OutputStream。
     * <p>
     * 编译器完成编译后，会将编译结果输出到该 OutputStream 中，我们随后需要使用它获取编译结果
     *
     * @return
     * @throws IOException
     */
    @Override
    public OutputStream openOutputStream() {
        return this.byteArrayOutputStream;
    }

    /**
     * FileManager会使用该方法获取编译后的byte，然后将类加载到JVM
     */
    public byte[] getBytes() {
        return this.byteArrayOutputStream.toByteArray();
    }
}