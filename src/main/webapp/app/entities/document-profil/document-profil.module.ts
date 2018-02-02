import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CvthequeSharedModule } from '../../shared';
import {
    DocumentProfilService,
    DocumentProfilPopupService,
    DocumentProfilComponent,
    DocumentProfilDetailComponent,
    DocumentProfilDialogComponent,
    DocumentProfilPopupComponent,
    DocumentProfilDeletePopupComponent,
    DocumentProfilDeleteDialogComponent,
    documentProfilRoute,
    documentProfilPopupRoute,
    DocumentProfilResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...documentProfilRoute,
    ...documentProfilPopupRoute,
];

@NgModule({
    imports: [
        CvthequeSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        DocumentProfilComponent,
        DocumentProfilDetailComponent,
        DocumentProfilDialogComponent,
        DocumentProfilDeleteDialogComponent,
        DocumentProfilPopupComponent,
        DocumentProfilDeletePopupComponent,
    ],
    entryComponents: [
        DocumentProfilComponent,
        DocumentProfilDialogComponent,
        DocumentProfilPopupComponent,
        DocumentProfilDeleteDialogComponent,
        DocumentProfilDeletePopupComponent,
    ],
    providers: [
        DocumentProfilService,
        DocumentProfilPopupService,
        DocumentProfilResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CvthequeDocumentProfilModule {}
