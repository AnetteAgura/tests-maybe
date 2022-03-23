package ee.bitweb.testingsample.common.retrofit;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RetrofitBuilder {
/*
    private static final HttpLoggingInterceptor LOGGING_INTERCEPTOR = new HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BASIC);
*/

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE)
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

    private static final JacksonConverterFactory CONVERTER_FACTORY = JacksonConverterFactory.create(OBJECT_MAPPER);

    public static <T> T createApiService(String baseUrl, Class<T> apiDefinition) {
        log.info("Creating new API service {} with base url: {} ", apiDefinition, baseUrl);

        return createDefaultRetrofitBuilder(baseUrl)
                .client(createDefaultHttpClientBuilder().build())
                .build()
                .create(apiDefinition);
    }

    public static <T> T createApiServiceWithInterceptor(String baseUrl, Class<T> apiDefinition, Interceptor interceptor) {
        log.info("Creating new API service {} with base url: {} ", apiDefinition, baseUrl);

        return createDefaultRetrofitBuilder(baseUrl)
                .client(createHttpClientBuilderWithInterceptor(interceptor).build())
                .build()
                .create(apiDefinition);
    }

    public static <T> T createApiServiceWithMapper(String baseUrl, Class<T> apiDefinition, ObjectMapper objectMapper) {
        log.info("Creating new API service {} with custom object mapper with base url: {} ", apiDefinition, baseUrl);

        return createRetrofitBuilderWithMapper(baseUrl, objectMapper)
                .client(createDefaultHttpClientBuilder().build())
                .build()
                .create(apiDefinition);
    }

    private static Retrofit.Builder createDefaultRetrofitBuilder(String baseUrl) {
        log.info("Creating new Retrofit.Builder for base url: {}", baseUrl);
        log.debug("Adding converter factory: {}", JacksonConverterFactory.class);

        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(CONVERTER_FACTORY);
    }

    private static Retrofit.Builder createRetrofitBuilderWithMapper(String baseUrl, ObjectMapper mapper) {
        log.info("Creating new Retrofit.Builder with custom object mapper for base url: {}", baseUrl);
        log.debug("Adding converter factory: {}", JacksonConverterFactory.class);

        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(JacksonConverterFactory.create(mapper));
    }

    private static OkHttpClient.Builder createHttpClientBuilderWithInterceptor(Interceptor interceptor) {
        var httpClient = defaultBuilder();
        addInterceptorIfMissing(httpClient, interceptor);

        return httpClient;
    }

    private static OkHttpClient.Builder createDefaultHttpClientBuilder() {
        return defaultBuilder();
    }

    private static OkHttpClient.Builder defaultBuilder() {
        var httpClient = new OkHttpClient.Builder();
        //addInterceptorIfMissing(httpClient, LOGGING_INTERCEPTOR);

        return httpClient;
    }

    private static void addInterceptorIfMissing(OkHttpClient.Builder httpClient, Interceptor interceptor) {
        log.info("Adding interceptor {} to {}", interceptor.getClass(), httpClient.getClass());

        if (!httpClient.interceptors().contains(interceptor)) {
            httpClient.interceptors().add(interceptor);
        } else {
            log.debug("Skipping interceptor {} as it is already added", interceptor.getClass());
        }
    }
}
