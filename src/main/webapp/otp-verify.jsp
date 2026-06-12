<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Verify OTP - CodeMock</title>
    <link rel="stylesheet" href="assets/css/style.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        /* OTP specific input styling */
        .otp-input {
            text-align: center;
            font-size: 1.5rem;
            letter-spacing: 5px;
            font-weight: bold;
        }
    </style>
</head>
<body>

    <div class="auth-container">
        <div class="auth-brand">
            <i class="fa-solid fa-shield-halved" style="font-size: 4rem; margin-bottom: 20px;"></i>
            <h1>Security First</h1>
            <p>We've sent a 6-digit security code to your email. Please enter it below to verify your identity.</p>
        </div>

        <div class="auth-form-section">
            <h2>Verify Your Email</h2>
            
            <%-- Checking if email exists in session --%>
            <% 
                String userEmail = (String) session.getAttribute("tempEmail");
                if(userEmail == null) {
                    response.sendRedirect("index.jsp"); // Agar direct page khola hai bina register kiye
                }
            %>
            
            <p style="color: var(--text-muted); margin-bottom: 20px;">
                Code sent to: <b><%= userEmail %></b>
            </p>

            <form action="VerifyOtpServlet" method="POST">
                <div class="input-group">
                    <i class="fa-solid fa-key"></i>
                    <input type="text" name="otp" class="otp-input" placeholder="000000" maxlength="6" pattern="\d{6}" required title="Enter 6-digit OTP">
                </div>
                
                <button type="submit" class="btn-primary">Verify & Activate Account</button>
            </form>
            
            <p class="toggle-text">
                Didn't receive the code? <a href="#">Resend OTP</a>
            </p>
        </div>
    </div>

</body>
</html>