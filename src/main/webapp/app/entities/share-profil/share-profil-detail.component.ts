import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { ShareProfil } from './share-profil.model';
import { ShareProfilService } from './share-profil.service';

@Component({
    selector: 'jhi-share-profil-detail',
    templateUrl: './share-profil-detail.component.html'
})
export class ShareProfilDetailComponent implements OnInit, OnDestroy {

    shareProfil: ShareProfil;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private shareProfilService: ShareProfilService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInShareProfils();
    }

    load(id) {
        this.shareProfilService.find(id)
            .subscribe((shareProfilResponse: HttpResponse<ShareProfil>) => {
                this.shareProfil = shareProfilResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInShareProfils() {
        this.eventSubscriber = this.eventManager.subscribe(
            'shareProfilListModification',
            (response) => this.load(this.shareProfil.id)
        );
    }
}
