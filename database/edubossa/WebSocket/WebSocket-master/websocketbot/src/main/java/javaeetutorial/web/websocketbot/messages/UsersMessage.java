/**
 * Copyright (c) 2013 Oracle and/or its affiliates. All rights reserved.
 *
 * You may not modify, use, reproduce, or distribute this software except in
 * compliance with  the terms of the License at:
 * http://java.net/projects/javaeetutorial/pages/BerkeleyLicense
 */
package javaeetutorial.web.websocketbot.messages;

import java.util.List;

/* Represents the list of users currently connected to the chat */
public class UsersMessage extends Message {
    private List<String> userlist;
}
