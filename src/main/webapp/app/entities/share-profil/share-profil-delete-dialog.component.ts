import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ShareProfil } from './share-profil.model';
import { ShareProfilPopupService } from './share-profil-popup.service';
import { ShareProfilService } from './share-profil.service';

@Component({
    selector: 'jhi-share-profil-delete-dialog',
    templateUrl: './share-profil-delete-dialog.component.html'
})
export class ShareProfilDeleteDialogComponent {

    shareProfil: ShareProfil;

    constructor(
        private shareProfilService: ShareProfilService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.shareProfilService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'shareProfilListModification',
                content: 'Deleted an shareProfil'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-share-profil-delete-popup',
    template: ''
})
export class ShareProfilDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private shareProfilPopupService: ShareProfilPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.shareProfilPopupService
                .open(ShareProfilDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
