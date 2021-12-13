package com.jzf.remote.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class MavenUtils {
    private static final Logger LOG = LoggerFactory.getLogger(MavenUtils.class);

    public static void goals(String projectName,List<String> goals, Consumer<BufferedReader> consumer) {
        List<String> cmds = new ArrayList<>();
        cmds.add("mvn");
        cmds.add("--file");
        cmds.add(Constants.SOURCE_DIR + projectName);
//        cmds.add("/Users/jiazhifeng/IdeaProjects/remote-coder/remote-web/src/main/resources/sourceCodeFiles/" + projectName);
        cmds.addAll(goals);
        LOG.info("Command: {}", String.join(" ", cmds));

        ProcessBuilder processBuilder = new ProcessBuilder(cmds);
        processBuilder.redirectErrorStream(true);
        try {
            Process process = processBuilder.start();
            BufferedReader inputReader = new BufferedReader(new InputStreamReader(process.getInputStream(), Charset.defaultCharset()));
            new Thread(() -> consumer.accept(inputReader)).start();
            int resultState = process.waitFor();
            if (resultState == 0) {
                LOG.info("命令执行成功");
            } else {
                LOG.info("命令执行失败");
            }
        } catch (IOException | InterruptedException e) {
            LOG.info(e.getMessage());
        }
    }
}
