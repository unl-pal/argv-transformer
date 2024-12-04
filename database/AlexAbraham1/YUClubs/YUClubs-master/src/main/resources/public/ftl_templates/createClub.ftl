<#import "lib/main.ftl" as u>

<@u.page title="Create Club">

<div class="container">

    <div class="col-sm-6 col-sm-offset-3">

        <h1><span class="fa fa-sign-in"></span> Create New Club</h1>

    <#-- show any messages that come back with authentication -->
        <#if message??>
            <div class="alert alert-danger">${message}</div>
        </#if>

    <#-- Assign any values passed from failed login -->


        <#if !clubName??>
            <#assign clubName="">
        </#if>
        <#if !clubDescription??>
            <#assign clubDescription="">
        </#if>
        <#if !clubImage??>
            <#assign clubImage="">
        </#if>
        <#if !presidentEmail??>
            <#assign presidentEmail="">
        </#if>
        <#if !presidentImage??>
            <#assign presidentImage="">
        </#if>
        <#if !presidentName??>
            <#assign presidentName="">
        </#if>
        <#if !presidentPhone??>
            <#assign presidentPhone="">
        </#if>

        <!-- LOGIN FORM -->
        <form action="/api/user/new/club" method="post">
            <div class="form-group">
                <label>Club Name</label>
                <input type="text" class="form-control" name="clubName" value="${clubName}">
            </div>
            <div class="form-group">
                <label>Club Description</label>
                <input type="text" class="form-control" name="clubDescription" value="${clubDescription}">
            </div>
            <div class="form-group">
                <label>Club Image</label>
                <input type="text" placeholder="optional" class="form-control" name="clubImage" value="${clubImage}">
            </div>

            <#if !hasPresident??>
                <div class="form-group">
                    <label>President Email</label>
                    <input type="text" class="form-control" name="presidentEmail" value="${presidentEmail}">
                </div>
                <div class="form-group">
                    <label>President Image</label>
                    <input type="text" class="form-control" name="presidentImage" value="${presidentImage}">
                </div>
                <div class="form-group">
                    <label>President Name</label>
                    <input type="text" class="form-control" name="presidentName" value="${presidentName}">
                </div>
                <div class="form-group">
                    <label>President Phone</label>
                    <input type="text" class="form-control" name="presidentPhone" value="${presidentPhone}">
                </div>
            </#if>



            <button type="submit" class="btn btn-warning btn-lg">Add Club</button>
        </form>

        <hr>

    </div>

</div>

</@u.page>