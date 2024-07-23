package com.bank.dao;
import com.bank.model.Account;
import com.bank.model.Transaction;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountsDAO {

    private String jdbcURL;
    private String jdbcUsername;
    private String jdbcPassword;
    private Connection jdbcConnection;

    public AccountsDAO(String jdbcURL, String jdbcUsername, String jdbcPassword) {
        this.jdbcURL = jdbcURL;
        this.jdbcUsername = jdbcUsername;
        this.jdbcPassword = jdbcPassword;
    }

    protected void connect() throws SQLException {
        if (jdbcConnection == null || jdbcConnection.isClosed()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                throw new SQLException(e);
            }
            jdbcConnection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        }
    }

    protected void disconnect() throws SQLException {
        if (jdbcConnection != null && !jdbcConnection.isClosed()) {
            jdbcConnection.close();
        }
    }

    public boolean addAccount(int customerId, double balance) throws SQLException {
        String sql = "INSERT INTO Accounts (customer_id, balance) VALUES (?, ?)";
        connect();

        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setInt(1, customerId);
        statement.setDouble(2, balance);

        boolean rowInserted = statement.executeUpdate() > 0;
        statement.close();
        disconnect();
        return rowInserted;
    }

    public Account getAccount(int accountNo) throws SQLException {
        Account account = null;
        String sql = "SELECT * FROM Accounts WHERE account_no = ?";

        connect();

        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setInt(1, accountNo);

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            int customerId = resultSet.getInt("customer_id");
            double balance = resultSet.getDouble("balance");

            account = new Account(accountNo, customerId, balance);
        }

        resultSet.close();
        statement.close();

        return account;
    }

    public List<Account> listAllAccounts() throws SQLException {
        List<Account> listAccounts = new ArrayList<>();

        String sql = "SELECT * FROM Accounts";

        connect();

        Statement statement = jdbcConnection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        while (resultSet.next()) {
            int accountNo = resultSet.getInt("account_no");
            int customerId = resultSet.getInt("customer_id");
            double balance = resultSet.getDouble("initial_balance");

            Account account = new Account(accountNo, customerId, balance);
            listAccounts.add(account);
        }

        resultSet.close();
        statement.close();

        disconnect();

        return listAccounts;
    }

    public boolean updateAccount(Account account) throws SQLException {
        String sql = "UPDATE Accounts SET balance = ? WHERE account_no = ?";
        connect();

        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setDouble(1, account.getBalance());
        statement.setInt(2, account.getAccountNo());

        boolean rowUpdated = statement.executeUpdate() > 0;
        statement.close();
        disconnect();
        return rowUpdated;
    }

    public boolean deleteAccount(int accountNo) throws SQLException {
        String sql = "DELETE FROM Accounts WHERE account_no = ?";

        connect();

        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setInt(1, accountNo);

        boolean rowDeleted = statement.executeUpdate() > 0;
        statement.close();
        disconnect();
        return rowDeleted;
    }

    public boolean addTransaction(int accountNo, String transactionType, double amount) throws SQLException {
        String sql = "INSERT INTO Transactions (account_no, transaction_type, amount) VALUES (?, ?, ?)";
        connect();

        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setInt(1, accountNo);
        statement.setString(2, transactionType);
        statement.setDouble(3, amount);

        boolean rowInserted = statement.executeUpdate() > 0;
        statement.close();
        disconnect();
        return rowInserted;
    }

    public List<Transaction> listTransactionsByAccount(int accountNo) throws SQLException {
        List<Transaction> listTransactions = new ArrayList<>();

        String sql = "SELECT * FROM Transactions WHERE account_no = ?";

        connect();

        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setInt(1, accountNo);

        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String transactionType = resultSet.getString("transaction_type");
            double amount = resultSet.getDouble("amount");
            Timestamp transactionDate = resultSet.getTimestamp("transaction_date");
            String acc=String.valueOf(accountNo);

            Transaction transaction = new Transaction(id, acc, transactionType, amount, transactionDate);
            listTransactions.add(transaction);
        }

        resultSet.close();
        statement.close();

        disconnect();

        return listTransactions;
    }
}