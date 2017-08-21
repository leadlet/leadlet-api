import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { ContactEmail } from './contact-email.model';
import { ContactEmailService } from './contact-email.service';

@Component({
    selector: 'jhi-contact-email-detail',
    templateUrl: './contact-email-detail.component.html'
})
export class ContactEmailDetailComponent implements OnInit, OnDestroy {

    contactEmail: ContactEmail;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private contactEmailService: ContactEmailService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInContactEmails();
    }

    load(id) {
        this.contactEmailService.find(id).subscribe((contactEmail) => {
            this.contactEmail = contactEmail;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInContactEmails() {
        this.eventSubscriber = this.eventManager.subscribe(
            'contactEmailListModification',
            (response) => this.load(this.contactEmail.id)
        );
    }
}
