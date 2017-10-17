<%@tag description="Login Form" pageEncoding="UTF-8"%>

<form action="login" method="post">
    <p>Username: <input type="text" name="uName" value="${user.username}" /></p>
    <p>Password: <input type="text" name="pass" /></p>
    <p><input type="submit" value="Login"></p>
    <p><input ${checked} type="checkbox"  name="remember"> Remember me </p>
</form>        
${message}