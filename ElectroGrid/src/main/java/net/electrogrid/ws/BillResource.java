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

@Path("/bills")
public class BillResource {
	
private BillDAO dao = BillDAO.getInstance();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Bill> list(){
		return dao.ListAll();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response add(Bill bill) throws URISyntaxException {
		int newBilltId = dao.add(bill);
		URI uri = new URI("/billmanagement/bills/" + newBilltId);
		
		return Response.created(uri).build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String add(@FormParam("unit") int unit,
			          @FormParam("tax") int tax,
			          @FormParam("billNo") String billNo,
			          @FormParam("amount") int amount
			          ) throws URISyntaxException {
		Bill bill = new Bill();
		bill.setUnit(unit);
		bill.setTax(tax);
		bill.setBillNo(billNo);
		bill.setAmount(amount);
		
		int newBillId = dao.add(bill);
		
		return "Item added! new id = "+newBillId;
	}
	
	@GET
	@Path("{id}")
	public Response get(@PathParam("id") int id) {
		Bill bill = dao.get(id);
		if(bill != null) {
			return Response.ok(bill, MediaType.APPLICATION_JSON).build();
		}else {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		
	}
	
	@PUT
	@Consumes (MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Response update(@PathParam("id") int id, Bill bill) {
		bill.setBillId(id);
		if(dao.update(bill)) {
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

