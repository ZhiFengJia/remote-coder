package com.jzf.remote.web.controller;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.jzf.remote.web.model.dto.TreeDTO;
import com.jzf.remote.web.util.TreeUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/project")
public class projectController {

    @PostMapping("/getFile")
    public String getFile(String filePath) throws IOException {
        File file = new File(TreeUtils.fileDirectory + filePath);
        if (file.isFile()){
            return FileUtils.readFileToString(file, Charset.forName("UTF-8"));
        }else{
            return "";
        }
    }

    @GetMapping("/tree")
    public List<TreeDTO> tree() {
        TreeDTO root = new TreeDTO(null);
        TreeUtils.generateTree(new File(TreeUtils.fileDirectory),root);
        return root.getChildren();
    }
}
