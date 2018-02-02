import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { RecrutementProfil } from './recrutement-profil.model';
import { RecrutementProfilService } from './recrutement-profil.service';

@Component({
    selector: 'jhi-recrutement-profil-detail',
    templateUrl: './recrutement-profil-detail.component.html'
})
export class RecrutementProfilDetailComponent implements OnInit, OnDestroy {

    recrutementProfil: RecrutementProfil;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private recrutementProfilService: RecrutementProfilService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInRecrutementProfils();
    }

    load(id) {
        this.recrutementProfilService.find(id)
            .subscribe((recrutementProfilResponse: HttpResponse<RecrutementProfil>) => {
                this.recrutementProfil = recrutementProfilResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInRecrutementProfils() {
        this.eventSubscriber = this.eventManager.subscribe(
            'recrutementProfilListModification',
            (response) => this.load(this.recrutementProfil.id)
        );
    }
}
