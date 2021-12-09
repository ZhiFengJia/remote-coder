package com.jzf.remote.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.tools.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Locale;

/**
 * 运行时编译
 *
 * @author jiazhifeng
 * @date 2021/11/25 14:53
 */
public class DynamicCompiler {
    private static final Logger LOG = LoggerFactory.getLogger(DynamicCompiler.class);
    private final JavaCompiler javaCompiler;
    private final DiagnosticCollector<JavaFileObject> diagnosticCollector;
    private final ClassFileManager fileManager;

    public DynamicCompiler() {
        javaCompiler = ToolProvider.getSystemJavaCompiler();
        diagnosticCollector = new DiagnosticCollector<>();
        StandardJavaFileManager standardFileManager = javaCompiler.getStandardFileManager(diagnosticCollector,
                Locale.getDefault(), StandardCharsets.UTF_8);
        fileManager = new ClassFileManager<>(standardFileManager);
    }

    /**
     * 编译源码并加载，获取Class对象
     *
     * @param className
     * @param sourceCode
     * @return
     * @throws ClassNotFoundException
     * @throws IOException
     */
    public Class<?> compileAndLoad(String className, String sourceCode) throws ClassNotFoundException, IOException {
        JavaFileObject fileObject = new JavaFileObjectSource(className, sourceCode);
        JavaCompiler.CompilationTask task = javaCompiler.getTask(null, fileManager, diagnosticCollector, null, null,
                Arrays.asList(fileObject));
        boolean result = task.call();
        diagnosticCollector.getDiagnostics().forEach(diagnostic -> {
            LOG.info("msg:{}", diagnostic.getMessage(Locale.getDefault()));
            LOG.info("source:{} line:{}", diagnostic.getSource(), diagnostic.getLineNumber());
        });
        if (result) {
            LOG.info("Compiler Succeeded");
            HotSwapClassLoader hotSwapClassLoader = new HotSwapClassLoader(fileManager.getBytes());
            return hotSwapClassLoader.loadClass(className);
        } else {
            LOG.info("Compiler failure!");
            return null;
        }
    }

    /**
     * 关闭fileManager
     *
     * @throws IOException
     */
    public void closeFileManager() throws IOException {
        fileManager.close();
    }

}