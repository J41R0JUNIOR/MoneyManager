package shared.model;

import java.util.List;

public class Card {
	private String id;
	private String type;
	private Double limit;
	private Double amount;
	private String closeDay;
	private List<Expense> expenses;
}