package com.lunarsonic.geometryservice.resource;
import com.lunarsonic.geometryservice.entity.Point;
import com.lunarsonic.geometryservice.model.PointData;
import com.lunarsonic.geometryservice.service.PointService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.logging.Logger;

@Path("/groups/active/points")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PointResource extends BaseResource {
    private static final Logger logger = Logger.getLogger(PointResource.class.getName());

    @Inject
    private PointService pointService;

    @POST
    public Response addPoint(PointData pointData) {
        Long userId = getUserId();
        String token = getToken();
        logger.info("Добавляется точка у пользователя с id=" + userId
                + " x=" + pointData.getX()
                + " y=" + pointData.getY()
                + " r=" + pointData.getR());
        Point newPoint = pointService.addPoint(pointData, userId, token);
        logger.info("Точка добавлена: id=" + newPoint.getId() + " hit=" + newPoint.isHit() + " groupId=" + newPoint.getPointGroup().getId());
        return Response.status(Response.Status.CREATED).entity(newPoint).build();
    }

    @GET
    public Response getPoints() {
        Long userId = getUserId();
        List<Point> points = pointService.getPoints(userId);
            logger.info("У пользователя с id=" + userId + " кол-во точек: " + points.size());
        return Response.ok(points).build();
    }
}
