package ressourcerest;

import entities.Option;
import metiers.OptionBusiness;
import utilities.Services;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("options")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OptionRest {

        private final OptionBusiness business = Services.OPTIONS;

        // A.1  POST /options
        @POST
        public Response addOption(Option option) {
                if (option == null || !business.addOption(option)) {
                        return Response.status(Response.Status.NOT_FOUND).build();
                }
                return Response.ok(option).build();
        }

        // A.2 + A.3  GET /options   and   GET /options?domaine=Mathématiques
        @GET
        public Response getOptions(@QueryParam("domaine") String domaine) {
                List<Option> list = (domaine == null || domaine.isEmpty())
                        ? business.getListeOptions()
                        : business.getOptionsByDomaine(domaine);
                return Response.ok(list).build();
        }

        // A.6  GET /options/1
        @GET
        @Path("{code}")
        public Response getOption(@PathParam("code") int code) {
                Option option = business.getOptionByCode(code);
                if (option == null) {
                        return Response.status(Response.Status.NOT_FOUND).build();
                }
                return Response.ok(option).build();
        }

        // A.5  PUT /options/1
        @PUT
        @Path("{code}")
        public Response updateOption(@PathParam("code") int code, Option option) {
                if (option == null || business.getOptionByCode(code) == null) {
                        return Response.status(Response.Status.NOT_FOUND).build();
                }
                option.setCodeOption(code);
                business.updateOption(code, option);
                return Response.ok(option).build();
        }

        // A.4  DELETE /options/2
        @DELETE
        @Path("{code}")
        public Response deleteOption(@PathParam("code") int code) {
                if (!business.deleteOption(code)) {
                        return Response.status(Response.Status.NOT_FOUND).build();
                }
                return Response.noContent().build();
        }
}