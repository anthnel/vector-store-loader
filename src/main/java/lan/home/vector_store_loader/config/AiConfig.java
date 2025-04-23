package lan.home.vector_store_loader.config;

import java.net.http.HttpClient;
import java.time.Duration;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.model.NoopApiKey;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.http.client.reactive.JdkClientHttpConnector;
import org.springframework.web.client.RestClient;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class AiConfig {

    @Bean
    OpenAiChatOptions openAiChatOptions() {
        return OpenAiChatOptions.builder()
                .model("meta-llama-3.1-8b-instruct")
                .temperature(0.4)
                .maxTokens(1000)
                .build();
    }

    @Bean
    ChatClient chatClient(ChatClient.Builder builder, OpenAiChatOptions openAiChatOptions) {
        return builder.defaultSystem(
                "Tu es quelqu'un qui a l'esprit très vif. Tes réponses sont toujours intelligentes et concises.")
                .defaultOptions(openAiChatOptions)
                .build();
    }

    @Bean
    OpenAiChatModel chatModel(OpenAiApi openAiApi, OpenAiChatOptions openApiChatOptions) {

        return OpenAiChatModel.builder()
                .openAiApi(openAiApi)
                .defaultOptions(openApiChatOptions)
                .build();

    }

    @Bean
    OpenAiApi openAiApi() {
        return OpenAiApi.builder()
                .baseUrl("http://localhost:1234")
                .apiKey(new NoopApiKey())
                .webClientBuilder(WebClient.builder()
                        // Force HTTP/1.1 for streaming
                        .clientConnector(new JdkClientHttpConnector(HttpClient.newBuilder()
                                .version(HttpClient.Version.HTTP_1_1)
                                .connectTimeout(Duration.ofSeconds(30))
                                .build())))
                .restClientBuilder(RestClient.builder()
                        // Force HTTP/1.1 for non-streaming
                        .requestFactory(new JdkClientHttpRequestFactory(HttpClient.newBuilder()
                                .version(HttpClient.Version.HTTP_1_1)
                                .connectTimeout(Duration.ofSeconds(30))
                                .build())))
                .build();

    }

}
