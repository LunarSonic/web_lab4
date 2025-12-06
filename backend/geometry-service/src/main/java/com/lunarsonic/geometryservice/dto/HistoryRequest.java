package com.lunarsonic.geometryservice.dto;
import com.lunarsonic.geometryservice.model.HistoryActionType;

public record HistoryRequest(Long userId, HistoryActionType actionType, String payload) {
}
