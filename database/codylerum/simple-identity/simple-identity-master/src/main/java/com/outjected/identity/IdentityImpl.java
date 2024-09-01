package com.outjected.identity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import javax.inject.Named;

import com.outjected.identity.events.PostLoggedOutEvent;

/**
 * @author Cody Lerum
 */
@SessionScoped @Named("identity") public class IdentityImpl implements Serializable, Identity {

    private static final long serialVersionUID = 1L;

    @Inject @Any private Instance<Authenticator> authenticators;

    @Inject private BeanManager beanManager;

    // Data
    private Class<? extends Authenticator> authenticatorClass;
    private IdentityUser user;
    private Set<SimpleRole> roles = new HashSet<>();
    private Set<SimplePermission> permissions = new HashSet<>();


}
