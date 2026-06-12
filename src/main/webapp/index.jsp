<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>CodeMock - AI Powered Mock Tests</title>
    
    <link rel="stylesheet" href="assets/css/style.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body>

    <div class="auth-container">
        
        <div class="auth-brand">
            <i class="fa-solid fa-laptop-code" style="font-size: 4rem; margin-bottom: 20px;"></i>
            <h1>CodeMock</h1>
            <p>Elevate your coding skills with AI-powered mock tests and real-time feedback.</p>
        </div>

        <div class="auth-form-section" id="login-section">
            <h2>Welcome Back</h2>
            <form action="AuthServlet" method="POST">
                <input type="hidden" name="action" value="login">
                
                <div class="input-group">
                    <i class="fa-solid fa-envelope"></i>
                    <input type="email" name="email" placeholder="Email Address" required>
                </div>
                
                <div class="input-group">
                    <i class="fa-solid fa-lock"></i>
                    <input type="password" name="password" placeholder="Password" required>
                </div>
                
                <button type="submit" class="btn-primary">Login to Dashboard</button>
            </form>
            
            <p class="toggle-text">
                New to CodeMock? <a href="#" onclick="toggleForms()">Create an account</a>
            </p>
        </div>

        <div class="auth-form-section" id="register-section" style="display: none;">
            <h2>Create Account</h2>
            <form action="AuthServlet" method="POST">
                <input type="hidden" name="action" value="register">
                
                <div class="input-group">
                    <i class="fa-solid fa-user"></i>
                    <input type="text" name="fullName" placeholder="Full Name" required>
                </div>
                
                <div class="input-group">
                    <i class="fa-solid fa-envelope"></i>
                    <input type="email" name="email" placeholder="Email Address" required>
                </div>

                <div class="input-group">
                    <i class="fa-solid fa-phone"></i>
                    <input type="text" name="phone" placeholder="Phone Number" required pattern="[0-9]{10}" title="Enter a valid 10-digit mobile number">
                </div>
                
                <div class="input-group">
                    <i class="fa-solid fa-lock"></i>
                    <input type="password" name="password" placeholder="Create Password" required minlength="6">
                </div>
                
                <button type="submit" class="btn-primary">Sign Up</button>
            </form>
            
            <p class="toggle-text">
                Already have an account? <a href="#" onclick="toggleForms()">Login here</a>
            </p>
        </div>

    </div>

    <script>
        function toggleForms() {
            const loginSection = document.getElementById('login-section');
            const registerSection = document.getElementById('register-section');
            
            if (loginSection.style.display === 'none') {
                loginSection.style.display = 'flex';
                registerSection.style.display = 'none';
            } else {
                loginSection.style.display = 'none';
                registerSection.style.display = 'flex';
            }
        }
    </script>
</body>
</html>