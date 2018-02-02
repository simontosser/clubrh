import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { RecrutementProfil } from './recrutement-profil.model';
import { RecrutementProfilService } from './recrutement-profil.service';

@Injectable()
export class RecrutementProfilPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private recrutementProfilService: RecrutementProfilService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.recrutementProfilService.find(id)
                    .subscribe((recrutementProfilResponse: HttpResponse<RecrutementProfil>) => {
                        const recrutementProfil: RecrutementProfil = recrutementProfilResponse.body;
                        if (recrutementProfil.deadline) {
                            recrutementProfil.deadline = {
                                year: recrutementProfil.deadline.getFullYear(),
                                month: recrutementProfil.deadline.getMonth() + 1,
                                day: recrutementProfil.deadline.getDate()
                            };
                        }
                        this.ngbModalRef = this.recrutementProfilModalRef(component, recrutementProfil);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.recrutementProfilModalRef(component, new RecrutementProfil());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    recrutementProfilModalRef(component: Component, recrutementProfil: RecrutementProfil): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.recrutementProfil = recrutementProfil;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
