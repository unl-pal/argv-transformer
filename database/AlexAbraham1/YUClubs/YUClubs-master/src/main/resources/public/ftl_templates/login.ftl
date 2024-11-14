<#import "lib/main.ftl" as u>

<@u.page title="Login">

<div class="container">

    <div class="col-sm-6 col-sm-offset-3">

        <h1><span class="fa fa-sign-in"></span> Login</h1>

        <#-- show any messages that come back with authentication -->
        <#if message??>
            <div class="alert alert-danger">${message}</div>
        </#if>

        <#-- Assign any values passed from failed login -->


        <#if !email??>
            <#assign email="">
        </#if>

        <!-- LOGIN FORM -->
        <form action="/api/login" method="post">
            <div class="form-group">
                <label>Email</label>
                <input type="text" class="form-control" name="email" value="${email}">
            </div>
            <div class="form-group">
                <label>Password</label>
                <input type="password" class="form-control" name="password">
            </div>

            <button type="submit" class="btn btn-warning btn-lg">Login</button>
        </form>

        <hr>

        <p>Need an account? <a href="/signup">Signup</a></p>
        <p>Or go <a href="/">home</a>.</p>

    </div>

</div>

</@u.page>