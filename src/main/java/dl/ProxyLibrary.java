package dl;

import java.awt.EventQueue;
import java.lang.Class;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Response;
import com.ning.http.client.AsyncHttpClient.BoundRequestBuilder;
import com.ning.http.client.websocket.WebSocket;
import com.ning.http.client.websocket.WebSocketTextListener;
import com.ning.http.client.websocket.WebSocketUpgradeHandler;

import bl.Customer;
import bl.Gadget;
import bl.Loan;
import bl.Reservation;



public class ProxyLibrary  implements LibraryData {

	private List<Customer> customers;
	private List<Gadget> gadgets;
	private List<Loan> loans;
	private List<Reservation> reservations;
	private List<CrudListener<Gadget>> gadgetListener = new ArrayList<CrudListener<Gadget>>();
	private List<CrudListener<Loan>> loanListener = new ArrayList<CrudListener<Loan>>();
	private List<CrudListener<Reservation>> reservationListener = new ArrayList<CrudListener<Reservation>>();
	private List<CrudListener<Customer>> customerListener = new ArrayList<CrudListener<Customer>>();
	private AsyncHttpClient httpClient;
	
	private Gson createGsonObject()
	{
		return  new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
	}
	
	public void close()
	{
		httpClient.close();
	}
	
	public ProxyLibrary() {	 
		try {
			this.httpClient = new AsyncHttpClient();			
			httpClient.prepareGet("ws://localhost:4731").execute(new WebSocketUpgradeHandler.Builder().addWebSocketListener(new WebSocketTextListener() {
				@Override
				public void onMessage(String message) {
					System.out.println("message");
					System.out.println(message);
					
					
					Gson gson = createGsonObject();					
					final Message messageInfo = gson.fromJson(message, Message.class);					
					
					EventQueue.invokeLater(new Runnable() {
						public void run() {
							try {								
								if(messageInfo.getTarget().equals("gadget"))
								{
									handleMessage(messageInfo, Gadget.class, gadgets, gadgetListener  );
								}		
								else if(messageInfo.getTarget().equals("loan"))
								{
									handleMessage(messageInfo, Loan.class, loans, loanListener  );							
								}
								else if(messageInfo.getTarget().equals("reservation"))
								{
									handleMessage(messageInfo, Reservation.class, reservations, reservationListener  );
								}								
								else if(messageInfo.getTarget().equals("customer"))
								{
									handleMessage(messageInfo, Customer.class, customers, customerListener  );
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						}

						
						private  <T extends Dto<T>> void  handleMessage(final Message messageInfo, Class<T> changed, List<T> itemList, List<CrudListener<T>> listeners ) {
							Gson gson = createGsonObject();
							T item = gson.<T>fromJson(messageInfo.getData(), changed);							
							Dto<?> itemData = item;
							
							if(messageInfo.getType().equals("add"))
							{
								itemList.add(item);
							}
							else if(messageInfo.getType().equals("update"))
							{
								int index = itemList.indexOf(item);
								if(index >= 0)
								{
									itemList.get(index).setData(item);
									itemData = itemList.get(index);
								}
								else
								{
									itemList.add(item);
								}
								
							}
							for(CrudListener<T> listener : listeners)
							{																						
								listener.changed(new MessageData(messageInfo.getTarget(), messageInfo.getType(), itemData));	
							}
						}
					});
				}
				
				@Override
				public void onOpen(WebSocket websocket) { 
					System.out.println("open");
				}

				@Override
				public void onClose(WebSocket websocket) { 
					System.out.println("closed");
				}

				@Override
				public void onError(Throwable t) {
					
				}

				@Override
				public void onFragment(String arg0, boolean arg1) {
					System.out.println("fragment");
					System.out.println(arg0);

				}
			}).build()).get();
		} catch (Exception e) {
			e.printStackTrace();
		} 
	 
	}
	


	public List<Customer> getCustomers() {
		if( customers == null)
		{
			customers = getData("customers", new TypeToken<List<Customer>>() {}.getType());
		}
		return customers;
	}

	
	public void createCustomer(Customer toAdd) {		
		createData("customers",toAdd);
	}



	
	@Override
	public List<Loan> getLoans() {
		if( loans == null)
		{
			loans = getData("loans", new TypeToken<List<Loan>>() {}.getType());
		}
		return loans;
	}
	

	@Override
	public void addLoan(Loan loan) {
		createData("loans", loan);		
	}
	
	

	@Override
	public void updateLoan(Loan loan) {
		updatedata("loans", loan.getLoanId(), loan);
	}


	@Override
	public List<Reservation> getReservations() {
		if( reservations == null)
		{
			reservations = getData("reservations", new TypeToken<List<Reservation>>() {}.getType());
		}
		return reservations;
	}
	
	@Override
	public void addReservation(Reservation reservation) {
		createData("reservations", reservation);		
	}
	
	

	@Override
	public void updateReservation(Reservation reservation) {
		updatedata("reservations", reservation.getReservationId(), reservation);
	}

	
	@Override
	public List<Gadget> getGadgets() {
		if( gadgets == null)
		{
			gadgets = getData("gadgets", new TypeToken<List<Gadget>>() {}.getType());
		}
		return gadgets;
	}
	
	
	@Override
	public void addGadget(Gadget gadget) {
		createData("gadgets",gadget);
	}


	@Override
	public void updateGadget(Gadget gadget) {
		 
		updatedata("gadgets", gadget.getInventoryNumber(), gadget);
	}	
	
	
	private <E> List<E> getData(String url, Type type) {
		AsyncHttpClient c = new AsyncHttpClient();
		try {			
			BoundRequestBuilder x = c.prepareGet("http://localhost:4730/" + url);
			Future<Response> f = x.execute();
			Response r = f.get();
			Gson gson = createGsonObject();
			List<E> fromJson;
			
			String text = r.getResponseBody();
			fromJson = gson.fromJson(text, type);
			
			return fromJson;
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			c.close();			
		}		
		return null;
	}	
	
	private <E> void updatedata(String url, String id, E toAdd) {
		try {
			AsyncHttpClient c = new AsyncHttpClient();
			BoundRequestBuilder x = c.preparePost("http://localhost:4730/"+url + "/"+id);

			Gson gson = createGsonObject();
			String toJson = gson.toJson(toAdd, toAdd.getClass());
			
			x.addParameter("value", toJson);			
			Future<Response> f = x.execute();
			f.get();
			c.close();

		} catch (Exception e) { 
			e.printStackTrace();
		}
	}
	

	private <E> void createData(String url, E toAdd) {
		try {
			AsyncHttpClient c = new AsyncHttpClient();
			BoundRequestBuilder x = c.preparePost("http://localhost:4730/"+url);

			Gson gson = createGsonObject();
			String toJson = gson.toJson(toAdd, toAdd.getClass());

			x.addParameter("value", toJson);
			Future<Response> f = x.execute();
			 f.get();
			c.close();

		} catch (Exception e) { 
			e.printStackTrace();
		}
	}



	@Override
	public void registerGadgetListener(CrudListener<Gadget> listener) {
		gadgetListener.add(listener);
		
	}
	
	@Override
	public void registerLoanListener(CrudListener<Loan> listener) {
		loanListener.add(listener);		
	}
	

	@Override
	public void registerReservationListener(CrudListener<Reservation> listener) {
		reservationListener.add(listener);		
	}
	
	@Override
	public void registerCustomerListener(CrudListener<Customer> listener) {
		customerListener.add(listener);		
	}	
	
}
