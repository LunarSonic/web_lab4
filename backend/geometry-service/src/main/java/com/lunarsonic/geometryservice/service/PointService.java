package com.lunarsonic.geometryservice.service;
import com.lunarsonic.geometryservice.abstraction.Handler;
import com.lunarsonic.geometryservice.core.RequestChainExecutor;
import com.lunarsonic.geometryservice.dao.PointDAO;
import com.lunarsonic.geometryservice.dto.Result;
import com.lunarsonic.geometryservice.entity.Point;
import com.lunarsonic.geometryservice.entity.PointGroup;
import com.lunarsonic.geometryservice.exception.ApplicationError;
import com.lunarsonic.geometryservice.exception.ApplicationException;
import com.lunarsonic.geometryservice.model.HistoryActionType;
import com.lunarsonic.geometryservice.model.PointData;
import com.lunarsonic.geometryservice.model.ResultContext;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class PointService {

    @Inject
    private PointDAO pointDAO;

    @Inject
    private PointGroupService pointGroupService;

    @Inject
    private HistoryLoggingService historyLoggingService;

    private final RequestChainExecutor requestChainExecutor = new RequestChainExecutor();
    private final Handler chain = requestChainExecutor.createChain();

    public Point addPoint(PointData pointData, Long userId, String token) {
        PointGroup activeGroup = pointGroupService.getActiveGroup(userId);
        if (isSamePoint(pointData, activeGroup.getId())) {
            throw new ApplicationException(ApplicationError.POINT_ALREADY_EXISTS);
        }
        Point newPoint = createPoint(pointData, userId, activeGroup);
        pointDAO.savePoint(newPoint);
        JsonObject payload = Json.createObjectBuilder()
                .add("x", newPoint.getX())
                .add("y", newPoint.getY())
                .add("r", newPoint.getR())
                .add("hit", newPoint.isHit())
                .add("groupId", newPoint.getPointGroup().getId())
                .build();
        historyLoggingService.logHistory(userId, HistoryActionType.POINT_ADDED, payload, token);
        return newPoint;
    }

    private boolean isSamePoint(PointData pointData, Long groupId) {
        Point existingPoint = pointDAO.findPointByCoordinatesAndGroup(pointData, groupId);
        return existingPoint != null;
    }

    @Transactional
    public List<Point> getPoints(Long userId) {
        PointGroup activeGroup = pointGroupService.getActiveGroup(userId);
        return pointDAO.findPointsByGroupId(activeGroup.getId());
    }

    private Point createPoint(PointData pointData, Long userId, PointGroup activeGroup) {
        ResultContext resultContext = new ResultContext();
        resultContext.getCoordinates().put("x", String.valueOf(pointData.getX()));
        resultContext.getCoordinates().put("y", String.valueOf(pointData.getY()));
        resultContext.getCoordinates().put("r", String.valueOf(pointData.getR()));
        chain.handle(resultContext);
        Result result = resultContext.getResult();
        return mapResultToPoint(result, userId, activeGroup);
    }

    private Point mapResultToPoint(Result result, Long userId, PointGroup activeGroup) {
        Point point = new Point();
        point.setX(result.x());
        point.setY(result.y());
        point.setR(result.r());
        point.setHit(result.hit());
        point.setServerTime(result.serverTime());
        point.setScriptTime(result.scriptTime());
        point.setUserId(userId);
        point.setPointGroup(activeGroup);
        return point;
    }
}
