import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { CompanySubscriptionPlan } from './company-subscription-plan.model';
import { CompanySubscriptionPlanService } from './company-subscription-plan.service';

@Component({
    selector: 'jhi-company-subscription-plan-detail',
    templateUrl: './company-subscription-plan-detail.component.html'
})
export class CompanySubscriptionPlanDetailComponent implements OnInit, OnDestroy {

    companySubscriptionPlan: CompanySubscriptionPlan;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private companySubscriptionPlanService: CompanySubscriptionPlanService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCompanySubscriptionPlans();
    }

    load(id) {
        this.companySubscriptionPlanService.find(id).subscribe((companySubscriptionPlan) => {
            this.companySubscriptionPlan = companySubscriptionPlan;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCompanySubscriptionPlans() {
        this.eventSubscriber = this.eventManager.subscribe(
            'companySubscriptionPlanListModification',
            (response) => this.load(this.companySubscriptionPlan.id)
        );
    }
}
