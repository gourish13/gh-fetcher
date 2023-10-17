package oss.gourish.simple.ghfetch;

import picocli.CommandLine;
import oss.gourish.simple.ghfetch.cli.GithubFetcher;

public class Main {
    public static void main(String[] args) {
        int exitCode = new CommandLine(new GithubFetcher()).execute(args);
        System.exit(exitCode);
    }
}