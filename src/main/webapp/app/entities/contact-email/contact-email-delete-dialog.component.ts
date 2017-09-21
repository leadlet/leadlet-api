import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ContactEmail } from './contact-email.model';
import { ContactEmailPopupService } from './contact-email-popup.service';
import { ContactEmailService } from './contact-email.service';

@Component({
    selector: 'jhi-contact-email-delete-dialog',
    templateUrl: './contact-email-delete-dialog.component.html'
})
export class ContactEmailDeleteDialogComponent {

    contactEmail: ContactEmail;

    constructor(
        private contactEmailService: ContactEmailService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.contactEmailService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'contactEmailListModification',
                content: 'Deleted an contactEmail'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-contact-email-delete-popup',
    template: ''
})
export class ContactEmailDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private contactEmailPopupService: ContactEmailPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.contactEmailPopupService
                .open(ContactEmailDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
