package gc.unir.actividad2.elastic_buscador.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;

import java.util.Arrays;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticsearchConfig {

	private static final int BONSAI_PORT = 443; // Puerto estándar para HTTPS
    private static final String BONSAI_URL = "personal-search-6042288385.us-east-1.bonsaisearch.net"; // Reemplaza con tu URL de Bonsai
    private static final String BONSAI_USERNAME = "6gjmtt4pp5"; // Reemplaza con tus credenciales
    private static final String BONSAI_PASSWORD = "9774yrticw"; // Reemplaza con tus credenciales

    @Bean
    public ElasticsearchClient elasticsearchClient() {
        BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, 
            new UsernamePasswordCredentials(BONSAI_USERNAME, BONSAI_PASSWORD));

        RestClientBuilder builder = RestClient.builder(new HttpHost(BONSAI_URL, BONSAI_PORT, "https"))
            .setHttpClientConfigCallback(httpClientBuilder -> {
                return httpClientBuilder
                    .setDefaultCredentialsProvider(credentialsProvider)
                    .addInterceptorFirst((HttpResponseInterceptor) (request, context) -> {
                        request.addHeader("X-Elastic-Product", "Elasticsearch");
                    }).setDefaultHeaders(Arrays.asList(
                            new BasicHeader("Content-Type", "application/json"),
                            new BasicHeader("Accept", "application/json")
                        ));
            });

        RestClient restClient = builder.build();
        RestClientTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());

        return new ElasticsearchClient(transport);
    }
}
