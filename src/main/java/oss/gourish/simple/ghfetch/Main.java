package oss.gourish.simple.ghfetch;

import oss.gourish.simple.ghfetch.cli.GitHubFetcher;
import picocli.CommandLine;

public class Main {
    public static void main(String[] args) {
        int exitCode = new CommandLine(new GitHubFetcher()).execute(args);
        System.exit(exitCode);
    }
}