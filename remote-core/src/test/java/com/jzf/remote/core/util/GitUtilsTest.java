package com.jzf.remote.core.util;

import org.eclipse.jgit.api.errors.GitAPIException;

public class GitUtilsTest {

    public static void main(String[] args) throws GitAPIException {
        test1();
    }

    private static void test1() throws GitAPIException {
        String repository = "https://github.com/ZhiFengJia/remote-coder.git";
        boolean success = GitUtils.clone(repository);
        System.out.println(success);
    }

}