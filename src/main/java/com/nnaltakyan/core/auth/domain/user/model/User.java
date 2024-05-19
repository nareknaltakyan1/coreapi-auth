package com.nnaltakyan.core.auth.domain.user.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_user", indexes = {
		@Index(name = "idx_user_email", columnList = "email"),
		@Index(name = "idx_user_created", columnList = "created") },
		uniqueConstraints = {
		@UniqueConstraint(name = "nn_email_unique", columnNames = "email")
		}
)
public class User implements UserDetails
{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private Timestamp created;
	private Timestamp updated;
	private Timestamp deleted;
	private String status;

	@Enumerated(EnumType.STRING)
	private Role role;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities()
	{
		return List.of(new SimpleGrantedAuthority(role.name()));
	}

	@Override
	public String getUsername()
	{
		return email;
	}

	@Override
	public boolean isAccountNonExpired()
	{
		return true;
	}

	@Override
	public boolean isAccountNonLocked()
	{
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired()
	{
		return true;
	}

	@Override
	public boolean isEnabled()
	{
		return true;
	}
}
