import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LeadletApiSharedModule } from '../../shared';
import {
    DealService,
    DealPopupService,
    DealComponent,
    DealDetailComponent,
    DealDialogComponent,
    DealPopupComponent,
    DealDeletePopupComponent,
    DealDeleteDialogComponent,
    dealRoute,
    dealPopupRoute,
    DealResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...dealRoute,
    ...dealPopupRoute,
];

@NgModule({
    imports: [
        LeadletApiSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        DealComponent,
        DealDetailComponent,
        DealDialogComponent,
        DealDeleteDialogComponent,
        DealPopupComponent,
        DealDeletePopupComponent,
    ],
    entryComponents: [
        DealComponent,
        DealDialogComponent,
        DealPopupComponent,
        DealDeleteDialogComponent,
        DealDeletePopupComponent,
    ],
    providers: [
        DealService,
        DealPopupService,
        DealResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LeadletApiDealModule {}
