package com.jzf.remote.web.controller;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;


/**
 * @author jiazhifeng
 * @date 2021/12/21 13:39
 */
class FileControllerTest {

    @Test
    void create() {
        String path = "/logs/";
//        String fileName = "/src/";
        String fileName ="test.txt";
        boolean isDirectory = false;
        File filePath = new File("D:\\项目文档\\API模块\\工具\\", path);
        if (filePath.isDirectory()) {
            File file = new File(filePath, fileName);
            if (isDirectory) {
                System.out.println(file.mkdir());
            } else {
                try {
                    System.out.println(file.createNewFile());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    void rename() {
        String path = "/logs/";
//        String oldFileName="/src/";
//        String newFileName="/src2/";
        String oldFileName = "test.txt";
        String newFileName = "newtest.txt";
        File filePath = new File("D:\\项目文档\\API模块\\工具\\", path);
        if (filePath.isDirectory()) {
            File file = new File(filePath, oldFileName);
            if (file.isDirectory()) {
                try {
                    FileUtils.moveDirectory(file, new File(filePath, newFileName));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    FileUtils.moveFile(file, new File(filePath, newFileName));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    void delete() {
        String path = "/logs/";
        String fileName = "newTest.txt";
        File filePath = new File("D:\\项目文档\\API模块\\工具\\", path);
        if (filePath.isDirectory()) {
            File file = new File(filePath, fileName);
            if (file.isDirectory()) {
                try {
                    FileUtils.deleteDirectory(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    FileUtils.delete(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    void move() {
        String oldPath="/logs/";
        String newPath="/logs/src2";
        String fileName="/src/";
        File fileOldPath = new File("D:\\项目文档\\API模块\\工具\\", oldPath);
        if (fileOldPath.isDirectory()) {
            File file = new File(fileOldPath,fileName);
            if (file.isDirectory()){
                try {
                    FileUtils.moveDirectoryToDirectory(file,new File("D:\\项目文档\\API模块\\工具\\", newPath),false);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else{
                try {
                    FileUtils.moveFileToDirectory(file,new File("D:\\项目文档\\API模块\\工具\\", newPath),false);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}