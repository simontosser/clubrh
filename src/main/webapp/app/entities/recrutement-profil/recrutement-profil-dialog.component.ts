import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService, JhiDataUtils} from 'ng-jhipster';

import { RecrutementProfil } from './recrutement-profil.model';
import { RecrutementProfilPopupService } from './recrutement-profil-popup.service';
import { RecrutementProfilService } from './recrutement-profil.service';
import { Company, CompanyService } from '../company';
import { DocumentProfil } from '../document-profil/document-profil.model';

@Component({
    selector: 'jhi-recrutement-profil-dialog',
    templateUrl: './recrutement-profil-dialog.component.html'
})
export class RecrutementProfilDialogComponent implements OnInit {

    recrutementProfil: RecrutementProfil;
    isSaving: boolean;
    tempFile = new DocumentProfil();

    companies: Company[];
    deadlineDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private recrutementProfilService: RecrutementProfilService,
        private companyService: CompanyService,
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
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
        if (this.recrutementProfil.id !== undefined) {
            this.subscribeToSaveResponse(
                this.recrutementProfilService.update(this.recrutementProfil));
        } else {
            this.subscribeToSaveResponse(
                this.recrutementProfilService.create(this.recrutementProfil));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<RecrutementProfil>>) {
        result.subscribe((res: HttpResponse<RecrutementProfil>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: RecrutementProfil) {
        this.eventManager.broadcast({ name: 'recrutementProfilListModification', content: 'OK'});
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

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    setFileData(event, entity, field, isImage) {
        if (event && event.target.files && event.target.files[0]) {
            entity[field + 'Name'] = event.target.files[0].name;
        }
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    addFile(event, entity) {
      event.path[3].children[0].value = '';
      this.recrutementProfil.documentProfils.push(entity);
      this.tempFile = new DocumentProfil();
    }

    deleteFile(entity) {
       this.recrutementProfil.documentProfils = this.recrutementProfil.documentProfils.filter((item: DocumentProfil) =>
          item.id !== entity.id && item.documentFileName !== entity.documentFileName);
    }
}

@Component({
    selector: 'jhi-recrutement-profil-popup',
    template: ''
})
export class RecrutementProfilPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private recrutementProfilPopupService: RecrutementProfilPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.recrutementProfilPopupService
                    .open(RecrutementProfilDialogComponent as Component, params['id']);
            } else {
                this.recrutementProfilPopupService
                    .open(RecrutementProfilDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
