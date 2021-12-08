package com.jzf.remote.web.model.dto;

import com.alibaba.fastjson.JSON;
import com.jzf.remote.web.util.TreeUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jiazhifeng
 * @date 2021/12/8 9:37
 */
public class TreeDTOTest {

    @Test
    public void test() {
        List<TreeDTO> tree = new ArrayList<>();

        TreeDTO treeDTO1 = new TreeDTO("其他项目名称(测试)");
        tree.add(treeDTO1);

        TreeDTO treeDTO2 = new TreeDTO("项目名称(测试)");
        treeDTO2.getState().setOpened(true);

        TreeDTO treeDTO2_1 = new TreeDTO("HelloWorld", "HelloWorld.java", TreeDTO.FILE_ICON);
        treeDTO2.getChildren().add(treeDTO2_1);

        TreeDTO treeDTO2_2 = new TreeDTO("RemoteClassLoader", "RemoteClassLoader.java", TreeDTO.FILE_ICON);
        treeDTO2.getChildren().add(treeDTO2_2);

        TreeDTO treeDTO2_3 = new TreeDTO("RemoteMonitor", "RemoteMonitor.java", TreeDTO.FILE_ICON);
        treeDTO2.getChildren().add(treeDTO2_3);

        tree.add(treeDTO2);

        String json = JSON.toJSONString(tree);
        System.out.println(json);
    }

    //    @Test
    public void test2() {
        String fileDirectory = "D:\\项目文档\\API模块\\工具";

        TreeDTO root = new TreeDTO(null);
        TreeUtils.generateTree(new File(fileDirectory), root);

        String json = JSON.toJSONString(root.getChildren());
        System.out.println(json);
    }

    @Test
    public void test3() {
        String className = "\\DemoProject\\RemoteClassLoader.java";
        System.out.println(className);
        className = className.substring(className.lastIndexOf("\\"), className.lastIndexOf("."));
        System.out.println(className);
    }
}