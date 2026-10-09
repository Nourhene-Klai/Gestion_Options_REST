package ressourcerest;

import entities.Etudiant;
import entities.EtudiantList;
import entities.Option;
import metiers.EtudiantBusiness;
import metiers.OptionBusiness;
import utilities.Services;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("etudiants")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EtudiantRest {

    private final EtudiantBusiness business = Services.ETUDIANTS;
    private final OptionBusiness optionBusiness = Services.OPTIONS;

    // B.1  POST /etudiants   (404 if the option does not exist)
    @POST
    public Response addEtudiant(Etudiant etudiant) {
        if (etudiant == null || etudiant.getOption() == null
                || optionBusiness.getOptionByCode(etudiant.getOption().getCodeOption()) == null
                || !business.addEtudiant(etudiant)) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(etudiant).build();
    }

    // B.2  GET /etudiants
    @GET
    public Response getEtudiants() {
        return Response.ok(business.getAllEtudiants()).build();
    }

    // B.6  GET /etudiants/option?codeOption=1   -> XML
    // The literal path "option" takes priority over "{id}", so there is no conflict.
    @GET
    @Path("option")
    @Produces(MediaType.APPLICATION_XML)
    public Response getEtudiantsByOption(@QueryParam("codeOption") int codeOption) {
        Option option = optionBusiness.getOptionByCode(codeOption);
        if (option == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(new EtudiantList(business.getEtudiantsByOption(option))).build();
    }

    // B.3  GET /etudiants/I001
    @GET
    @Path("{id}")
    public Response getEtudiant(@PathParam("id") String id) {
        Etudiant etudiant = business.getEtudiantByIdentifiant(id);
        if (etudiant == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(etudiant).build();
    }

    // B.5  PUT /etudiants/I001
    @PUT
    @Path("{id}")
    public Response updateEtudiant(@PathParam("id") String id, Etudiant etudiant) {
        Etudiant existing = business.getEtudiantByIdentifiant(id);
        if (etudiant == null || existing == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        etudiant.setIdentifiant(id);
        if (etudiant.getOption() == null) {
            etudiant.setOption(existing.getOption());
        }
        business.updateEtudiant(id, etudiant);
        return Response.ok(etudiant).build();
    }

    // B.4  DELETE /etudiants/I003
    @DELETE
    @Path("{id}")
    public Response deleteEtudiant(@PathParam("id") String id) {
        if (!business.deleteEtudiant(id)) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.noContent().build();
    }
}