import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CvthequeSharedModule } from '../../shared';

import {
    ElasticsearchReindexComponent,
    ElasticsearchReindexModalComponent,
    ElasticsearchReindexService,
    elasticsearchReindexRoute
} from './';

const ADMIN_ROUTES = [
    elasticsearchReindexRoute
];

@NgModule({
    imports: [
        CvthequeSharedModule,
        RouterModule.forRoot(ADMIN_ROUTES, { useHash: true })
    ],
    declarations: [
        ElasticsearchReindexComponent,
        ElasticsearchReindexModalComponent
    ],
    entryComponents: [
        ElasticsearchReindexModalComponent
    ],
    providers: [
        ElasticsearchReindexService
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})

export class CvthequeElasticsearchReindexModule {}
