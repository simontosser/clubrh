import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CvthequeSharedModule } from '../../shared';

import {
    MyProfilRoute,
    MyProfilComponent,
    MyProfilShareComponent,
    MyProfilRecrutementComponent,
    MyProfilResolvePagingParams
} from './';

@NgModule({
    imports: [
        CvthequeSharedModule,
        RouterModule.forChild([MyProfilRoute])
    ],
    declarations: [
        MyProfilComponent,
        MyProfilShareComponent,
        MyProfilRecrutementComponent
    ],
    entryComponents: [
        MyProfilComponent,
        MyProfilShareComponent,
        MyProfilRecrutementComponent
    ],
    providers: [
        MyProfilResolvePagingParams
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CvthequeMyProfilModule {}
