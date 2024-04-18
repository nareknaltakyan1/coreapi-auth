package com.nnaltakyan.core.auth.domain.service.user;

import com.nnaltakyan.core.auth.domain.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>
{
	Optional<User> findByEmail(final String email);
}
