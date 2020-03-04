package com.yakut.azone.controller;

import com.yakut.azone.beans.User;

 

/**
 *
 * @author yakut
 */
public class UserManager {

          static UserManager userManager = new UserManager();
          User user = null;

          private UserManager() {
                  
          }

          public void setActiveUser(User user) {
                    this.user = user;
          }

          public static void setUser(User user) {
                    userManager.setActiveUser(user);
          }

          public User getActiveUser() {
                    return user;
          }

          public static User getUser() {
                    return userManager.getActiveUser();
          }
}
