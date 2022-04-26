package net.electrogrid.ws;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/payments")
public class PaymentResource {
	
private  PaymentDAO dao = PaymentDAO.getInstance();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Payment> list(){
		return dao.ListAll();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response add(Payment payment) throws URISyntaxException {
		int newPaymentId = dao.add(payment);
		URI uri = new URI("/paymentmanagement/payments/" + newPaymentId);
		
		return Response.created(uri).build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String add(@FormParam("payOptions") String payOptions,
			          @FormParam("totalPayment") int totalPayment,
			          @FormParam("cardName") String cardName,
			          @FormParam("customerName") String customerName
			        
			          ) throws URISyntaxException {
		Payment payment = new Payment();
		payment.setPayOptions(payOptions);
		payment.setTotalPayment(totalPayment);
		payment.setCardName(cardName);
		payment.setCustomerName(customerName);
		
		
		int newPaymentId = dao.add(payment);
		
		return "Item added! new id = "+newPaymentId;
	}
	
	@GET
	@Path("{id}")
	public Response get(@PathParam("id") int id) {
		Payment payment = dao.get(id);
		if(payment != null) {
			return Response.ok(payment, MediaType.APPLICATION_JSON).build();
		}else {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		
	}
	
	@PUT
	@Consumes (MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Response update(@PathParam("id") int id, Payment payment) {
		payment.setPaymentId(id);
		if(dao.update(payment)) {
			return Response.ok().build();
		}else {
			return Response.notModified().build();
		}
	}
	
	@DELETE
	@Path("{id}")
	public Response delete(@PathParam("id") int id) {
		if(dao.delete(id)) {
			return Response.ok().build();
		}else {
			return Response.notModified().build();
		}
	}

}


