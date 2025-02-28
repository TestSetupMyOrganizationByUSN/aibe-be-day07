package web_client;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.util.Map;

public abstract class WebClient implements IWebClient {

    protected WebClient() {

    }

    @Override
    public String sendRequest(HttpRequest request) {
        return "";
    }

    @Override
    public HttpRequest makeRequest(String url, String body, Map<String, String> headers) {
        return null;
    }
}

interface IWebClient {
    HttpClient httpClient = HttpClient.newBuilder().build();

    String sendRequest(HttpRequest request);
    HttpRequest makeRequest(String url, String body, Map<String, String> headers);
}
