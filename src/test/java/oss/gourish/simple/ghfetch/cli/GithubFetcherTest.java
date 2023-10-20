package oss.gourish.simple.ghfetch.cli;

import org.junit.jupiter.api.Test;
import picocli.CommandLine;
import picocli.CommandLine.ExitCode;

import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GithubFetcherTest {
    @Test
    void successExitCodeIsZero() {
        // Using default branch and destPath
        String[] args = new String[]{"-t", "asdn3eqj", "gourish13/gh-fetcher"};
        int exitCode = new CommandLine(new GithubFetcher()).execute(args);
        assertEquals(ExitCode.OK, exitCode);

        // Using non default branch and destPath
        args = new String[]{"-t", "asdn3eqj", "-b", "dev", "gourish13/gh-fetcher", "/home/user"};
        exitCode = new CommandLine(new GithubFetcher()).execute(args);
        assertEquals(ExitCode.OK, exitCode);

        // Non default branch, testing param --branch instead of -b
        args[2] = "--branch";
        exitCode = new CommandLine(new GithubFetcher()).execute(args);
        assertEquals(ExitCode.OK, exitCode);

        // testing param --token instead of -t
        args[0] = "--token";
        exitCode = new CommandLine(new GithubFetcher()).execute(args);
        assertEquals(ExitCode.OK, exitCode);
    }

    @Test
    void anyExceptionExitCodeIsOne() {
        // Preparing input with no line for NoSuchElementException to occur.
        ByteArrayInputStream input = new ByteArrayInputStream(new byte[0]);
        System.out.println(input);
        System.setIn(input);

        String[] args = new String[]{"gourish13/gh-fetcher", "/home/user"};
        int exitCode = new CommandLine(new GithubFetcher()).execute(args);
        assertEquals(ExitCode.SOFTWARE, exitCode);
    }

    @Test
    void invalidArgsExitCodeIsTwo() {
        // Incorrect no. of args
        String[] args = new String[]{"-t", "asdn3eqj"};
        int exitCode = new CommandLine(new GithubFetcher()).execute(args);
        assertEquals(ExitCode.USAGE, exitCode);

        // Invalid arg name
        args = new String[]{"--taken", "asdn3eqj", "gourish13/gh-fetcher", "/home/user"};
        exitCode = new CommandLine(new GithubFetcher()).execute(args);
        assertEquals(ExitCode.USAGE, exitCode);

        // Invalid arg option
        args = new String[]{"--token", "asdn3eqj", "-branch", "dev", "gourish13/gh-fetcher", "/home/user"};
        exitCode = new CommandLine(new GithubFetcher()).execute(args);
        assertEquals(ExitCode.USAGE, exitCode);

        // No args i.e., args = { "" }, Prompt input for missing token in cmd line args
        System.setIn(new ByteArrayInputStream("asdn3eqj".getBytes()));
        exitCode = new CommandLine(new GithubFetcher()).execute();
        assertEquals(ExitCode.USAGE, exitCode);
    }

    @Test
    void valuesCorrectlyPopulatedFromCmdLineArgs() {
        GithubFetcher githubFetcher = new GithubFetcher();

        String[] args = new String[]{"-t", "asdn3eqj", "-b", "dev", "gourish13/gh-fetcher", "/home/user"};
        new CommandLine(githubFetcher).execute(args);
        assertEquals("asdn3eqj", githubFetcher.getToken());
        assertEquals("dev", githubFetcher.getBranchName());
        assertEquals("gourish13/gh-fetcher", githubFetcher.getRepoPath());
        assertEquals("/home/user", githubFetcher.getDestPath());
        githubFetcher = new GithubFetcher();

        // Long form of args, also default value for destPath
        args = new String[]{"--token", "asdn3eqj", "--branch", "dev", "gourish13/gh-fetcher"};
        new CommandLine(githubFetcher).execute(args);
        assertEquals("asdn3eqj", githubFetcher.getToken());
        assertEquals("dev", githubFetcher.getBranchName());
        assertEquals("gourish13/gh-fetcher", githubFetcher.getRepoPath());
        assertEquals(".", githubFetcher.getDestPath());
    }

    @Test
    void defaultBranchNameIsEmpty() {
        String[] args = new String[]{"-t", "asdn3eqj", "gourish13/gh-fetcher", "/home/user"};
        GithubFetcher githubFetcher = new GithubFetcher();
        new CommandLine(githubFetcher).execute(args);
        assertEquals("", githubFetcher.getBranchName());
    }

    @Test
    void tokenAcceptedViaPromptIfNotProvided() {
        String tokenInputValue = "asdn3eqj";
        ByteArrayInputStream input = new ByteArrayInputStream(tokenInputValue.getBytes());
        System.setIn(input);

        GithubFetcher githubFetcher = new GithubFetcher();
        int exitCode = new CommandLine(githubFetcher)
                .execute("gourish13/gh-fetcher /home/user".split(" "));

        assertEquals(ExitCode.OK, exitCode);
        assertEquals(tokenInputValue, githubFetcher.getToken());
    }
}