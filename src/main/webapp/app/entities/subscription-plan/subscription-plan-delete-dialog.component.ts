import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { SubscriptionPlan } from './subscription-plan.model';
import { SubscriptionPlanPopupService } from './subscription-plan-popup.service';
import { SubscriptionPlanService } from './subscription-plan.service';

@Component({
    selector: 'jhi-subscription-plan-delete-dialog',
    templateUrl: './subscription-plan-delete-dialog.component.html'
})
export class SubscriptionPlanDeleteDialogComponent {

    subscriptionPlan: SubscriptionPlan;

    constructor(
        private subscriptionPlanService: SubscriptionPlanService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.subscriptionPlanService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'subscriptionPlanListModification',
                content: 'Deleted an subscriptionPlan'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-subscription-plan-delete-popup',
    template: ''
})
export class SubscriptionPlanDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private subscriptionPlanPopupService: SubscriptionPlanPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.subscriptionPlanPopupService
                .open(SubscriptionPlanDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
