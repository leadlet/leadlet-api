import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { CompanySubscriptionPlan } from './company-subscription-plan.model';
import { CompanySubscriptionPlanPopupService } from './company-subscription-plan-popup.service';
import { CompanySubscriptionPlanService } from './company-subscription-plan.service';
import { AppAccount, AppAccountService } from '../app-account';
import { SubscriptionPlan, SubscriptionPlanService } from '../subscription-plan';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-company-subscription-plan-dialog',
    templateUrl: './company-subscription-plan-dialog.component.html'
})
export class CompanySubscriptionPlanDialogComponent implements OnInit {

    companySubscriptionPlan: CompanySubscriptionPlan;
    isSaving: boolean;

    appaccounts: AppAccount[];

    subscriptionplans: SubscriptionPlan[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private companySubscriptionPlanService: CompanySubscriptionPlanService,
        private appAccountService: AppAccountService,
        private subscriptionPlanService: SubscriptionPlanService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.appAccountService.query()
            .subscribe((res: ResponseWrapper) => { this.appaccounts = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.subscriptionPlanService.query()
            .subscribe((res: ResponseWrapper) => { this.subscriptionplans = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.companySubscriptionPlan.id !== undefined) {
            this.subscribeToSaveResponse(
                this.companySubscriptionPlanService.update(this.companySubscriptionPlan));
        } else {
            this.subscribeToSaveResponse(
                this.companySubscriptionPlanService.create(this.companySubscriptionPlan));
        }
    }

    private subscribeToSaveResponse(result: Observable<CompanySubscriptionPlan>) {
        result.subscribe((res: CompanySubscriptionPlan) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: CompanySubscriptionPlan) {
        this.eventManager.broadcast({ name: 'companySubscriptionPlanListModification', content: 'OK'});
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

    trackAppAccountById(index: number, item: AppAccount) {
        return item.id;
    }

    trackSubscriptionPlanById(index: number, item: SubscriptionPlan) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-company-subscription-plan-popup',
    template: ''
})
export class CompanySubscriptionPlanPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private companySubscriptionPlanPopupService: CompanySubscriptionPlanPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.companySubscriptionPlanPopupService
                    .open(CompanySubscriptionPlanDialogComponent as Component, params['id']);
            } else {
                this.companySubscriptionPlanPopupService
                    .open(CompanySubscriptionPlanDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
