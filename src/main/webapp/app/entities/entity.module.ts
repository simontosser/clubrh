import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { CvthequeShareProfilModule } from './share-profil/share-profil.module';
import { CvthequeRecrutementProfilModule } from './recrutement-profil/recrutement-profil.module';
import { CvthequeCompanyModule } from './company/company.module';
import { CvthequeAddressModule } from './address/address.module';
import { CvthequeDocumentProfilModule } from './document-profil/document-profil.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        CvthequeShareProfilModule,
        CvthequeRecrutementProfilModule,
        CvthequeCompanyModule,
        CvthequeAddressModule,
        CvthequeDocumentProfilModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CvthequeEntityModule {}
