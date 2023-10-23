package oss.gourish.simple.ghfetch.cli;

import oss.gourish.simple.ghfetch.api.FileDownloadService;
import oss.gourish.simple.ghfetch.api.GitHubContentService;
import oss.gourish.simple.ghfetch.consts.Endpoints;
import oss.gourish.simple.ghfetch.utils.TarballUtils;

import java.io.File;
import java.util.Arrays;
import java.util.StringJoiner;

public class CliOperations {
    void downloadRepoAsTarball(String repoDetails[], String token, String branchName, File destPath) {
        String tarFileName = repoDetails[1] + ".tar.gz";
        String url = String
                .format(Endpoints.TARBALL.value(), repoDetails[0], repoDetails[1], branchName);
        FileDownloadService
                .getInstance()
                .downloadTarball(url, token, destPath, tarFileName);
        new TarballUtils(tarFileName).uncompressTarGZ(destPath.toPath(), true);
    }

    void downloadRepoContents(String repoDetails[], String token, String branchName, File destPath) {
        String contentPathInRepo = getContentPathInsideRepo(repoDetails);
        String url = String
                .format(Endpoints.CONTENT.value(), repoDetails[0], repoDetails[1], contentPathInRepo);
        GitHubContentService
                .getInstance()
                .getGitHubContentsInfo(url, token, branchName, destPath);
    }

    private String getContentPathInsideRepo(String repoDetails[]) {
        String contentInRepo[] = Arrays.copyOfRange(repoDetails, 2, repoDetails.length);

        StringJoiner joiner = new StringJoiner("/");
        for (String item : contentInRepo)
            joiner.add(item);

        return joiner.toString();
    }
}