package com.jzf.remote.core;

import com.jzf.remote.core.util.Constants;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
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

        String path = Constants.COMPILED_DIR + name + ".class";
        LOG.info("保存动态编译文件路径:{}", path);
        try (FileOutputStream fos = FileUtils.openOutputStream(new File(path))) {
            fos.write(modiBytes);
        } catch (IOException e) {
            LOG.error(e.getMessage());
        }
        return super.defineClass(name.replaceAll("/", "."), modiBytes, 0, modiBytes.length);
    }
}
