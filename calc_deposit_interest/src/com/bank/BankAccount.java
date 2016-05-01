package com.bank;

import java.math.BigDecimal;

public class BankAccount implements BankAccountable {
	private String customerNumber;
	private BigDecimal depositBalance;
	private int durationInDays;
	private DepositType depositType;
	private float interestRate;

	public BankAccount(String customernumber, BigDecimal depositbalance, int durationindays, DepositType deposittype,
			float interestrate) {
		setCustomerNumber(customernumber);
		setDepositBalance(depositbalance);
		setDurationInDays(durationindays);
		setDepositType(deposittype);
		setInterestRate(interestrate);
	}

	public void setInterestRate(float interestrate2) {
		this.interestRate = interestrate2;

	}

	public void setDurationInDays(int durationindays2) {
		this.durationInDays = durationindays2;

	}

	public void setDepositType(DepositType deposittype2) {
		this.depositType = deposittype2;

	}

	public void setDepositBalance(BigDecimal depositbalance2) {
		this.depositBalance = depositbalance2;

	}

	public void setCustomerNumber(String customernumber2) {
		this.customerNumber = customernumber2;
	}

	public String getCustomerNumber(){
		return customerNumber;
	}
	public BigDecimal getDepositBalance(){
		return depositBalance;
	}
	public int getDurationInDays(){
		return durationInDays;
	}
	public DepositType getDepositType(){
		return depositType;
	}
	public float getInterestRate(){
		return interestRate;
	}
	@Override
	public BigDecimal addDeposit(BigDecimal amount) {
		depositBalance = depositBalance.add(amount);
		return depositBalance;
	}

	@Override
	public BigDecimal calcInterest() {
		BigDecimal pi;
		pi = (depositBalance.multiply(new BigDecimal(interestRate)).multiply(new BigDecimal(durationInDays)));
		pi = pi.divideToIntegralValue(new BigDecimal(36500));
		return pi;
	}

	@Override
	public void addInterest() {
		addDeposit(calcInterest());
		durationInDays--;
	}

	public String toCustomerInterest() {
		return String.format("%s#%s", customerNumber, calcInterest().toString());
	}

}
