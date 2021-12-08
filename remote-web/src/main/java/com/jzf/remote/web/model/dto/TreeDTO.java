package com.jzf.remote.web.model.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jiazhifeng
 * @date 2021/12/8 9:25
 */
@Data
public class TreeDTO {
    public static final String FOLDER_ICON = "jstree-folder";
    public static final String FILE_ICON = "jstree-file";

    private String id;
    private String text;
    private String icon;
    private State state = new State();
    private List<TreeDTO> children = new ArrayList<>();
    private String li_attr;
    private String a_attr;

    public TreeDTO(String name){
        this.id = name;
        this.text = name;
    }

    public TreeDTO(String id,String text){
        this.id = id;
        this.text = text;
    }

    public TreeDTO(String id,String text,String icon){
        this.id = id;
        this.text = text;
        this.icon = icon;
    }

    @Data
    public static class State{
        private boolean opened;
        private boolean disabled;
        private boolean selected;
    }
}
