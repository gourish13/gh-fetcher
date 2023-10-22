package oss.gourish.simple.ghfetch.utils;

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class TarballUtils {
    private final Path inputFile;

    public TarballUtils(String inputFile) {
        this.inputFile = new File(inputFile).toPath();
    }

    public void uncompressTarGZ(Path outputPath, boolean removeSource) {
        try {
            TarArchiveInputStream tarArchiveInputStream =
                    new TarArchiveInputStream(
                            new GzipCompressorInputStream(
                                    new BufferedInputStream(
                                            Files.newInputStream(inputFile))));

            ArchiveEntry archiveEntry;
            while ((archiveEntry = tarArchiveInputStream.getNextEntry()) != null) {
                Path outputEntry = outputPath.resolve(archiveEntry.getName());
                // If entry is a directory, then create it else copy entry.
                if (archiveEntry.isDirectory()) {
                    if (!Files.exists(outputEntry))
                        Files.createDirectory(outputEntry);
                } else
                    Files.copy(tarArchiveInputStream, outputEntry);
            }
            tarArchiveInputStream.close();
            // Delete source tarball if removeSource is specified
            if (removeSource)
                Files.delete(inputFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void uncompressTarGZ(Path outputPath) {
        uncompressTarGZ(outputPath, false);
    }
}