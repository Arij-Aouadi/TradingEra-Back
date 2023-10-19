package tn.esprit.shadowtradergo.DAO.Entities;

import org.springframework.security.core.GrantedAuthority;

public enum TypeRole implements GrantedAuthority
{
    ADMIN,CLIENT,AGENT;


    @Override
    public String getAuthority() {
        return this.name();
    }
}
