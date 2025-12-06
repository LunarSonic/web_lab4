package com.lunarsonic.geometryservice.service;
import com.lunarsonic.geometryservice.client.HistoryClient;
import com.lunarsonic.geometryservice.model.HistoryActionType;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.JsonObject;
import jakarta.ws.rs.core.Response;
import java.util.logging.Logger;

@ApplicationScoped
public class HistoryLoggingService {
    private static final Logger logger = Logger.getLogger(HistoryLoggingService.class.getName());

    @Inject
    HistoryClient historyClient;

    public void logHistory(Long userId, HistoryActionType action, JsonObject payload, String token) {
        Response response = historyClient.sendHistory(userId, action, payload.toString(), token);
        if (response.getStatus() < 200 || response.getStatus() >= 300) {
            logger.warning("Ошибка на сервисе History: " + response.getStatus());
        }
    }
}
