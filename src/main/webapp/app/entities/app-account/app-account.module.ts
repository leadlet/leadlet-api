import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LeadletApiSharedModule } from '../../shared';
import {
    AppAccountService,
    AppAccountPopupService,
    AppAccountComponent,
    AppAccountDetailComponent,
    AppAccountDialogComponent,
    AppAccountPopupComponent,
    AppAccountDeletePopupComponent,
    AppAccountDeleteDialogComponent,
    appAccountRoute,
    appAccountPopupRoute,
    AppAccountResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...appAccountRoute,
    ...appAccountPopupRoute,
];

@NgModule({
    imports: [
        LeadletApiSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        AppAccountComponent,
        AppAccountDetailComponent,
        AppAccountDialogComponent,
        AppAccountDeleteDialogComponent,
        AppAccountPopupComponent,
        AppAccountDeletePopupComponent,
    ],
    entryComponents: [
        AppAccountComponent,
        AppAccountDialogComponent,
        AppAccountPopupComponent,
        AppAccountDeleteDialogComponent,
        AppAccountDeletePopupComponent,
    ],
    providers: [
        AppAccountService,
        AppAccountPopupService,
        AppAccountResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LeadletApiAppAccountModule {}
