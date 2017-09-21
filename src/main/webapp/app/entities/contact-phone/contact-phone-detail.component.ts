import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { ContactPhone } from './contact-phone.model';
import { ContactPhoneService } from './contact-phone.service';

@Component({
    selector: 'jhi-contact-phone-detail',
    templateUrl: './contact-phone-detail.component.html'
})
export class ContactPhoneDetailComponent implements OnInit, OnDestroy {

    contactPhone: ContactPhone;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private contactPhoneService: ContactPhoneService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInContactPhones();
    }

    load(id) {
        this.contactPhoneService.find(id).subscribe((contactPhone) => {
            this.contactPhone = contactPhone;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInContactPhones() {
        this.eventSubscriber = this.eventManager.subscribe(
            'contactPhoneListModification',
            (response) => this.load(this.contactPhone.id)
        );
    }
}
