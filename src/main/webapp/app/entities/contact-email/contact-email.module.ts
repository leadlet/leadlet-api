import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LeadletApiSharedModule } from '../../shared';
import {
    ContactEmailService,
    ContactEmailPopupService,
    ContactEmailComponent,
    ContactEmailDetailComponent,
    ContactEmailDialogComponent,
    ContactEmailPopupComponent,
    ContactEmailDeletePopupComponent,
    ContactEmailDeleteDialogComponent,
    contactEmailRoute,
    contactEmailPopupRoute,
    ContactEmailResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...contactEmailRoute,
    ...contactEmailPopupRoute,
];

@NgModule({
    imports: [
        LeadletApiSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ContactEmailComponent,
        ContactEmailDetailComponent,
        ContactEmailDialogComponent,
        ContactEmailDeleteDialogComponent,
        ContactEmailPopupComponent,
        ContactEmailDeletePopupComponent,
    ],
    entryComponents: [
        ContactEmailComponent,
        ContactEmailDialogComponent,
        ContactEmailPopupComponent,
        ContactEmailDeleteDialogComponent,
        ContactEmailDeletePopupComponent,
    ],
    providers: [
        ContactEmailService,
        ContactEmailPopupService,
        ContactEmailResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LeadletApiContactEmailModule {}
