import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LeadletApiSharedModule } from '../../shared';
import {
    CompanySubscriptionPlanService,
    CompanySubscriptionPlanPopupService,
    CompanySubscriptionPlanComponent,
    CompanySubscriptionPlanDetailComponent,
    CompanySubscriptionPlanDialogComponent,
    CompanySubscriptionPlanPopupComponent,
    CompanySubscriptionPlanDeletePopupComponent,
    CompanySubscriptionPlanDeleteDialogComponent,
    companySubscriptionPlanRoute,
    companySubscriptionPlanPopupRoute,
    CompanySubscriptionPlanResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...companySubscriptionPlanRoute,
    ...companySubscriptionPlanPopupRoute,
];

@NgModule({
    imports: [
        LeadletApiSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        CompanySubscriptionPlanComponent,
        CompanySubscriptionPlanDetailComponent,
        CompanySubscriptionPlanDialogComponent,
        CompanySubscriptionPlanDeleteDialogComponent,
        CompanySubscriptionPlanPopupComponent,
        CompanySubscriptionPlanDeletePopupComponent,
    ],
    entryComponents: [
        CompanySubscriptionPlanComponent,
        CompanySubscriptionPlanDialogComponent,
        CompanySubscriptionPlanPopupComponent,
        CompanySubscriptionPlanDeleteDialogComponent,
        CompanySubscriptionPlanDeletePopupComponent,
    ],
    providers: [
        CompanySubscriptionPlanService,
        CompanySubscriptionPlanPopupService,
        CompanySubscriptionPlanResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LeadletApiCompanySubscriptionPlanModule {}
