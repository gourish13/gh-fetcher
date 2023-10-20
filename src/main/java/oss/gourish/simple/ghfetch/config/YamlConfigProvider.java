package oss.gourish.simple.ghfetch.config;

import org.yaml.snakeyaml.Yaml;
import picocli.CommandLine.IDefaultValueProvider;
import picocli.CommandLine.Model.ArgSpec;
import picocli.CommandLine.Model.OptionSpec;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class YamlConfigProvider implements IDefaultValueProvider {
    private String getConfigFilePath() {
        String os = System.getProperty("os.name").toLowerCase();

        if (os.indexOf("windows") > 0)
            return System.getenv("USERPROFILE") + "\\AppData\\Local\\gh-fetcher\\config.yml";
        if (os.indexOf("mac") > 0)
            return System.getenv("HOME") + "/.gh_fetcher.yml";
        // Linux: gh-fetcher/config.yml in $XDG_CONFIG_HOME or $HOME/.config.
        return (System.getenv("XDG_CONFIG_HOME") == null
                ? System.getenv("HOME") + "/.config"
                : System.getenv("XDG_CONFIG_HOME")) + "/gh-fetcher/config.yml";
    }

    Map<String, Object> getYamlConfigProperties() {
        String filePath = getConfigFilePath();
        // Read Yaml config file
        Map<String, Object> map;
        try {
            InputStream inputStream = new FileInputStream(filePath);
            Yaml yaml = new Yaml();
            map = yaml.load(inputStream);
        } catch (FileNotFoundException e) {
            map = null;
        }
        // If no config make map empty rather than null.
        if (map == null)
            map = new HashMap<>();

        return map;
    }

    Properties readProperties() {
        Map<String, Object> map = getYamlConfigProperties();
        Properties properties = new Properties();
        // Convert Yaml to Properties
        for (Map.Entry<String, Object> mapEntry : map.entrySet())
            properties.put(mapEntry.getKey(), mapEntry.getValue().toString());

        return properties;
    }

    @Override
    public String defaultValue(ArgSpec argSpec) {
        // Get property values
        Properties properties = readProperties();

        // return default value for arguments
        if (argSpec.isOption()) {
            OptionSpec optionSpec = (OptionSpec) argSpec;
            return properties.getProperty(optionSpec.longestName());
        }

        return properties.getProperty(argSpec.paramLabel());
    }
}
