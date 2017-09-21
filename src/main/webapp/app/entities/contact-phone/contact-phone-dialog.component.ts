import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ContactPhone } from './contact-phone.model';
import { ContactPhonePopupService } from './contact-phone-popup.service';
import { ContactPhoneService } from './contact-phone.service';
import { Contact, ContactService } from '../contact';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-contact-phone-dialog',
    templateUrl: './contact-phone-dialog.component.html'
})
export class ContactPhoneDialogComponent implements OnInit {

    contactPhone: ContactPhone;
    isSaving: boolean;

    contacts: Contact[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private contactPhoneService: ContactPhoneService,
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
        if (this.contactPhone.id !== undefined) {
            this.subscribeToSaveResponse(
                this.contactPhoneService.update(this.contactPhone));
        } else {
            this.subscribeToSaveResponse(
                this.contactPhoneService.create(this.contactPhone));
        }
    }

    private subscribeToSaveResponse(result: Observable<ContactPhone>) {
        result.subscribe((res: ContactPhone) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: ContactPhone) {
        this.eventManager.broadcast({ name: 'contactPhoneListModification', content: 'OK'});
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
    selector: 'jhi-contact-phone-popup',
    template: ''
})
export class ContactPhonePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private contactPhonePopupService: ContactPhonePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.contactPhonePopupService
                    .open(ContactPhoneDialogComponent as Component, params['id']);
            } else {
                this.contactPhonePopupService
                    .open(ContactPhoneDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
