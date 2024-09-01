package com.corejsf;

import java.util.ArrayList;
import java.util.List;

public class Registrar {

   private static List<User> registeredUsers = new ArrayList<User>();
   static {
      registeredUsers.add(new User("Hiro", "secret"));
   }
}
