package com.jzf.remote.core.util;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PullCommand;
import org.eclipse.jgit.api.PullResult;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class GitUtils {
    private static final Logger LOG = LoggerFactory.getLogger(GitUtils.class);

    public static boolean clone(String repository) {
        String projectName = repository.substring(repository.lastIndexOf("/") + 1, repository.indexOf(".git"));

        try {
            Git git = Git.cloneRepository()
//                .setCredentialsProvider(provider)
                    .setURI(repository)
                    .setDirectory(new File("/Users/jiazhifeng/IdeaProjects/remote-coder/remote-core/target/" + projectName)).call();

            PullCommand pull = git.pull();
            PullResult pullResult = pull.call();
            return pullResult.isSuccessful();
        } catch (GitAPIException e) {
            e.printStackTrace();
        }
        return false;
    }

}
