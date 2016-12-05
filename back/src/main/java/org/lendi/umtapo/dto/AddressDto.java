package org.lendi.umtapo.dto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


/**
 * Created by axel on 29/11/16.
 */
@Entity
public class AddressDto {

 @Id
 @GeneratedValue(strategy = GenerationType.AUTO)
 private Integer id;
 private String address1;
 private String address2;
 private String zip;
 private String city;
 private String phone;
 private String email;


 public Integer getId() {
  return id;
 }


 public void setId(Integer id) {
  this.id = id;
 }


 public String getAddress1() {
  return address1;
 }


 public void setAddress1(String address1) {
  this.address1 = address1;
 }


 public String getAddress2() {
  return address2;
 }


 public void setAddress2(String address2) {
  this.address2 = address2;
 }


 public String getZip() {
  return zip;
 }


 public void setZip(String zip) {
  this.zip = zip;
 }


 public String getCity() {
  return city;
 }


 public void setCity(String city) {
  this.city = city;
 }


 public String getPhone() {
  return phone;
 }


 public void setPhone(String phone) {
  this.phone = phone;
 }


 public String getEmail() {
  return email;
 }


 public void setEmail(String email) {
  this.email = email;
 }
}