package edu.unc.mapseq.ws.ncnexus38.alignment;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.ws.BindingType;

import org.renci.vcf.VCFResult;

@BindingType(value = javax.xml.ws.soap.SOAPBinding.SOAP11HTTP_BINDING)
@WebService(targetNamespace = "http://baseline.ncnexus.ws.mapseq.unc.edu", serviceName = "NCNEXUS38AlignmentService", portName = "NCNEXUS38AlignmentPort")
@SOAPBinding(style = Style.DOCUMENT, use = Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
@Path("/NCNEXUS38AlignmentService/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface NCNEXUS38AlignmentService {

    @GET
    @Path("/lookupQuantificationResults/{sampleId}")
    @WebMethod
    public QualityControlInfo lookupQuantificationResults(@PathParam("sampleId") @WebParam(name = "sampleId") Long sampleId);

    @GET
    @Path("/lookupIdentityInfoFromVCF/{sampleId}")
    @WebMethod
    public VCFResult lookupIdentityInfoFromVCF(@PathParam("sampleId") @WebParam(name = "sampleId") Long sampleId);

}
