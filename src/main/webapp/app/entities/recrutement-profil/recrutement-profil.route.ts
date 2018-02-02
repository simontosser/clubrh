import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { RecrutementProfilComponent } from './recrutement-profil.component';
import { RecrutementProfilDetailComponent } from './recrutement-profil-detail.component';
import { RecrutementProfilPopupComponent } from './recrutement-profil-dialog.component';
import { RecrutementProfilDeletePopupComponent } from './recrutement-profil-delete-dialog.component';

@Injectable()
export class RecrutementProfilResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const recrutementProfilRoute: Routes = [
    {
        path: 'recrutement-profil',
        component: RecrutementProfilComponent,
        resolve: {
            'pagingParams': RecrutementProfilResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cvthequeApp.recrutementProfil.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'recrutement-profil/:id',
        component: RecrutementProfilDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cvthequeApp.recrutementProfil.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const recrutementProfilPopupRoute: Routes = [
    {
        path: 'recrutement-profil-new',
        component: RecrutementProfilPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cvthequeApp.recrutementProfil.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'recrutement-profil/:id/edit',
        component: RecrutementProfilPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cvthequeApp.recrutementProfil.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'recrutement-profil/:id/delete',
        component: RecrutementProfilDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cvthequeApp.recrutementProfil.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
