package com.corejsf;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class CredentialsManager {
   @PersistenceContext(unitName="default")
   private EntityManager em;
}
