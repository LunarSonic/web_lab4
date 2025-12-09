package com.lunarsonic.historyservice.dto;
import com.lunarsonic.historyservice.model.HistoryActionType;

public record HistoryRequest(Long userId, HistoryActionType actionType, String payload) {
}
