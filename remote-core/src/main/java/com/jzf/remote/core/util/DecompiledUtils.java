package com.jzf.remote.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author jiazhifeng
 * @date 2021/12/11 16:23
 */
public class DecompiledUtils {
    private static final Logger LOG = LoggerFactory.getLogger(DecompiledUtils.class);

    public static String exec(String classFullName) {
        List<String> cmds = new ArrayList<>();
        cmds.add("javap");
        cmds.add("-c");//对代码进行反汇编
        cmds.add("-constants");//显示最终常量
        cmds.add("-private");//显示所有类和成员
        cmds.add("-verbose");//输出附加信息
        cmds.add("-l");//输出行号和本地变量表
        cmds.add("-s");//输出内部类型签名
        cmds.add("-sysinfo");//显示正在处理的类的系统信息 (路径, 大小, 日期, MD5 散列)
        cmds.add("-classpath");//指定查找用户类文件的位置
        cmds.add(Constants.COMPILED_DIR);
        cmds.add(classFullName);
        LOG.info("Command: {}", String.join(" ", cmds));

        ProcessBuilder processBuilder = new ProcessBuilder(cmds);
        processBuilder.redirectErrorStream(true);
        String result;
        try {
            Process process = processBuilder.start();
            BufferedReader inputReader = new BufferedReader(new InputStreamReader(process.getInputStream(), Charset.defaultCharset()));
            result = inputReader.lines().collect(Collectors.joining("\r\n"));
            int resultState = process.waitFor();
            if (resultState == 0) {
                LOG.info("命令执行成功");
            } else {
                LOG.info("命令执行失败");
            }
        } catch (IOException | InterruptedException e) {
            LOG.info(e.getMessage());
            result = e.getMessage();
        }
        return result;
    }
}
