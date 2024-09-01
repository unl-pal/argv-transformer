/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import ejb.EmailSessionBean;
import ejb.UserFacade;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import model.UserInfo;

/**
 *
 * @author rmsor_000
 */
@Named("signupController")
@SessionScoped
public class SignupController implements Serializable {

    @Inject
    private EmailSessionBean emailSessionBean;
    @Inject
    private UserFacade userFacade;
    private UserInfo user;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String conPassword;

    private String phoneNumber;
    private String addressLine1;
    private String addressLine2;
    private String age;
    private String gender;
    private String profilePic;

    private String extension;

    //for current user id
    private Long id;

    private Part file1;

    private String message;

    public String upload() throws IOException {

        InputStream inputStream = null;
        OutputStream outputStream = null;
        FacesContext context = FacesContext.getCurrentInstance();
        ServletContext servletContext = (ServletContext) context
                .getExternalContext().getContext();
        String path = servletContext.getRealPath("");
        boolean file1Success = false;

        String fileName = "";

        if (file1.getSize() > 0) {
            fileName = getFileNameFromPart(file1);
            /**
             * destination where the file will be uploaded
             */
            File outputFile = new File(path + File.separator + "resources"
                    + File.separator + "profile_pic" + File.separator + fileName);
            inputStream = file1.getInputStream();
            outputStream = new FileOutputStream(outputFile);
            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead = 0;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            if (outputStream != null) {
                outputStream.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
            file1Success = true;
        }

        if (file1Success) {
            System.out.println("File uploaded to : " + path);
            /**
             * set the success message when the file upload is successful
             */
            setMessage("File successfully uploaded to " + path);
        } else {
            /**
             * set the error message when error occurs during the file upload
             */
            setMessage("Error, select atleast one file!");
        }

        File oldfile = new File(path + File.separator + "resources"
                + File.separator + "profile_pic" + File.separator + fileName);

        extension = fileName.substring(fileName.lastIndexOf(".") + 1);

        File newfile = new File(path + File.separator + "resources"
                + File.separator + "profile_pic" + File.separator + id + "." + extension);

        if (oldfile.renameTo(newfile)) {
            System.out.println("Rename succesful");
            return "success";

        } else {
            System.out.println("Rename failed");

            return "fail";
        }

    }

    public static final int BUFFER_SIZE = 3000000;
}
