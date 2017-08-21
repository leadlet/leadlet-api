import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { LeadletApiTeamModule } from './team/team.module';
import { LeadletApiAppAccountModule } from './app-account/app-account.module';
import { LeadletApiSubscriptionPlanModule } from './subscription-plan/subscription-plan.module';
import { LeadletApiCompanySubscriptionPlanModule } from './company-subscription-plan/company-subscription-plan.module';
import { LeadletApiEmailTemplatesModule } from './email-templates/email-templates.module';
import { LeadletApiObjectiveModule } from './objective/objective.module';
import { LeadletApiPipelineModule } from './pipeline/pipeline.module';
import { LeadletApiStageModule } from './stage/stage.module';
import { LeadletApiPipelineStageModule } from './pipeline-stage/pipeline-stage.module';
import { LeadletApiContactModule } from './contact/contact.module';
import { LeadletApiDocumentModule } from './document/document.module';
import { LeadletApiContactEmailModule } from './contact-email/contact-email.module';
import { LeadletApiContactPhoneModule } from './contact-phone/contact-phone.module';
import { LeadletApiDealModule } from './deal/deal.module';
import { LeadletApiActivityModule } from './activity/activity.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        LeadletApiTeamModule,
        LeadletApiAppAccountModule,
        LeadletApiSubscriptionPlanModule,
        LeadletApiCompanySubscriptionPlanModule,
        LeadletApiEmailTemplatesModule,
        LeadletApiObjectiveModule,
        LeadletApiPipelineModule,
        LeadletApiStageModule,
        LeadletApiPipelineStageModule,
        LeadletApiContactModule,
        LeadletApiDocumentModule,
        LeadletApiContactEmailModule,
        LeadletApiContactPhoneModule,
        LeadletApiDealModule,
        LeadletApiActivityModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LeadletApiEntityModule {}
