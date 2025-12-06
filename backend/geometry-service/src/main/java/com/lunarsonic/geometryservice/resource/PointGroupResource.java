package com.lunarsonic.geometryservice.resource;
import com.lunarsonic.geometryservice.dto.GroupRequest;
import com.lunarsonic.geometryservice.dto.GroupResponse;
import com.lunarsonic.geometryservice.entity.PointGroup;
import com.lunarsonic.geometryservice.service.PointGroupService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Path("/groups")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PointGroupResource extends BaseResource {
    private static final Logger logger = Logger.getLogger(PointGroupResource.class.getName());

    @Inject
    private PointGroupService pointGroupService;

    @POST
    public Response createGroup(GroupRequest request) {
        Long userId = getUserId();
        String token = getToken();
        logger.info("Пользователь с id=" + userId + " создает группу: " + request.groupName());
        PointGroup pointGroup = pointGroupService.createNewPointGroup(userId, request.groupName(), token);
        logger.info("Группа создана: " + pointGroup.getId());
        return Response.status(Response.Status.CREATED).entity(new GroupResponse(pointGroup.getId(), pointGroup.getName(), pointGroup.getIsActive())).build();
    }

    @GET
    public Response getGroups() {
        Long userId = getUserId();
        logger.info("Получение групп пользователя с id=" + userId);
        List<PointGroup> pointGroupList = pointGroupService.getAllGroups(userId);
        logger.info("Найдено " + pointGroupList.size() + " групп");
        List<GroupResponse> groupResponses = pointGroupList.stream()
                .map(pointGroup -> new GroupResponse(pointGroup.getId(), pointGroup.getName(), pointGroup.getIsActive()))
                .collect(Collectors.toList());
        return Response.ok(groupResponses).build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteGroup(@PathParam("id") Long groupId) {
        Long userId = getUserId();
        String token = getToken();
        PointGroup pointGroup = pointGroupService.deletePointGroup(groupId, userId, token);
        logger.info("Удалилась группа у пользователя с id=" + pointGroup.getId());
        return Response.ok().build();
    }

    @GET
    @Path("/active")
    public Response getActiveGroup() {
        Long userId = getUserId();
        PointGroup pointGroup = pointGroupService.getActiveGroup(userId);
        return Response.ok(new GroupResponse(pointGroup.getId(), pointGroup.getName(), pointGroup.getIsActive())).build();
    }

    @POST
    @Path("{id}/switch")
    public Response switchGroup(@PathParam("id") Long groupId) {
        Long userId = getUserId();
        String token = getToken();
        pointGroupService.activateGroup(groupId, userId, token);
        logger.info("Активировалась группа у пользователя с id=" + userId);
        PointGroup activeGroup = pointGroupService.getActiveGroup(userId);
        return Response.ok(new GroupResponse(activeGroup.getId(), activeGroup.getName(), activeGroup.getIsActive())).build();
    }
}
