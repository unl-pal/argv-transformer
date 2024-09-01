package views.formdata;

import models.Contact;
import play.data.validation.ValidationError;

import java.util.ArrayList;
import java.util.List;

/**
 * The backing class for the Contact form.
 */
public class ContactFormData {
  /**
   * The first name.
   */
  public String firstName;
  /**
   * The last name.
   */
  public String lastName;
  /**
   * The telephone number in xxx-xxx-xxxx format.
   */
  public String telephone;
  /**
   * The address.
   */
  public String address;
  /**
   * The id.
   */
  public long id;
  /**
   * The telephone type.
   */
  public String telephoneType;

}
