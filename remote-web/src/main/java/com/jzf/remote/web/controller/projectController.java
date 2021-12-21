package com.jzf.remote.web.controller;

import com.jzf.remote.core.util.Constants;
import com.jzf.remote.core.util.DecompiledUtils;
import com.jzf.remote.core.util.HexUtils;
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
            return null;
        }
    }

    @PostMapping("/getHexStrByFilePath")
    public String getHexStrByFilePath(String filePath) throws IOException {
        File file = new File(Constants.SOURCE_DIR + filePath);
        if (file.isFile()) {
            byte[] bytes = FileUtils.readFileToByteArray(file);
            return HexUtils.bytesToBeautiful(bytes);
        } else {
            return null;
        }
    }

    @PostMapping("/getBytecode")
    public String getBytecode(String filePath) {
        String bytecode = DecompiledUtils.exec(filePath);
        int index = bytecode.indexOf("\r\n");
        if (index == -1) {
            return bytecode;
        }
        return bytecode.substring(index + 2);
    }
}
