import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ShareProfil } from './share-profil.model';
import { ShareProfilPopupService } from './share-profil-popup.service';
import { ShareProfilService } from './share-profil.service';
import { Company, CompanyService } from '../company';

@Component({
    selector: 'jhi-share-profil-dialog',
    templateUrl: './share-profil-dialog.component.html'
})
export class ShareProfilDialogComponent implements OnInit {

    shareProfil: ShareProfil;
    isSaving: boolean;

    companies: Company[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private shareProfilService: ShareProfilService,
        private companyService: CompanyService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.companyService.query()
            .subscribe((res: HttpResponse<Company[]>) => { this.companies = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.shareProfil.id !== undefined) {
            this.subscribeToSaveResponse(
                this.shareProfilService.update(this.shareProfil));
        } else {
            this.subscribeToSaveResponse(
                this.shareProfilService.create(this.shareProfil));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ShareProfil>>) {
        result.subscribe((res: HttpResponse<ShareProfil>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: ShareProfil) {
        this.eventManager.broadcast({ name: 'shareProfilListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackCompanyById(index: number, item: Company) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-share-profil-popup',
    template: ''
})
export class ShareProfilPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private shareProfilPopupService: ShareProfilPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.shareProfilPopupService
                    .open(ShareProfilDialogComponent as Component, params['id']);
            } else {
                this.shareProfilPopupService
                    .open(ShareProfilDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
