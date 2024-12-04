<#import "lib/main.ftl" as u>

<@u.page title="Signup">

<div class="container">

    <div class="col-sm-6 col-sm-offset-3">

        <h1><span class="fa fa-sign-in"></span> Signup</h1>

    <#-- show any messages that come back with authentication -->
        <#if message??>
            <div class="alert alert-danger">${message}</div>
        </#if>

    <#-- Assign any values passed from failed login -->


        <#if !email??>
            <#assign email="">
        </#if>
        <#if !fullname??>
            <#assign fullname="">
        </#if>

        <!-- LOGIN FORM -->
        <form action="/api/new/user" method="post">
            <div class="form-group">
                <label>Full Name</label>
                <input type="text" class="form-control" name="fullName" value="${fullname}">
            </div>
            <div class="form-group">
                <label>Email</label>
                <input type="text" class="form-control" name="email" value="${email}">
            </div>
            <div class="form-group">
                <label>Password</label>
                <input type="password" class="form-control" name="password1">
            </div>
            <div class="form-group">
                <label>Retype Password</label>
                <input type="password" class="form-control" name="password2">
            </div>

            <button type="submit" class="btn btn-warning btn-lg">Signup</button>
        </form>

        <hr>

        <p>Already have an account? <a href="/login">Login</a></p>
        <p>Or go <a href="/">home</a>.</p>

    </div>

</div>

</@u.page>