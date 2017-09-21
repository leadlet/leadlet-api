import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { SubscriptionPlan } from './subscription-plan.model';
import { SubscriptionPlanPopupService } from './subscription-plan-popup.service';
import { SubscriptionPlanService } from './subscription-plan.service';

@Component({
    selector: 'jhi-subscription-plan-dialog',
    templateUrl: './subscription-plan-dialog.component.html'
})
export class SubscriptionPlanDialogComponent implements OnInit {

    subscriptionPlan: SubscriptionPlan;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private subscriptionPlanService: SubscriptionPlanService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.subscriptionPlan.id !== undefined) {
            this.subscribeToSaveResponse(
                this.subscriptionPlanService.update(this.subscriptionPlan));
        } else {
            this.subscribeToSaveResponse(
                this.subscriptionPlanService.create(this.subscriptionPlan));
        }
    }

    private subscribeToSaveResponse(result: Observable<SubscriptionPlan>) {
        result.subscribe((res: SubscriptionPlan) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: SubscriptionPlan) {
        this.eventManager.broadcast({ name: 'subscriptionPlanListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-subscription-plan-popup',
    template: ''
})
export class SubscriptionPlanPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private subscriptionPlanPopupService: SubscriptionPlanPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.subscriptionPlanPopupService
                    .open(SubscriptionPlanDialogComponent as Component, params['id']);
            } else {
                this.subscriptionPlanPopupService
                    .open(SubscriptionPlanDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
