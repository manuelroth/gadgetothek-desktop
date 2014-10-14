package dl;

import java.util.List;
import bl.Customer;
import bl.Gadget;
import bl.Loan;
import bl.Reservation;

public abstract interface LibraryData {
	List<Customer> getCustomers();
	List<Gadget> getGadgets();
	List<Loan> getLoans();
	List<Reservation> getReservations();
	
	void addGadget(Gadget gadget);	
	void updateGadget(Gadget gadget);
	
	void addLoan(Loan loan);
	void updateLoan(Loan loan);
	
	void addReservation(Reservation reservation);
	void updateReservation(Reservation reservation);
	
	void registerReservationListener(CrudListener<Reservation> listener);
	void registerCustomerListener(CrudListener<Customer> listener);
	void registerGadgetListener(CrudListener<Gadget> listener);
	void registerLoanListener(CrudListener<Loan> listener);
}
