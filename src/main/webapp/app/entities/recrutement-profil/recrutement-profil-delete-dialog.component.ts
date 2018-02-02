import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { RecrutementProfil } from './recrutement-profil.model';
import { RecrutementProfilPopupService } from './recrutement-profil-popup.service';
import { RecrutementProfilService } from './recrutement-profil.service';

@Component({
    selector: 'jhi-recrutement-profil-delete-dialog',
    templateUrl: './recrutement-profil-delete-dialog.component.html'
})
export class RecrutementProfilDeleteDialogComponent {

    recrutementProfil: RecrutementProfil;

    constructor(
        private recrutementProfilService: RecrutementProfilService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.recrutementProfilService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'recrutementProfilListModification',
                content: 'Deleted an recrutementProfil'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-recrutement-profil-delete-popup',
    template: ''
})
export class RecrutementProfilDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private recrutementProfilPopupService: RecrutementProfilPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.recrutementProfilPopupService
                .open(RecrutementProfilDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
