<#import "lib/main.ftl" as u>

<@u.page title="Create Club">

<div class="container">

    <div class="col-sm-6 col-sm-offset-3">

        <h1><span class="fa fa-sign-in"></span> Create New Event</h1>

    <#-- show any messages that come back with authentication -->
        <#if message??>
            <div class="alert alert-danger">${message}</div>
        </#if>

    <#-- Assign any values passed from failed login -->


        <#if !clubID??>
            <#assign clubID="">
        </#if>
        <#if !eventName??>
            <#assign eventName="">
        </#if>
        <#if !eventDescription??>
            <#assign eventDescription="">
        </#if>
        <#if !eventTime??>
            <#assign eventTime="">
        </#if>
        <#if !eventLocation??>
            <#assign eventLocation="">
        </#if>
        <#if !eventFlyer??>
            <#assign eventFlyer="">
        </#if>

        <!-- LOGIN FORM -->
        <form action="/api/user/new/event" method="post">
            <div class="form-group">
                <label>Club ID</label>
                <input type="text" class="form-control" name="clubID" value="${clubID}">
            </div>
            <div class="form-group">
                <label>Event Name</label>
                <input type="text" class="form-control" name="eventName" value="${eventName}">
            </div>
            <div class="form-group">
                <label>Event Description</label>
                <input type="text" class="form-control" name="eventDescription" value="${eventDescription}">
            </div>

            <div class="form-group">
                <label>Event Time</label>
                <input type="date" class="form-control" name="eventTime" value="${eventTime}">
            </div>
            <div class="form-group">
                <label>Event Location</label>
                <input type="text" class="form-control" name="eventLocation" value="${eventLocation}">
            </div>




            <button type="submit" class="btn btn-warning btn-lg">Add Event</button>
        </form>

        <hr>

    </div>

</div>

</@u.page>