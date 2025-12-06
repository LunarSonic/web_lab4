package com.lunarsonic.historyservice.resource;
import com.lunarsonic.historyservice.dto.HistoryMessage;
import com.lunarsonic.historyservice.dto.HistoryRequest;
import com.lunarsonic.historyservice.secutiry.UserPrincipal;
import com.lunarsonic.historyservice.service.HistoryService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import java.util.List;
import java.util.logging.Logger;

@Path("/history")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class HistoryResource {
    private static final Logger logger = Logger.getLogger(HistoryResource.class.getName());

    @Inject
    private HistoryService historyService;

    @POST
    public Response saveAction(@Context SecurityContext securityContext, HistoryRequest historyRequest) {
        UserPrincipal userPrincipal = (UserPrincipal) securityContext.getUserPrincipal();
        Long userId = userPrincipal.getUserId();
        historyService.saveAction(userId, historyRequest);
        logger.info("Действие пользователя сохранилось: " + historyRequest);
        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    public Response get(@Context SecurityContext securityContext) {
        UserPrincipal userPrincipal = (UserPrincipal) securityContext.getUserPrincipal();
        Long userId = userPrincipal.getUserId();
        logger.info("Получение истории пользователя с id=" + userId);
        List<HistoryMessage> records = historyService.getHistoryMessages(userId);
        logger.info("У пользователя с id=" + userId + " найдено " + records.size() + " записей в истории");
        return Response.ok(records).build();
    }
}
