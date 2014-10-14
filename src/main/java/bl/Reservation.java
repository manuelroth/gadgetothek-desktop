package bl;

import java.util.Date; 
import java.util.UUID;

import dl.Dto;

public class Reservation implements Dto<Reservation> {

	private String id;
	private String gadgetId;
	private String customerId;
	private Date reservationDate;	
	private boolean finished;
	
	public Reservation(Customer customer, Gadget copy) {
		id = ""+Math.abs(UUID.randomUUID().getMostSignificantBits());
		this.gadgetId = copy.getInventoryNumber();
		this.customerId = customer.getStudentNumber();
		this.reservationDate = new Date();
	}

	 
	public String getGadgetId() {
		return gadgetId;
	}

	public String getCustomerId() {
		return customerId;
	}
	
	public String getReservationId() {
		return id;
	}

	public Date getReservationDate() {
		return reservationDate;
	}

	public boolean getFinished() {
		return finished;
	}

	public void setFinished(boolean finished) {
		this.finished = finished;
	} 
	

	public void setData(Reservation reservation)
	{
		this.finished = reservation.finished;
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
		Reservation other = (Reservation) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}	
}
