package com.nnaltakyan.core.auth.domain.user.service;

import com.nnaltakyan.core.auth.domain.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>
{
	Optional<User> findByEmail(final String email);

	@Query("UPDATE _user SET status = :status WHERE userId=userId")
	void updateUserStatus(final Long userId, final String status);
}
