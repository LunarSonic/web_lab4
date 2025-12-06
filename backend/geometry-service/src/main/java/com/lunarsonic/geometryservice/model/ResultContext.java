package com.lunarsonic.geometryservice.model;

import com.lunarsonic.geometryservice.dto.Result;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class ResultContext {
    private final long startTime;
    private PointData pointData;
    private Result result;
    private final Map<String, String> coordinates;

    public ResultContext() {
        startTime = System.nanoTime();
        this.coordinates = new HashMap<>();
    }
}
