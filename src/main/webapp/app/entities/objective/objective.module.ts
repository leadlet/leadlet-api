import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LeadletApiSharedModule } from '../../shared';
import {
    ObjectiveService,
    ObjectivePopupService,
    ObjectiveComponent,
    ObjectiveDetailComponent,
    ObjectiveDialogComponent,
    ObjectivePopupComponent,
    ObjectiveDeletePopupComponent,
    ObjectiveDeleteDialogComponent,
    objectiveRoute,
    objectivePopupRoute,
    ObjectiveResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...objectiveRoute,
    ...objectivePopupRoute,
];

@NgModule({
    imports: [
        LeadletApiSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ObjectiveComponent,
        ObjectiveDetailComponent,
        ObjectiveDialogComponent,
        ObjectiveDeleteDialogComponent,
        ObjectivePopupComponent,
        ObjectiveDeletePopupComponent,
    ],
    entryComponents: [
        ObjectiveComponent,
        ObjectiveDialogComponent,
        ObjectivePopupComponent,
        ObjectiveDeleteDialogComponent,
        ObjectiveDeletePopupComponent,
    ],
    providers: [
        ObjectiveService,
        ObjectivePopupService,
        ObjectiveResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LeadletApiObjectiveModule {}
