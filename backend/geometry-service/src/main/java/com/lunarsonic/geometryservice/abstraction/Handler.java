package com.lunarsonic.geometryservice.abstraction;
import com.lunarsonic.geometryservice.model.ResultContext;

public interface Handler {
    void handle(ResultContext resultContext);
    void setNextHandler(Handler nextHandler);
}
