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
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotEmpty;
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
    
    @NotEmpty(message = "name cannot be empty")
    private String name;
    
    @NotEmpty(message = "address1 cannot be empty")
    private String address1;
    
    @NotEmpty(message = "address2 cannot be empty")
    private String address2;
    
    @NotEmpty(message = "city cannot be empty")
    private String city;
    
    @NotEmpty(message = "state cannot be empty")
    private String state;
    
    @NotEmpty(message = "code cannot be empty")
    private String code;
    
    @NotEmpty(message = "country cannot be empty")
    private String country;
    
    @NotEmpty(message = "phone cannot be empty")
    private String phone;
    
    @NotEmpty(message = "website cannot be empty")
    private String website;
    
    @NotEmpty(message = "image cannot be empty")
    private String image;
    
    
    @Lob
    private String description;
    
    private Integer add_user;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date last_mod;
    
    private Double credit_limit;
    private String email;
}
