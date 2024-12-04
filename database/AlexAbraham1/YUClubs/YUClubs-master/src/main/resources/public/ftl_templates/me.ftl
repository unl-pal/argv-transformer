<#import "lib/main.ftl" as u>

<@u.page title="Profile Page">

<div class="container">

    <div class="page-header text-center">
        <h1><span class="fa fa-anchor"></span> Profile Page</h1>
        <a href="/logout" class="btn btn-default btn-sm">Logout</a>

        <#if goodMessage??>
            <div class="alert alert-success">${goodMessage}</div>
        <#elseif badMessage??>
            <div class="alert alert-danger">${badMessage}</div>
        </#if>

    </div>

    <div class="row">

        <a href="/createClub" class="btn btn-default btn-sm">Create Club</a>

        <!-- LOCAL INFORMATION -->
        <div class="col-md-6">
            <div class="well">
                <h3><span class="fa fa-user"></span> Local</h3>

                <p>
                    <strong>id</strong>: ${user.getId()}<br>
                    <strong>name</strong>: ${user.getName()}<br>
                    <strong>email</strong>: ${user.getEmail()}<br>
                </p>

                <hr>
                <h3>Change Password</h3>


                <#if !oldPassword??>
                    <#assign oldPassword="">
                </#if>

                <#if !newPassword??>
                    <#assign newPassword="">
                </#if>

                <#if !newPassword2??>
                    <#assign newPassword2="">
                </#if>

                <form action="/passwordReset" method="post">
                    <div class="form-group">
                        <label>Current Password</label>
                        <input type="password" class="form-control" name="oldPassword" value="${oldPassword}">
                    </div>
                    <div class="form-group">
                        <label>New Password</label>
                        <input type="password" class="form-control" name="newPassword" value="${newPassword}">
                    </div>
                    <div class="form-group">
                        <label>Retype Password</label>
                        <input type="password" class="form-control" name="newPassword2" value="${newPassword2}">
                    </div>

                    <button type="submit" class="btn btn-warning btn-lg">CHANGE</button>
                </form>

            </div>
        </div>
    </div>

</div>
</@u.page>