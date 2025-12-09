package com.lunarsonic.historyservice.dto;
import com.lunarsonic.historyservice.model.HistoryActionType;

public record HistoryRequest(HistoryActionType actionType, String payload) {
}
