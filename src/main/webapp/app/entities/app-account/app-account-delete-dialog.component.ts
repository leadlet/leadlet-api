import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { AppAccount } from './app-account.model';
import { AppAccountPopupService } from './app-account-popup.service';
import { AppAccountService } from './app-account.service';

@Component({
    selector: 'jhi-app-account-delete-dialog',
    templateUrl: './app-account-delete-dialog.component.html'
})
export class AppAccountDeleteDialogComponent {

    appAccount: AppAccount;

    constructor(
        private appAccountService: AppAccountService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.appAccountService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'appAccountListModification',
                content: 'Deleted an appAccount'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-app-account-delete-popup',
    template: ''
})
export class AppAccountDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private appAccountPopupService: AppAccountPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.appAccountPopupService
                .open(AppAccountDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
