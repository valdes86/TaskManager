package com.mimacom.taskmanager.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="USER")
public class User implements UserDetails {

	private static final long serialVersionUID = 5478689793661762500L;

	@Id
	@GeneratedValue
	@Column(name="ID")
	private Long userId;
	@NotEmpty
	@Column(name="USERNAME")
	private String username;
	@NotEmpty
	@Column(name="PASSWORD")
	private String password;
	
	@ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
        		.map(SimpleGrantedAuthority::new)
        		.collect(Collectors.toList());
	}
	@Override
	public boolean isAccountNonExpired() {
		return true;

	}
	@Override
	public boolean isAccountNonLocked() {
		return true;

	}
	@Override
	public boolean isCredentialsNonExpired() {
		return true;

	}
	@Override
	public boolean isEnabled() {
		return true;

	}
}
