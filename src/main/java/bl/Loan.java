package bl;

import java.util.Calendar;
import java.util.Date; 
import java.util.UUID;

import dl.Dto;

public class Loan implements Dto<Loan>  {
	
	private String id;
	private String gadgetId;
	private String customerId;
	private Date pickupDate, returnDate;
	private final static int DAYS_TO_RETURN= 7;

	
	public Loan(Customer customer, Gadget copy) {
		id = ""+Math.abs(UUID.randomUUID().getMostSignificantBits());
		this.gadgetId = copy.getInventoryNumber();
		this.customerId = customer.getStudentNumber();
		pickupDate = new Date();		
	}

	public boolean isLent() {
		return returnDate == null;
	}

	public boolean returnCopy() {
		try {
			returnCopy(new Date());
		} catch (IllegalLoanOperationException e) {
			return false;
		}
		return true;
	}

	public void returnCopy(Date returnDate)	throws IllegalLoanOperationException {
		if (returnDate.before(pickupDate)) {
			throw new IllegalLoanOperationException("Return Date is before pickupDate");
		}
		this.returnDate = returnDate;
	}

	public Date getPickupDate() {
		return pickupDate;
	}

	public Date getReturnDate() {
		return returnDate;
	}

	public String getGadgetId() {
		return gadgetId;
	}

	public String getCustomerId() {
		return customerId;
	}
	
	public String getLoanId() {
		return id;
	}
 
	public boolean isOverdue() {
		if ( !isLent() )
			return false;		
		return overDueDate().before(new Date());		
	}
	
	public Date overDueDate() {		
		Calendar cal = Calendar.getInstance();  
		cal.setTime(pickupDate);  
		cal.add(Calendar.DATE, DAYS_TO_RETURN); // add DAYS_TO_RETURN days
		return cal.getTime();		
	}
	
	
	public void setData(Loan loan)
	{
		this.pickupDate = loan.pickupDate;
		this.returnDate = loan.returnDate;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Loan other = (Loan) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
