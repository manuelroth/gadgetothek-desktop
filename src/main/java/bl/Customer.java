package bl;


import dl.Dto;

public class Customer implements Dto<Customer> {
	
	private String name;		
	private String password;	
	private String email;	
	private String studentnumber;
	

	public Customer(String name, String password, String email, String studentNumber) {
		this.name = name;
		this.password = password; 
		this.email = email; 
		this.studentnumber = studentNumber; 
	}	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getStudentNumber() {
		return studentnumber;
	}

	public void setStudentNumber(String studentNumber) {
		this.studentnumber = studentNumber;
	}
	
	@Override
	public String toString() {
		return name;
	}

	@Override
	public void setData(Customer customer) {

		
	}
}
