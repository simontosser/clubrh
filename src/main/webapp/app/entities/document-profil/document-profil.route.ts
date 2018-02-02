import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { DocumentProfilComponent } from './document-profil.component';
import { DocumentProfilDetailComponent } from './document-profil-detail.component';
import { DocumentProfilPopupComponent } from './document-profil-dialog.component';
import { DocumentProfilDeletePopupComponent } from './document-profil-delete-dialog.component';

@Injectable()
export class DocumentProfilResolvePagingParams implements Resolve<any> {

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

export const documentProfilRoute: Routes = [
    {
        path: 'document-profil',
        component: DocumentProfilComponent,
        resolve: {
            'pagingParams': DocumentProfilResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cvthequeApp.documentProfil.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'document-profil/:id',
        component: DocumentProfilDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cvthequeApp.documentProfil.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const documentProfilPopupRoute: Routes = [
    {
        path: 'document-profil-new',
        component: DocumentProfilPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cvthequeApp.documentProfil.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'document-profil/:id/edit',
        component: DocumentProfilPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cvthequeApp.documentProfil.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'document-profil/:id/delete',
        component: DocumentProfilDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cvthequeApp.documentProfil.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
