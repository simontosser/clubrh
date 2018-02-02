import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CvthequeSharedModule } from '../../shared';
import {
    RecrutementProfilService,
    RecrutementProfilPopupService,
    RecrutementProfilComponent,
    RecrutementProfilDetailComponent,
    RecrutementProfilDialogComponent,
    RecrutementProfilPopupComponent,
    RecrutementProfilDeletePopupComponent,
    RecrutementProfilDeleteDialogComponent,
    recrutementProfilRoute,
    recrutementProfilPopupRoute,
    RecrutementProfilResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...recrutementProfilRoute,
    ...recrutementProfilPopupRoute,
];

@NgModule({
    imports: [
        CvthequeSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        RecrutementProfilComponent,
        RecrutementProfilDetailComponent,
        RecrutementProfilDialogComponent,
        RecrutementProfilDeleteDialogComponent,
        RecrutementProfilPopupComponent,
        RecrutementProfilDeletePopupComponent,
    ],
    entryComponents: [
        RecrutementProfilComponent,
        RecrutementProfilDialogComponent,
        RecrutementProfilPopupComponent,
        RecrutementProfilDeleteDialogComponent,
        RecrutementProfilDeletePopupComponent,
    ],
    providers: [
        RecrutementProfilService,
        RecrutementProfilPopupService,
        RecrutementProfilResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CvthequeRecrutementProfilModule {}
