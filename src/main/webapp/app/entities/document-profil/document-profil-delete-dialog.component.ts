import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { DocumentProfil } from './document-profil.model';
import { DocumentProfilPopupService } from './document-profil-popup.service';
import { DocumentProfilService } from './document-profil.service';

@Component({
    selector: 'jhi-document-profil-delete-dialog',
    templateUrl: './document-profil-delete-dialog.component.html'
})
export class DocumentProfilDeleteDialogComponent {

    documentProfil: DocumentProfil;

    constructor(
        private documentProfilService: DocumentProfilService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.documentProfilService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'documentProfilListModification',
                content: 'Deleted an documentProfil'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-document-profil-delete-popup',
    template: ''
})
export class DocumentProfilDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private documentProfilPopupService: DocumentProfilPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.documentProfilPopupService
                .open(DocumentProfilDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
