package com.example.playgroundapp.design_pattern.structural

import java.util.UUID

/***
 *
 * Facade pattern provides a simplified interface to a complex subsystem.
 * ***/
class FacadePattern {

    // Step1 : Define the service Interface
    private interface IAccount {
        fun deposit(amount: Double)
        fun withDraw(amount: Double): Double
        fun transfer(toAccount: IAccount, amount: Double)
        fun checkBalance(): Double
        fun getAccountNumber(): Long
    }

    // Step2 : implement the interface

    private class CurrentAccount : IAccount {
        var balance = 0.0
        override fun deposit(amount: Double) {
            balance += amount
        }

        override fun withDraw(amount: Double): Double {
            balance -= amount
            return amount
        }

        override fun transfer(toAccount: IAccount, amount: Double) {
            toAccount.deposit(amount)
            balance -= amount
        }

        override fun checkBalance(): Double {
            return balance
        }

        override fun getAccountNumber(): Long {
            return UUID.randomUUID().node()
        }

    }

    private class SavingAccount : IAccount {
        var balance = 0.0

        override fun deposit(amount: Double) {
            balance += amount
        }

        override fun withDraw(amount: Double): Double {
            balance -= amount
            return amount
        }

        override fun transfer(toAccount: IAccount, amount: Double) {
            toAccount.deposit(amount)
            balance -= amount
        }

        override fun checkBalance(): Double {
            return balance
        }

        override fun getAccountNumber(): Long {
            return UUID.randomUUID().node()
        }
    }

    // step3 : create the facade class and wrap the classes that implements service interface
    class BankFacade {
        private val accounts = mutableMapOf<Long, IAccount>()

        fun createNewAccount(type: String): Long? {
            val account = when (type) {
                "saving" -> SavingAccount()
                "current" -> CurrentAccount()
                else -> null
            }
            if (account != null)
                accounts[account.getAccountNumber()] = account
            return account?.getAccountNumber()
        }

        fun transferAmount(to: Long, from: Long, amount: Double) {
            val toAccount = accounts[to] ?: return
            val fromAccount = accounts[from] ?: return
            fromAccount.transfer(toAccount, amount)
        }

        fun depositAmount(to: Long, amount: Double) {
            accounts[to]?.deposit(amount)
        }
    }


    init {
        val bankFacade = BankFacade()
        val senderAccountNumber = bankFacade.createNewAccount("saving")
        val receiverAccountNumber = bankFacade.createNewAccount("current")

        if (senderAccountNumber != null && receiverAccountNumber != null) {
            bankFacade.depositAmount(senderAccountNumber, 5000.0)
            bankFacade.transferAmount(receiverAccountNumber, senderAccountNumber, 500.0)
        }
    }
}