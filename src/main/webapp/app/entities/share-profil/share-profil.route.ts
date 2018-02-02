import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { ShareProfilComponent } from './share-profil.component';
import { ShareProfilDetailComponent } from './share-profil-detail.component';
import { ShareProfilPopupComponent } from './share-profil-dialog.component';
import { ShareProfilDeletePopupComponent } from './share-profil-delete-dialog.component';

@Injectable()
export class ShareProfilResolvePagingParams implements Resolve<any> {

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

export const shareProfilRoute: Routes = [
    {
        path: 'share-profil',
        component: ShareProfilComponent,
        resolve: {
            'pagingParams': ShareProfilResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cvthequeApp.shareProfil.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'share-profil/:id',
        component: ShareProfilDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cvthequeApp.shareProfil.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const shareProfilPopupRoute: Routes = [
    {
        path: 'share-profil-new',
        component: ShareProfilPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cvthequeApp.shareProfil.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'share-profil/:id/edit',
        component: ShareProfilPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cvthequeApp.shareProfil.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'share-profil/:id/delete',
        component: ShareProfilDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cvthequeApp.shareProfil.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
