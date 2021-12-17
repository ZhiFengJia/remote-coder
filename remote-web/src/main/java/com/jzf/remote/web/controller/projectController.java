package com.jzf.remote.web.controller;

import com.jzf.remote.core.util.Constants;
import com.jzf.remote.core.util.DecompiledUtils;
import com.jzf.remote.core.util.HexUtils;
import com.jzf.remote.core.util.MavenUtils;
import com.jzf.remote.web.model.dto.TreeDTO;
import com.jzf.remote.web.util.TreeUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping("/project")
@EnableAsync
public class projectController {

    @GetMapping("/tree")
    public List<TreeDTO> tree() {
        TreeDTO root = new TreeDTO(null);
        TreeUtils.generateTree(new File(Constants.SOURCE_DIR), root);
        return root.getChildren();
    }

    @PostMapping("/getFile")
    public String getFile(String filePath) throws IOException {
        File file = new File(Constants.SOURCE_DIR + filePath);
        if (file.isFile()) {
            if (filePath.endsWith(".class")) {
                byte[] bytes = FileUtils.readFileToByteArray(file);
                return HexUtils.bytesToBeautiful(bytes);
            } else {
                return FileUtils.readFileToString(file, StandardCharsets.UTF_8);
            }
        } else {
            return "";
        }
    }

    @PostMapping("/getHexStrByFilePath")
    public String getHexStrByFilePath(String filePath) throws IOException {
        File file = new File(Constants.SOURCE_DIR + filePath);
        if (file.isFile()) {
            byte[] bytes = FileUtils.readFileToByteArray(file);
            return HexUtils.bytesToBeautiful(bytes);
        } else {
            return "";
        }
    }

    @PostMapping("/getBytecode")
    public String getBytecode(String classFullName) {
        String bytecode = DecompiledUtils.exec(classFullName);
        int index = bytecode.indexOf("\r\n");
        if (index == -1) {
            return bytecode;
        }
        return bytecode.substring(index + 2);
    }

    @PostMapping("/mvn")
    public String mvn(String projectName, List<String> goals) {
        MavenUtils.goals(projectName, goals,
                consumer -> {
                    try {
                        do {
                            String line = consumer.readLine();
                            if (line == null) {
                                break;
                            }
                            System.out.println(line);
                        } while (true);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
        return "";
    }
}
