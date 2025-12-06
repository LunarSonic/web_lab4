package com.lunarsonic.geometryservice.service;
import com.lunarsonic.geometryservice.dao.PointGroupDAO;
import com.lunarsonic.geometryservice.entity.PointGroup;
import com.lunarsonic.geometryservice.exception.ApplicationError;
import com.lunarsonic.geometryservice.exception.ApplicationException;
import com.lunarsonic.geometryservice.model.HistoryActionType;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.transaction.Transactional;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@ApplicationScoped
public class PointGroupService {

    @Inject
    private PointGroupDAO pointGroupDAO;

    @Inject
    private HistoryLoggingService historyLoggingService;

    public PointGroup createNewPointGroup(Long userId, String groupName, String token) {
        pointGroupDAO.deactivateGroups(userId);
        PointGroup pointGroup = mapToPointGroup(userId, groupName);
        pointGroupDAO.saveGroup(pointGroup);
        JsonObject payload = Json.createObjectBuilder()
                .add("groupId", pointGroup.getId())
                .add("groupName", pointGroup.getName())
                .build();
        historyLoggingService.logHistory(userId, HistoryActionType.GROUP_CREATED, payload, token);
        return pointGroup;
    }

    @Transactional
    public PointGroup deletePointGroup(Long groupId, Long userId, String token) {
        PointGroup pointGroup = pointGroupDAO.getGroupByGroupIdAndUserId(groupId, userId);
        if (pointGroup == null) {
            throw new ApplicationException(ApplicationError.NO_POINT_GROUPS);
        }
        pointGroupDAO.deletePointsByGroupId(groupId);
        pointGroupDAO.deleteGroup(groupId, userId);
        JsonObject payload = Json.createObjectBuilder()
                .add("groupId", pointGroup.getId())
                .add("groupName", pointGroup.getName())
                .build();
        historyLoggingService.logHistory(userId, HistoryActionType.GROUP_DELETED, payload, token);
        return pointGroup;
    }

    public List<PointGroup> getAllGroups(Long userId) {
        return pointGroupDAO.getAllGroupsByUserId(userId);
    }

    public PointGroup getActiveGroup(Long userId) {
        PointGroup pointGroup = pointGroupDAO.findActiveGroupByUserId(userId);
        if (pointGroup == null) {
            throw new ApplicationException(ApplicationError.NO_ACTIVE_POINT_GROUP);
        }
        return pointGroup;
    }

    public void activateGroup(Long groupId, Long userId, String token) {
        PointGroup pointGroup = pointGroupDAO.getGroupByGroupIdAndUserId(groupId, userId);
        if (pointGroup == null) {
            throw new ApplicationException(ApplicationError.NO_POINT_GROUPS);
        }
        pointGroupDAO.deactivateGroups(userId);
        pointGroupDAO.activateGroup(groupId, userId);
        JsonObject payload = Json.createObjectBuilder()
                .add("groupId", pointGroup.getId())
                .add("groupName", pointGroup.getName())
                .build();
        historyLoggingService.logHistory(userId, HistoryActionType.GROUP_SWITCHED, payload, token);
    }

    private PointGroup mapToPointGroup(Long userId, String groupName) {
        PointGroup pointGroup = new PointGroup();
        pointGroup.setUserId(userId);
        pointGroup.setName(groupName);
        pointGroup.setIsActive(true);
        pointGroup.setCreatedAt(ZonedDateTime.now(ZoneId.of("Europe/Moscow")).toLocalDateTime());
        return pointGroup;
    }
}
