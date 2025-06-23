package models;
import java.util.ArrayList;
import java.util.List;

import models.Account;

public class ListAccount {
    private List<Account> accounts;

    public ListAccount() {
        accounts = new ArrayList<>();
    }

    public List<Account> getAccounts() {
        return accounts;
    }

//    public void generate_sample_dataset() {
//        Account a1 = new Account(1, "user1", "pass123");
//        Account a2 = new Account(2, "user2", "pass456");
//        Account a3 = new Account(3, "user3", "pass789");
//        Account a4 = new Account(4, "admin1", "admin123");
//        Account a5 = new Account(5, "admin2", "admin456");
//        Account a6 = new Account(6, "guest1", "guest123");
//        Account a7 = new Account(7, "guest2", "guest456");
//        Account a8 = new Account(8, "test1", "test123");
//        Account a9 = new Account(9, "test2", "test456");
//        Account a10 = new Account(10, "demo1", "demo123");
//
//        accounts.add(a1);
//        accounts.add(a2);
//        accounts.add(a3);
//        accounts.add(a4);
//        accounts.add(a5);
//        accounts.add(a6);
//        accounts.add(a7);
//        accounts.add(a8);
//        accounts.add(a9);
//        accounts.add(a10);
//    }
}