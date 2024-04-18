package com.nnaltakyan.core.auth.domain.service.system;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;

@Service
public class SystemDateTimeService
{
	public LocalDateTime getCurrentDateTime()
	{
		return LocalDateTime.now();
	}

	public Date getCurrentDate()
	{
		return new Date();
	}

	public Date getCurrentDatePlusExtraTime(int extraTime)
	{
		return new Date(System.currentTimeMillis() + extraTime);
	}
}
