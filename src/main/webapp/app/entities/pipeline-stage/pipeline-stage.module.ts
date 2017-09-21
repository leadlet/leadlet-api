import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LeadletApiSharedModule } from '../../shared';
import {
    PipelineStageService,
    PipelineStagePopupService,
    PipelineStageComponent,
    PipelineStageDetailComponent,
    PipelineStageDialogComponent,
    PipelineStagePopupComponent,
    PipelineStageDeletePopupComponent,
    PipelineStageDeleteDialogComponent,
    pipelineStageRoute,
    pipelineStagePopupRoute,
    PipelineStageResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...pipelineStageRoute,
    ...pipelineStagePopupRoute,
];

@NgModule({
    imports: [
        LeadletApiSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        PipelineStageComponent,
        PipelineStageDetailComponent,
        PipelineStageDialogComponent,
        PipelineStageDeleteDialogComponent,
        PipelineStagePopupComponent,
        PipelineStageDeletePopupComponent,
    ],
    entryComponents: [
        PipelineStageComponent,
        PipelineStageDialogComponent,
        PipelineStagePopupComponent,
        PipelineStageDeleteDialogComponent,
        PipelineStageDeletePopupComponent,
    ],
    providers: [
        PipelineStageService,
        PipelineStagePopupService,
        PipelineStageResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LeadletApiPipelineStageModule {}
