package com.bank.dao;

public interface ChangePasswordDAO {
    boolean verifyOldPassword(int accountNo, String oldPassword);
    boolean updatePassword(int accountNo, String newPassword);
}