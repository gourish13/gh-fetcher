package oss.gourish.simple.ghfetch.cli;

import oss.gourish.simple.ghfetch.api.FileDownloadService;
import oss.gourish.simple.ghfetch.consts.Endpoints;
import oss.gourish.simple.ghfetch.utils.TarballUtils;

import java.io.File;

public class CliOperations {
    void downloadRepoAsTarball(String repoDetails[], String token, String branchName, File destPath) {
        String tarFileName = repoDetails[1] + ".tar.gz";
        String url = String
                .format(Endpoints.TARBALL.value(), repoDetails[0], repoDetails[1], branchName);
        FileDownloadService
                .getFileDownloadService()
                .downloadTarball(url, token, destPath, tarFileName);
        new TarballUtils(tarFileName).uncompressTarGZ(destPath.toPath(), true);
    }
}