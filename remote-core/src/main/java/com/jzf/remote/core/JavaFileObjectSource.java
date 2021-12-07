package com.jzf.remote.core;

import javax.tools.SimpleJavaFileObject;

import java.net.URI;

/**
 * 字符串java源代码。JavaFileObject表示
 *
 * @author jiazhifeng
 * @date 2021/11/25 14:51
 */
public class JavaFileObjectSource extends SimpleJavaFileObject {

    // 表示java源代码
    private final CharSequence content;

    protected JavaFileObjectSource(String className, CharSequence content) {
        super(URI.create("string:///" + className.replaceAll("\\.", "/") + Kind.SOURCE.extension), Kind.SOURCE);
        this.content = content;
    }

    /**
     * 获取需要编译的源代码
     *
     * @param ignoreEncodingErrors
     * @return
     */
    @Override
    public CharSequence getCharContent(boolean ignoreEncodingErrors) {
        return content;
    }
}