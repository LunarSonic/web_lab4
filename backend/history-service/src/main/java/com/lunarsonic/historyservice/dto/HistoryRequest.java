package com.lunarsonic.historyservice.dto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.lunarsonic.historyservice.model.HistoryActionType;

@JsonIgnoreProperties(ignoreUnknown = true)
public record HistoryRequest(HistoryActionType actionType, String payload) {
}
