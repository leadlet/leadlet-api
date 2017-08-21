import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LeadletApiSharedModule } from '../../shared';
import {
    ActivityService,
    ActivityPopupService,
    ActivityComponent,
    ActivityDetailComponent,
    ActivityDialogComponent,
    ActivityPopupComponent,
    ActivityDeletePopupComponent,
    ActivityDeleteDialogComponent,
    activityRoute,
    activityPopupRoute,
    ActivityResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...activityRoute,
    ...activityPopupRoute,
];

@NgModule({
    imports: [
        LeadletApiSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ActivityComponent,
        ActivityDetailComponent,
        ActivityDialogComponent,
        ActivityDeleteDialogComponent,
        ActivityPopupComponent,
        ActivityDeletePopupComponent,
    ],
    entryComponents: [
        ActivityComponent,
        ActivityDialogComponent,
        ActivityPopupComponent,
        ActivityDeleteDialogComponent,
        ActivityDeletePopupComponent,
    ],
    providers: [
        ActivityService,
        ActivityPopupService,
        ActivityResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LeadletApiActivityModule {}
