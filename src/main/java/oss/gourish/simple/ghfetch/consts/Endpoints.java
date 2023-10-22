package oss.gourish.simple.ghfetch.consts;

public enum Endpoints {
    BASEURL("https://api.github.com/"),
    TARBALL("repos/%s/%s/tarball/%s");

    private final String value;

    Endpoints(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}