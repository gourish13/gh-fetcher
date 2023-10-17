package oss.gourish.simple.ghfetch.config;

import org.yaml.snakeyaml.Yaml;
import picocli.CommandLine.IDefaultValueProvider;
import picocli.CommandLine.Model.ArgSpec;
import picocli.CommandLine.Model.OptionSpec;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

public class YamlConfigProvider implements IDefaultValueProvider {
    private Properties readProperties() throws IOException {
        String filePath = "./";
        String os = System.getProperty("os.name");

        if (os.equalsIgnoreCase("Linux"))
            filePath = System.getenv("XDG_CONFIG_HOME") != null
                    ? System.getenv("XDG_CONFIG_HOME")
                    : (System.getenv("HOME") + "/.config") + "/gh-fetcher/config.yml";
        // TODO: Implement config file path for Mac and Windows

        // Read Yaml config file
        InputStream inputStream = new FileInputStream(filePath);
        Yaml yaml = new Yaml();
        Map<String, Object> map = yaml.load(inputStream);

        Properties properties = new Properties();
        // Convert Yaml to Properties
        for (Map.Entry<String, Object> mapEntry : map.entrySet())
            properties.put(mapEntry.getKey(), mapEntry.getValue().toString());

        return properties;
    }

    @Override
    public String defaultValue(ArgSpec argSpec) throws Exception {
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
