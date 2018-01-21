import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { MeanOfContact } from './mean-of-contact.model';
import { MeanOfContactService } from './mean-of-contact.service';

@Component({
    selector: 'jhi-mean-of-contact-detail',
    templateUrl: './mean-of-contact-detail.component.html'
})
export class MeanOfContactDetailComponent implements OnInit, OnDestroy {

    meanOfContact: MeanOfContact;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private meanOfContactService: MeanOfContactService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInMeanOfContacts();
    }

    load(id) {
        this.meanOfContactService.find(id).subscribe((meanOfContact) => {
            this.meanOfContact = meanOfContact;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInMeanOfContacts() {
        this.eventSubscriber = this.eventManager.subscribe(
            'meanOfContactListModification',
            (response) => this.load(this.meanOfContact.id)
        );
    }
}
