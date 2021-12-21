package com.jzf.remote.web.controller;

import com.jzf.remote.core.util.Constants;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author jiazhifeng
 * @date 2021/12/21 11:36
 */
@RestController
@RequestMapping("/file")
public class FileController {
    private static final Logger LOG = LoggerFactory.getLogger(FileController.class);

    @PostMapping("/create")
    public boolean create(String path, String fileName, boolean isDirectory) {
        LOG.info("create:{} {} {}", path, fileName, isDirectory ? "创建文件夹" : "创建文件");
        File filePath = new File(Constants.SOURCE_DIR, path);
        if (filePath.isDirectory()) {
            File file = new File(filePath, fileName);
            if (isDirectory) {
                return file.mkdir();
            } else {
                try {
                    return file.createNewFile();
                } catch (IOException e) {
                    LOG.info(e.getMessage());
                    return false;
                }
            }
        }
        return true;
    }

    @PostMapping("/rename")
    public boolean rename(String path, String oldFileName, String newFileName) {
        LOG.info("rename:{} {} {}", path, oldFileName, newFileName);
        File filePath = new File(Constants.SOURCE_DIR, path);
        if (filePath.isDirectory()) {
            File file = new File(filePath, oldFileName);
            if (file.isDirectory()) {
                try {
                    FileUtils.moveDirectory(file, new File(filePath, newFileName));
                } catch (IOException e) {
                    LOG.info(e.getMessage());
                    return false;
                }
            } else {
                try {
                    FileUtils.moveFile(file, new File(filePath, newFileName));
                } catch (IOException e) {
                    LOG.info(e.getMessage());
                    return false;
                }
            }
        }
        return true;
    }

    @PostMapping("/delete")
    public boolean delete(String path, String fileName) {
        LOG.info("delete:{} {}", path, fileName);
        File filePath = new File(Constants.SOURCE_DIR, path);
        if (filePath.isDirectory()) {
            File file = new File(filePath, fileName);
            if (file.isDirectory()) {
                try {
                    FileUtils.deleteDirectory(file);
                } catch (IOException e) {
                    LOG.info(e.getMessage());
                    return false;
                }
            } else {
                try {
                    FileUtils.delete(file);
                } catch (IOException e) {
                    LOG.info(e.getMessage());
                    return false;
                }
            }
        }
        return true;
    }

    @PostMapping("/move")
    public boolean move(String oldPath, String newPath, String fileName) {
        LOG.info("move:{} {} {}", oldPath, newPath, fileName);
        File fileOldPath = new File(Constants.SOURCE_DIR, oldPath);
        if (fileOldPath.isDirectory()) {
            File file = new File(fileOldPath, fileName);
            if (file.isDirectory()) {
                try {
                    FileUtils.moveDirectoryToDirectory(file, new File(Constants.SOURCE_DIR, newPath), false);
                } catch (Exception e) {
                    LOG.info(e.getMessage());
                    return false;
                }
            } else {
                try {
                    FileUtils.moveFileToDirectory(file, new File(Constants.SOURCE_DIR, newPath), false);
                } catch (Exception e) {
                    LOG.info(e.getMessage());
                    return false;
                }
            }
        }
        return true;
    }

    @PostMapping("/save")
    public boolean save(String filePath, String content) {
        LOG.info("save:{} {}", filePath, content);
        try {
            FileUtils.write(new File(Constants.SOURCE_DIR, filePath), content, StandardCharsets.UTF_8);
        } catch (IOException e) {
            LOG.info(e.getMessage());
            return false;
        }
        return true;
    }
}
