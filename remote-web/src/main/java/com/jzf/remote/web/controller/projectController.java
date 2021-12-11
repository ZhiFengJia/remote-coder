package com.jzf.remote.web.controller;

import com.jzf.remote.core.util.DecompiledUtils;
import com.jzf.remote.web.model.dto.TreeDTO;
import com.jzf.remote.web.util.TreeUtils;
import org.apache.commons.io.FileUtils;
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
public class projectController {

    @GetMapping("/tree")
    public List<TreeDTO> tree() {
        TreeDTO root = new TreeDTO(null);
        TreeUtils.generateTree(new File(TreeUtils.SOURCE_DIR), root);
        return root.getChildren();
    }

    @PostMapping("/getFile")
    public String getFile(String filePath) throws IOException {
        File file = new File(TreeUtils.SOURCE_DIR + filePath);
        if (file.isFile()) {
            return FileUtils.readFileToString(file, StandardCharsets.UTF_8);
        } else {
            return "";
        }
    }

    @PostMapping("/getBytecode")
    public String getBytecode(String classFullName) {
        return DecompiledUtils.exec(classFullName);
    }
}
