import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LeadletApiSharedModule } from '../../shared';
import {
    StageService,
    StagePopupService,
    StageComponent,
    StageDetailComponent,
    StageDialogComponent,
    StagePopupComponent,
    StageDeletePopupComponent,
    StageDeleteDialogComponent,
    stageRoute,
    stagePopupRoute,
    StageResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...stageRoute,
    ...stagePopupRoute,
];

@NgModule({
    imports: [
        LeadletApiSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        StageComponent,
        StageDetailComponent,
        StageDialogComponent,
        StageDeleteDialogComponent,
        StagePopupComponent,
        StageDeletePopupComponent,
    ],
    entryComponents: [
        StageComponent,
        StageDialogComponent,
        StagePopupComponent,
        StageDeleteDialogComponent,
        StageDeletePopupComponent,
    ],
    providers: [
        StageService,
        StagePopupService,
        StageResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LeadletApiStageModule {}
