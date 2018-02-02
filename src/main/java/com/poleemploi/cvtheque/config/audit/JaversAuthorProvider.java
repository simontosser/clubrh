package com.poleemploi.cvtheque.config.audit;

import com.poleemploi.cvtheque.config.Constants;
import com.poleemploi.cvtheque.security.SecurityUtils;
import org.javers.spring.auditable.AuthorProvider;
import org.springframework.stereotype.Component;

@Component
public class JaversAuthorProvider implements AuthorProvider {

   @Override
   public String provide() {
       String userName = SecurityUtils.getCurrentUserLogin().get();
       return (userName != null ? userName : Constants.SYSTEM_ACCOUNT);
   }
}
