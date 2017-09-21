import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ContactEmail } from './contact-email.model';
import { ContactEmailPopupService } from './contact-email-popup.service';
import { ContactEmailService } from './contact-email.service';
import { Contact, ContactService } from '../contact';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-contact-email-dialog',
    templateUrl: './contact-email-dialog.component.html'
})
export class ContactEmailDialogComponent implements OnInit {

    contactEmail: ContactEmail;
    isSaving: boolean;

    contacts: Contact[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private contactEmailService: ContactEmailService,
        private contactService: ContactService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.contactService.query()
            .subscribe((res: ResponseWrapper) => { this.contacts = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.contactEmail.id !== undefined) {
            this.subscribeToSaveResponse(
                this.contactEmailService.update(this.contactEmail));
        } else {
            this.subscribeToSaveResponse(
                this.contactEmailService.create(this.contactEmail));
        }
    }

    private subscribeToSaveResponse(result: Observable<ContactEmail>) {
        result.subscribe((res: ContactEmail) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: ContactEmail) {
        this.eventManager.broadcast({ name: 'contactEmailListModification', content: 'OK'});
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
}

@Component({
    selector: 'jhi-contact-email-popup',
    template: ''
})
export class ContactEmailPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private contactEmailPopupService: ContactEmailPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.contactEmailPopupService
                    .open(ContactEmailDialogComponent as Component, params['id']);
            } else {
                this.contactEmailPopupService
                    .open(ContactEmailDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
