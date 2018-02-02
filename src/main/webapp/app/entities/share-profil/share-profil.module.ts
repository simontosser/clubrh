import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CvthequeSharedModule } from '../../shared';
import {
    ShareProfilService,
    ShareProfilPopupService,
    ShareProfilComponent,
    ShareProfilDetailComponent,
    ShareProfilDialogComponent,
    ShareProfilPopupComponent,
    ShareProfilDeletePopupComponent,
    ShareProfilDeleteDialogComponent,
    shareProfilRoute,
    shareProfilPopupRoute,
    ShareProfilResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...shareProfilRoute,
    ...shareProfilPopupRoute,
];

@NgModule({
    imports: [
        CvthequeSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ShareProfilComponent,
        ShareProfilDetailComponent,
        ShareProfilDialogComponent,
        ShareProfilDeleteDialogComponent,
        ShareProfilPopupComponent,
        ShareProfilDeletePopupComponent,
    ],
    entryComponents: [
        ShareProfilComponent,
        ShareProfilDialogComponent,
        ShareProfilPopupComponent,
        ShareProfilDeleteDialogComponent,
        ShareProfilDeletePopupComponent,
    ],
    providers: [
        ShareProfilService,
        ShareProfilPopupService,
        ShareProfilResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CvthequeShareProfilModule {}
