import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Deal } from './deal.model';
import { DealPopupService } from './deal-popup.service';
import { DealService } from './deal.service';
import { PipelineStage, PipelineStageService } from '../pipeline-stage';
import { Contact, ContactService } from '../contact';
import { AppAccount, AppAccountService } from '../app-account';
import { AppUser, AppUserService } from '../app-user';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-deal-dialog',
    templateUrl: './deal-dialog.component.html'
})
export class DealDialogComponent implements OnInit {

    deal: Deal;
    isSaving: boolean;

    pipelinestages: PipelineStage[];

    contacts: Contact[];

    appaccounts: AppAccount[];

    appusers: AppUser[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private dealService: DealService,
        private pipelineStageService: PipelineStageService,
        private contactService: ContactService,
        private appAccountService: AppAccountService,
        private appUserService: AppUserService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.pipelineStageService.query()
            .subscribe((res: ResponseWrapper) => { this.pipelinestages = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
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
        if (this.deal.id !== undefined) {
            this.subscribeToSaveResponse(
                this.dealService.update(this.deal));
        } else {
            this.subscribeToSaveResponse(
                this.dealService.create(this.deal));
        }
    }

    private subscribeToSaveResponse(result: Observable<Deal>) {
        result.subscribe((res: Deal) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Deal) {
        this.eventManager.broadcast({ name: 'dealListModification', content: 'OK'});
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

    trackPipelineStageById(index: number, item: PipelineStage) {
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
    selector: 'jhi-deal-popup',
    template: ''
})
export class DealPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private dealPopupService: DealPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.dealPopupService
                    .open(DealDialogComponent as Component, params['id']);
            } else {
                this.dealPopupService
                    .open(DealDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
