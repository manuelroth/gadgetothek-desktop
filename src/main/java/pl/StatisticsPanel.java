package pl;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import javax.swing.JPanel;

import bl.Gadget;
import bl.Library;
import bl.Loan;
import bl.Reservation;

public class StatisticsPanel extends JPanel {
	List<Gadget> gadgetList = new ArrayList<Gadget>();
	List<Loan> loanList = new ArrayList<Loan>();
	List<Reservation> reservationList = new ArrayList<Reservation>();	
	TreeMap<Date, Integer> borrowHistory = new TreeMap<Date, Integer>();
	List<Date> dateList = new ArrayList<Date>();
	Gadget currentGadget = null;
	Library library = null;
	int X = 100;
	int Y = 10;
	int height = 300;
	int width = 700;
	int loanCountSpan = 10;
	int pixelPerLoan = 30;
	int daySpan = 0;
	int pixelPerDay = 1;
	private Date startDate;
	private Date endDate;
	
	public StatisticsPanel(Library library) {
		this.library = library;
		setMinimumSize(new Dimension(540, 340));
		setSize(new Dimension(540, 340));
		setBounds(X, Y, width, height);
	}
	
	@Override
	public void paint(Graphics g)
	{
		borrowHistory = new TreeMap<Date, Integer>();
		gadgetList = new ArrayList<Gadget>();
		loanList = new ArrayList<Loan>();
		reservationList = new ArrayList<Reservation>();
		dateList = new ArrayList<Date>();
		
		g.setColor(Color.black);
		g.drawRect(X, Y, width, height);
		gadgetList = library.getGadgets();
		
		for (Gadget currentGadget : gadgetList)
		{
			List<Loan> currentLoanList = library.getLoansFor(currentGadget, false);
			List<Reservation> currentReservationList = library.getReservatonFor(currentGadget, false);
			loanList.addAll(currentLoanList);			
		}
		
		Collections.sort(loanList, (Loan p1, Loan p2) -> p1.getPickupDate().compareTo(p2.getPickupDate()));
		
		for (Loan loan : loanList)
		{
			if (loan.getPickupDate() != null)
			{
				if (!borrowHistory.containsKey(loan.getPickupDate()))
				{
					borrowHistory.put(loan.getPickupDate(), 1);
				}
				else
				{
					borrowHistory.put(loan.getPickupDate(), borrowHistory.get(loan.getPickupDate()) + 1);					
				}
			}
			if (loan.getReturnDate() != null)
			{
				if (!borrowHistory.containsKey(loan.getReturnDate()))
				{
					borrowHistory.put(loan.getReturnDate(), -1);
				}
				else
				{
					borrowHistory.put(loan.getReturnDate(), borrowHistory.get(loan.getReturnDate()) - 1);					
				}
			}
		}
		
		for (Date date : borrowHistory.keySet())
		{
			if (!dateList.contains(date))
				dateList.add(date);
		}
		Comparator<Date> dateComparator = new Comparator<Date>()
			{
				@Override
				public int compare(Date left, Date right)
				{
					return left.compareTo(right);
				}
			};
		dateList.sort(dateComparator);
				
		startDate = dateList.get(0);
		/*
		if (loanList.size() > 1)		
			endDate = dateList.get(dateList.size() - 1);		
		else*/
			endDate = new Date();						
		
		loanCountSpan = (int) loanList.size();
		pixelPerLoan = height / loanCountSpan;
		daySpan = (int) getDateDiff(startDate, endDate, TimeUnit.DAYS);
		pixelPerDay = Math.round((float) 1.0 * width / daySpan);
		
		int totalBorrows = 0;
		int currentX = 100;
		int currentY = 10 + 300;
		
		for (int counter = 0; counter <= loanCountSpan; counter++)
		{
			g.drawString("" + counter, currentX - 10, currentY - counter * pixelPerLoan + 5);
		}
		
		for (Date borrowDate : dateList)
		{
			Integer historyEntry = borrowHistory.get(borrowDate);
			int newTotalBorrows = totalBorrows + historyEntry;
			
			g.drawLine(currentX, currentY, DateToPixel(borrowDate), Y + height - (newTotalBorrows * pixelPerLoan));
			Format formatter = new SimpleDateFormat("dd.MM.yyyy");
			g.drawString(formatter.format(borrowDate), DateToPixel(borrowDate) - 30, Y + height - (newTotalBorrows * pixelPerLoan) + 20);
			totalBorrows = newTotalBorrows;
			currentX = DateToPixel(borrowDate);
			currentY = Y + height - (newTotalBorrows * pixelPerLoan);
		}		
		g.drawLine(currentX, currentY, X + width, Y + height - (totalBorrows * pixelPerLoan));
		Format formatter = new SimpleDateFormat("dd.MM.yyyy");
		g.drawString(formatter.format(new Date()), X + width - 30, Y + height - (totalBorrows * pixelPerLoan) + 20);
	}
	
	/**
	 * Get a diff between two dates
	 * @param date1 the oldest date
	 * @param date2 the newest date
	 * @param timeUnit the unit in which you want the diff
	 * @return the diff value, in the provided unit
	 */
	public static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
	    long diffInMillies = date2.getTime() - date1.getTime();
	    return timeUnit.convert(diffInMillies,TimeUnit.MILLISECONDS);
	}
	
	private int DateToPixel(Date date)
	{		
		long differenceInDays = getDateDiff(startDate, date, TimeUnit.DAYS);
		System.out.println("" + differenceInDays + ";" + pixelPerDay);
		return (int) ((int) X + differenceInDays * pixelPerDay);
	}
}
