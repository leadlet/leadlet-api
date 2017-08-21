import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Activity } from './activity.model';
import { ActivityPopupService } from './activity-popup.service';
import { ActivityService } from './activity.service';
import { Deal, DealService } from '../deal';
import { Contact, ContactService } from '../contact';
import { AppAccount, AppAccountService } from '../app-account';
import { AppUser, AppUserService } from '../app-user';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-activity-dialog',
    templateUrl: './activity-dialog.component.html'
})
export class ActivityDialogComponent implements OnInit {

    activity: Activity;
    isSaving: boolean;

    deals: Deal[];

    contacts: Contact[];

    appaccounts: AppAccount[];

    appusers: AppUser[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private activityService: ActivityService,
        private dealService: DealService,
        private contactService: ContactService,
        private appAccountService: AppAccountService,
        private appUserService: AppUserService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.dealService.query()
            .subscribe((res: ResponseWrapper) => { this.deals = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.contactService.query()
            .subscribe((res: ResponseWrapper) => { this.contacts = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.appAccountService.query()
            .subscribe((res: ResponseWrapper) => { this.appaccounts = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.appUserService.query()
            .subscribe((res: ResponseWrapper) => { this.appusers = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.activity.id !== undefined) {
            this.subscribeToSaveResponse(
                this.activityService.update(this.activity));
        } else {
            this.subscribeToSaveResponse(
                this.activityService.create(this.activity));
        }
    }

    private subscribeToSaveResponse(result: Observable<Activity>) {
        result.subscribe((res: Activity) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Activity) {
        this.eventManager.broadcast({ name: 'activityListModification', content: 'OK'});
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

    trackDealById(index: number, item: Deal) {
        return item.id;
    }

    trackContactById(index: number, item: Contact) {
        return item.id;
    }

    trackAppAccountById(index: number, item: AppAccount) {
        return item.id;
    }

    trackAppUserById(index: number, item: AppUser) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-activity-popup',
    template: ''
})
export class ActivityPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private activityPopupService: ActivityPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.activityPopupService
                    .open(ActivityDialogComponent as Component, params['id']);
            } else {
                this.activityPopupService
                    .open(ActivityDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
