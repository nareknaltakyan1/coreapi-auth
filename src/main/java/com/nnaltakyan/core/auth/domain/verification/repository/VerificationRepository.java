package com.nnaltakyan.core.auth.domain.verification.repository;

import com.nnaltakyan.core.auth.domain.verification.model.Verification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VerificationRepository extends JpaRepository<Verification, Long>
{
	Optional<Verification> findByUserid(final Long userid);

	Optional<String> findVerificationCodeByUserId(final Long id);

	@Query("UPDATE verification SET verified=true WHERE userId = :userId")
	void updateVerifiedFlag(final Long userId);
}
