package com.lunarsonic.historyservice.service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lunarsonic.historyservice.dao.HistoryDAO;
import com.lunarsonic.historyservice.dto.*;
import com.lunarsonic.historyservice.entity.History;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
public class HistoryService {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger logger = Logger.getLogger(HistoryService.class.getName());

    @Inject
    private HistoryDAO historyDAO;

    public void saveAction(Long userId, HistoryRequest historyRequest) {
        History history = mapToHistory(userId, historyRequest);
        historyDAO.saveAction(history);
    }

    private History mapToHistory(Long userId, HistoryRequest historyRequest) {
        History history = new History();
        history.setUserId(userId);
        history.setAction(historyRequest.actionType().name());
        history.setPayload(historyRequest.payload());
        ZoneId zone = ZoneId.of("Europe/Moscow");
        history.setActionTime(ZonedDateTime.now(zone));
        return history;
    }

    private String buildMessage(History history) {
        try {
            switch (history.getAction()) {
                case "POINT_ADDED":
                    PointAddedPayload point = objectMapper.readValue(history.getPayload(), PointAddedPayload.class);
                    return String.format("Точка добавлена в группу %d: x = %.2f, y = %.2f, r = %.1f", point.groupId(), point.x(), point.y(), point.r());
                case "GROUP_CREATED":
                    GroupAddedPayload groupAdded = objectMapper.readValue(history.getPayload(), GroupAddedPayload.class);
                    return String.format("Создана группа %s (id = %d)", groupAdded.groupName(), groupAdded.groupId());
                case "GROUP_SWITCHED":
                    GroupSwitchedPayload groupSwitched = objectMapper.readValue(history.getPayload(), GroupSwitchedPayload.class);
                    return String.format("Группа переключена на %s (id = %d)", groupSwitched.groupName(), groupSwitched.groupId());
                case "GROUP_DELETED":
                    GroupDeletedPayload groupDeleted = objectMapper.readValue(history.getPayload(), GroupDeletedPayload.class);
                    return String.format("Удалена группа %s (id = %d)", groupDeleted.groupName(), groupDeleted.groupId());
                default:
                    return history.getPayload();
            }
        } catch (JsonProcessingException e) {
            logger.severe(e.getMessage());
            return history.getPayload();
        }
    }

    public List<HistoryMessage> getHistoryMessages(Long userId) {
        return historyDAO.findHistoryByUserId(userId).stream().map(history -> {
            String message = buildMessage(history);
            String formattedDate = history.getActionTime()
                    .withZoneSameInstant(ZoneId.of("Europe/Moscow"))
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            return new HistoryMessage(history.getAction(), message, formattedDate);
        }).toList();
    }
}
