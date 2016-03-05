package cmpe282swaroop121.server;



import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.Response.StatusType;

import org.bson.BSON;
import org.codehaus.jettison.json.JSONObject;

import cmpe282swaroop121.client.Employee;
import cmpe282swaroop121.client.Project;

import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.DBCursor;

@Path("/")
public class Server {
	
	
	@POST
	@Path("/employee")	
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON	)	
	public Response createEmployeeInJSON(Employee e) throws Exception {
		DBObject doc=null;
		String result="";
		BasicDBObject whereQuery = new BasicDBObject();
		int id = e.getId();
		whereQuery.put("id", id);
		BasicDBObject dbo = new BasicDBObject();
		MongoClient mongoClient = new MongoClient("127.0.0.1", 27017);
		DB db  = mongoClient.getDB("cmpe282swaroop121");					
		DBCollection col = db.getCollection("employee");	
		dbo = (BasicDBObject)col.findOne(whereQuery);	
		if(dbo !=null){
			return Response.status(Status.CONFLICT). build();
		}

		try
		{							
				
				doc = createDBObject1(e);
				col.insert(doc);
				result = "created";						 			   
				
											
		}
		catch(Exception er)
		{
			throw er;
		}		
		mongoClient.close();
		return Response.ok().entity("").build();
		
	}	
	
	 private static DBObject createDBObject1(Employee ee) {
	        BasicDBObjectBuilder docBuilder = BasicDBObjectBuilder.start();
	                                 	 
	        docBuilder.append("id", ee.getId());	
	        docBuilder.append("firstName",ee.getFirstName());
	    	docBuilder.append("lastName",ee.getLastName());
			return docBuilder.get();
	    }
	 
	 
	 
	
	 
	 @GET
		@Path("/employee/{id}")	
		@Produces(MediaType.APPLICATION_JSON)
		public Response selectEmployeeInJSON(@PathParam("id") int id) throws Exception {	
			BasicDBObject whereQuery = new BasicDBObject();
			whereQuery.put("id", id);		
			Employee crc = new Employee();	
			BasicDBObject dbo = new BasicDBObject();
			MongoClient mongoClient = new MongoClient("127.0.0.1", 27017);
			try
			{							
				DB db  = mongoClient.getDB("cmpe282swaroop121");					
				
					DBCollection col = db.getCollection("employee");				
					

					dbo = (BasicDBObject)col.findOne(whereQuery);	
					if(dbo ==null){
						return Response.status(Status.NOT_FOUND). build();
					}
					int idd = dbo.getInt("id");
				        crc.setId(idd);
				        crc.setfirstName(dbo.getString("firstName"));
				        crc.setlastName(dbo.getString("lastName"));
				        
				        
			}
			catch(Exception e)
			{
				throw e;
			}		
			mongoClient.close();
			return Response.ok().entity(crc).build();		
		}
	 	
	 	
	
	 
	 	
	
	 
	 
	 @GET
		@Path("/employee")	
		@Produces(MediaType.APPLICATION_JSON)
		public Response selectEmployee() throws Exception {	
	 		BasicDBObject obj = null;
	 		BasicDBObject r = new BasicDBObject();
	 	
			List<Employee> pList = new ArrayList<Employee>(); 
			
		MongoClient mongoClient = new MongoClient("127.0.0.1", 27017);
			try
			{							
			DB db  = mongoClient.getDB("cmpe282swaroop121");					
				
				DBCollection col = db.getCollection("employee");	
				DBCursor cursor = col.find();
				r = (BasicDBObject)col.findOne();	
				if(r==null)
				{
					return Response.status(Status.NOT_FOUND). build();
				}
				
		         while (cursor.hasNext()) { 
		        	 obj = (BasicDBObject) cursor.next();
		        	 Employee employee = new Employee();
		        	System.out.println(obj);
		        	employee.setfirstName(obj.getString("firstName"));
		        	employee.setlastName(obj.getString("lastName"));
		        	employee.setId(obj.getInt("id"));
		        	
		        
		        	 
		            System.out.println("Inserted Document: "+obj.get("budget")+" "); 
		            pList.add(employee);
		           
		         }
					
																							 			  												
			}
			catch(Exception e)
			{
				throw e;
		}		
		mongoClient.close();
			
			return Response.ok().entity(pList).build();
			
		}
	
	 
	 
	 
	 
	 
	 
	 
	 
	
	 
	 
	 @PUT
		@Path("/employee/{id}")
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		public Response updateEmployeeInJSON(@PathParam("id")int id, Employee em) throws Exception {			 
		String result = null;
		//	BasicDBObject dbResult = null;
			
			BasicDBObject whereQuery = new BasicDBObject();
			BasicDBObject pl = new BasicDBObject();
			whereQuery.put("id", id);
		//	Project cr = new Project();	
			System.out.println("id is "+id);
			MongoClient mongoClient = new MongoClient("127.0.0.1", 27017);
			try
			{							
				DB db  = mongoClient.getDB("cmpe282swaroop121");					
				
					DBCollection col = db.getCollection("employee");				
					
					//BasicDBObject newDocument = new BasicDBObject();
					//newDocument.append("$set", new BasicDBObject().append("name", pr.getName()));
					pl = (BasicDBObject)col.findOne(whereQuery);	
					if(pl ==null){
						return Response.status(Status.NOT_FOUND). build();
					}
					BasicDBObject newDocument = new BasicDBObject();  
					
					newDocument.put("firstName", em.getFirstName());  
					newDocument.put("lastName", em.getLastName());  
					newDocument.put("id", em.getId());  
					   
					col.update(new BasicDBObject().append("id",id), newDocument);  
					
					result = "Updated Successfully";
				}
	
			
				catch(Exception e)
				{
					throw e;
				}		
				mongoClient.close();
				return Response.ok().build();	
			
		}

	 
	 
	 
	 	
	 	@DELETE
		@Path("/employee/{id}")
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		public Response deleteEmployeeinJSON(@PathParam("id")int id) throws Exception {			 
			String result1 = null;
			BasicDBObject whereQuery = new BasicDBObject();
			BasicDBObject p = new BasicDBObject();
			whereQuery.put("id", id);
			
			MongoClient mongoClient = new MongoClient("127.0.0.1", 27017);
			try
			{							
				DB db  = mongoClient.getDB("cmpe282swaroop121");					
				
					DBCollection col = db.getCollection("employee");														
					
					BasicDBObject document = new BasicDBObject();
					document.put("id", id);
					p = (BasicDBObject)col.findOne(whereQuery);	
					if(p ==null){
						return Response.status(Status.NOT_FOUND). build();
					}
					col.remove(document);
					
					result1 = "Deleted Successfully";
				
				
			}
				catch(Exception e)
				{
					throw e;
				}		
				mongoClient.close();
				return Response.ok().entity(result1).build();
					
			
		}
	
	
	 	
	 	
	 	
	 	
	 	
	 	
	 	
	 	
	 	
	 	
	 	
	 	
	 	
	 	@POST
		@Path("/project")	
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)	
		public Response createProjectInJSON(Project p) throws Exception {
			DBObject doc = null;
			BasicDBObject whereQuery = new BasicDBObject();
			
			int id = p.getId();
			whereQuery.put("id", id);
			
			BasicDBObject n = new BasicDBObject();
			BasicDBObject dbo = new BasicDBObject();
		
			 
			
			MongoClient mongoClient = new MongoClient("127.0.0.1", 27017);
			try
			{							
				DB db  = mongoClient.getDB("cmpe282swaroop121");					
					
					DBCollection col = db.getCollection("project");
					dbo = (BasicDBObject)col.findOne(whereQuery);	
					if(dbo !=null){
						return Response.status(Status.CONFLICT). build();
					}
					System.out.println(p);
					doc = createDBObject(p);
					col.insert(doc);
					
				
					
												
			}
			catch(Exception e)
			{
				throw e;
			}		
			mongoClient.close();
			return Response.ok().entity("").build();
			
			
		}	
		
		 private static DBObject createDBObject(Project pr) {
		        BasicDBObjectBuilder docBuilder = BasicDBObjectBuilder.start();
				docBuilder.append("id", pr.getId());	
		        docBuilder.append("name",pr.getName());
		    	docBuilder.append("budget",pr.getBudget());
				return docBuilder.get();
		    }
		 
		 
	 	
		 
		 
		 
		 @GET
			@Path("/project/{id}")	
			@Produces(MediaType.APPLICATION_JSON)
			public Response selectProjectInJSON(@PathParam("id") int id) throws Exception {	
				BasicDBObject whereQuery = new BasicDBObject();
				whereQuery.put("id", id);		
				Project cr = new Project();	
				BasicDBObject dbo = new BasicDBObject();
				MongoClient mongoClient = new MongoClient("127.0.0.1", 27017);
				try
				{							
					DB db  = mongoClient.getDB("cmpe282swaroop121");					
					
						DBCollection col = db.getCollection("project");				
						

						dbo = (BasicDBObject)col.findOne(whereQuery);	
						if(dbo ==null){
							return Response.status(Status.NOT_FOUND). build();
						}
						int idd = dbo.getInt("id");
					        cr.setId(idd);
					        cr.setName(dbo.getString("name"));
					        cr.setBudget(Float.parseFloat(dbo.getString("budget")));
					        
					        
				}
				catch(Exception e)
				{
					throw e;
				}		
				mongoClient.close();
				return Response.ok().entity(cr).build();		
			}
		 	
		 	
	 	
		 
		 
		 
		 @GET
			@Path("/project")	
			@Produces(MediaType.APPLICATION_JSON)
			public Response selectProject() throws Exception {	
		 		BasicDBObject obj = null;
		 		BasicDBObject l = new BasicDBObject();
		
				List<Project> prList = new ArrayList<Project>(); 
				
			MongoClient mongoClient = new MongoClient("127.0.0.1", 27017);
				try
				{							
				DB db  = mongoClient.getDB("cmpe282swaroop121");					
					
					DBCollection col = db.getCollection("project");	
					DBCursor cursor = col.find();
					l = (BasicDBObject)col.findOne();	
					if(l==null)
					{
						return Response.status(Status.NOT_FOUND). build();
					}
					
			         while (cursor.hasNext()) { 
			        	 obj = (BasicDBObject) cursor.next();
			        	 Project project = new Project();
			        	System.out.println(obj);
			        	project.setName(obj.getString("name"));
			        	project.setId(obj.getInt("id"));
			        	
			        	project.setBudget(Float.parseFloat(obj.getString("budget"))); 
			        	 
			            System.out.println("Inserted Document: "+obj.get("budget")+" "); 
			            prList.add(project);
			           
			         }
						
																								 			  												
				}
				catch(Exception e)
				{
					throw e;
			}		
			mongoClient.close();
				//lis.add(cr);
				//return lis;
				return Response.ok().entity(prList).build();
				
			}
	 	
		 
		 
		 
		 
		 @PUT
			@Path("/project/{id}")
			@Consumes(MediaType.APPLICATION_JSON)
			@Produces(MediaType.APPLICATION_JSON)
			public Response updateProjectInJSON(@PathParam("id")int id, Project pr) throws Exception {			 
			String result = null;
			//	BasicDBObject dbResult = null;
				
				BasicDBObject whereQuery = new BasicDBObject();
				BasicDBObject p = new BasicDBObject();
				whereQuery.put("id", id);
			//	Project cr = new Project();	
				System.out.println("id is "+id);
				MongoClient mongoClient = new MongoClient("127.0.0.1", 27017);
				try
				{							
					DB db  = mongoClient.getDB("cmpe282swaroop121");					
					
						DBCollection col = db.getCollection("project");				
						
				
						p = (BasicDBObject)col.findOne(whereQuery);	
						if(p ==null){
							return Response.status(Status.NOT_FOUND). build();
						}
						BasicDBObject newDocument = new BasicDBObject();  
						
						newDocument.put("name", pr.getName());  
						newDocument.put("budget", pr.getBudget());  
						newDocument.put("id", pr.getId());  
						   
						col.update(new BasicDBObject().append("id",id), newDocument);  
						
						result = "Updated Successfully";
					}
		
				
					catch(Exception e)
					{
						throw e;
					}		
					mongoClient.close();
					return Response.ok().build();	
				
			}
	
		 
		 @DELETE
			@Path("/project/{id}")
			@Consumes(MediaType.APPLICATION_JSON)
			@Produces(MediaType.APPLICATION_JSON)
			public Response deleteProjectinJSON(@PathParam("id")int id) throws Exception {			 
				String result = null;
				BasicDBObject whereQuery = new BasicDBObject();
				BasicDBObject dbo1 = new BasicDBObject();
				whereQuery.put("id", id);
				
				MongoClient mongoClient = new MongoClient("127.0.0.1", 27017);
				try
				{							
					DB db  = mongoClient.getDB("cmpe282swaroop121");					
					
						DBCollection col = db.getCollection("project");
						dbo1 = (BasicDBObject)col.findOne(whereQuery);	
						if(dbo1 ==null){
							return Response.status(Status.NOT_FOUND). build();
						}
						
						BasicDBObject document = new BasicDBObject();
						document.put("id", id);
						
						
						col.remove(document);
						
						result = "Deleted Successfully";
					
					
				}
					catch(Exception e)
					{
						throw e;
					}		
					mongoClient.close();
					return Response.ok().entity(result).build();
				
			}
}
