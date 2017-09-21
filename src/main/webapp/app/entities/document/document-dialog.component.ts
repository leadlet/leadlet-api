import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Document } from './document.model';
import { DocumentPopupService } from './document-popup.service';
import { DocumentService } from './document.service';
import { Contact, ContactService } from '../contact';
import { AppAccount, AppAccountService } from '../app-account';
import { Activity, ActivityService } from '../activity';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-document-dialog',
    templateUrl: './document-dialog.component.html'
})
export class DocumentDialogComponent implements OnInit {

    document: Document;
    isSaving: boolean;

    contacts: Contact[];

    appaccounts: AppAccount[];

    activities: Activity[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private documentService: DocumentService,
        private contactService: ContactService,
        private appAccountService: AppAccountService,
        private activityService: ActivityService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.contactService.query()
            .subscribe((res: ResponseWrapper) => { this.contacts = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.appAccountService.query()
            .subscribe((res: ResponseWrapper) => { this.appaccounts = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.activityService.query()
            .subscribe((res: ResponseWrapper) => { this.activities = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.document.id !== undefined) {
            this.subscribeToSaveResponse(
                this.documentService.update(this.document));
        } else {
            this.subscribeToSaveResponse(
                this.documentService.create(this.document));
        }
    }

    private subscribeToSaveResponse(result: Observable<Document>) {
        result.subscribe((res: Document) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Document) {
        this.eventManager.broadcast({ name: 'documentListModification', content: 'OK'});
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

    trackContactById(index: number, item: Contact) {
        return item.id;
    }

    trackAppAccountById(index: number, item: AppAccount) {
        return item.id;
    }

    trackActivityById(index: number, item: Activity) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-document-popup',
    template: ''
})
export class DocumentPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private documentPopupService: DocumentPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.documentPopupService
                    .open(DocumentDialogComponent as Component, params['id']);
            } else {
                this.documentPopupService
                    .open(DocumentDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
