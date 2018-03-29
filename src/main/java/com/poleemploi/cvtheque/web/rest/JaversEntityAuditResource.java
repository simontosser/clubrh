package com.poleemploi.cvtheque.web.rest;

import com.poleemploi.cvtheque.domain.EntityAuditEvent;
import com.poleemploi.cvtheque.web.rest.util.PaginationUtil;
import org.javers.core.Javers;
import org.javers.core.metamodel.object.CdoSnapshot;
import org.javers.repository.jql.QueryBuilder;
import com.poleemploi.cvtheque.security.AuthoritiesConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.access.annotation.Secured;
import com.codahale.metrics.annotation.Timed;

import java.net.URISyntaxException;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * REST controller for getting the audit events for entity.
 */
@RestController
@RequestMapping("/api")
public class JaversEntityAuditResource {

    /** The log. */
    private final Logger log = LoggerFactory.getLogger(JaversEntityAuditResource.class);

    /** The javers. */
    private final Javers javers;

    /**
     * Instantiates a new javers entity audit resource.
     *
     * @param javers the javers
     */
    public JaversEntityAuditResource(Javers javers) {
        this.javers = javers;
    }

    /**
     * fetches all the audited entity types.
     *
     * @return the audited entities
     */
    @RequestMapping(value = "/audits/entity/all",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public List<String> getAuditedEntities() {

      return Arrays.asList("Address", "Company", "DocumentProfil", "RecrutementProfil", "ShareProfil");
    }

    /**
     * fetches the last 100 change list for an entity class, if limit is passed fetches that many changes.
     *
     * @param entityType the entity type
     * @param limit the limit
     * @return the changes
     * @throws URISyntaxException the URI syntax exception
     * @throws ClassNotFoundException the class not found exception
     */
    @RequestMapping(value = "/audits/entity/changes",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<List<EntityAuditEvent>> getChanges(@RequestParam(value = "entityType") String entityType,
                                                             @RequestParam(value = "limit") int limit)
        throws URISyntaxException, ClassNotFoundException {
        log.debug("REST request to get a page of EntityAuditEvents");
        Pageable pageRequest = createPageRequest(limit);

        Class entityTypeToFetch = Class.forName("com.poleemploi.cvtheque.domain." + entityType);
        QueryBuilder jqlQuery = QueryBuilder.byClass(entityTypeToFetch)
                                            .limit(limit)
                                            .withNewObjectChanges(true);

        List<CdoSnapshot> snapshots =  javers.findSnapshots(jqlQuery.build());

        List<EntityAuditEvent> auditEvents = new ArrayList<>();

        snapshots.forEach(snapshot -> {
           EntityAuditEvent event = EntityAuditEvent.fromJaversSnapshot(snapshot);
           event.setEntityType(entityType);
           auditEvents.add(event);
        });

        Page<EntityAuditEvent> page = new PageImpl<>(auditEvents);

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/audits/entity/changes");

        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);

    }

    /**
     * fetches a previous version for for an entity class and id.
     *
     * @param qualifiedName the qualified name
     * @param entityId the entity id
     * @param commitVersion the commit version
     * @return the prev version
     * @throws URISyntaxException the URI syntax exception
     * @throws ClassNotFoundException the class not found exception
     */
    @RequestMapping(value = "/audits/entity/changes/version/previous",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<EntityAuditEvent> getPrevVersion(@RequestParam(value = "qualifiedName") String qualifiedName,
                                                           @RequestParam(value = "entityId") String entityId,
                                                           @RequestParam(value = "commitVersion") Long commitVersion)
        throws URISyntaxException, ClassNotFoundException {

        Class entityTypeToFetch = Class.forName("com.poleemploi.cvtheque.domain." + qualifiedName);

        QueryBuilder jqlQuery = QueryBuilder.byInstanceId(entityId, entityTypeToFetch)
                                           .limit(1)
                                           .withVersion(commitVersion - 1)
                                           .withNewObjectChanges(true);

        EntityAuditEvent prev = EntityAuditEvent.fromJaversSnapshot(javers.findSnapshots(jqlQuery.build()).get(0));

        return new ResponseEntity<>(prev, HttpStatus.OK);

    }

    /**
     * creates a page request object for PaginationUti.
     *
     * @param size the size
     * @return the pageable
     */
    private Pageable createPageRequest(int size) {
        return new PageRequest(0, size);
    }

}
