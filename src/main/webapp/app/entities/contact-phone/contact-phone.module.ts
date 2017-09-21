import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LeadletApiSharedModule } from '../../shared';
import {
    ContactPhoneService,
    ContactPhonePopupService,
    ContactPhoneComponent,
    ContactPhoneDetailComponent,
    ContactPhoneDialogComponent,
    ContactPhonePopupComponent,
    ContactPhoneDeletePopupComponent,
    ContactPhoneDeleteDialogComponent,
    contactPhoneRoute,
    contactPhonePopupRoute,
    ContactPhoneResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...contactPhoneRoute,
    ...contactPhonePopupRoute,
];

@NgModule({
    imports: [
        LeadletApiSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ContactPhoneComponent,
        ContactPhoneDetailComponent,
        ContactPhoneDialogComponent,
        ContactPhoneDeleteDialogComponent,
        ContactPhonePopupComponent,
        ContactPhoneDeletePopupComponent,
    ],
    entryComponents: [
        ContactPhoneComponent,
        ContactPhoneDialogComponent,
        ContactPhonePopupComponent,
        ContactPhoneDeleteDialogComponent,
        ContactPhoneDeletePopupComponent,
    ],
    providers: [
        ContactPhoneService,
        ContactPhonePopupService,
        ContactPhoneResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LeadletApiContactPhoneModule {}
