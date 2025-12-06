package com.lunarsonic.historyservice.dto;

public record PointAddedPayload(float x, float y, float r, boolean hit, int groupId) {
}
