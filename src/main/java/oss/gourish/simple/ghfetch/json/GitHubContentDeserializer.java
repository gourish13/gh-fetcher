package oss.gourish.simple.ghfetch.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import oss.gourish.simple.ghfetch.model.GitHubContent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GitHubContentDeserializer extends StdDeserializer<GitHubContent[]> {
    public GitHubContentDeserializer() {
        this(null);
    }

    public GitHubContentDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public GitHubContent[] deserialize(JsonParser jsonParser,
                                       DeserializationContext deserializationContext)
            throws IOException {
        System.out.println("In deserializer");
        JsonNode jsonNode = jsonParser.getCodec().readTree(jsonParser);

        // Single Object element
        if (!jsonNode.isArray()) {
            System.out.println("In deserializer: Is not array");
            GitHubContent gitHubContent =
                    deserializeJsonElement(jsonNode, new GitHubContent());
            return new GitHubContent[]{gitHubContent};
        }

        System.out.println("In deserializer: Is array");
        // Array of Object elements
        List<GitHubContent> contents = new ArrayList<>();
        for (JsonNode node : jsonNode)
            contents.add(deserializeJsonElement(node, new GitHubContent()));

        System.out.println("In deserializer: parsing done");
        return contents.toArray(new GitHubContent[0]);
    }

    private GitHubContent deserializeJsonElement(JsonNode jsonNode, GitHubContent gitHubContent) {
        gitHubContent.setName(jsonNode.get("name").asText());
        gitHubContent.setSize(jsonNode.get("size").asInt());
        gitHubContent.setType(jsonNode.get("type").asText());
        gitHubContent.setDownloadUrl(jsonNode.get("download_url").asText(null));
        return gitHubContent;
    }
}
