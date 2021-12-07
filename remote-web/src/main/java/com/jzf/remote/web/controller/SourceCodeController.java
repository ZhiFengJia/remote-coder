package com.jzf.remote.web.controller;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sourceCode")
public class SourceCodeController {
    private String fileDirectory = System.getProperty("user.dir") + "/../source_code_files/";

    @PostMapping("/{fileName}")
    public String executeJavaSourceCode(@PathVariable("fileName") String fileName) throws IOException {
        return FileUtils.readFileToString(new File(fileDirectory + fileName + ".java"), Charset.forName("UTF-8"));
    }

    @GetMapping("/list")
    public List<String> listFileNames() {
        Collection<File> files = FileUtils.listFiles(new File(fileDirectory), new String[] { "java" }, false);
        List<String> fileNames = files.stream()
                .map(File::getName)
                .map(fileName -> fileName.substring(0, fileName.lastIndexOf(".java")))
                .collect(Collectors.toList());
        return fileNames;
    }
}
