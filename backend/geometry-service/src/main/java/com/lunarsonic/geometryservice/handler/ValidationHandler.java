package com.lunarsonic.geometryservice.handler;

import com.lunarsonic.geometryservice.abstraction.BaseHandler;
import com.lunarsonic.geometryservice.core.Validation;
import com.lunarsonic.geometryservice.exception.ApplicationError;
import com.lunarsonic.geometryservice.exception.ApplicationException;
import com.lunarsonic.geometryservice.model.PointData;
import com.lunarsonic.geometryservice.model.ResultContext;

import java.util.Map;

public class ValidationHandler extends BaseHandler {
    @Override
    protected void process(ResultContext resultContext) {
        Map<String, String> coordinates = resultContext.getCoordinates();
        Validation validation = new Validation();
        validation.validateXYR(coordinates);
        if (validation.hasErrors()) {
            throw new ApplicationException(ApplicationError.VALIDATION_FAILED);
        }
        float x = validation.getX();
        float y = validation.getY();
        float r = validation.getR();
        PointData point = new PointData(x, y, r);
        resultContext.setPointData(point);
    }
}
