package dl;

import java.util.ArrayList;
import java.util.List;

import bl.Customer;
import bl.Gadget;
import bl.Loan;
import bl.Reservation;

public class LocalLibrary  implements LibraryData {

	private List<Customer> customers = new ArrayList<Customer>();
	private List<Gadget> gadgets = new ArrayList<Gadget>();
	private List<Loan> loans = new ArrayList<Loan>();
	private List<Reservation> reservation = new ArrayList<Reservation>();
	
	private List<CrudListener<Gadget>> gadgetListener = new ArrayList<CrudListener<Gadget>>();
	private List<CrudListener<Loan>> loanListener = new ArrayList<CrudListener<Loan>>();
	private List<CrudListener<Reservation>> reservationListener = new ArrayList<CrudListener<Reservation>>();
	private List<CrudListener<Customer>> customerListener = new ArrayList<CrudListener<Customer>>();
	
	public LocalLibrary() {	 
		createCustomer(new Customer("Bob", "12345", "bob@bob.ch", "1"));
		createCustomer(new Customer("Michael", "12345", "mgfeller@hsr.ch", "2"));
		addGadget(new Gadget("IPhone 4"));
		addGadget(new Gadget("IPhone 5"));
		addGadget(new Gadget("IPhone 6"));
		
		addLoan(new Loan(this.customers.get(0), this.gadgets.get(0)));
		addReservation(new Reservation(this.customers.get(1), this.gadgets.get(0)));
		addReservation(new Reservation(this.customers.get(1), this.gadgets.get(1)));	
	}
	

	public void createCustomer(Customer toAdd) {	
		customers.add(toAdd);		
		customerListener.forEach( x -> x.changed(new MessageData("customer", "add", toAdd )));
	}
 

	public List<Customer> getCustomers() {
		return customers;
	}
 
	@Override
	public List<Gadget> getGadgets() {
		return gadgets; 
	}
	
	@Override
	public List<Loan> getLoans() {	
		return loans;
	}

	@Override
	public void addGadget(Gadget toAdd) {
		gadgets.add(toAdd);
		gadgetListener.forEach( x -> x.changed(new MessageData("gadget", "add", toAdd)));
	}

	@Override
	public void addLoan(Loan loan) {
		loanListener.forEach( x -> x.changed(new MessageData("loan", "update", loan )));
		loans.add(loan);		
	}	



	@Override
	public void updateGadget(Gadget gadget) {
		gadgetListener.forEach( x -> x.changed(new MessageData("gadget", "update", gadget )));		
	}

	
	@Override
	public void updateLoan(Loan loan) {
		loanListener.forEach( x -> x.changed(new MessageData("loan", "update", loan)));		
	}

	@Override
	public List<Reservation> getReservations() {	
		return reservation;
	}


	@Override
	public void addReservation(Reservation reservation) {
		this.reservation.add(reservation);
		reservationListener.forEach( x -> x.changed(new MessageData("reservation", "add", reservation )));
		
	}


	@Override
	public void updateReservation(Reservation reservation) {
		reservationListener.forEach( x -> x.changed(new MessageData("reservation", "update", reservation)));
	}


	@Override
	public void registerReservationListener(CrudListener<Reservation> listener) {
		reservationListener.add(listener);		
		
	}


	@Override
	public void registerCustomerListener(CrudListener<Customer> listener) {
		customerListener.add(listener);		
		
	}	
	
	@Override
	public void registerGadgetListener(CrudListener<Gadget> listener) {
		gadgetListener.add(listener);		
	}

	
	@Override
	public void registerLoanListener(CrudListener<Loan> listener) {
		loanListener.add(listener);
	}
}
