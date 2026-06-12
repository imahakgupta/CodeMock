package com.codemock.dao;

import com.codemock.model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {

    // Method to register/save a new user into the database
    public boolean saveUser(User user) {
        boolean isSuccess = false;
        Connection conn = null;
        PreparedStatement pstmt = null;

        // SQL Query to insert user details
        String query = "INSERT INTO Users (FullName, Email, Phone, PasswordHash, IsVerified, CurrentOTP, OTPExpiry) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
            // 1. Get database connection using our DBConnection class
            conn = DBConnection.getConnection();

            if (conn != null) {
                // 2. Prepare the statement to prevent SQL Injection
                pstmt = conn.prepareStatement(query);

                // 3. Set parameters replacing the '?' placeholders
                pstmt.setString(1, user.getFullName());
                pstmt.setString(2, user.getEmail());
                pstmt.setString(3, user.getPhone());
                pstmt.setString(4, user.getPasswordHash());
                pstmt.setBoolean(5, user.isVerified());
                pstmt.setString(6, user.getCurrentOtp());
                pstmt.setTimestamp(7, user.getOtpExpiry());

                // 4. Execute the query
                int rowsInserted = pstmt.executeUpdate();
                if (rowsInserted > 0) {
                    isSuccess = true;
                }
            }
        } catch (Exception e) {
            System.err.println("Error in saveUser: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // 5. Close resources to prevent memory leaks
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        return isSuccess;
    }
    
    // Method to verify OTP and activate account
    public boolean verifyAndActivateUser(String email, String enteredOtp) {
        boolean isVerified = false;
        Connection conn = null;
        PreparedStatement pstmt = null;

        // SQL: Update IsVerified to 1 IF Email and OTP match, AND OTP is not expired
        String query = "UPDATE Users SET IsVerified = 1 "
                     + "WHERE Email = ? AND CurrentOTP = ? AND OTPExpiry > GETDATE()";

        try {
            conn = DBConnection.getConnection();
            if (conn != null) {
                pstmt = conn.prepareStatement(query);
                pstmt.setString(1, email);
                pstmt.setString(2, enteredOtp);

                // executeUpdate() returns the number of rows affected
                int rowsUpdated = pstmt.executeUpdate();
                
                if (rowsUpdated > 0) {
                    isVerified = true; // Match successful and account activated!
                }
            }
        } catch (Exception e) {
            System.err.println("Error in verifyAndActivateUser: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        return isVerified;
    }
    
    // Method to check login credentials
    public User loginUser(String email, String password) {
        User user = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        // SQL: Fetch user only if Email and Password match AND Account is Verified (1)
        String query = "SELECT * FROM Users WHERE Email = ? AND PasswordHash = ? AND IsVerified = 1";

        try {
            conn = DBConnection.getConnection();
            if (conn != null) {
                pstmt = conn.prepareStatement(query);
                pstmt.setString(1, email);
                pstmt.setString(2, password);

                rs = pstmt.executeQuery();

                // If a matching record is found
                if (rs.next()) {
                    user = new User();
                    user.setUserId(rs.getInt("UserID"));
                    user.setFullName(rs.getString("FullName"));
                    user.setEmail(rs.getString("Email"));
                    user.setPhone(rs.getString("Phone"));
                    user.setVerified(rs.getBoolean("IsVerified"));
                }
            }
        } catch (Exception e) {
            System.err.println("Error in loginUser: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        return user; // Returns the user object if login is successful, else returns null
    }
}