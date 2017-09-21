import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { AppAccount } from './app-account.model';
import { AppAccountService } from './app-account.service';

@Component({
    selector: 'jhi-app-account-detail',
    templateUrl: './app-account-detail.component.html'
})
export class AppAccountDetailComponent implements OnInit, OnDestroy {

    appAccount: AppAccount;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private appAccountService: AppAccountService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInAppAccounts();
    }

    load(id) {
        this.appAccountService.find(id).subscribe((appAccount) => {
            this.appAccount = appAccount;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInAppAccounts() {
        this.eventSubscriber = this.eventManager.subscribe(
            'appAccountListModification',
            (response) => this.load(this.appAccount.id)
        );
    }
}
