import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { SubscriptionPlan } from './subscription-plan.model';
import { SubscriptionPlanService } from './subscription-plan.service';

@Component({
    selector: 'jhi-subscription-plan-detail',
    templateUrl: './subscription-plan-detail.component.html'
})
export class SubscriptionPlanDetailComponent implements OnInit, OnDestroy {

    subscriptionPlan: SubscriptionPlan;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private subscriptionPlanService: SubscriptionPlanService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInSubscriptionPlans();
    }

    load(id) {
        this.subscriptionPlanService.find(id).subscribe((subscriptionPlan) => {
            this.subscriptionPlan = subscriptionPlan;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInSubscriptionPlans() {
        this.eventSubscriber = this.eventManager.subscribe(
            'subscriptionPlanListModification',
            (response) => this.load(this.subscriptionPlan.id)
        );
    }
}
