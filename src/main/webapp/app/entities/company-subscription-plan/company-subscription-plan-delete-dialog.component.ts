import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { CompanySubscriptionPlan } from './company-subscription-plan.model';
import { CompanySubscriptionPlanPopupService } from './company-subscription-plan-popup.service';
import { CompanySubscriptionPlanService } from './company-subscription-plan.service';

@Component({
    selector: 'jhi-company-subscription-plan-delete-dialog',
    templateUrl: './company-subscription-plan-delete-dialog.component.html'
})
export class CompanySubscriptionPlanDeleteDialogComponent {

    companySubscriptionPlan: CompanySubscriptionPlan;

    constructor(
        private companySubscriptionPlanService: CompanySubscriptionPlanService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.companySubscriptionPlanService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'companySubscriptionPlanListModification',
                content: 'Deleted an companySubscriptionPlan'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-company-subscription-plan-delete-popup',
    template: ''
})
export class CompanySubscriptionPlanDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private companySubscriptionPlanPopupService: CompanySubscriptionPlanPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.companySubscriptionPlanPopupService
                .open(CompanySubscriptionPlanDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
