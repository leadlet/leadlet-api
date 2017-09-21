import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ContactPhone } from './contact-phone.model';
import { ContactPhonePopupService } from './contact-phone-popup.service';
import { ContactPhoneService } from './contact-phone.service';

@Component({
    selector: 'jhi-contact-phone-delete-dialog',
    templateUrl: './contact-phone-delete-dialog.component.html'
})
export class ContactPhoneDeleteDialogComponent {

    contactPhone: ContactPhone;

    constructor(
        private contactPhoneService: ContactPhoneService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.contactPhoneService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'contactPhoneListModification',
                content: 'Deleted an contactPhone'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-contact-phone-delete-popup',
    template: ''
})
export class ContactPhoneDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private contactPhonePopupService: ContactPhonePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.contactPhonePopupService
                .open(ContactPhoneDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
