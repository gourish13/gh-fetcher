package oss.gourish.simple.ghfetch.utils;

import picocli.CommandLine.ITypeConverter;
import picocli.CommandLine.TypeConversionException;

public class ParameterRegexChecker implements ITypeConverter<String> {
    private final String regex;

    public ParameterRegexChecker() {
        regex = "(.+/.+)";
    }

    @Override
    public String convert(String value) {
        if (value.matches(regex))
            return value;
        throw new TypeConversionException("Invalid input. Must atleast contain owner/repository[/path/in/repo].");
    }
}
