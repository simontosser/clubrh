import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { DocumentProfil } from './document-profil.model';
import { DocumentProfilService } from './document-profil.service';

@Component({
    selector: 'jhi-document-profil-detail',
    templateUrl: './document-profil-detail.component.html'
})
export class DocumentProfilDetailComponent implements OnInit, OnDestroy {

    documentProfil: DocumentProfil;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private documentProfilService: DocumentProfilService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInDocumentProfils();
    }

    load(id) {
        this.documentProfilService.find(id)
            .subscribe((documentProfilResponse: HttpResponse<DocumentProfil>) => {
                this.documentProfil = documentProfilResponse.body;
            });
    }
    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInDocumentProfils() {
        this.eventSubscriber = this.eventManager.subscribe(
            'documentProfilListModification',
            (response) => this.load(this.documentProfil.id)
        );
    }
}
