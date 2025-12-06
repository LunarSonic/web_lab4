package com.lunarsonic.geometryservice.core;
import com.lunarsonic.geometryservice.abstraction.Handler;
import com.lunarsonic.geometryservice.handler.ResultHandler;
import com.lunarsonic.geometryservice.handler.ValidationHandler;

public class RequestChainExecutor {
    public Handler createChain() {
        Handler validationHandler = new ValidationHandler();
        Handler resultHandler = new ResultHandler();
        validationHandler.setNextHandler(resultHandler);
        return validationHandler;
    }
}
