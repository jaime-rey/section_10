package dev.jaimerey.accounts.audit;


import org.jspecify.annotations.NullMarked;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("auditAwareImpl")
public class AuditAwareImpl implements AuditorAware<String> {

    /**
     * Returns the current auditor of the application.
     *
     * @return the current auditor.
     */
    @Override
    @NullMarked
    public Optional<String> getCurrentAuditor() {
        return Optional.of("ACCOUNTS_MS");
    }

}
