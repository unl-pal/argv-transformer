/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.model;

import java.io.Serializable;

/**
 *
 * @author rmsor_000
 */
public class Tea implements Serializable {

    String name;
    String description;
    int quantity;
    double price;
    private boolean editable;
    Category category;
    String picture;

}
