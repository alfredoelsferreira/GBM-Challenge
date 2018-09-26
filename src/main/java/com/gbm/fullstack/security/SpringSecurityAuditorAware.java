package com.gbm.fullstack.security;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;

import com.gbm.fullstack.configuration.Constants;
import com.gbm.fullstack.util.SecurityUtils;

public class SpringSecurityAuditorAware implements AuditorAware<String>{
	
	@Override
	public Optional<String> getCurrentAuditor() {
		String auditor = SecurityUtils.getCurrentUserLogin() == null ? Constants.SYSTEM_ACCOUNT : SecurityUtils.getCurrentUserLogin();
        return Optional.of(auditor);
    }
}
