import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LeadletApiSharedModule } from '../../shared';
import {
    EmailTemplatesService,
    EmailTemplatesPopupService,
    EmailTemplatesComponent,
    EmailTemplatesDetailComponent,
    EmailTemplatesDialogComponent,
    EmailTemplatesPopupComponent,
    EmailTemplatesDeletePopupComponent,
    EmailTemplatesDeleteDialogComponent,
    emailTemplatesRoute,
    emailTemplatesPopupRoute,
    EmailTemplatesResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...emailTemplatesRoute,
    ...emailTemplatesPopupRoute,
];

@NgModule({
    imports: [
        LeadletApiSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        EmailTemplatesComponent,
        EmailTemplatesDetailComponent,
        EmailTemplatesDialogComponent,
        EmailTemplatesDeleteDialogComponent,
        EmailTemplatesPopupComponent,
        EmailTemplatesDeletePopupComponent,
    ],
    entryComponents: [
        EmailTemplatesComponent,
        EmailTemplatesDialogComponent,
        EmailTemplatesPopupComponent,
        EmailTemplatesDeleteDialogComponent,
        EmailTemplatesDeletePopupComponent,
    ],
    providers: [
        EmailTemplatesService,
        EmailTemplatesPopupService,
        EmailTemplatesResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LeadletApiEmailTemplatesModule {}
