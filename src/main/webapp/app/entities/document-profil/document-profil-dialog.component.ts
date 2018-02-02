import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { DocumentProfil } from './document-profil.model';
import { DocumentProfilPopupService } from './document-profil-popup.service';
import { DocumentProfilService } from './document-profil.service';
import { ShareProfil, ShareProfilService } from '../share-profil';
import { RecrutementProfil, RecrutementProfilService } from '../recrutement-profil';

@Component({
    selector: 'jhi-document-profil-dialog',
    templateUrl: './document-profil-dialog.component.html'
})
export class DocumentProfilDialogComponent implements OnInit {

    documentProfil: DocumentProfil;
    isSaving: boolean;

    shareprofils: ShareProfil[];

    recrutementprofils: RecrutementProfil[];

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private documentProfilService: DocumentProfilService,
        private shareProfilService: ShareProfilService,
        private recrutementProfilService: RecrutementProfilService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.shareProfilService.query()
            .subscribe((res: HttpResponse<ShareProfil[]>) => { this.shareprofils = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.recrutementProfilService.query()
            .subscribe((res: HttpResponse<RecrutementProfil[]>) => { this.recrutementprofils = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.documentProfil.id !== undefined) {
            this.subscribeToSaveResponse(
                this.documentProfilService.update(this.documentProfil));
        } else {
            this.subscribeToSaveResponse(
                this.documentProfilService.create(this.documentProfil));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<DocumentProfil>>) {
        result.subscribe((res: HttpResponse<DocumentProfil>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: DocumentProfil) {
        this.eventManager.broadcast({ name: 'documentProfilListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackShareProfilById(index: number, item: ShareProfil) {
        return item.id;
    }

    trackRecrutementProfilById(index: number, item: RecrutementProfil) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-document-profil-popup',
    template: ''
})
export class DocumentProfilPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private documentProfilPopupService: DocumentProfilPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.documentProfilPopupService
                    .open(DocumentProfilDialogComponent as Component, params['id']);
            } else {
                this.documentProfilPopupService
                    .open(DocumentProfilDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
