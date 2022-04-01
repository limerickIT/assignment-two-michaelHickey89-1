/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sd4.model;

import java.io.Serializable;
import java.sql.Clob;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


import org.springframework.hateoas.RepresentationModel;

/**
 *
 * @author Alan.Ryan
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Brewery extends RepresentationModel<Brewery> implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @NotBlank(message = "name cannot be empty")
    private String name;
    
    @NotEmpty(message = "address1 cannot be empty")
    private String address1;
    
   
    private String address2;
    
    @NotEmpty(message = "city cannot be empty")
    private String city;
    
    
    private String state;
    
    
    private String code;
    
    @NotEmpty(message = "country cannot be empty")
    private String country;
    
    @NotEmpty(message = "phone cannot be empty")
    private String phone;
    
    
    private String website;
    
    @NotEmpty(message = "image cannot be empty")
    private String image;
    
    
    @Lob
    private String description;
    
    private Integer add_user;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date last_mod;
    
    private Double credit_limit;
    
    @Email
    private String email;
}
