package com.jzf.remote.core;

import com.jzf.remote.core.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author jiazhifeng
 * @date 2021/11/25 16:29
 */
public class HotSwapClassLoader extends ClassLoader {
    private static final Logger LOG = LoggerFactory.getLogger(HotSwapClassLoader.class);
    private byte[] byteCode;

    public HotSwapClassLoader(byte[] byteCode) {
        super(HotSwapClassLoader.class.getClassLoader());
        this.byteCode = byteCode;
    }

    @Override
    protected Class<?> findClass(String name) {
        ClassModifier cm = new ClassModifier(name, byteCode);
        byte[] modiBytes = cm.modifyUTF8Constant("java/lang/System", Constants.SYSTEM_CLASS);
        modiBytes = cm.modifyByteCode(modiBytes);

        String userDir = System.getProperty("user.dir");
        String nameSuffix = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
        String path = userDir + "/../dynamic_compiler_files/" + name + "_" + nameSuffix + ".class";
        LOG.info("动态编译存储路径:{}", path);
        try (FileOutputStream fos = new FileOutputStream(path)) {
            fos.write(modiBytes);
        } catch (IOException e) {
            LOG.error(e.getMessage());
        }
        return super.defineClass(name.replaceAll("/", "."), modiBytes, 0, modiBytes.length);
    }
}
