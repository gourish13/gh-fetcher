package oss.gourish.simple.ghfetch.cli;

import oss.gourish.simple.ghfetch.config.YamlConfigProvider;
import oss.gourish.simple.ghfetch.utils.ParameterRegexChecker;
import picocli.CommandLine.Command;
import picocli.CommandLine.ExitCode;
import picocli.CommandLine.IExitCodeGenerator;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.io.File;
import java.util.Scanner;

@Command(name = "gh_fetcher",
        mixinStandardHelpOptions = true,
        version = "0.1.0",
        description = "Downloads a specific file/folder from Github Repository",
        defaultValueProvider = YamlConfigProvider.class)
public class GitHubFetcher implements Runnable, IExitCodeGenerator {
    @Parameters(index = "0", paramLabel = "SRC", converter = ParameterRegexChecker.class,
            description = "Github repository path of file or folder starting with owner/repository")
    private String repoPath;
    @Parameters(index = "1", paramLabel = "DEST", arity = "0..1", defaultValue = ".",
            description = "Local path where file or folder is to be downloaded")
    private File destPath;
    @Option(names = {"-t", "--token"}, paramLabel = "TOKEN",
            description = "Github API Token", arity = "0..1", interactive = true)
    private String token;
    @Option(names = {"-b", "--branch"}, paramLabel = "BRANCH", defaultValue = "",
            description = "GIthub repository branch name, default branch will be used if not specified",
            arity = "0..1")
    private String branchName;
    private CliOperations cliOperations;

    public String getRepoPath() {
        return repoPath;
    }

    public String getDestPath() {
        return destPath.toString();
    }

    public String getToken() {
        return token;
    }

    public String getBranchName() {
        return branchName;
    }

    private void promptTokenIfMissing() {
        Scanner in = new Scanner(System.in);
        if (token == null) {
            System.out.print("Enter github api token: ");
            token = in.nextLine();
        }
        in.close();
    }

    @Override
    public void run() {
        promptTokenIfMissing();
        cliOperations = new CliOperations();
        detectOperationTypeAndStart();
    }

    public void detectOperationTypeAndStart() {
        String[] repoDetails = repoPath.split("/");
        if (repoDetails.length == 2) {
            cliOperations.downloadRepoAsTarball(repoDetails, token, branchName, destPath);
            return;
        }
        // TODO : Download file/folder inside repository.
        System.out.println("Not entire repo");


    }

    @Override
    public int getExitCode() {
        return ExitCode.OK;
    }
}
