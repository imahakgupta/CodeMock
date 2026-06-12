package com.codemock.controller;

import com.codemock.dao.UserDAO;
import com.codemock.model.User;
import com.codemock.service.EmailService;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Random;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

// URL Mapping: Yeh batata hai ki frontend form is file ko kis naam se pukarega
@WebServlet(name = "AuthServlet", urlPatterns = {"/AuthServlet"})
public class AuthServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Form se hidden input action nikalna (login ya register)
        String action = request.getParameter("action");

        if ("register".equals(action)) {
            handleRegistration(request, response);
        } else if ("login".equals(action)) {
            handleLogin(request, response); // Naya method call
        }
    }

    private void handleRegistration(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        
        // 1. Frontend form se saara data nikalna
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String password = request.getParameter("password"); // Real project me ise encrypt karte hain

        // 2. 6-Digit Random OTP Generate karna
        Random rnd = new Random();
        int number = rnd.nextInt(999999);
        String otp = String.format("%06d", number);

        // OTP ka expiry time (Current time + 10 minutes)
        Timestamp expiryTime = new Timestamp(System.currentTimeMillis() + (10 * 60 * 1000));

        // 3. User object me data set karna
        User newUser = new User();
        newUser.setFullName(fullName);
        newUser.setEmail(email);
        newUser.setPhone(phone);
        newUser.setPasswordHash(password);
        newUser.setVerified(false);
        newUser.setCurrentOtp(otp);
        newUser.setOtpExpiry(expiryTime);

        // 4. Database mein save karna aur Email bhejna
        UserDAO userDao = new UserDAO();
        boolean isSaved = userDao.saveUser(newUser);

        if (isSaved) {
            EmailService emailService = new EmailService();
            boolean isEmailSent = emailService.sendOtpEmail(email, otp);

            if (isEmailSent) {
                // Session mein email save kar lo taaki OTP page par use kar sakein
                HttpSession session = request.getSession();
                session.setAttribute("tempEmail", email);
                
                // User ko OTP page par bhej do
                response.sendRedirect("otp-verify.jsp");
            } else {
                response.getWriter().print("User registered but failed to send OTP email.");
            }
        } else {
            response.getWriter().print("Registration failed. Email might already exist.");
        }
    }
    private void handleLogin(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        UserDAO userDao = new UserDAO();
        User loggedInUser = userDao.loginUser(email, password);

        if (loggedInUser != null) {
            // Login Successful: Create a Session to remember the user
            HttpSession session = request.getSession();
            session.setAttribute("activeUser", loggedInUser);
            
            // Redirect to Dashboard
            response.sendRedirect("dashboard.jsp");
        } else {
            // Login Failed: Invalid credentials or unverified account
            response.getWriter().print("Login failed! Please check your credentials or verify your email.");
        }
    }
}