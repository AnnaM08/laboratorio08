package it.unibo.bank.impl;

import it.unibo.bank.api.AccountHolder;
import it.unibo.bank.api.BankAccount;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static it.unibo.bank.impl.SimpleBankAccount.*;
import static it.unibo.bank.impl.SimpleBankAccount.ATM_TRANSACTION_FEE;
import static it.unibo.bank.impl.StrictBankAccount.TRANSACTION_FEE;
import static org.junit.jupiter.api.Assertions.*;

public class TestStrictBankAccount {

    private final static int INITIAL_AMOUNT = 100;
    private final static int WITHDRAWED_AMOUNT = -500;

    // 1. Create a new AccountHolder and a StrictBankAccount for it each time tests are executed.
    private AccountHolder mRossi;
    private BankAccount bankAccount;

    @BeforeEach
    public void setUp() {
        this.mRossi = new AccountHolder("Mario", "Rossi", 1209 );
        this.bankAccount = new StrictBankAccount(mRossi, 0.0);
    }

    // 2. Test the initial state of the StrictBankAccount
    @Test
    public void testInitialization() {
        Assertions.assertEquals(mRossi, bankAccount.getAccountHolder());
        Assertions.assertEquals(0.0, bankAccount.getBalance());
        Assertions.assertEquals(0, bankAccount.getTransactionsCount());
    }


    // 3. Perform a deposit of 100€, compute the management fees, and check that the balance is correctly reduced.
    @Test
    public void testManagementFees() {
        bankAccount.deposit(this.mRossi.getUserID(), INITIAL_AMOUNT);
        Assertions.assertEquals(INITIAL_AMOUNT, bankAccount.getBalance());
    }

    // 4. Test the withdraw of a negative value
    @Test
    public void testNegativeWithdraw() {
       try {
            bankAccount.withdraw(this.mRossi.getUserID(), WITHDRAWED_AMOUNT);
            Assertions.fail();
        } catch (IllegalArgumentException e) {
            Assertions.assertEquals("Cannot withdraw a negative amount", e.getMessage());
        }
    }

    // 5. Test withdrawing more money than it is in the account
    @Test
    public void testWithdrawingTooMuch() {
        Assertions.assertEquals(0.0, bankAccount.getBalance());//il bilancio del bank account è 0
        /*Questo avviene perchè ogni test è indipendente dagli altri*/
        try{
        bankAccount.withdraw(this.mRossi.getUserID(), INITIAL_AMOUNT);
        } catch(IllegalArgumentException e) {
            Assertions.assertEquals("Insufficient balance", e.getMessage());
        }

    }
}
