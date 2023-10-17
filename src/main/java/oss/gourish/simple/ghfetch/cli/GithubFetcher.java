package oss.gourish.simple.ghfetch.cli;

import oss.gourish.simple.ghfetch.config.YamlConfigProvider;
import picocli.CommandLine.Command;
import picocli.CommandLine.ExitCode;
import picocli.CommandLine.IExitCodeGenerator;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.io.File;

@Command(name = "gh_fetcher",
        mixinStandardHelpOptions = true,
        version = "0.1.0",
        description = "Downloads a specific file/folder form Github Repository",
        defaultValueProvider = YamlConfigProvider.class)
public class GithubFetcher implements Runnable, IExitCodeGenerator {

    @Parameters(index = "0", paramLabel = "SRC",
            description = "Github repository path of file or folder starting with author/repo")
    private String repoPath;
    @Parameters(index = "1", paramLabel = "DEST", description = "Local path where file or folder is to be downloaded")
    private File destPath;
    @Option(names = {"-t", "--token"}, paramLabel = "TOKEN",
            description = "Github API Token", arity = "0..1", interactive = true)
    private char[] token;

    @Override
    public void run() {
        System.out.println("Hello and Welcome!");
        System.out.println(repoPath);
        System.out.println(destPath);
    }

    @Override
    public int getExitCode() {
        return ExitCode.OK;
    }
}
