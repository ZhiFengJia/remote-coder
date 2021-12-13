package com.jzf.remote.web.util;

import com.jzf.remote.core.util.Constants;
import com.jzf.remote.web.model.dto.TreeDTO;

import java.io.File;

/**
 * @author jiazhifeng
 * @date 2021/12/8 10:59
 */
public class TreeUtils {

    public static void generateTree(File fileDir, TreeDTO tree) {
        if (fileDir.exists()) {
            File[] files = fileDir.listFiles();
            if (files != null) {
                for (File file : files) {
                    String filePath = file.getPath().substring(Constants.SOURCE_DIR.length() - 1)
                            .replaceAll("\\\\", "/");
                    if (file.isDirectory()) {
                        TreeDTO treeDTO = new TreeDTO(filePath + "/", file.getName(), TreeDTO.FOLDER_ICON);
                        tree.getChildren().add(treeDTO);
                        generateTree(file, treeDTO);
                    } else {
                        tree.getChildren().add(new TreeDTO(filePath, file.getName(), TreeDTO.FILE_ICON));
                    }
                }
            }
        }
    }

}
