package com.lunarsonic.geometryservice.client;
import com.lunarsonic.geometryservice.dto.HistoryRequest;
import com.lunarsonic.geometryservice.model.HistoryActionType;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class HistoryClient {
    private final WebTarget webTarget;
    private final Client client;

    private static final String HISTORY_SERVICE_URL = System.getenv("HISTORY_SERVICE_URL");

    public HistoryClient() {
        client = ClientBuilder.newClient();
        webTarget = client.target(HISTORY_SERVICE_URL);
    }

    public Response sendHistory(Long userId, HistoryActionType action, String payload, String token) {
        HistoryRequest historyRequest = new HistoryRequest(userId, action, payload);
        return webTarget.request(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token)
                .post(Entity.entity(historyRequest, MediaType.APPLICATION_JSON), Response.class);
    }
}
