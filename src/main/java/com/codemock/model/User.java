package com.codemock.model;

import java.sql.Timestamp;

public class User {
    // 1. Private Variables (Same as Database Columns)
    private int userId;
    private String fullName;
    private String email;
    private String phone;
    private String passwordHash;
    private boolean isVerified;
    private String currentOtp;
    private Timestamp otpExpiry;
    private Timestamp createdAt;

    // 2. Default Constructor (Khali object banane ke liye)
    public User() {
    }

    // 3. Getters aur Setters (Variables ko read aur write karne ke liye)
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    public boolean isVerified() { return isVerified; }
    public void setVerified(boolean isVerified) { this.isVerified = isVerified; }

    public String getCurrentOtp() { return currentOtp; }
    public void setCurrentOtp(String currentOtp) { this.currentOtp = currentOtp; }

    public Timestamp getOtpExpiry() { return otpExpiry; }
    public void setOtpExpiry(Timestamp otpExpiry) { this.otpExpiry = otpExpiry; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
}