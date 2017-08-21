import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { AppAccount } from './app-account.model';
import { AppAccountPopupService } from './app-account-popup.service';
import { AppAccountService } from './app-account.service';

@Component({
    selector: 'jhi-app-account-dialog',
    templateUrl: './app-account-dialog.component.html'
})
export class AppAccountDialogComponent implements OnInit {

    appAccount: AppAccount;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private appAccountService: AppAccountService,
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
        if (this.appAccount.id !== undefined) {
            this.subscribeToSaveResponse(
                this.appAccountService.update(this.appAccount));
        } else {
            this.subscribeToSaveResponse(
                this.appAccountService.create(this.appAccount));
        }
    }

    private subscribeToSaveResponse(result: Observable<AppAccount>) {
        result.subscribe((res: AppAccount) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: AppAccount) {
        this.eventManager.broadcast({ name: 'appAccountListModification', content: 'OK'});
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
    selector: 'jhi-app-account-popup',
    template: ''
})
export class AppAccountPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private appAccountPopupService: AppAccountPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.appAccountPopupService
                    .open(AppAccountDialogComponent as Component, params['id']);
            } else {
                this.appAccountPopupService
                    .open(AppAccountDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
