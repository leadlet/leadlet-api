import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { CompanySubscriptionPlan } from './company-subscription-plan.model';
import { CompanySubscriptionPlanService } from './company-subscription-plan.service';

@Injectable()
export class CompanySubscriptionPlanPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private companySubscriptionPlanService: CompanySubscriptionPlanService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.companySubscriptionPlanService.find(id).subscribe((companySubscriptionPlan) => {
                    companySubscriptionPlan.startDate = this.datePipe
                        .transform(companySubscriptionPlan.startDate, 'yyyy-MM-ddThh:mm');
                    companySubscriptionPlan.endDate = this.datePipe
                        .transform(companySubscriptionPlan.endDate, 'yyyy-MM-ddThh:mm');
                    this.ngbModalRef = this.companySubscriptionPlanModalRef(component, companySubscriptionPlan);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.companySubscriptionPlanModalRef(component, new CompanySubscriptionPlan());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    companySubscriptionPlanModalRef(component: Component, companySubscriptionPlan: CompanySubscriptionPlan): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.companySubscriptionPlan = companySubscriptionPlan;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
