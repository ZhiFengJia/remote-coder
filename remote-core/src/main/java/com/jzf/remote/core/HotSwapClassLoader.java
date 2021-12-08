package com.jzf.remote.core;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.jzf.remote.core.util.Constants;

/**
 * @author jiazhifeng
 * @date 2021/11/25 16:29
 */
public class HotSwapClassLoader extends ClassLoader {
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
        String path = userDir + "/../dynamic_compiler_files/" + name + "_"+ nameSuffix + ".class";
        System.out.println("动态编译存储路径: " + path);
        try (FileOutputStream fos = new FileOutputStream(path)) {
            fos.write(modiBytes);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return super.defineClass(name.replaceAll("/","."), modiBytes, 0, modiBytes.length);
    }
}
