package com.bank;

import java.math.BigDecimal;

public interface BankAccountable {
	public BigDecimal addDeposit(BigDecimal amount);

	public BigDecimal calcInterest();

	public void addInterest();
}
