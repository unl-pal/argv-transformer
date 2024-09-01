/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author rmsor_000
 */
@Entity
@Table(name = "theater")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Theater.findAll", query = "SELECT t FROM Theater t"),
    @NamedQuery(name = "Theater.findByTid", query = "SELECT t FROM Theater t WHERE t.tid = :tid"),
    @NamedQuery(name = "Theater.findByTname", query = "SELECT t FROM Theater t WHERE t.tname = :tname"),
    @NamedQuery(name = "Theater.findByLocation", query = "SELECT t FROM Theater t WHERE t.location = :location"),
    @NamedQuery(name = "Theater.findByNoOfScreens", query = "SELECT t FROM Theater t WHERE t.noOfScreens = :noOfScreens")})
public class Theater implements Serializable {
    @Column(name = "noOfScreens")
    private Integer noOfScreens;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tid")
    private Collection<Show> showCollection;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "tid")
    private Long tid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "tname")
    private String tname;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "location")
    private String location;
    
}
